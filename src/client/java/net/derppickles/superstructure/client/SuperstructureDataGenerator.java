package net.derppickles.superstructure.client;

import net.derppickles.superstructure.client.datagen.ModBlockLootTableProvider;
import net.derppickles.superstructure.client.datagen.ModBlockTagsProvider;
import net.derppickles.superstructure.client.datagen.ModModelProvider;
import net.derppickles.superstructure.client.datagen.ModRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SuperstructureDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		var pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModBlockTagsProvider::new);
		pack.addProvider(ModBlockLootTableProvider::new);
		pack.addProvider(ModRecipeProvider::new);
	}
}
