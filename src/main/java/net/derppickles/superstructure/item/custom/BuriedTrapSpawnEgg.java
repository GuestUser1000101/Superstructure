package net.derppickles.superstructure.item.custom;

import net.derppickles.superstructure.entity.custom.BuriedTrapEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.UseOnContext;

public class BuriedTrapSpawnEgg extends SpawnEggItem {
    public BuriedTrapSpawnEgg(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getClickedFace() != Direction.UP) return InteractionResult.FAIL;
        return super.useOn(context);
    }
}
