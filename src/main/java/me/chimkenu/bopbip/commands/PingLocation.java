package me.chimkenu.bopbip.commands;

import me.chimkenu.bopbip.Bopbip;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class PingLocation implements CommandExecutor {

    private static final HashMap<Player, Long> commandCD = new HashMap<>();
    private static boolean canSendCommand(Player player) {
        commandCD.putIfAbsent(player, System.currentTimeMillis() - 2501);
        return (System.currentTimeMillis() - commandCD.get(player)) > 2500;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (canSendCommand(player)) {

                // ray... trace? cast? whatever i want to get the location theyre pinging
                Vector origin = player.getEyeLocation().toVector();
                Vector direction = player.getEyeLocation().getDirection();
                double blocksAway = 200;
                double accuracy = 0.2;
                World world = player.getWorld();

                for (double d = 0; d <= blocksAway; d += accuracy) {
                    Location loc = origin.clone().add(direction.clone().multiply(d)).toLocation(world);
                    if (!world.getBlockAt(loc).getType().equals(Material.AIR)) {
                        loc.add(0, 300, 0);
                        Slime slime = world.spawn(loc, Slime.class);
                        slime.setInvulnerable(true);
                        slime.setInvisible(true);
                        slime.setSize(1);
                        slime.setAI(false);
                        slime.setGravity(false);
                        slime.setSilent(true);
                        slime.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 0));
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute as " + slime.getUniqueId() + " at @s run tp @s ~ ~-300 ~");

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                slime.remove();
                            }
                        }.runTaskLater(Bopbip.instance, 20 * 5);

                        sender.sendMessage(Bopbip.prefix + ChatColor.YELLOW + "boop");
                        return true;
                    }
                }

                sender.sendMessage(ChatColor.RED + "Too far?");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Only players may execute this command.");
        }
        return true;
    }
}
