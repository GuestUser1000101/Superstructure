package net.derppickles.superstructure.structure;

import com.mojang.serialization.MapCodec;
import net.derppickles.superstructure.Superstructure;
import net.derppickles.superstructure.structure.custom.TombStructure;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class ModStructures {
    public static StructureType<TombStructure> TOMB = registerStructureType("tomb_structures", TombStructure.CODEC);

    public static <T extends Structure> StructureType<T> registerStructureType(String name, MapCodec<T> codec) {
        return Registry.register(
                BuiltInRegistries.STRUCTURE_TYPE,
                Identifier.fromNamespaceAndPath(Superstructure.MOD_ID, name),
                () -> codec
        );
    }

    public static void registerModStructureTypes() {
        Superstructure.LOGGER.info("Registering Mod Structure Types for + " + Superstructure.MOD_ID);
    }
}
