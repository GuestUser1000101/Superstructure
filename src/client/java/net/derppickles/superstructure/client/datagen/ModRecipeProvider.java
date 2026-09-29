package net.derppickles.superstructure.client.datagen;

import net.derppickles.superstructure.Superstructure;
import net.derppickles.superstructure.block.ModBlocks;
import net.derppickles.superstructure.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                List<ItemLike> TITANIUM_SMELTABLES = List.of(ModBlocks.TITANIUM_ORE);

                oreSmelting(TITANIUM_SMELTABLES,
                        RecipeCategory.MISC,
                        CookingBookCategory.BLOCKS,
                        ModItems.TITANIUM,
                        0.25f,
                        200,
                        "titanium");

                oreBlasting(TITANIUM_SMELTABLES,
                        RecipeCategory.MISC,
                        CookingBookCategory.BLOCKS,
                        ModItems.TITANIUM,
                        0.25f,
                        100,
                        "titanium");

                nineBlockStorageRecipes(RecipeCategory.MISC,
                        ModItems.TITANIUM,
                        RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.TITANIUM_ORE);

                shaped(RecipeCategory.MISC, ModItems.DICE)
                        .pattern("RR")
                        .pattern("RR")
                        .define('R', ModItems.TITANIUM)
                        .unlockedBy(getHasName(ModItems.TITANIUM), has(ModItems.TITANIUM))
                        .group("titanium")
                        .save(output);

                shapeless(RecipeCategory.MISC, ModItems.TITANIUM, 4)
                        .requires(ModItems.DICE)
                        .unlockedBy(getHasName(ModItems.DICE), has(ModItems.DICE))
                        .group("titanium")
                        .save(output, "titanium_from_dice");


                shapeless(RecipeCategory.MISC, ModItems.TITANIUM, 64)
                        .requires(ModItems.DICE, 3)
                        .unlockedBy(getHasName(ModItems.DICE), has(ModItems.DICE))
                        .group("titanium")
                        .save(output, "titanium_from_random_bs");
            }
        };
    }

    @Override
    public String getName() {
        return "Superstructure Recipes";
    }
}
