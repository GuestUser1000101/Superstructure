package net.derppickles.superstructure.client;

import net.derppickles.superstructure.client.creativemodetab.ModCreativeModeTabs;
import net.derppickles.superstructure.client.entity.BuriedEntityRenderer;
import net.derppickles.superstructure.client.entity.BuriedTrapEntityRenderer;
import net.derppickles.superstructure.client.entity.ModEntityModelLayers;
import net.derppickles.superstructure.entity.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class SuperstructureClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModCreativeModeTabs.registerModCreativeModeTabs();
		ModEntityModelLayers.registerModelLayers();

		EntityRenderers.register(ModEntityTypes.BURIED, BuriedEntityRenderer::new);
		EntityRenderers.register(ModEntityTypes.BURIED_TRAP, BuriedTrapEntityRenderer::new);
	}
}