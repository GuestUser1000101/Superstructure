package net.derppickles.superstructure.item;

import net.derppickles.superstructure.Superstructure;
import net.derppickles.superstructure.entity.ModEntityTypes;
import net.derppickles.superstructure.item.custom.BuriedSpawnEggItem;
import net.derppickles.superstructure.item.custom.BuriedTrapSpawnEgg;
import net.derppickles.superstructure.item.custom.TitaniumSwordItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Function;

public class ModItems {
    public static final Item DICE = registerItem("dice", Item::new);
    public static final Item TITANIUM = registerItem("titanium", Item::new);
    public static final Item TITANIUM_SWORD = registerItem("titanium_sword", properties -> new TitaniumSwordItem(properties.durability(32)));
    public static final Item BURIED_SPAWN_EGG = registerItem("buried_spawn_egg", properties ->  new BuriedSpawnEggItem(properties.spawnEgg(ModEntityTypes.BURIED)));
    public static final Item BURIED_TRAP_SPAWN_EGG = registerItem("buried_trap_spawn_egg", properties -> new BuriedTrapSpawnEgg(properties.spawnEgg(ModEntityTypes.BURIED_TRAP)));

    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        Identifier itemIdentifier = Identifier.fromNamespaceAndPath(Superstructure.MOD_ID, name);
        return Registry.register(BuiltInRegistries.ITEM, itemIdentifier, function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, itemIdentifier))));
    }

    public static void registerModItems() {
        Superstructure.LOGGER.info("Registering Mod Items for + " + Superstructure.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> {
            output.accept(DICE);
            output.accept(TITANIUM);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(output -> {
            output.accept(BURIED_SPAWN_EGG);
            output.accept(BURIED_TRAP_SPAWN_EGG);
        });
    }
}
