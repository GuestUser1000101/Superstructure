package net.derppickles.superstructure.client.entity;

import net.derppickles.superstructure.Superstructure;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.resources.Identifier;

public class ModEntityModelLayers {
    public static final ModelLayerLocation BURIED = register("buried");
    public static final ArmorModelSet<ModelLayerLocation> BURIED_ARMOR = registerArmorSet("buried");
    public static final ModelLayerLocation BURIED_TRAP = register("buried_trap");

    private static ModelLayerLocation register(final String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(final String name, final String layer) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(Superstructure.MOD_ID, name), layer);
    }

    private static ArmorModelSet<ModelLayerLocation> registerArmorSet(final String modelId) {
        return new ArmorModelSet<>(register(modelId, "helmet"), register(modelId, "chestplate"), register(modelId, "leggings"), register(modelId, "boots"));
    }

    public static void registerModelLayers() {
        ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.BURIED, BuriedEntityModel::getTexturedModelData);
        ModelLayerRegistry.registerArmorModelLayers(ModEntityModelLayers.BURIED_ARMOR, BuriedEntityModel::createArmorLayers);
        ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.BURIED_TRAP, BuriedTrapEntityModel::getTexturedModelData);
    }
}
