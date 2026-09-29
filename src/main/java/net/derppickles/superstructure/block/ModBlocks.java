package net.derppickles.superstructure.block;

import net.derppickles.superstructure.Superstructure;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

public class ModBlocks {
    public static final Block TITANIUM_ORE = registerBlock("titanium_ore",
            properties -> new DropExperienceBlock(UniformInt.of(5, 10), properties
                    .mapColor(MapColor.COLOR_BLACK)
                    .requiresCorrectToolForDrops()
                    .strength(50f, 1200f)
                    .sound(SoundType.DEEPSLATE)
            ));

    public static ResourceKey<Block> getRK(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).get();
    }

    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> function) {
        Block toRegister = function.apply(BlockBehaviour.Properties
                .of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Superstructure.MOD_ID, name))));
        registerBlockItem(name, toRegister);
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(Superstructure.MOD_ID, name), toRegister);
    }

    private static void registerBlockItem(String name, Block block) {
        Identifier identifier = Identifier.fromNamespaceAndPath(Superstructure.MOD_ID, name);
        Registry.register(
                BuiltInRegistries.ITEM,
                identifier,
                new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix()
                        .setId(ResourceKey.create(Registries.ITEM, identifier))));
    }

    public static void registerModBlocks() {
        Superstructure.LOGGER.info("Registering Mod Blocks for " + Superstructure.MOD_ID);
    }
}
