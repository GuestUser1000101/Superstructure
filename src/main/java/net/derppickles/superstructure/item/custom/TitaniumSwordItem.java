package net.derppickles.superstructure.item.custom;

import net.derppickles.superstructure.Superstructure;
import net.derppickles.superstructure.attachment.handler.DesertCurseHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;

import java.lang.reflect.Type;
import java.util.Map;

public class TitaniumSwordItem extends Item {
    public TitaniumSwordItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            DesertCurseHandler.setCurse(serverPlayer, true);
            Superstructure.LOGGER.info("Curse applied");
        }
        return InteractionResult.SUCCESS;
    }

    /*
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if (!level.isClientSide()) {
            level.setBlockAndUpdate(context.getClickedPos(), Blocks.GOLD_ORE.defaultBlockState());
            context.getItemInHand().hurtAndBreak(1, context.getPlayer(), context.getHand());
        }

        return InteractionResult.SUCCESS;
    }
     */
}
