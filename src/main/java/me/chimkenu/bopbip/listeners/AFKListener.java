package me.chimkenu.bopbip.listeners;

import me.chimkenu.bopbip.Bopbip;
import me.chimkenu.bopbip.commands.AFK;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

public class AFKListener implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (AFK.afkList.contains(e.getPlayer())) AFK.setAFK(e.getPlayer(), false);
    }

    @EventHandler
    public void onClick2(PlayerInteractAtEntityEvent e) {
        if (AFK.afkList.contains(e.getPlayer())) AFK.setAFK(e.getPlayer(), false);
    }

    @EventHandler
    public void onClick3(PlayerInteractEntityEvent e) {
        if (AFK.afkList.contains(e.getPlayer())) AFK.setAFK(e.getPlayer(), false);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (AFK.afkList.contains(e.getPlayer())) AFK.setAFK(e.getPlayer(), false);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (AFK.afkList.contains(e.getPlayer())) AFK.setAFK(e.getPlayer(), false);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && AFK.afkList.contains((Player) e.getEntity())) {
            Bukkit.broadcastMessage(Bopbip.prefix + ChatColor.DARK_AQUA + ((Player) e.getEntity()).getDisplayName() + " " + ChatColor.YELLOW + "was hit while AFK. Kicking...");
            ((Player) e.getEntity()).kickPlayer(ChatColor.RED + "You were hit while AFK.");
        }
    }
}
