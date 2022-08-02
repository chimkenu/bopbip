package me.chimkenu.bopbip.commands;

import me.chimkenu.bopbip.Bopbip;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.TreeMap;
import java.util.UUID;

public class Playtime implements Runnable, CommandExecutor {

    private static void addOne(Player player) {
        FileConfiguration config = Bopbip.instance.getConfig();
        if (!config.contains("playtime." + player.getUniqueId())) config.set("playtime." + player.getUniqueId(), 0);
        config.set("playtime." + player.getUniqueId(), config.getInt("playtime." + player.getUniqueId())+1);
        Bopbip.instance.saveConfig();
    }

    private static String ticksToTime(int ticks) {
        long duration = ticks * 50L;
        long second = (duration / 1000) % 60;
        long minute = (duration / (1000 * 60)) % 60;
        long hour = (duration / (1000 * 60 * 60));

        String hours = hour + " hour" + (hour == 1 ? "" : "s");
        String minutes = minute + " minute" + (minute == 1 ? "" : "s");
        String seconds = second + " second" + (second == 1 ? "" : "s");
        return hours + " " + minutes + " " + seconds;
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            addOne(p);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("top")) {
                    FileConfiguration config = Bopbip.instance.getConfig();
                    TreeMap<Integer, UUID> treeMap = new TreeMap<>();
                    for (String key : config.getConfigurationSection("playtime").getKeys(false)) {
                        if (treeMap.containsKey(config.getInt("playtime." + key))) {
                            int newValueLower = config.getInt("playtime." + key);
                            while (treeMap.containsKey(newValueLower)) { newValueLower--; }
                            treeMap.put(newValueLower, UUID.fromString(key));

                        } else treeMap.put(config.getInt("playtime." + key), UUID.fromString(key));
                    }
                    sender.sendMessage(ChatColor.AQUA + "Top Playtime:");
                    for (int i : treeMap.descendingKeySet()) {
                        sender.sendMessage("  " + ChatColor.DARK_AQUA + Bukkit.getOfflinePlayer(treeMap.get(i)).getName() + ChatColor.YELLOW + ": " + ChatColor.GRAY + ticksToTime(i));
                    }

                } else {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if (!Bopbip.instance.getConfig().contains("playtime." + target.getUniqueId())) {
                        sender.sendMessage(ChatColor.DARK_AQUA + args[0] + ChatColor.RED + " has not been on the server.");
                        return true;
                    }
                    sender.sendMessage(Bopbip.prefix + ChatColor.translateAlternateColorCodes('&', "&3" + target.getName() + " &eplaytime: &7") + ticksToTime(Bopbip.instance.getConfig().getInt("playtime." + target.getUniqueId())));
                }
            }
            else sender.sendMessage(Bopbip.prefix + ChatColor.translateAlternateColorCodes('&', "&eYour playtime: &7") + ticksToTime(Bopbip.instance.getConfig().getInt("playtime." + player.getUniqueId())));
        }else sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
        return true;
    }
}
