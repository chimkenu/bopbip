package me.chimkenu.bopbip.commands;

import me.chimkenu.bopbip.Bopbip;
import me.chimkenu.bopbip.TPS;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Lag implements CommandExecutor {

    private static String colorTPS(double d, boolean isTicks) {
        ChatColor color = ChatColor.DARK_GREEN;
        if (isTicks) {
            if (d >= 19) {
                if (d < 20) { color = ChatColor.GREEN; }
            }
            else if (d >= 17) color = ChatColor.YELLOW;
            else if (d >= 14) color = ChatColor.GOLD;
            else color = ChatColor.RED;

        } else {
            if (d >= 19*5) {
                if (d < 20*5) { color = ChatColor.GREEN; }
            }
            else if (d >= 17*5) color = ChatColor.YELLOW;
            else if (d >= 14*5) color = ChatColor.GOLD;
            else color = ChatColor.RED;
        }
        return color + "" + Math.round(d);
    }

    private static String colorPing(int i) {
        ChatColor color = ChatColor.DARK_GREEN;
        if (i > 50) {
            if (i < 150) color = ChatColor.GREEN;
            else if (i < 250) color = ChatColor.YELLOW;
            else if (i < 500) color = ChatColor.GOLD;
            else if (i < 600) color = ChatColor.RED;
            else color = ChatColor.DARK_RED;
        }
        return color + "" + i;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                Bopbip.prefix + "&bCurrent TPS: " +
                        colorTPS(TPS.getTPS(), true) +
                        "\n&bServer performance: " + colorTPS(TPS.getTPS()*5, false) + "%" +
                        (sender instanceof Player ? "\n&bYour ping: " + colorPing(((Player) sender).getPing()) : "")
                ));
        return true;
    }
}
