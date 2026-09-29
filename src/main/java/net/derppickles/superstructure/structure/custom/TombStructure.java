package net.derppickles.superstructure.structure.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.derppickles.superstructure.structure.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class TombStructure extends Structure {
    public static final MapCodec<TombStructure> CODEC = RecordCodecBuilder.mapCodec(i ->
            i.group(
                    settingsCodec(i),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(s -> s.startPool),
                    Identifier.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(s -> s.startJigsawName),
                    Codec.intRange(0, 20).fieldOf("size").forGetter(s -> s.maxDepth),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(s -> s.startHeight),
                    Codec.BOOL.fieldOf("use_expansion_hack").forGetter(s -> s.useExpansionHack),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(s -> s.projectStartToHeightmap),
                    JigsawStructure.MaxDistance.CODEC.fieldOf("max_distance_from_center").forGetter(s -> s.maxDistanceFromCenter),
                    Codec.list(PoolAliasBinding.CODEC).optionalFieldOf("pool_aliases", List.of()).forGetter(s -> s.poolAliases),
                    DimensionPadding.CODEC.optionalFieldOf("dimension_padding", JigsawStructure.DEFAULT_DIMENSION_PADDING).forGetter(s -> s.dimensionPadding),
                    LiquidSettings.CODEC.optionalFieldOf("liquid_settings", JigsawStructure.DEFAULT_LIQUID_SETTINGS).forGetter(s -> s.liquidSettings),
                    Codec.INT.fieldOf("min_relative_height").forGetter(s -> s.minRelativeHeight),
                    Codec.INT.fieldOf("max_relative_height").forGetter(s -> s.maxRelativeHeight)
            ).apply(i, TombStructure::new)
    );

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<Identifier> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final JigsawStructure.MaxDistance maxDistanceFromCenter;
    private final List<PoolAliasBinding> poolAliases;
    private final DimensionPadding dimensionPadding;
    private final LiquidSettings liquidSettings;
    private final int minRelativeHeight;
    private final int maxRelativeHeight;

    public TombStructure(
            Structure.StructureSettings settings,
            Holder<StructureTemplatePool> startPool,
            Optional<Identifier> startJigsawName,
            int maxDepth,
            HeightProvider startHeight,
            boolean useExpansionHack,
            Optional<Heightmap.Types> projectStartToHeightmap,
            JigsawStructure.MaxDistance maxDistanceFromCenter,
            List<PoolAliasBinding> poolAliases,
            DimensionPadding dimensionPadding,
            LiquidSettings liquidSettings,
            int minRelativeHeight,
            int maxRelativeHeight) {
        super(settings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.poolAliases = poolAliases;
        this.dimensionPadding = dimensionPadding;
        this.liquidSettings = liquidSettings;
        this.minRelativeHeight = minRelativeHeight;
        this.maxRelativeHeight = maxRelativeHeight;
    }


    private static boolean extraSpawningChecks(Structure.GenerationContext context, BlockPos startPos, int width, int depth, int y) {
        ChunkGenerator generator = context.chunkGenerator();
        LevelHeightAccessor heightAccessor = context.heightAccessor();
        RandomState randomState = context.randomState();

        int minX = startPos.getX() - width / 2;
        int maxX = startPos.getX() + width / 2;
        int minZ = startPos.getZ() - depth / 2;
        int maxZ = startPos.getZ() + depth / 2;
        int midX = startPos.getX();
        int midZ = startPos.getZ();

        int[][] samplePoints = {
                {minX, minZ}, {maxX, minZ}, {minX, maxZ}, {maxX, maxZ}, // corners
                {midX, minZ}, {midX, maxZ}, {minX, midZ}, {maxX, midZ}  // side midpoints
        };

        for (int[] point : samplePoints) {
            if (!isValidSpawnBlock(generator, heightAccessor, randomState, point[0], y, point[1])) {
                return false;
            }
        }

        return true;
    }

    private static boolean isValidSpawnBlock(ChunkGenerator generator, LevelHeightAccessor heightAccessor, RandomState randomState, int x, int y, int z) {
        NoiseColumn column = generator.getBaseColumn(x, z, heightAccessor, randomState);
        return !column.getBlock(y).isAir() && !column.getBlock(y).is(Blocks.WATER);
    }

    @Override
    public Optional<Structure.GenerationStub> findGenerationPoint(final Structure.GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        int height = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
        BlockPos startPos = new BlockPos(chunkPos.getMinBlockX(), height, chunkPos.getMinBlockZ());

        if (!TombStructure.extraSpawningChecks(context, startPos, 47, 47, startPos.getY())) {
            return Optional.empty();
        }

        return JigsawPlacement.addPieces(
                context,
                this.startPool,
                this.startJigsawName,
                this.maxDepth,
                startPos,
                this.useExpansionHack,
                this.projectStartToHeightmap,
                this.maxDistanceFromCenter,
                PoolAliasLookup.create(this.poolAliases, startPos, context.seed()),
                this.dimensionPadding,
                this.liquidSettings
        );

    }

    @Override
    public StructureType<?> type() {
        return ModStructures.TOMB;
    }
}
