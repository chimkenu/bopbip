package me.chimkenu.bopbip.listeners;

import me.chimkenu.bopbip.Bopbip;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Random;

public class Drops implements Listener {

    public static ItemStack nugget = Bopbip.createItem(Material.IRON_NUGGET, 1, "&6Chicken Nugget", true, "&7It's RAW!");

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        // chicken nugget drop
        if (e.getEntityType().equals(EntityType.CHICKEN)) {
            for (int i = 0; i < new Random().nextInt(4); i++) {
                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), nugget);
            }
        }
        // hotdog drop
        else if (e.getEntityType().equals(EntityType.WOLF)) {
            for (int i = 0; i < new Random().nextInt(4); i++) {
                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), Bopbip.hotDog);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            if (killer.getInventory().getItemInMainHand().equals(Bopbip.decapitator)) {
                killer.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                ItemStack skull = Bopbip.createItem(Material.PLAYER_HEAD, 1, ChatColor.YELLOW + e.getEntity().getDisplayName(), false, "");
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                if (skullMeta != null) skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(e.getEntity().getUniqueId()));
                skull.setItemMeta(skullMeta);
                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), skull);
            }
        }
    }
}
