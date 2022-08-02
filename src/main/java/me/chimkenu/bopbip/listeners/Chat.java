package me.chimkenu.bopbip.listeners;

import me.chimkenu.bopbip.Bopbip;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

public class Chat implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().setFreezeTicks(0);
        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&7[&a+&7] &r" + e.getPlayer().getDisplayName()));
        PermissionAttachment attachment = e.getPlayer().addAttachment(Bopbip.instance);
        attachment.setPermission("GSit.Lay", true);
        attachment.setPermission("GSit.BellyFlop", true);
        attachment.setPermission("GSit.Spin", true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&7[&c-&7] &r" + e.getPlayer().getDisplayName()));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage().replaceAll("%", "%%");
        e.setFormat(e.getPlayer().getDisplayName() + ChatColor.DARK_GRAY + " » " + ChatColor.translateAlternateColorCodes('&', "&r" + message));
    }
}
