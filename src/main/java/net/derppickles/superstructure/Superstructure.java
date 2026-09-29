package net.derppickles.superstructure;

import net.derppickles.superstructure.attachment.ModAttachmentTypes;
import net.derppickles.superstructure.block.ModBlocks;
import net.derppickles.superstructure.entity.ModEntityTypes;
import net.derppickles.superstructure.item.ModItems;
import net.derppickles.superstructure.structure.ModStructures;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Superstructure implements ModInitializer {
	public static final String MOD_ID = "superstructure";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEntityTypes.registerModEntityTypes();
		ModEntityTypes.registerAttributes();
		ModAttachmentTypes.registerModAttachments();
		ModStructures.registerModStructureTypes();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
