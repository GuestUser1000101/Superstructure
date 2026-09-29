package net.derppickles.superstructure.client.entity;// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class BuriedTrapEntityModel extends EntityModel<BuriedTrapEntityRenderState> {
	private final KeyframeAnimation attackingAnimation;
	private final KeyframeAnimation idlingAnimation;

	public final ModelPart head;

	public BuriedTrapEntityModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.attackingAnimation = BuriedTrapEntityAnimations.attack.bake(root);
		this.idlingAnimation = BuriedTrapEntityAnimations.idle.bake(root);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 27.0F, 0.0F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 51.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(32, 16).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(32, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(2.0F, 38.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(8, 48).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 36).addBox(-1.45F, -2.025F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, 25.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(16, 48).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(36, 36).addBox(-1.55F, -2.025F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, 25.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-2.0F, 38.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(BuriedTrapEntityRenderState state) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;
		this.idlingAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);
		this.attackingAnimation.apply(state.attackAnimationState, state.ageInTicks, 1f);
	}
}