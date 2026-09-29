package net.derppickles.superstructure.attachment.handler;

import net.derppickles.superstructure.Superstructure;
import net.derppickles.superstructure.attachment.ModAttachmentTypes;
import net.derppickles.superstructure.entity.ModEntityTypes;
import net.derppickles.superstructure.entity.custom.BuriedEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class DesertCurseHandler {
    public static final int CONVERSION_ATTEMPTS = 12;
    public static final int MIN_CONVERSION_RADIUS = 2;
    public static final int MAX_CONVERSION_RADIUS = 10;
    public static final Set<Block> CONVERTIBLE_BLOCKS = Set.of(
            Blocks.SANDSTONE, Blocks.SANDSTONE_SLAB, Blocks.SANDSTONE_STAIRS, Blocks.SANDSTONE_WALL,
            Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_SANDSTONE_STAIRS,
            Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CUT_SANDSTONE_SLAB
    );

    public static final float BURIED_SPAWN_ON_BLOCK_BREAK_CHANCE = 0.3F;
    public static final int SPAWN_ATTEMPTS = 12;
    public static final int MIN_SPAWN_RADIUS = 6;
    public static final int MAX_SPAWN_RADIUS = 12;
    public static final float BURIED_TRAP_RATIO = 0.35F;
    public static final Set<Block> BURIED_SPAWN_BLOCKS = Set.of(
      Blocks.SANDSTONE, Blocks.SAND, Blocks.SMOOTH_SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE
    );


    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(DesertCurseHandler::onServerTick);
        PlayerBlockBreakEvents.AFTER.register(DesertCurseHandler::onBlockBreak);
    }

    private static void onServerTick(MinecraftServer server) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (isCurseActive(player)) {
                tickCurse(player);
            }
        }
    }

    private static void onBlockBreak(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (level instanceof ServerLevel serverLevel) {
            if (isCurseActive(player)) {
                RandomSource random = level.getRandom();
                if (random.nextFloat() < BURIED_SPAWN_ON_BLOCK_BREAK_CHANCE
                        && BURIED_SPAWN_BLOCKS.contains(state.getBlock())
                        && BURIED_SPAWN_BLOCKS.contains(level.getBlockState(pos.below()).getBlock())
                ) {
                    BuriedEntity buried = ModEntityTypes.BURIED.create(level, EntitySpawnReason.TRIGGERED);
                    if (buried != null) {
                        buried.snapTo(pos.getX() + 0.5F, pos.getY() - 1.0F, pos.getZ() + 0.5F, 0.0F, 0.0F);
                        buried.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(pos), EntitySpawnReason.TRIGGERED, null);
                        level.addFreshEntity(buried);
                    }
                }
            }
        }
    }

    private static void tickCurse(ServerPlayer player) {
        ServerLevel level = player.level();
        BlockPos center = player.blockPosition();
        RandomSource random = level.getRandom();

        if (level.canSeeSky(center) && center.getY() >= 60) {
            Superstructure.LOGGER.info("Curse removed");
            setCurse(player, false);
            return;
        }

        if (player.tickCount % 3 == 0) {
            for (int i = 0; i < CONVERSION_ATTEMPTS; i++) {
                int dx = random.nextInt(MAX_CONVERSION_RADIUS * 2 + 1) - MAX_CONVERSION_RADIUS;
                int dy = random.nextInt(2, MAX_CONVERSION_RADIUS);
                int dz = random.nextInt(MAX_CONVERSION_RADIUS * 2 + 1) - MAX_CONVERSION_RADIUS;
                if ((dx * dx + dy * dy * dz * dz) < MIN_CONVERSION_RADIUS * MIN_CONVERSION_RADIUS) continue;

                BlockPos pos = center.offset(dx, dy, dz);
                BlockState state = level.getBlockState(pos);

                if (isBlockConvertible(pos, state, level)) {
                    level.setBlockAndUpdate(pos, Blocks.SAND.defaultBlockState());
                }
            }
        }

        if (player.tickCount % 30 == 0) {
            for (int i = 0; i < SPAWN_ATTEMPTS; i++) {
                int radius = random.nextInt(MAX_SPAWN_RADIUS - MIN_SPAWN_RADIUS + 1) + MIN_SPAWN_RADIUS;
                double angle = random.nextFloat() * 2.0 * Math.PI;
                int dx = (int) Math.round(radius * Math.cos(angle));
                int dz = (int) Math.round(radius * Math.sin(angle));
                int dy = random.nextInt(7) - 3;

                BlockPos pos = center.offset(dx, dy, dz);
                BlockState state = level.getBlockState(pos);


                if (isSpawnValid(pos, state, level)) {
                    Mob buried;
                    if (random.nextFloat() < BURIED_TRAP_RATIO) {
                        buried = ModEntityTypes.BURIED_TRAP.create(level, EntitySpawnReason.TRIGGERED);
                    } else {
                        buried = ModEntityTypes.BURIED.create(level, EntitySpawnReason.TRIGGERED);
                    }
                    if (buried != null) {
                        buried.snapTo(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, 0.0F, 0.0F);
                        buried.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), EntitySpawnReason.TRIGGERED, null);
                        level.addFreshEntity(buried);
                        break;
                    }
                }
            }
        }
    }

    public static boolean isBlockConvertible(BlockPos pos, BlockState state, ServerLevel level) {
        return FallingBlock.isFree(level.getBlockState(pos.below())) && CONVERTIBLE_BLOCKS.contains(state.getBlock());
    }

    public static boolean isSpawnValid(BlockPos pos, BlockState state, ServerLevel level) {
        return BURIED_SPAWN_BLOCKS.contains(level.getBlockState(pos.below()).getBlock())
                && state.isAir()
                && level.getBlockState(pos.above()).isAir();
    }

    public static void setCurse(ServerPlayer player, boolean value) {
        player.setAttached(ModAttachmentTypes.DESERT_CURSE, value);
    }

    public static boolean isCurseActive(Player player) {
        return player.getAttachedOrElse(ModAttachmentTypes.DESERT_CURSE, false);
    }
}
