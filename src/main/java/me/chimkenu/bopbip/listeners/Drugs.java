package me.chimkenu.bopbip.listeners;

import me.chimkenu.bopbip.Bopbip;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Drugs implements Listener {

    public static ItemStack freshAirExtractionTool = Bopbip.createItem(Material.SHEARS, 1, "&3Fresh Air™ Extraction Tool", true);
    public static ItemStack ephedraPlant = Bopbip.createItem(Material.FERN, 1, "&2Ephedra Plant", true);
    public static ItemStack crystalMeth = Bopbip.createItem(Material.AMETHYST_SHARD, 1, "&5Crystal Meth", true);
    public static ItemStack ergotFungus = Bopbip.createItem(Material.CRIMSON_ROOTS, 1, "&4Ergot Fungus", true);
    public static ItemStack lsd = Bopbip.createItem(Material.PRISMARINE_CRYSTALS, 1, "&bLSD", true);
    public static ItemStack erythroxylaceaeCoca = Bopbip.createItem(Material.COCOA_BEANS, 1, "&6Erythroxylaceae coca plant", true);
    public static ItemStack cocaine = Bopbip.createItem(Material.SUGAR, 1, "&f&lCocaine", true);

    private BukkitRunnable pulse(Player player, PotionEffectType potionEffectType, int amplifier) {
        return new BukkitRunnable() {
            int i = (20 * 60) / 5;
            @Override
            public void run() {
                if (!player.isOnline()) this.cancel();
                if (i < 1) this.cancel();
                i -= 1;
                player.addPotionEffect(new PotionEffect(potionEffectType, 2, amplifier));
            }
        };
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(freshAirExtractionTool)) {
            Material blockType = e.getBlock().getType();
            if (blockType.equals(Material.LARGE_FERN)) {
                e.setDropItems(false);
                e.setExpToDrop(5);
                for (int i = 0; i < 3; i++) {
                    e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(), ephedraPlant);
                }
            } else if (blockType.equals(Material.COCOA)) {
                e.setDropItems(false);
                e.setExpToDrop(5);
                e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(), erythroxylaceaeCoca);
                e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(), erythroxylaceaeCoca);
                e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(), erythroxylaceaeCoca);

            } else if (blockType.equals(Material.CRIMSON_ROOTS)) {
                e.setDropItems(false);
                e.setExpToDrop(5);
                e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(), ergotFungus);
                e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(), ergotFungus);
                e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(), ergotFungus);
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            ItemStack item = e.getItem();
            Player player = e.getPlayer();

            if (item == null) return;

            if (item.isSimilar(crystalMeth)) {
                if (!player.getInventory().getItemInMainHand().isSimilar(crystalMeth)) player.getInventory().getItemInOffHand().setAmount(e.getPlayer().getInventory().getItemInOffHand().getAmount() - 1);
                else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60,2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 60,0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 60,1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 20 * 60,0));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!player.isOnline()) this.cancel();
                        player.sendMessage(ChatColor.DARK_GRAY + "You feel the effects wear off...");

                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 60,4));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 60,1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 60,0));

                        pulse(player, PotionEffectType.SLOW, 4).runTaskTimer(Bopbip.instance, 0, 5);
                    }
                }.runTaskLater(Bopbip.instance, 20 * 60);

            } else if (item.isSimilar(lsd)) {
                if (!player.getInventory().getItemInMainHand().isSimilar(lsd)) player.getInventory().getItemInOffHand().setAmount(e.getPlayer().getInventory().getItemInOffHand().getAmount() - 1);
                else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60,4));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 60,4));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 60,2));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!player.isOnline()) this.cancel();
                        player.sendMessage(ChatColor.DARK_GRAY + "You feel the effects wear off...");

                        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 20 * 60,0));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 60,0));

                        pulse(player, PotionEffectType.SPEED, 4).runTaskTimer(Bopbip.instance, 0, 5);
                    }
                }.runTaskLater(Bopbip.instance, 20 * 60);

            } else if (item.isSimilar(cocaine)) {
                if (!player.getInventory().getItemInMainHand().isSimilar(cocaine)) player.getInventory().getItemInOffHand().setAmount(e.getPlayer().getInventory().getItemInOffHand().getAmount() - 1);
                else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60,1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 60,0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 60,0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 20 * 60,0));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!player.isOnline()) this.cancel();
                        player.sendMessage(ChatColor.DARK_GRAY + "You feel the effects wear off...");

                        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 5, 1));

                        pulse(player, PotionEffectType.SLOW, 9).runTaskTimer(Bopbip.instance, 0, 5);

                        // freeze effect!
                        if (new Random().nextInt(4) + 1 == 1) {
                            player.setFreezeTicks(Integer.MAX_VALUE);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (player.isOnline()) player.setFreezeTicks(0);
                                }
                            }.runTaskLater(Bopbip.instance, 20 * 20);
                        }
                    }
                }.runTaskLater(Bopbip.instance, 20*60);
            }
        }
    }
}
