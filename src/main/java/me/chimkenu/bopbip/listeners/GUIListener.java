package me.chimkenu.bopbip.listeners;

import me.chimkenu.bopbip.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class GUIListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) { return; }

        UUID player_uuid = player.getUniqueId();

        UUID inventory_uuid = GUI.openInventories.get(player_uuid);
        if (inventory_uuid != null) {
            e.setCancelled(true);
            GUI gui = GUI.getInventoriesByUUID().get(inventory_uuid);
            GUI.Action action = gui.getActions().get(e.getSlot());

            if (action != null){
                if (e.getClickedInventory() != null && e.getClickedInventory().getType() == InventoryType.PLAYER) { return; }
                action.click(player);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        GUI.openInventories.remove(uuid);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        GUI.openInventories.remove(uuid);
    }
}
