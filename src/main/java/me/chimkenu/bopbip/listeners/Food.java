package me.chimkenu.bopbip.listeners;

import me.chimkenu.bopbip.Bopbip;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static me.chimkenu.bopbip.Bopbip.createItem;

public class Food implements Listener {
    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        if (e.getItem().isSimilar(Bopbip.hotDog)) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20*30, 0));
            e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel()+2);
            e.getPlayer().sendMessage(ChatColor.YELLOW + "ew.");
        } else if (e.getItem().isSimilar(Bopbip.cookedHotDog)) {
            for (int i = 0; i < 10; i++) {
                e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() + 20);
                e.getPlayer().setSaturation(e.getPlayer().getSaturation() + 20);
            }
            e.getPlayer().sendMessage(ChatColor.YELLOW + "yummy");
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            ItemStack item = e.getItem();
            Player player = e.getPlayer();

            if (item == null) return;

            if (item.isSimilar(Drops.nugget)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * 30, 0));
                player.setFoodLevel(e.getPlayer().getFoodLevel() + 2);
                player.sendMessage(ChatColor.YELLOW + "you know that's raw, right?");
                player.getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount()-1);
                if (!player.getInventory().getItemInMainHand().isSimilar(Drops.nugget)) player.getInventory().getItemInOffHand().setAmount(e.getPlayer().getInventory().getItemInOffHand().getAmount()-1);
                else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

            } else if (item.isSimilar(Bopbip.cookedNuggets)) {
                player.setFoodLevel(e.getPlayer().getFoodLevel() + 4);
                player.setSaturation(e.getPlayer().getSaturation() + 2.0f);
                player.sendMessage(ChatColor.YELLOW + "yummy");
                if (!player.getInventory().getItemInMainHand().isSimilar(Bopbip.cookedNuggets)) player.getInventory().getItemInOffHand().setAmount(e.getPlayer().getInventory().getItemInOffHand().getAmount()-1);
                else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
            }

            // fresh air products
            else if (item.getType().equals(Material.GLASS_BOTTLE)) {
                if (item.isSimilar(createItem(Material.GLASS_BOTTLE, 1, "&fFresh Air™ Original", false, ""))) {
                    player.setSaturation(e.getPlayer().getSaturation() + 1.0f);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 30, 0));
                    player.sendMessage(ChatColor.YELLOW + "You feel lighter.");
                    if (!player.getInventory().getItemInMainHand().isSimilar(createItem(Material.GLASS_BOTTLE, 1, "&fFresh Air™ Original", false, ""))) player.getInventory().getItemInOffHand().setAmount(e.getPlayer().getInventory().getItemInOffHand().getAmount()-1);
                    else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                }

                else if (item.isSimilar(createItem(Material.GLASS_BOTTLE, 1, "&5Fresh Air™ Grape", false, ""))) {
                    player.setSaturation(e.getPlayer().getSaturation() + 1.0f);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 30, 4));
                    player.sendMessage(ChatColor.YELLOW + "You feel lighter.");
                    if (!player.getInventory().getItemInMainHand().isSimilar(createItem(Material.GLASS_BOTTLE, 1, "&5Fresh Air™ Grape", false, ""))) player.getInventory().getItemInOffHand().setAmount(e.getPlayer().getInventory().getItemInOffHand().getAmount()-1);
                    else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                }

                else if (item.isSimilar(createItem(Material.GLASS_BOTTLE, 1, "&2Fresh Air™ Watermelon", false, ""))) {
                    player.setSaturation(e.getPlayer().getSaturation() + 1.0f);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 30, 4));
                    player.sendMessage(ChatColor.YELLOW + "You feel lighter.");
                    if (!player.getInventory().getItemInMainHand().isSimilar(createItem(Material.GLASS_BOTTLE, 1, "&2Fresh Air™ Watermelon", false, ""))) player.getInventory().getItemInOffHand().setAmount(e.getPlayer().getInventory().getItemInOffHand().getAmount()-1);
                    else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                }

                else if (item.isSimilar(createItem(Material.GLASS_BOTTLE, 1, "&bFresh Air™ Mint", false, ""))) {
                    player.setSaturation(e.getPlayer().getSaturation() + 1.0f);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 30, 4));
                    player.sendMessage(ChatColor.YELLOW + "You feel lighter.");
                    if (!player.getInventory().getItemInMainHand().isSimilar(createItem(Material.GLASS_BOTTLE, 1, "&bFresh Air™ Mint", false, ""))) player.getInventory().getItemInOffHand().setAmount(e.getPlayer().getInventory().getItemInOffHand().getAmount()-1);
                    else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                }

                else if (item.isSimilar(createItem(Material.GLASS_BOTTLE, 1, "&6Fresh Air™ Choccy", false, ""))) {
                    player.setSaturation(e.getPlayer().getSaturation() + 1.0f);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 30, 2));
                    player.sendMessage(ChatColor.YELLOW + "You feel lighter.");
                    if (!player.getInventory().getItemInMainHand().isSimilar(createItem(Material.GLASS_BOTTLE, 1, "&6Fresh Air™ Choccy", false, ""))) player.getInventory().getItemInOffHand().setAmount(e.getPlayer().getInventory().getItemInOffHand().getAmount()-1);
                    else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                }
            }
        }
    }
}
