package me.chimkenu.bopbip;

import me.chimkenu.bopbip.listeners.Drops;
import me.chimkenu.bopbip.listeners.Drugs;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import static me.chimkenu.bopbip.Bopbip.*;

public class CustomCraftingRecipes {
    public static void registerCraftingRecipes(JavaPlugin plugin) {
        // hot dog
        ShapelessRecipe hd = new ShapelessRecipe(NamespacedKey.minecraft("hotdog"), hotDog);
        hd.addIngredient(Material.BEEF);
        hd.addIngredient(Material.PORKCHOP);
        hd.addIngredient(Material.CHICKEN);
        hd.addIngredient(Material.SUGAR);
        plugin.getServer().addRecipe(hd);

        // cooked hot dog
        SmokingRecipe hotDogWithSmoker = new SmokingRecipe(NamespacedKey.minecraft("hotdog_smoker"), cookedHotDog, new RecipeChoice.ExactChoice(hotDog), 0.5f, 15 * 20);
        CampfireRecipe hotDogWithCampfire = new CampfireRecipe(NamespacedKey.minecraft("hotdog_campfire"), cookedHotDog, new RecipeChoice.ExactChoice(hotDog), 0.5f, 15 * 20);
        plugin.getServer().addRecipe(hotDogWithSmoker);
        plugin.getServer().addRecipe(hotDogWithCampfire);

        // decapitator
        ShapedRecipe dl = new ShapedRecipe(NamespacedKey.minecraft("decapitator_left"), decapitator);
        ShapedRecipe dr = new ShapedRecipe(NamespacedKey.minecraft("decapitator_right"), decapitator);
        dl.shape("CG ", "GS ", " S ");
        dr.shape(" GC", " SG", " S ");
        dl.setIngredient('G', Material.GOLD_BLOCK);
        dl.setIngredient('C', Material.WAXED_COPPER_BLOCK);
        dl.setIngredient('S', Material.STICK);
        dr.setIngredient('G', Material.GOLD_BLOCK);
        dr.setIngredient('C', Material.WAXED_COPPER_BLOCK);
        dr.setIngredient('S', Material.STICK);
        plugin.getServer().addRecipe(dl);
        plugin.getServer().addRecipe(dr);

        // chicken nuggets
        SmokingRecipe nuggetsWithSmoker = new SmokingRecipe(NamespacedKey.minecraft("chicken_nugget_smoker"), cookedNuggets, new RecipeChoice.ExactChoice(Drops.nugget), 1.0f, 10 * 20);
        CampfireRecipe nuggetsWithCampfire = new CampfireRecipe(NamespacedKey.minecraft("chicken_nugget_campfire"), cookedNuggets, new RecipeChoice.ExactChoice(Drops.nugget), 1.0f, 10 * 20);
        plugin.getServer().addRecipe(nuggetsWithSmoker);
        plugin.getServer().addRecipe(nuggetsWithCampfire);

        // water
        ItemStack bottle = new ItemStack(Material.POTION, 1);
        PotionMeta pmeta = (PotionMeta) bottle.getItemMeta();
        if (pmeta != null) {
            pmeta.setBasePotionData(new PotionData(PotionType.WATER));
            bottle.setItemMeta(pmeta);
        }

        // red wine
        ItemStack redWine = createItem(Material.POTION, 1, "&cRed Wine", false, "");
        PotionMeta wine_meta = (PotionMeta) redWine.getItemMeta();
        if (wine_meta != null) {
            wine_meta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 30, 0, false, true, true), false);
            wine_meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 20, 0, false, true, true), true);
            wine_meta.setColor(Color.fromRGB(255, 78, 78));
            redWine.setItemMeta(wine_meta);
        }
        ShapelessRecipe wine_recipe = new ShapelessRecipe(NamespacedKey.minecraft("red_wine"), redWine);
        wine_recipe.addIngredient(Material.SWEET_BERRIES);
        wine_recipe.addIngredient(new RecipeChoice.ExactChoice(bottle));
        wine_recipe.addIngredient(Material.SUGAR);
        plugin.getServer().addRecipe(wine_recipe);

        // white wine
        ItemStack whiteWine = createItem(Material.POTION, 1, "&fWhite Wine", false, "");
        PotionMeta whiteWine_meta = (PotionMeta) whiteWine.getItemMeta();
        if (whiteWine_meta != null) {
            whiteWine_meta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 30, 0, false, true, true), false);
            whiteWine_meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 1, false, true, true), true);
            whiteWine_meta.setColor(Color.fromRGB(247, 230, 114));
            whiteWine.setItemMeta(whiteWine_meta);
        }
        ShapelessRecipe whiteWine_recipe = new ShapelessRecipe(NamespacedKey.minecraft("white_wine"), whiteWine);
        whiteWine_recipe.addIngredient(Material.GLOW_BERRIES);
        whiteWine_recipe.addIngredient(new RecipeChoice.ExactChoice(bottle));
        whiteWine_recipe.addIngredient(Material.SUGAR);
        plugin.getServer().addRecipe(whiteWine_recipe);

        // sparkling wine
        ItemStack sparklingWine = createItem(Material.POTION, 1, "&eSparkling Wine", false, "");
        PotionMeta sparklingWine_meta = (PotionMeta) sparklingWine.getItemMeta();
        if (sparklingWine_meta != null) {
            sparklingWine_meta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 30, 0, false, true, true), false);
            sparklingWine_meta.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * 20, 0, false, true, true), true);
            sparklingWine_meta.setColor(Color.fromRGB(255, 231, 166));
            sparklingWine.setItemMeta(sparklingWine_meta);
        }
        ShapelessRecipe sparklingWine_recipe = new ShapelessRecipe(NamespacedKey.minecraft("sparkling_wine"), sparklingWine);
        sparklingWine_recipe.addIngredient(Material.GLOW_INK_SAC);
        sparklingWine_recipe.addIngredient(new RecipeChoice.ExactChoice(bottle));
        sparklingWine_recipe.addIngredient(Material.SUGAR);
        plugin.getServer().addRecipe(sparklingWine_recipe);

        // beer
        ItemStack beer = createItem(Material.POTION, 1, "&eBeer", false, "");
        PotionMeta beer_meta = (PotionMeta) beer.getItemMeta();
        if (beer_meta != null) {
            beer_meta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 60, 1, false, true, true), false);
            beer_meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 30, 0, false, true, true), true);
            beer_meta.setColor(Color.fromRGB(255, 247, 0));
            beer.setItemMeta(beer_meta);
        }
        ShapelessRecipe beer_recipe = new ShapelessRecipe(NamespacedKey.minecraft("beer"), beer);
        beer_recipe.addIngredient(Material.SUGAR_CANE);
        beer_recipe.addIngredient(new RecipeChoice.ExactChoice(bottle));
        beer_recipe.addIngredient(Material.SUGAR);
        plugin.getServer().addRecipe(beer_recipe);

        // whiskey
        ItemStack whiskey = createItem(Material.POTION, 1, "&6Whiskey", false, "");
        PotionMeta whiskey_meta = (PotionMeta) whiskey.getItemMeta();
        if (whiskey_meta != null) {
            whiskey_meta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 60, 3, false, true, true), false);
            whiskey_meta.setColor(Color.fromRGB(250, 200, 0));
            whiskey.setItemMeta(whiskey_meta);
        }
        ShapelessRecipe whiskey_recipe = new ShapelessRecipe(NamespacedKey.minecraft("whiskey"), whiskey);
        whiskey_recipe.addIngredient(Material.WHEAT);
        whiskey_recipe.addIngredient(new RecipeChoice.ExactChoice(bottle));
        whiskey_recipe.addIngredient(Material.SUGAR);
        plugin.getServer().addRecipe(whiskey_recipe);

        // bacardi
        ItemStack bacardi = createItem(Material.POTION, 1, "&8&lBacardi", false, "");
        PotionMeta bacardi_meta = (PotionMeta) bacardi.getItemMeta();
        if (bacardi_meta != null) {
            bacardi_meta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 120, 4, false, true, true), false);
            bacardi_meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 120, 1, false, true, true), false);
            bacardi_meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 120, 0, false, true, true), false);
            bacardi_meta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 120, 1, false, true, true), false);
            bacardi_meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 120, 2, false, true, true), false);
            bacardi_meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 20 * 120, 1, false, true, true), false);
            bacardi_meta.setColor(Color.fromRGB(56, 31, 30));
            bacardi.setItemMeta(bacardi_meta);
        }
        ShapelessRecipe bacardi_recipe = new ShapelessRecipe(NamespacedKey.minecraft("bacardi"), bacardi);
        bacardi_recipe.addIngredient(Material.BEETROOT_SEEDS);
        bacardi_recipe.addIngredient(new RecipeChoice.ExactChoice(bottle));
        bacardi_recipe.addIngredient(Material.SUGAR);
        plugin.getServer().addRecipe(bacardi_recipe);

        // bone block
        ShapelessRecipe boneBlock = new ShapelessRecipe(NamespacedKey.minecraft("bone_block_compressed"), new ItemStack(Material.BONE_BLOCK));
        for (int i = 0; i < 3; i++) boneBlock.addIngredient(Material.BONE);;
        plugin.getServer().addRecipe(boneBlock);

        // sticky piston
        ShapelessRecipe stickyPiston = new ShapelessRecipe(NamespacedKey.minecraft("honey_piston"), new ItemStack(Material.STICKY_PISTON));
        stickyPiston.addIngredient(Material.PISTON);
        stickyPiston.addIngredient(Material.HONEY_BLOCK);
        plugin.getServer().addRecipe(stickyPiston);

        // fresh air
        ItemStack fresh_air_original = createItem(Material.GLASS_BOTTLE, 1, "&fFresh Air™ Original", false, "");
        ShapelessRecipe fresh_air_original_recipe = new ShapelessRecipe(NamespacedKey.minecraft("fresh_air_original"), fresh_air_original);
        fresh_air_original_recipe.addIngredient(Material.GLASS_BOTTLE);
        fresh_air_original_recipe.addIngredient(Material.CHORUS_FRUIT);
        plugin.getServer().addRecipe(fresh_air_original_recipe);

        ItemStack fresh_air_grape = createItem(Material.GLASS_BOTTLE, 1, "&5Fresh Air™ Grape", false, "");
        ShapelessRecipe fresh_air_grape_recipe = new ShapelessRecipe(NamespacedKey.minecraft("fresh_air_grape"), fresh_air_grape);
        fresh_air_grape_recipe.addIngredient(Material.GLASS_BOTTLE);
        fresh_air_grape_recipe.addIngredient(Material.CHORUS_FRUIT);
        fresh_air_grape_recipe.addIngredient(new RecipeChoice.MaterialChoice(Material.GLOW_BERRIES, Material.SWEET_BERRIES));
        plugin.getServer().addRecipe(fresh_air_grape_recipe);

        ItemStack fresh_air_watermelon = createItem(Material.GLASS_BOTTLE, 1, "&2Fresh Air™ Watermelon", false, "");
        ShapelessRecipe fresh_air_watermelon_recipe = new ShapelessRecipe(NamespacedKey.minecraft("fresh_air_watermelon"), fresh_air_watermelon);
        fresh_air_watermelon_recipe.addIngredient(Material.GLASS_BOTTLE);
        fresh_air_watermelon_recipe.addIngredient(Material.CHORUS_FRUIT);
        fresh_air_watermelon_recipe.addIngredient(Material.MELON_SLICE);
        plugin.getServer().addRecipe(fresh_air_watermelon_recipe);

        ItemStack fresh_air_mint = createItem(Material.GLASS_BOTTLE, 1, "&bFresh Air™ Mint", false, "");
        ShapelessRecipe fresh_air_mint_recipe = new ShapelessRecipe(NamespacedKey.minecraft("fresh_air_mint"), fresh_air_mint);
        fresh_air_mint_recipe.addIngredient(Material.GLASS_BOTTLE);
        fresh_air_mint_recipe.addIngredient(Material.CHORUS_FRUIT);
        fresh_air_mint_recipe.addIngredient(new RecipeChoice.MaterialChoice(Material.OAK_LEAVES, Material.AZALEA_LEAVES, Material.ACACIA_LEAVES, Material.DARK_OAK_LEAVES, Material.BIRCH_LEAVES, Material.JUNGLE_LEAVES, Material.SPRUCE_LEAVES, Material.BIRCH_LEAVES));
        plugin.getServer().addRecipe(fresh_air_mint_recipe);

        ItemStack fresh_air_choccy = createItem(Material.GLASS_BOTTLE, 1, "&6Fresh Air™ Choccy", false, "");
        ShapelessRecipe fresh_air_choccy_recipe = new ShapelessRecipe(NamespacedKey.minecraft("fresh_air_choccy"), fresh_air_choccy);
        fresh_air_choccy_recipe.addIngredient(Material.GLASS_BOTTLE);
        fresh_air_choccy_recipe.addIngredient(Material.CHORUS_FRUIT);
        fresh_air_choccy_recipe.addIngredient(Material.COCOA_BEANS);
        plugin.getServer().addRecipe(fresh_air_choccy_recipe);


        // DRUGS!!!
        // fresh air extraction tool
        ShapelessRecipe freshAirExtractionTool = new ShapelessRecipe(NamespacedKey.minecraft("fresh_air_tool"), Drugs.freshAirExtractionTool);
        freshAirExtractionTool.addIngredient(Material.SHEARS);
        freshAirExtractionTool.addIngredient(Material.END_CRYSTAL);
        plugin.getServer().addRecipe(freshAirExtractionTool);

        // crystal matt
        FurnaceRecipe crystalMeth = new FurnaceRecipe(NamespacedKey.minecraft("crystal_meth"), Drugs.crystalMeth, new RecipeChoice.ExactChoice(Drugs.ephedraPlant), 20, 20);
        plugin.getServer().addRecipe(crystalMeth);

        // lsd
        BlastingRecipe lsd = new BlastingRecipe(NamespacedKey.minecraft("lsd"), Drugs.lsd, new RecipeChoice.ExactChoice(Drugs.ergotFungus), 20, 20);
        plugin.getServer().addRecipe(lsd);

        // cocaine
        FurnaceRecipe cocaine = new FurnaceRecipe(NamespacedKey.minecraft("cocaine"), Drugs.cocaine, new RecipeChoice.ExactChoice(Drugs.erythroxylaceaeCoca), 20, 20);
        plugin.getServer().addRecipe(cocaine);


        // REINFORCED ELYTRA ?
        SmithingRecipe elytra = new SmithingRecipe(NamespacedKey.minecraft("reinforced_elytra"), new ItemStack(Material.ELYTRA), new RecipeChoice.MaterialChoice(Material.ELYTRA), new RecipeChoice.MaterialChoice(Material.NETHER_STAR));
        plugin.getServer().addRecipe(elytra);
    }
}
