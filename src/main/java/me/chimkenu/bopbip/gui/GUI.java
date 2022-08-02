package me.chimkenu.bopbip.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class GUI {
    private final Inventory inventory;
    private final Map<Integer, Action> actions;
    private final UUID uuid;

    public static HashMap<UUID, GUI> inventoriesByUUID = new HashMap<>();
    public static HashMap<UUID, UUID> openInventories = new HashMap<>();

    public GUI(int size, String name) {
        uuid = UUID.randomUUID();
        inventory = Bukkit.createInventory(null, size, name);
        actions = new HashMap<>();
        GUI.inventoriesByUUID.put(getUuid(), this);
    }

    public Inventory getInventory() { return inventory; }

    public void setItem(int slot, ItemStack item, Action action) {
        inventory.setItem(slot, item);
        if (action != null) {
            actions.put(slot, action);
        }
    }

    public void setItem(int slot, ItemStack item) { setItem(slot, item, null); }

    public void addItem(ItemStack item, Action action) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                setItem(i, item, action);
                break;
            }
        }
    }

    public void addItem(ItemStack item) { addItem(item, null); }

    public UUID getUuid() { return uuid; }

    public void open(Player player) {
        player.openInventory(inventory);
        openInventories.put(player.getUniqueId(), getUuid());
    }

    public void delete() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID uuid = openInventories.get(player.getUniqueId());
            if (uuid.equals(getUuid())) {
                player.closeInventory();
            }
        }
        inventoriesByUUID.remove(getUuid());
    }

    public static Map<UUID, GUI> getInventoriesByUUID() { return inventoriesByUUID; }

    public static Map<UUID, UUID> getOpenInventories() { return openInventories; }

    public Map<Integer, Action> getActions() { return actions; }

    public interface Action { void click(Player player); }

    public ItemStack newGUIItem(Material material, String displayName, boolean isGlowing, int amount) {
        ItemStack item = new ItemStack(material);
        item.setAmount(amount);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            if (isGlowing) {
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack newGUIItem(Material material, String displayName, boolean isGlowing) {
        return newGUIItem(material, displayName, isGlowing, 1);
    }

    public ItemStack newGUIItem(Material material, String displayName) {
        return newGUIItem(material, displayName, false);
    }
}
