package me.chimkenu.bopbip.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Help implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                """

                        -------[ &aCommands &r]-------
                         &b/help &r- &7sends the commands list
                         &b/afk &r- &7toggles your afk status
                         &b/lag &r- &7sends current server tps and your ping
                         &b/ping &8<player> &r- &7pings a player in game
                         &b/playtime &8<player> &r- &7shows a player's total time here in bopbip. &8(you can also do &b/playtime top &8for the whole list)
                         &b/compliment &8<player> &r- &7compliments a player
                         &b/insult &8<player> &r- &7insults a player

                        -------[ =+= ]-------"""
        ));
        return true;
    }
}
