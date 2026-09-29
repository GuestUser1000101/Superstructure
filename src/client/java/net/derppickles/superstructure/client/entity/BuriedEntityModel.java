package net.derppickles.superstructure.client.entity;


import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.util.Mth;

public class BuriedEntityModel extends HumanoidModel<BuriedEntityRenderState> {
	private final KeyframeAnimation walkingAnimation;
	private final KeyframeAnimation idlingAnimation;

	public BuriedEntityModel(ModelPart root) {
		super(root);
		this.walkingAnimation = BuriedEntityAnimations.walk.bake(root);
		this.idlingAnimation = BuriedEntityAnimations.idle.bake(root);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(32, 16).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 21).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(8, 48).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(24, 36).addBox(-1.45F, -2.025F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(16, 48).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(36, 36).addBox(-1.55F, -2.025F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, 2.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(48, 36).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(44, 21).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(BuriedEntityRenderState state) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;
		this.idlingAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);
		this.walkingAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 3.0f, 2.5f);
	}

	public static ArmorModelSet<LayerDefinition> createArmorLayers() {
		ArmorModelSet<MeshDefinition> mesh = HumanoidModel.createArmorMeshSet(new CubeDeformation(0.5F), new CubeDeformation(1.0F));
		return mesh.map((meshDefinition) -> LayerDefinition.create(meshDefinition, 64, 32));
	}
}