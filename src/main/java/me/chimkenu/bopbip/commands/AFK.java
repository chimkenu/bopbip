package me.chimkenu.bopbip.commands;

import me.chimkenu.bopbip.Bopbip;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AFK implements CommandExecutor {

    public static ArrayList<Player> afkList = new ArrayList<>();

    public static void setAFK(Player player, boolean isAFK) {
        if (isAFK == afkList.contains(player)) return;
        if (isAFK) {
            afkList.add(player);
            player.setPlayerListName(ChatColor.RED + "AFK " + ChatColor.RESET + player.getDisplayName());
        } else {
            afkList.remove(player);
            player.setPlayerListName(player.getDisplayName());
        }

        Bukkit.broadcastMessage(Bopbip.prefix + ChatColor.DARK_AQUA + player.getDisplayName() + " " + (isAFK ? (ChatColor.RED + "is now AFK") : (ChatColor.GREEN + "is no longer AFK")));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            setAFK(player, !afkList.contains(player));

        }
        else sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
        return true;
    }
}
