package me.chimkenu.bopbip.listeners;

import me.chimkenu.bopbip.Bopbip;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Wet implements Listener {

    private void summonArmorStand(World world, Location loc, String name, double radius, double yMin, double yMax) {
        int timeInTicks = ThreadLocalRandom.current().nextInt(20, 40);

        loc.add(ThreadLocalRandom.current().nextDouble(-radius, radius), ThreadLocalRandom.current().nextDouble(yMin, yMax), ThreadLocalRandom.current().nextDouble(-radius, radius));
        loc.add(0, 300, 0);

        ArmorStand armorStand = world.spawn(loc, ArmorStand.class);
        armorStand.addScoreboardTag("DAMAGE");
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setMarker(true);
        armorStand.setInvisible(true);
        armorStand.setSmall(true);
        armorStand.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
        armorStand.setCustomNameVisible(true);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute as " + armorStand.getUniqueId() + " at @s run tp @s ~ ~-300 ~");

        new BukkitRunnable() {
            @Override
            public void run() {
                armorStand.remove();
            }
        }.runTaskLater(Bopbip.instance, timeInTicks);
    }

    private void summonArmorStandWithNameForAShortPeriodOfTimeAtARandomLocationNearProvidedLocation(World world, Location loc, String name) {
        summonArmorStand(world, loc, name, 2, 1, 3);
    }

    private final HashMap<Player, Long> cd = new HashMap<>();
    private boolean lastWet(Player player) {
        cd.putIfAbsent(player, System.currentTimeMillis() - 10001);
        return (System.currentTimeMillis() - cd.get(player)) > 10000;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity().getType().equals(EntityType.ARMOR_STAND)) return;
        double damage = e.getFinalDamage();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        summonArmorStandWithNameForAShortPeriodOfTimeAtARandomLocationNearProvidedLocation(e.getEntity().getWorld(), e.getEntity().getLocation(), "&c&l" + df.format(damage));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getPlayer().getWorld().getBlockAt(e.getPlayer().getLocation()).getType().equals(Material.WATER) && lastWet(e.getPlayer())) {
            summonArmorStandWithNameForAShortPeriodOfTimeAtARandomLocationNearProvidedLocation(e.getPlayer().getWorld(), e.getPlayer().getLocation(), "&b&lwet");
            cd.put(e.getPlayer(), System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent e) {
        if (e.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)) {
            new BukkitRunnable() {
                final Player player = e.getPlayer();
                final String name = player.getDisplayName().equalsIgnoreCase("SirSunlight") ? "a mimir" : "z";

                @Override
                public void run() {
                    if (!player.isOnline()) this.cancel();
                    if (!player.isSleeping()) this.cancel();
                    summonArmorStand(player.getWorld(), player.getLocation(), name, 1, 1, 1.5);
                }
            }.runTaskTimer(Bopbip.instance, 2, 20);
        }
    }
}
