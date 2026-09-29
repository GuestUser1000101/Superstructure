package net.derppickles.superstructure.client.entity;

import net.derppickles.superstructure.Superstructure;
import net.derppickles.superstructure.entity.custom.BuriedEntity;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.Identifier;

public class BuriedEntityRenderer extends HumanoidMobRenderer<BuriedEntity, BuriedEntityRenderState, BuriedEntityModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Superstructure.MOD_ID, "textures/entity/buried.png");

    public BuriedEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new BuriedEntityModel(context.bakeLayer(ModEntityModelLayers.BURIED)), 0.375f);
        this.addLayer(new HumanoidArmorLayer<>(this, ArmorModelSet.bake(ModEntityModelLayers.BURIED_ARMOR, context.getModelSet(), BuriedEntityModel::new), context.getEquipmentRenderer()));
    }

    @Override
    public BuriedEntityRenderState createRenderState() {
        return new BuriedEntityRenderState();
    }

    @Override
    public Identifier getTextureLocation(BuriedEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public void extractRenderState(BuriedEntity entity, BuriedEntityRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);

        reusedState.idleAnimationState.copyFrom(entity.idleAnimationState);
    }
}
