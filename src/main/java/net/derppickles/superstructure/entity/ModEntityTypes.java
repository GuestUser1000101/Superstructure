package net.derppickles.superstructure.entity;

import net.derppickles.superstructure.Superstructure;
import net.derppickles.superstructure.entity.custom.BuriedEntity;
import net.derppickles.superstructure.entity.custom.BuriedTrapEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
    public static final EntityType<BuriedEntity> BURIED = register(
            "buried",
            EntityType.Builder.<BuriedEntity>of(BuriedEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F).eyeHeight(1.74F).ridingOffset(-0.7F).notInPeaceful()
    );

    public static final EntityType<BuriedTrapEntity> BURIED_TRAP = register(
            "buried_trap",
            EntityType.Builder.of(BuriedTrapEntity::new, MobCategory.MONSTER)
                    .sized(0.8F, 0.4F).eyeHeight(0.2F).notInPeaceful()
    );

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Superstructure.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void registerModEntityTypes() {
        Superstructure.LOGGER.info("Registering EntityTypes for " + Superstructure.MOD_ID);
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(BURIED, BuriedEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(BURIED_TRAP, BuriedTrapEntity.createAttributes());
    }
}
