package me.chimkenu.bopbip.commands;

import me.chimkenu.bopbip.Bopbip;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Ping implements CommandExecutor {

    private static final HashMap<Player, Long> commandCD = new HashMap<>();
    private static boolean canSendCommand(Player player) {
        if (commandCD.containsKey(player)) {
            return (System.currentTimeMillis() - commandCD.get(player)) > 5000;
        } else commandCD.put(player, System.currentTimeMillis() - 5000);
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }

        if (args.length > 0) {

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Bopbip.prefix + ChatColor.RED + "There is no online player named " + ChatColor.YELLOW + args[0] + ChatColor.RED + ".");
                return true;
            }

            if (!canSendCommand((Player) sender)) {
                sender.sendMessage(ChatColor.RED + "Please wait before doing this again.");
                return true;
            }
            commandCD.put((Player) sender, System.currentTimeMillis());

            sender.sendMessage(Bopbip.prefix + ChatColor.YELLOW + "Pinging " + ChatColor.DARK_AQUA + target.getDisplayName() + ChatColor.YELLOW + "...");
            target.sendMessage(Bopbip.prefix + ChatColor.DARK_AQUA + sender.getName() + ChatColor.YELLOW + " is pinging you.");
            new BukkitRunnable() {
                int t = 0;
                int i = 0;
                @Override
                public void run() {
                    if (i < 4) {target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 2.0f);}

                    if (i >= 9) i = 0;
                    else i++;

                    t++;
                    if (t >= 30) this.cancel();
                }
            }.runTaskTimer(Bopbip.instance, 1, 2);

        } else {
            sender.sendMessage(ChatColor.RED + "Please provide a player. " + ChatColor.GRAY + "/ping <player>");
        }
        return true;
    }
}
