package me.chimkenu.bopbip.listeners;

import me.chimkenu.bopbip.Bopbip;
import me.chimkenu.bopbip.gui.WaypointsGUI;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class Waypoints implements Listener {

    public static HashMap<String, Location> getWaypoints() {
        FileConfiguration config = Bopbip.instance.getConfig();
        HashMap<String, Location> hashMap = new HashMap<>();

        ConfigurationSection configSection = config.getConfigurationSection("waypoints");
        if (configSection != null) {
            for (String key : configSection.getKeys(false)) {
                hashMap.put(config.getString("waypoints." + key), new Location(Bukkit.getWorlds().get(0), config.getDouble("waypoints." + key + ".x"), config.getDouble("waypoints." + key + ".y"), config.getDouble("waypoints." + key + ".z")));
            }
        }
        return hashMap;
    }

    private static void addWaypoint(String name, Location loc) {
        FileConfiguration config = Bopbip.instance.getConfig();
        config.set("waypoints." + name + "." + "x", loc.getX());
        config.set("waypoints." + name + "." + "y", loc.getY());
        config.set("waypoints." + name + "." + "z", loc.getZ());
        Bopbip.instance.saveConfig();
    }

    private static void removeWaypoint(String name) {
        FileConfiguration config = Bopbip.instance.getConfig();
        config.set("waypoints." + name, null);
        Bopbip.instance.saveConfig();
    }

    @EventHandler
    public void onPlaceLodestone(BlockPlaceEvent e) {
        if (!e.getBlock().getType().equals(Material.LODESTONE)) return;
        World world = e.getBlock().getWorld();
        if (!world.getEnvironment().equals(World.Environment.NORMAL)) {
            e.getPlayer().sendMessage(Bopbip.prefix + ChatColor.YELLOW + "You placed a lodestone outside the overworld. Please do note that this does not count as a waypoint.");
            return;
        }

        Player player = e.getPlayer();

        String name = "null";
        if (e.getItemInHand().getItemMeta() != null) name = e.getItemInHand().getItemMeta().getDisplayName();
        name = name.trim();

        if (name.equalsIgnoreCase("null")) {
            player.sendMessage(Bopbip.prefix + ChatColor.RED + "You cannot use the name null. " + ChatColor.GRAY + "Sorry!");
            e.setCancelled(true);
            return;
        }
        else if (getWaypoints().containsKey(name)) {
            player.sendMessage(Bopbip.prefix + ChatColor.RED + "This name is in use. " + ChatColor.GRAY + "Sorry!");
            e.setCancelled(true);
            return;
        }
        else if (name.length() < 2) {
            player.sendMessage(Bopbip.prefix + ChatColor.RED + "This name is too short. (or there is no name) " + ChatColor.GRAY + "Sorry!");
            e.setCancelled(true);
            return;
        }

        addWaypoint(name, e.getBlock().getLocation());
        player.sendMessage(Bopbip.prefix + ChatColor.YELLOW + "Created waypoint " + ChatColor.DARK_AQUA + name);
    }

    @EventHandler
    public void onBreakLodestone(BlockBreakEvent e) {
        if (!e.getBlock().getType().equals(Material.LODESTONE)) return;
        World world = e.getBlock().getWorld();
        if (!world.getEnvironment().equals(World.Environment.NORMAL)) return;

        if (getWaypoints().containsValue(e.getBlock().getLocation())) {
            for (String s : getWaypoints().keySet()) {
                if (getWaypoints().get(s).equals(e.getBlock().getLocation())) {
                    removeWaypoint(s);
                    String name = s.replaceFirst("MemorySection\\[path='waypoints.", "").replaceFirst("', root='YamlConfiguration']", "").trim();
                    e.getPlayer().sendMessage(Bopbip.prefix + ChatColor.YELLOW + "Removed waypoint " + ChatColor.DARK_AQUA + name.trim());
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getClickedBlock() != null && !e.getClickedBlock().getType().equals(Material.LODESTONE)) return;
        if (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getPlayer().isSneaking())) return;
        if (!e.getPlayer().getWorld().getEnvironment().equals(World.Environment.NORMAL)) return;

        Player player = e.getPlayer();

        if (getWaypoints().containsValue(e.getClickedBlock().getLocation())) {
            for (String s : getWaypoints().keySet()) {
                if (getWaypoints().get(s).equals(e.getClickedBlock().getLocation())) {
                    WaypointsGUI waypointsGUI = new WaypointsGUI();
                    waypointsGUI.open(player);
                    return;
                }
            }
        }
    }
}
