package net.derppickles.superstructure.entity.custom;

import net.derppickles.superstructure.entity.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BuriedTrapEntity extends Monster {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(BuriedTrapEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean wasAttacking;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    public BuriedTrapEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 0;
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 0, true, true, null));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, 0, true, true, null));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Villager.class, 0, true, true, null));

        this.goalSelector.addGoal(1, new BuriedTrapEntityAttackGoal());
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACKING, false);
        wasAttacking = false;
    }

    public void triggerAttackAnimation() {
        this.entityData.set(ATTACKING, true);
    }

    @Override
    public Vec3 getDeltaMovement() {
        return Vec3.ZERO;
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel level, DamageSource source) {
        return source.is(DamageTypes.IN_WALL) || super.isInvulnerableTo(level, source);
    }

    @Override
    public int getMaxHeadYRot() {
        boolean isAttacking = this.entityData.get(ATTACKING);
        return isAttacking ? 0 : 40;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.0F)
                .add(Attributes.ATTACK_KNOCKBACK, 0.0F)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 2.0);
    }

    private boolean canStayAt(final BlockPos target) {
        return this.level().loadedAndEntityCanStandOnFace(target.relative(Direction.DOWN), this, Direction.UP);
    }

    @Override
    public void setPos(final double x, final double y, final double z) {
        if (this.isPassenger()) {
            super.setPos(x, y, z);
        } else {
            super.setPos(Mth.floor(x) + 0.5, Mth.floor(y + 0.5), Mth.floor(z) + 0.5);
        }
    }

    @Override
    protected boolean canRide(Entity vehicle) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && !this.isPassenger() && !this.canStayAt(this.blockPosition())) {
            this.convertTo(
                    ModEntityTypes.BURIED,
                    ConversionParams.single(this, false, false),
                    (buriedEntity) -> buriedEntity.setPos(buriedEntity.position().subtract(0, 1, 0))
            );
        }

        if (this.entityData.get(ATTACKING)) {
            if (!wasAttacking) {
                this.wasAttacking = true;
                this.idleAnimationState.stop();
                this.attackAnimationState.start(this.tickCount);
            }
        } else {
            this.idleAnimationState.startIfStopped(this.tickCount);
        }
    }

    private class BuriedTrapEntityAttackGoal extends Goal {
        private final BuriedTrapEntity buriedTrap;
        private final int ATTACK_PERIOD = 10;
        private final int DELAY_PERIOD = 2;
        private int attackTime;
        private int delayTime;
        private boolean attackActive;
        private Vec3 targetPos0;


        public BuriedTrapEntityAttackGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
            buriedTrap = BuriedTrapEntity.this;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = BuriedTrapEntity.this.getTarget();
            return target != null && target.isAlive();
        }

        @Override
        public void start() {
            buriedTrap.setAggressive(true);
        }

        @Override
        public void stop() {
            buriedTrap.setAggressive(false);
            if (attackActive) buriedTrap.discard();
        }

        @Override
        public void tick() {
            if (attackActive) {
                if (delayTime < DELAY_PERIOD) {
                    delayTime++;
                } else {
                    this.attackTime++;
                }
            }
            if (attackTime >= ATTACK_PERIOD) {
                buriedTrap.discard();
            }
            LivingEntity target = buriedTrap.getTarget();
            if (target != null) {
                buriedTrap.getLookControl().setLookAt(target, 30.0F, 30.0F);
                if (attackActive) {
                    if (delayTime >= DELAY_PERIOD) {
                        float distsq = buriedTrap.distanceTo(target);
                        if (distsq <= 2.0 * 2.0) {
                            buriedTrap.lookAt(target, 30.0F, 30.0F);
                            Vec3 undergroundPos = buriedTrap.position().subtract(0, 2.5F, 0)
                                    .add(buriedTrap.getLookAngle().multiply(0.2F, 0.2F, 0.2F));
                            float t = attackTime / (float) ATTACK_PERIOD;
                            float s = Math.min(3.0F * t, 1.0F);
                            Vec3 newTargetPos = (undergroundPos.subtract(targetPos0)).multiply(s, t, s).add(targetPos0);
                            target.teleportTo(newTargetPos.x, newTargetPos.y, newTargetPos.z);
                        }
                    }
                } else {
                    if (buriedTrap.position().y <= target.position().y && buriedTrap.isWithinMeleeAttackRange(target) && !(
                            target.getLastHurtByMob() != null
                                    && target.getLastHurtByMob().getClass() == BuriedTrapEntity.class
                                    && target.getLastHurtByMobTimestamp() - target.tickCount < 2 * ATTACK_PERIOD
                    )) {
                        buriedTrap.swing(InteractionHand.MAIN_HAND);
                        buriedTrap.doHurtTarget(getServerLevel(buriedTrap), target);
                        attackActive = true;
                        attackTime = 0;
                        targetPos0 = target.position();
                        buriedTrap.triggerAttackAnimation();
                    }
                }
            }
        }
    }


}
