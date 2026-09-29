// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class buried extends EntityModel<Entity> {
	private final ModelPart head;
	private final ModelPart headwear;
	private final ModelPart body;
	private final ModelPart left_leg;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart right_leg;
	public buried(ModelPart root) {
		this.head = root.getChild("head");
		this.headwear = root.getChild("headwear");
		this.body = root.getChild("body");
		this.left_leg = root.getChild("left_leg");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.right_leg = root.getChild("right_leg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 16).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.2F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData headwear = modelPartData.addChild("headwear", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 32).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F))
		.uv(32, 16).cuboid(-4.0F, 10.0F, -2.0F, 8.0F, 1.0F, 4.0F, new Dilation(0.0F))
		.uv(32, 0).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.025F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 48).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F))
		.uv(32, 21).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 12.0F, 0.0F));

		ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(8, 48).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F))
		.uv(24, 36).cuboid(-1.45F, -2.025F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, 2.0F, 0.0F));

		ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(16, 48).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F))
		.uv(36, 36).cuboid(-1.55F, -2.025F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.5F, 2.0F, 0.0F));

		ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(48, 36).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F))
		.uv(44, 21).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		headwear.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		left_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		left_arm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		right_arm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		right_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}