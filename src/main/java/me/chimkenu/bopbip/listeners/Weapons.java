package me.chimkenu.bopbip.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class Weapons implements Listener {

    private final HashMap<Player, Long> cd = new HashMap<>();
    private boolean canUse(Player player) {
        cd.putIfAbsent(player, System.currentTimeMillis());
        return (System.currentTimeMillis() - cd.get(player)) > 150;
    }

    private Location locationInFront(Player player) {
        Vector origin = player.getEyeLocation().toVector();
        Vector direction = player.getEyeLocation().getDirection();
        origin = origin.add(direction.multiply(4));
        return origin.toLocation(player.getWorld());
    }

    private void summonSweepParticle(Player player) {
        player.getWorld().spawnParticle(Particle.SWEEP_ATTACK, locationInFront(player),5);
    }

    @EventHandler
    public void onClickShortbow(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (!player.getInventory().getItemInMainHand().getType().equals(Material.BOW)) return;
        if (!(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))) return;
        if (!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().toLowerCase().contains("shortbow")) return;

        if (!canUse(player)) return;

        if (player.getInventory().contains(Material.ARROW)) {
            player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(1.5));
            for (ItemStack item : player.getInventory()) {
                if (item != null && item.getType().equals(Material.ARROW)) {
                    item.setAmount(item.getAmount() - 1);
                    break;
                }
            }

            ItemStack heldItem = player.getInventory().getItemInMainHand();
            Damageable damageable = (Damageable) heldItem.getItemMeta();
            damageable.setDamage(damageable.getDamage() + 1);
            heldItem.setItemMeta(damageable);

            cd.put(player, System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onClickScythe(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();

        if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (!mainHand.getType().toString().contains("HOE")) return;
            if (mainHand.getItemMeta() != null && mainHand.getItemMeta().getDisplayName().toLowerCase().contains("scythe")) {
                Location loc = locationInFront(player);
                for (Entity entity : player.getWorld().getNearbyEntities(loc, 2, 2, 2)) {
                    LivingEntity livingEntity;

                    try {
                        livingEntity = (LivingEntity) entity;
                    } catch (ClassCastException exception) {
                        continue;
                    }

                    if (!(livingEntity instanceof Player target && target == player)) {
                        livingEntity.damage(4, player);
                    }
                }
                summonSweepParticle(player);
            }
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent e) {
        Player player = e.getPlayer();
        PlayerInventory inventory = player.getInventory();
        ItemMeta mainHand = inventory.getItemInMainHand().getItemMeta();
        ItemMeta offHand = inventory.getItemInOffHand().getItemMeta();

        if ((mainHand != null && mainHand.getDisplayName().toLowerCase().contains("whip")) || (offHand != null && offHand.getDisplayName().toLowerCase().contains("whip"))) {
            if (e.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY) && e.getCaught() != null) {
                LivingEntity caught = (LivingEntity) e.getCaught();
                caught.setHealth(caught.getHealth() - 1);
                caught.damage(1);
                caught.setNoDamageTicks(0);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player player) {
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            if (mainHand.getType().toString().contains("HOE") && mainHand.getItemMeta() != null && mainHand.getItemMeta().getDisplayName().toLowerCase().contains("scythe")) {
                e.setDamage(e.getDamage() + 4);
                summonSweepParticle(player);
            }
        }
    }
}
