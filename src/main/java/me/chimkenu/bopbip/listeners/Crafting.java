package me.chimkenu.bopbip.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.UUID;

public class Crafting implements Listener {
    AttributeModifier tier1 = new AttributeModifier(UUID.randomUUID(), "1", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
    AttributeModifier tier2 = new AttributeModifier(UUID.randomUUID(), "2", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
    AttributeModifier tier3 = new AttributeModifier(UUID.randomUUID(), "3", 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);

    @EventHandler
    void onPrepareSmithing(PrepareSmithingEvent event) {
        SmithingInventory inventory = event.getInventory();

        ItemStack tool = inventory.getItem(0);
        ItemStack modifier = inventory.getItem(1);

        if (tool == null || modifier == null) {
            return;
        }

        if (tool.getType() != Material.ELYTRA || modifier.getType() != Material.NETHER_STAR) {
            return;
        }

        ItemStack result = tool.clone();
        ItemMeta meta = result.getItemMeta();

        if (meta == null) {
            return;
        }

        Collection<AttributeModifier> attributeModifiers = meta.getAttributeModifiers(Attribute.GENERIC_ARMOR);

        // checking tier
        // none

        if (attributeModifiers == null || attributeModifiers.isEmpty()) {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, tier1);
        }

        // 1
        else if (attributeModifiers.contains(tier1)) {
            meta.removeAttributeModifier(Attribute.GENERIC_ARMOR);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, tier2);
        }

        // 2
        else if (attributeModifiers.contains(tier2)) {
            meta.removeAttributeModifier(Attribute.GENERIC_ARMOR);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, tier3);
        }

        // 3 (shouldnt do anything)
        else {
            event.setResult(new ItemStack(Material.AIR));
            return;
        }

        result.setItemMeta(meta);
        event.setResult(result);
    }
}
