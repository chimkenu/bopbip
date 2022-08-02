package me.chimkenu.bopbip.gui;

import me.chimkenu.bopbip.Bopbip;
import me.chimkenu.bopbip.listeners.Waypoints;
import org.bukkit.*;

public class WaypointsGUI extends GUI {
    public WaypointsGUI() {
        super(27, ChatColor.BLACK + "Waypoints Menu");

        int i = 0;
        for (String s : Waypoints.getWaypoints().keySet()) {
            String name = s.replaceFirst("MemorySection\\[path='waypoints.", "").replaceFirst("', root='YamlConfiguration']", "").trim();
            setItem(i, newGUIItem(Material.SUGAR, ChatColor.DARK_PURPLE + name.trim(), true, 1), player -> {
                player.closeInventory();
                Location loc = Waypoints.getWaypoints().get(s).add(new Location(player.getWorld(), 0, 1, 0));
                if (player.getWorld().getBlockAt(loc).getType().isAir() && player.getWorld().getBlockAt(loc.add(0, 1, 0)).getType().isAir()) {
                    player.sendMessage(Bopbip.prefix + ChatColor.YELLOW + "Teleporting you to " + ChatColor.DARK_AQUA + name + ChatColor.YELLOW + ".");
                    Bukkit.dispatchCommand(Bopbip.instance.getServer().getConsoleSender(), "execute at " + player.getDisplayName() + " run particle minecraft:enchant ~ ~1 ~ 1 1 1 1 1000 force");
                    player.teleport(loc.add(0.5, -1, 0.5));
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, SoundCategory.PLAYERS, 1, 1);
                    Bukkit.dispatchCommand(Bopbip.instance.getServer().getConsoleSender(), "execute at " + player.getDisplayName() + " run particle minecraft:enchant ~ ~1 ~ 1 1 1 1 1000 force");
                }
                else player.sendMessage(Bopbip.prefix + ChatColor.RED + "Something is obstructing this waypoint. Contact your service provider.");
            });
            i++;
        }
    }
}
