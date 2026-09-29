package net.derppickles.superstructure.entity.custom;

import net.derppickles.superstructure.entity.ModEntityTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class BuriedEntity extends Monster {
    public final AnimationState idleAnimationState = new AnimationState();

    private final WeightedList<Item[]> SPAWN_EQUIPMENT = WeightedList.<Item[]>builder()
            .add(new Item[]{Items.IRON_HELMET, null, null, null}, 1)
            .add(new Item[]{Items.IRON_HELMET, null, null, Items.LEATHER_BOOTS}, 1)
            .add(new Item[]{null, null, null, Items.LEATHER_BOOTS}, 1)
            .add(new Item[]{null, Items.CHAINMAIL_CHESTPLATE, null, null}, 2)
            .add(new Item[]{null, Items.IRON_CHESTPLATE, null, null}, 2)
            .add(new Item[]{Items.IRON_HELMET, null, null, Items.IRON_BOOTS}, 2)
            .add(new Item[]{Items.LEATHER_HELMET, Items.IRON_CHESTPLATE, null, Items.LEATHER_BOOTS}, 2)
            .add(new Item[]{Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.IRON_BOOTS}, 1)
            .add(new Item[]{null, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.IRON_BOOTS}, 2)
            .add(new Item[]{null, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS}, 1)
            .build();

    private final WeightedList<Item> SPAWN_WEAPON = WeightedList.<Item>builder()
            .add(Items.IRON_AXE, 1)
            .add(Items.IRON_SHOVEL, 8)
            .add(Items.IRON_PICKAXE, 8)
            .add(Items.IRON_SWORD, 3)
            .build();

    public BuriedEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 0;
        this.setCanPickUpLoot(true);
    }

    public BuriedEntity(Level world) {
        this(ModEntityTypes.BURIED, world);
    }

    @Override
    public void tick() {
        super.tick();
        this.idleAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(
            final ServerLevelAccessor level, final DifficultyInstance difficulty, final EntitySpawnReason spawnReason, final @Nullable SpawnGroupData groupData
    ) {
        if (spawnReason != EntitySpawnReason.CONVERSION) {
            RandomSource random = level.getRandom();
            this.populateDefaultEquipmentSlots(random, difficulty);
            this.populateDefaultWeapon(random, difficulty);
            this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
            this.setDropChance(EquipmentSlot.HEAD, 0.0F);
            this.setDropChance(EquipmentSlot.CHEST, 0.0F);
            this.setDropChance(EquipmentSlot.LEGS, 0.0F);
            this.setDropChance(EquipmentSlot.FEET, 0.0F);
        }
        return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
    }

    protected void populateDefaultWeapon(final RandomSource random, final DifficultyInstance difficulty) {
        Optional<Item> weapon = SPAWN_WEAPON.getRandom(random);

        weapon.ifPresent(item -> this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(item)));
    }

    @Override
    protected void populateDefaultEquipmentSlots(final RandomSource random, final DifficultyInstance difficulty) {
        Optional<Item[]> equipment = SPAWN_EQUIPMENT.getRandom(random);

        equipment.ifPresent(items -> {
            EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
            for (int i = 0; i < 4; i++) {
                if (items[i] != null) this.setItemSlot(slots[i], new ItemStack(items[i]));
            }
        });
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel level, DamageSource source) {
        return source.is(DamageTypes.IN_WALL) || super.isInvulnerableTo(level, source);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Villager.class, true));

        this.goalSelector.addGoal(1, new RestrictSunGoal(this));
        this.goalSelector.addGoal(2, new FleeSunGoal(this, 1.0));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 2.0);
    }

    @Override
    protected void readAdditionalSaveData(final ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setCanPickUpLoot(input.getBooleanOr("CanPickUpLoot", true));
    }
}
