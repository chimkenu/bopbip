package me.chimkenu.bopbip.commands;

import me.chimkenu.bopbip.Bopbip;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class Tag implements CommandExecutor {
    public static boolean isGameRunning = false;
    public static Player tagged;
    public static Player target;

    private void endGame(Player player) {
        Bukkit.broadcastMessage(Bopbip.prefix + ChatColor.DARK_AQUA + player.getDisplayName() + ChatColor.YELLOW + " ended the game. " + ChatColor.DARK_AQUA + tagged.getDisplayName() + ChatColor.YELLOW + " was the last tagged player.");
        tagged.setPlayerListName(tagged.getDisplayName());
        isGameRunning = false;
    }

    private void endGame() {
        Bukkit.broadcastMessage(Bopbip.prefix + ChatColor.YELLOW + "Game ended! " + ChatColor.DARK_AQUA + tagged.getDisplayName() + ChatColor.YELLOW + " was the last tagged player.");
        tagged.setPlayerListName(tagged.getDisplayName());
        isGameRunning = false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length < 1) {
                player.sendMessage(Bopbip.prefix + ChatColor.YELLOW + "Tag??? " + ChatColor.DARK_AQUA + isGameRunning);
            } else {
                if (isGameRunning) {
                    endGame(player);
                } else {
                    Bukkit.broadcastMessage(Bopbip.prefix + ChatColor.DARK_AQUA + player.getDisplayName() + ChatColor.YELLOW + " started a game of tag.");
                    tagged = player;

                    Bukkit.broadcastMessage(Bopbip.prefix + ChatColor.DARK_AQUA + tagged.getDisplayName() + ChatColor.YELLOW + " is now tag!");
                    tagged.setPlayerListName(player.getDisplayName() + ChatColor.RED + " TAG");
                    isGameRunning = true;
                    new BukkitRunnable() {
                        int time = 60*10*20;
                        @Override
                        public void run() {
                            if (target == null || target == tagged) {
                                ArrayList<Player> allPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
                                allPlayers.remove(tagged);
                                int random = new Random().nextInt(allPlayers.size());
                                target = allPlayers.get(random);
                            }

                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (p == tagged) {
                                    p.setCompassTarget(target.getLocation());
                                } else {
                                    p.setCompassTarget(tagged.getLocation());
                                }
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(time)));
                            }
                            time--;
                            if (time <= 0) {
                                endGame();
                                this.cancel();
                            } else if (!isGameRunning) {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(Bopbip.instance, 1, 1);
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Only players may execute this command.");
        }
        return true;
    }
}
