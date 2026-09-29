package net.derppickles.superstructure.client.entity;

import net.derppickles.superstructure.Superstructure;
import net.derppickles.superstructure.entity.custom.BuriedEntity;
import net.derppickles.superstructure.entity.custom.BuriedTrapEntity;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.Identifier;

public class BuriedTrapEntityRenderer extends MobRenderer<BuriedTrapEntity, BuriedTrapEntityRenderState, BuriedTrapEntityModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Superstructure.MOD_ID, "textures/entity/buried.png");

    public BuriedTrapEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new BuriedTrapEntityModel(context.bakeLayer(ModEntityModelLayers.BURIED_TRAP)), 0.375f);
    }

    @Override
    public Identifier getTextureLocation(BuriedTrapEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public BuriedTrapEntityRenderState createRenderState() {
        return new BuriedTrapEntityRenderState();
    }

    @Override
    public void extractRenderState(BuriedTrapEntity entity, BuriedTrapEntityRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);

        reusedState.idleAnimationState.copyFrom(entity.idleAnimationState);
        reusedState.attackAnimationState.copyFrom(entity.attackAnimationState);
    }
}
