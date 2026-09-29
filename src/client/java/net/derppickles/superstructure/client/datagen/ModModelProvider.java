package net.derppickles.superstructure.client.datagen;

import net.derppickles.superstructure.block.ModBlocks;
import net.derppickles.superstructure.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createTrivialCube(ModBlocks.TITANIUM_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModItems.DICE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.TITANIUM, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.TITANIUM_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.BURIED_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.BURIED_TRAP_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
    }
}
