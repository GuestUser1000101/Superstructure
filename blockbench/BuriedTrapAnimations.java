// Save this class in your mod and generate all required imports

/**
 * Made with Blockbench 5.1.6
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * @author Author
 */
public class buried_trapAnimation {
	public static final AnimationDefinition idle = AnimationDefinition.Builder.withLength(5.9583F).looping()
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-24.6656F, 4.2085F, 9.0794F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(3.0F, KeyframeAnimations.degreeVec(-22.462F, 1.2511F, 2.1701F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(5.9583F, KeyframeAnimations.degreeVec(-24.6656F, 4.2085F, 9.0794F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-27.7022F, -6.6486F, 3.4787F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(3.0F, KeyframeAnimations.degreeVec(-30.2022F, -6.6486F, 3.4787F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(5.9583F, KeyframeAnimations.degreeVec(-27.7022F, -6.6486F, 3.4787F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-37.5264F, 1.9832F, -1.5225F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(3.0F, KeyframeAnimations.degreeVec(-32.5264F, 1.9832F, -1.5225F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(5.9583F, KeyframeAnimations.degreeVec(-37.5264F, 1.9832F, -1.5225F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition attack = AnimationDefinition.Builder.withLength(0.875F)
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-24.6656F, 4.2085F, 9.0794F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-46.05F, 4.21F, 9.08F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-47.1656F, 4.2085F, 9.0794F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(-4.6656F, 4.2085F, 9.0794F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(20.3344F, 4.2085F, 9.0794F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 11.33F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 14.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 13.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 10.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -1.33F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, -7.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 10.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 12.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 11.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 8.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, -1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-27.7022F, -6.6486F, 3.4787F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-38.84F, -12.7F, 9.33F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-44.4125F, -15.7253F, 12.2501F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-42.3517F, 0.7296F, -3.2851F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.625F, KeyframeAnimations.degreeVec(-42.6617F, 6.2622F, -8.3601F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-22.6617F, 6.2622F, -8.3601F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 9.33F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 11.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 10.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 7.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, -5.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -6.73F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, -9.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-37.5264F, 1.9832F, -1.5225F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-49.4F, 10.89F, -13.76F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-55.3343F, 15.3468F, -19.8799F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-52.8424F, -5.6132F, 8.4165F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-53.6732F, -10.1041F, 14.482F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-23.6732F, -10.1041F, 14.482F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 10.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 12.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 11.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 8.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, -3.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -4.86F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, -8.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();
}