package me.chimkenu.bopbip.commands;

import me.chimkenu.bopbip.Bopbip;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class Compliment implements CommandExecutor {

    private String[] compliments = {
            "You're amazing at this game!",
            "Your kindness is a balm to all who encounter it.",
            "When you make up your mind, nothing stands in your way.",
            "You light up the room.",
            "Colors seem brighter when you're around.",
            "You have impeccable manners.",
            "That thing you don't like about yourself is what makes you really interesting.",
            "You're a very smart cookie.",
            "You deserve a hug.",
            "Actions speak louder than words, and yours tells an incredibly story.",
            "You being alive gives me reason to keep going.",
            "You are a gift.",
            "I appreciate you.",
            "You are the most perfect you there is.",
            "You are more than enough.",
            "You are one of a kind.",
            "You radiate good vibes.",
            "How do you make me so overly happy?",
            "You do anything so effortlessly.",
            "You dedication to your craft is in inspiring.",
            "You're more amazing than you will ever realize.",
            "Your outstanding energy is contagious.",
            "You never fail to make me smile.",
            "Your sense of humor is the most amazing thing!",
            "Your capabilities are endless.",
            "I wish I was as great as you.",
            "big pp u <3",
            "are you a subreddit cuz your nextfuckinglevel",
            "Do you have a map because i'm getting lost in your eyes.",
            "&f/sender/ &8» &ailysm &3/player/",
            "We need more people like you.",
            "The world is so much better now that you are here."
    };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Provide an online player.");
                return true;
            }

            String message = compliments[new Random().nextInt(compliments.length)];

            message = message.replaceAll("/player/", target.getDisplayName());
            message = message.replaceAll("/sender/", ((Player) sender).getDisplayName());
            target.sendMessage(ChatColor.GREEN + ChatColor.translateAlternateColorCodes('&', message));

            sender.sendMessage(Bopbip.prefix + ChatColor.GREEN + "Sent the compliment.");
        }

        else sender.sendMessage(ChatColor.GREEN + "That's okay, please do " + ChatColor.GRAY + "/compliment <player>" + ChatColor.GREEN + " instead! I know you can do it!");
        return true;
    }
}
