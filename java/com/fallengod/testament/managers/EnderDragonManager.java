package com.fallengod.testament.bosses.impl;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class EnderDragonManager implements Listener {

    public static EnderDragon spawnCustomEnderDragon(Location location) {
        World world = location.getWorld();
        if (world == null) return null;

        EnderDragon dragon = world.spawn(location, EnderDragon.class);
        dragon.setCustomName("§5Resurrected Ender Dragon");
        dragon.setCustomNameVisible(true);

        buffDragon(dragon);
        return dragon;
    }

    public static void buffDragon(EnderDragon dragon) {
        PotionEffectType strength = Objects.requireNonNull(PotionEffectType.getByName("INCREASE_DAMAGE"));
        PotionEffectType resistance = Objects.requireNonNull(PotionEffectType.getByName("DAMAGE_RESISTANCE"));

        dragon.addPotionEffect(new PotionEffect(strength, Integer.MAX_VALUE, 1, true, false));
        dragon.addPotionEffect(new PotionEffect(resistance, Integer.MAX_VALUE, 1, true, false));
        dragon.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, true, false));
        dragon.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true, false));
    }
}