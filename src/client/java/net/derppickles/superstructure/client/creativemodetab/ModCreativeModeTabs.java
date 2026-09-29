package net.derppickles.superstructure.client.creativemodetab;

import net.derppickles.superstructure.Superstructure;
import net.derppickles.superstructure.block.ModBlocks;
import net.derppickles.superstructure.item.ModItems;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
    public static final CreativeModeTab SUPERSTRUCTURE_ITEMS = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(Superstructure.MOD_ID, "superstructure_items"),
            FabricCreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.DICE))
                    .title(Component.translatable("creativemodetab.superstructure.superstructure_items"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.DICE);
                        output.accept(ModItems.TITANIUM);
                        output.accept(ModBlocks.TITANIUM_ORE);
                        output.accept(ModItems.TITANIUM_SWORD);
                    }).build());


    public static void registerModCreativeModeTabs() {
        Superstructure.LOGGER.info("Registering Creative Mode Tabs for " + Superstructure.MOD_ID);
    }
}
