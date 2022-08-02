package me.chimkenu.bopbip;

import me.chimkenu.bopbip.commands.*;
import me.chimkenu.bopbip.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Bopbip extends JavaPlugin {

    public static String prefix = ChatColor.translateAlternateColorCodes('&', "&7[&bbopbip&7] &r");
    public static TPS tps = new TPS();
    public static JavaPlugin instance;
    public static ItemStack decapitator = createItem(Material.GOLDEN_AXE, 1, "&e&lDecapitator", true, "&7One time use. This item will disappear", "&7once an entity is killed with it.", "&fCurrently only works on players.");
    public static ItemStack hotDog = createItem(Material.PORKCHOP, 1, "&cHotdog", true, "&7It's a raw hotdog!", "&7Nothing special here.");
    public static ItemStack cookedHotDog = createItem(Material.BEEF, 1, "&cCooked Hotdog", true, "&7Wow, a cooked hotdog!", "&7I wonder what it does");
    public static ItemStack cookedNuggets = createItem(Material.GOLD_NUGGET, 1, "&6Cooked Chicken Nugget", true, "&7Yummy");

    @Override
    public void onEnable() {
        reloadConfig();
        instance = this;

        // tps thing + playtime counter
        getServer().getScheduler().scheduleSyncRepeatingTask(this, tps, 100, 1);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Playtime(), 100, 1);

        // events
        getServer().getPluginManager().registerEvents(new Chat(), this);
        getServer().getPluginManager().registerEvents(new AFKListener(), this);
        getServer().getPluginManager().registerEvents(new Sleeping(), this);
        getServer().getPluginManager().registerEvents(new Drops(), this);
        getServer().getPluginManager().registerEvents(new Food(), this);
        getServer().getPluginManager().registerEvents(new Waypoints(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getServer().getPluginManager().registerEvents(new Weapons(), this);
        getServer().getPluginManager().registerEvents(new TagListener(), this);
        getServer().getPluginManager().registerEvents(new Wet(), this);
        getServer().getPluginManager().registerEvents(new Drugs(), this);
        getServer().getPluginManager().registerEvents(new Crafting(), this);

        // commands
        getCommand("help").setExecutor(new Help());
        getCommand("afk").setExecutor(new AFK());
        getCommand("lag").setExecutor(new Lag());
        getCommand("ping").setExecutor(new Ping());
        getCommand("playtime").setExecutor(new Playtime());
        getCommand("insult").setExecutor(new Insult());
        getCommand("compliment").setExecutor(new Compliment());
        getCommand("tag").setExecutor(new Tag());
        getCommand("pinglocation").setExecutor(new PingLocation());

        // -- recipes --
        CustomCraftingRecipes.registerCraftingRecipes(this);
    }

    @Override
    public void onDisable() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[tag=DAMAGE]");
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public static ItemStack createItem(Material material, int amount, String displayName, boolean isGlowing, String... lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));

            List<String> list = new ArrayList<>();
            for (String s : lore) { list.add(ChatColor.translateAlternateColorCodes('&', s)); }
            itemMeta.setLore(list);

            if (isGlowing) {
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            itemMeta.setUnbreakable(true);
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
