package me.chimkenu.bopbip.listeners;

import me.chimkenu.bopbip.Bopbip;
import me.chimkenu.bopbip.commands.Tag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Random;

public class TagListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (Tag.isGameRunning) {
            if (e.getDamager() instanceof Player attacker && e.getEntity() instanceof Player victim) {
                if (Tag.tagged == attacker) {
                    Tag.tagged = victim;
                    Bukkit.broadcastMessage(Bopbip.prefix + ChatColor.DARK_AQUA + victim.getDisplayName() + ChatColor.YELLOW + " was tagged!");
                    victim.setPlayerListName(victim.getDisplayName() + ChatColor.RED + " TAG");
                    attacker.setPlayerListName(attacker.getDisplayName());
                }
            }
        }
    }

    @EventHandler
    public void onRightClickCompass(PlayerInteractEvent e) {
        if (Tag.isGameRunning && e.getPlayer() == Tag.tagged && e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.COMPASS)) {
            ArrayList<Player> allPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
            allPlayers.remove(Tag.tagged);
            int random = new Random().nextInt(allPlayers.size());
            Tag.target = allPlayers.get(random);
        }
    }
}
