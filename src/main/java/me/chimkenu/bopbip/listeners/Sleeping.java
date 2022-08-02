package me.chimkenu.bopbip.listeners;

import me.chimkenu.bopbip.Bopbip;
import me.chimkenu.bopbip.commands.AFK;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.ArrayList;

public class Sleeping implements Listener {

    private static void nextDay(World world) {
        Bukkit.broadcastMessage(Bopbip.prefix + ChatColor.YELLOW + "Sleeping through the night...");
        world.setTime(0);
        world.setStorm(false);
        world.setThundering(false);
        world.setWeatherDuration(0);
    }

    private static void testForNextDay(Player player, int sleeping) {
        if ((player.getWorld().getTime() >= 12010 && player.getWorld().getTime() <= 23991) || player.getWorld().isThundering()) {
            ArrayList<Player> required = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.isSleeping()) sleeping++;
                if (!p.getWorld().getEnvironment().equals(World.Environment.NETHER) && !p.getWorld().getEnvironment().equals(World.Environment.THE_END) && !AFK.afkList.contains(p))
                    required.add(p);
            }

            Bukkit.broadcastMessage(Bopbip.prefix + ChatColor.DARK_AQUA + sleeping + "/" + required.size() + ChatColor.YELLOW + " players sleeping.");
            if (sleeping >= required.size()) nextDay(player.getWorld());
        }
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent e) {
        if (!e.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)) return;
        if (AFK.afkList.contains(e.getPlayer())) AFK.setAFK(e.getPlayer(), false);
        testForNextDay(e.getPlayer(), 1);
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent e) {
        if (AFK.afkList.contains(e.getPlayer())) AFK.setAFK(e.getPlayer(), false);
        testForNextDay(e.getPlayer(), 0);
    }
}
