package com.fallengod.testament.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * CustomDrops.java
 * 
 * Handles creation of custom items and drops for the Testament plugin.
 * Focuses on Ender Dragon specific drops and items not already implemented
 * in the main plugin system.
 */
public class CustomDrops {
    
    /**
     * Creates the Soul of the Ender item - the ultimate drop from the custom Ender Dragon
     * This is a NEW item specific to the enhanced dragon boss system
     * @return ItemStack representing the Soul of the Ender
     */
    public static ItemStack getSoulOfTheEnder() {
        ItemStack soul = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = soul.getItemMeta();
        
        if (meta != null) {
            meta.setDisplayName("§5§lSoul of the Ender");
            meta.setLore(Arrays.asList(
                "§7A fragment of the Ender Dragon's essence,",
                "§7pulsing with otherworldly power.",
                "§8",
                "§d§lLEGENDARY ITEM",
                "§c§lWARNING: §7Carrying this item marks you for death!",
                "§7A massive bounty will be placed on your head.",
                "§8",
                "§7This item represents the ultimate challenge -",
                "§7defeating the enhanced Ender Dragon boss."
            ));
            
            // Add enchantment glow
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            
            soul.setItemMeta(meta);
        }
        
        return soul;
    }
    
    /**
     * Creates a Dragon Scale item - rare crafting material from enhanced dragon
     * @return ItemStack representing a Dragon Scale
     */
    public static ItemStack getDragonScale() {
        ItemStack scale = new ItemStack(Material.TURTLE_SCUTE);
        ItemMeta meta = scale.getItemMeta();
        
        if (meta != null) {
            meta.setDisplayName("§5§lDragon Scale");
            meta.setLore(Arrays.asList(
                "§7A shimmering scale from the Fallen Aspect,",
                "§7radiating with draconic energy.",
                "§8",
                "§5§lRARE MATERIAL",
                "§7Used in advanced dragon-themed crafting"
            ));
            
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            scale.setItemMeta(meta);
        }
        
        return scale;
    }
    
    /**
     * Creates Ender Essence - magical component from dragon abilities
     * @return ItemStack representing Ender Essence
     */
    public static ItemStack getEnderEssence() {
        ItemStack essence = new ItemStack(Material.ENDER_PEARL);
        ItemMeta meta = essence.getItemMeta();
        
        if (meta != null) {
            meta.setDisplayName("§d§lEnder Essence");
            meta.setLore(Arrays.asList(
                "§7Concentrated essence of teleportation magic,",
                "§7extracted from the dragon's abilities.",
                "§8",
                "§d§lMAGICAL COMPONENT",
                "§7Contains the power of dimensional travel"
            ));
            
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            essence.setItemMeta(meta);
        }
        
        return essence;
    }
    
    /**
     * Creates a Void Spawn Egg - summons enhanced endermites
     * @return ItemStack representing a Void Spawn Egg
     */
    public static ItemStack getVoidSpawnEgg() {
        ItemStack egg = new ItemStack(Material.ENDERMITE_SPAWN_EGG);
        ItemMeta meta = egg.getItemMeta();
        
        if (meta != null) {
            meta.setDisplayName("§8§lVoid Spawn Egg");
            meta.setLore(Arrays.asList(
                "§7An egg infused with void energy,",
                "§7capable of spawning enhanced endermites.",
                "§8",
                "§8§lVOID ITEM",
                "§7Spawns: Enhanced Endermite (20 HP, Strength I)"
            ));
            
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            egg.setItemMeta(meta);
        }
        
        return egg;
    }
}