package com.fallengod.testament.bosses;

import com.fallengod.testament.items.CustomDrops;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * EnderDragonManager.java
 * 
 * Manages the enhanced Ender Dragon boss for the Veil God testament.
 * This dragon serves as the ultimate guardian of the End dimension,
 * providing the final challenge for players seeking the Veil God's power.
 * 
 * Features:
 * - Difficulty-based scaling system
 * - Phase-based combat mechanics
 * - Integration with Testament bounty system
 * - Custom drops including Soul of the Ender
 */
public class EnderDragonManager implements Listener {
    
    private static final Random random = new Random();
    private static final String FALLEN_ASPECT_NAME = "§5Fallen Aspect of the End";
    private static final String RESURRECTED_NAME = "§5Resurrected Ender Dragon";
    
    /**
     * Buffs the Ender Dragon with enhanced stats and abilities based on world difficulty
     * @param dragon The EnderDragon entity to buff
     */
    public static void buffDragon(EnderDragon dragon) {
        World world = dragon.getWorld();
        Difficulty difficulty = world.getDifficulty();
        
        // Set custom name
        dragon.setCustomName(FALLEN_ASPECT_NAME);
        dragon.setCustomNameVisible(true);
        
        // Apply difficulty-based scaling
        double healthMultiplier = getHealthMultiplier(difficulty);
        double baseHealth = 400.0; // Base health for Normal difficulty
        double scaledHealth = baseHealth * healthMultiplier;
        
        dragon.getAttribute(Attribute.MAX_HEALTH).setBaseValue(scaledHealth);
        dragon.setHealth(scaledHealth);
        
        // Apply difficulty-based potion effects
        int strengthLevel = getEffectLevel(difficulty, 2);
        dragon.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, strengthLevel));
        dragon.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, Integer.MAX_VALUE, 1));
        dragon.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        
        // Start special ability scheduler with difficulty-based timing
        startDragonAbilities(dragon, difficulty);
        
        // Announce the dragon's arrival
        world.playSound(dragon.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 3.0f, 0.5f);
        world.spawnParticle(Particle.DRAGON_BREATH, dragon.getLocation(), 100, 5, 5, 5, 0.5);
        
        // Testament-style announcements with coordinates
        Location loc = dragon.getLocation();
        Bukkit.broadcastMessage("§5§l[Testament] §fThe Fallen Aspect of the End has awakened in " + world.getName() + "!");
        Bukkit.broadcastMessage("§d§lLocation: §f" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
        Bukkit.broadcastMessage("§c§lWARNING: §fThis guardian of the Veil God seeks to test your worthiness!");
    }
    
    /**
     * Gets health multiplier based on difficulty
     */
    private static double getHealthMultiplier(Difficulty difficulty) {
        switch (difficulty) {
            case PEACEFUL: return 0.5;
            case EASY: return 0.75;
            case NORMAL: return 1.0;
            case HARD: return 1.5;
            default: return 1.0;
        }
    }
    
    /**
     * Gets effect level based on difficulty
     */
    private static int getEffectLevel(Difficulty difficulty, int baseLevel) {
        switch (difficulty) {
            case PEACEFUL: return Math.max(0, baseLevel - 1);
            case EASY: return baseLevel;
            case NORMAL: return baseLevel;
            case HARD: return baseLevel + 1;
            default: return baseLevel;
        }
    }
    
    /**
     * Gets ability cooldown based on difficulty (in ticks)
     */
    private static long getAbilityCooldown(Difficulty difficulty) {
        switch (difficulty) {
            case PEACEFUL: return 600L; // 30 seconds
            case EASY: return 500L; // 25 seconds
            case NORMAL: return 400L; // 20 seconds
            case HARD: return 300L; // 15 seconds
            default: return 400L;
        }
    }
    
    /**
     * Starts the dragon's special abilities on a timer
     * @param dragon The EnderDragon to apply abilities to
     * @param difficulty The world difficulty for scaling
     */
    private static void startDragonAbilities(EnderDragon dragon, Difficulty difficulty) {
        long cooldown = getAbilityCooldown(difficulty);
        
        new BukkitRunnable() {
            @Override
            public void run() {
                if (dragon.isDead() || !dragon.isValid()) {
                    this.cancel();
                    return;
                }
                
                // Random special ability every 15-30 seconds
                int ability = random.nextInt(3);
                switch (ability) {
                    case 0:
                        dragonBreathAttack(dragon, difficulty);
                        break;
                    case 1:
                        summonEndermites(dragon, difficulty);
                        break;
                    case 2:
                        teleportAttack(dragon, difficulty);
                        break;
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("testament"), cooldown, cooldown);
    }
    
    /**
     * Dragon breath attack ability
     * @param difficulty World difficulty for scaling
     */
    private static void dragonBreathAttack(EnderDragon dragon, Difficulty difficulty) {
        Location loc = dragon.getLocation();
        World world = loc.getWorld();
        
        // Scale number of breath clouds based on difficulty
        int cloudCount = difficulty == Difficulty.PEACEFUL ? 3 : 
                        difficulty == Difficulty.EASY ? 4 : 
                        difficulty == Difficulty.NORMAL ? 5 : 7;
        
        double damageMultiplier = getHealthMultiplier(difficulty); // Reuse multiplier for damage
        
        // Create lingering dragon breath clouds
        for (int i = 0; i < cloudCount; i++) {
            Location breathLoc = loc.clone().add(
                random.nextInt(21) - 10,
                random.nextInt(11) - 5,
                random.nextInt(21) - 10
            );
            
            world.spawnParticle(Particle.DRAGON_BREATH, breathLoc, 50, 2, 2, 2, 0.1);
            
            // Damage nearby players
            for (Entity entity : world.getNearbyEntities(breathLoc, 3, 3, 3)) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    double scaledDamage = 6.0 * damageMultiplier;
                    player.damage(scaledDamage, dragon);
                    
                    int witherLevel = getEffectLevel(difficulty, 1);
                    int witherDuration = (int)(100 * damageMultiplier);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, witherDuration, witherLevel));
                }
            }
        }
        
        world.playSound(loc, Sound.ENTITY_ENDER_DRAGON_SHOOT, 2.0f, 0.8f);
    }
    
    /**
     * Summon endermites ability
     * @param difficulty World difficulty for scaling
     */
    private static void summonEndermites(EnderDragon dragon, Difficulty difficulty) {
        Location loc = dragon.getLocation();
        World world = loc.getWorld();
        
        // Scale endermite count based on difficulty
        int baseCount = difficulty == Difficulty.PEACEFUL ? 2 : 
                       difficulty == Difficulty.EASY ? 3 : 
                       difficulty == Difficulty.NORMAL ? 4 : 6;
        int count = baseCount + random.nextInt(2);
        
        for (int i = 0; i < count; i++) {
            Location spawnLoc = loc.clone().add(
                random.nextInt(11) - 5,
                random.nextInt(6) - 3,
                random.nextInt(11) - 5
            );
            
            Endermite endermite = (Endermite) world.spawnEntity(spawnLoc, EntityType.ENDERMITE);
            endermite.setCustomName("§5Veil Spawn");
            
            // Scale endermite health and strength
            double endermiteHealth = 20.0 * getHealthMultiplier(difficulty);
            endermite.getAttribute(Attribute.MAX_HEALTH).setBaseValue(endermiteHealth);
            endermite.setHealth(endermiteHealth);
            
            int strengthLevel = getEffectLevel(difficulty, 1);
            endermite.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, strengthLevel));
        }
        
        world.playSound(loc, Sound.ENTITY_ENDERMITE_AMBIENT, 2.0f, 0.5f);
        world.spawnParticle(Particle.PORTAL, loc, 30, 3, 3, 3, 1.0);
    }
    
    /**
     * Teleport attack ability
     * @param difficulty World difficulty for scaling
     */
    private static void teleportAttack(EnderDragon dragon, Difficulty difficulty) {
        Location loc = dragon.getLocation();
        World world = loc.getWorld();
        
        // Scale detection range based on difficulty
        double range = difficulty == Difficulty.PEACEFUL ? 30 : 
                      difficulty == Difficulty.EASY ? 40 : 
                      difficulty == Difficulty.NORMAL ? 50 : 70;
        
        // Find nearby players
        for (Entity entity : world.getNearbyEntities(loc, range, range, range)) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                
                // Teleport dragon near player for surprise attack
                Location playerLoc = player.getLocation();
                Location teleportLoc = playerLoc.clone().add(
                    random.nextInt(21) - 10,
                    5 + random.nextInt(10),
                    random.nextInt(21) - 10
                );
                
                dragon.teleport(teleportLoc);
                
                // Effects
                world.playSound(teleportLoc, Sound.ENTITY_ENDERMAN_TELEPORT, 3.0f, 0.5f);
                world.spawnParticle(Particle.PORTAL, teleportLoc, 50, 2, 2, 2, 1.0);
                
                player.sendMessage("§5The Fallen Aspect phases through reality near you!");
                break; // Only teleport to one player
            }
        }
    }
    
    @EventHandler
    public void onEnderDragonDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            EnderDragon dragon = (EnderDragon) event.getEntity();
            
            // Check if this is our custom dragon
            if (FALLEN_ASPECT_NAME.equals(dragon.getCustomName())) {
                handleCustomDragonDeath(dragon, event);
            }
        }
    }
    
    /**
     * Handles the death of the custom Ender Dragon
     */
    private void handleCustomDragonDeath(EnderDragon dragon, EntityDeathEvent event) {
        Location loc = dragon.getLocation();
        World world = loc.getWorld();
        
        // Clear default drops
        event.getDrops().clear();
        event.setDroppedExp(0);
        
        // Testament-style custom drops
        ItemStack soulOfTheEnder = CustomDrops.getSoulOfTheEnder();
        Item dropped = world.dropItemNaturally(loc, soulOfTheEnder);
        dropped.setCustomNameVisible(true);
        
        // Chance for Veil God fragments (Testament integration)
        if (random.nextDouble() < 0.3) { // 30% chance
            // This would integrate with the main plugin's fragment system
            // ItemStack fragment = TestamentAPI.createFragment(GodType.VEIL, 1 + random.nextInt(7));
            // world.dropItemNaturally(loc, fragment);
        }
        
        // Epic death effects
        world.playSound(loc, Sound.ENTITY_WITHER_SPAWN, 3.0f, 0.8f);
        world.playSound(loc, Sound.ENTITY_ENDER_DRAGON_DEATH, 2.0f, 0.5f);
        world.spawnParticle(Particle.DRAGON_BREATH, loc, 100, 5, 5, 5, 0.5);
        world.spawnParticle(Particle.EXPLOSION, loc, 10, 2, 2, 2, 0.1);
        
        // Testament-style server announcements with coordinates
        Bukkit.broadcastMessage("§5§l[Testament] §fThe Fallen Aspect of the End has been vanquished!");
        Bukkit.broadcastMessage("§d§lThe Soul of the Ender has been dropped at: §f" + 
            loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + " in " + world.getName());
        Bukkit.broadcastMessage("§c§lWARNING: §fWhoever claims this legendary item will receive a massive bounty!");
        Bukkit.broadcastMessage("§7The path to the Veil God's power grows clearer...");
        
        // Resurrection mechanic - 25% chance to resurrect after 5 minutes
        if (random.nextDouble() < 0.25) {
            scheduleResurrection(loc);
        }
    }
    
    /**
     * Schedules the dragon's resurrection
     */
    private void scheduleResurrection(Location deathLocation) {
        new BukkitRunnable() {
            @Override
            public void run() {
                World world = deathLocation.getWorld();
                if (world == null) return;
                
                // Check if there's already a dragon in the world
                boolean hasActiveDragon = world.getEntitiesByClass(EnderDragon.class).stream()
                    .anyMatch(d -> d.getCustomName() != null && 
                        (FALLEN_ASPECT_NAME.equals(d.getCustomName()) || RESURRECTED_NAME.equals(d.getCustomName())));
                
                if (!hasActiveDragon) {
                    Difficulty difficulty = world.getDifficulty();
                    
                    // Spawn resurrected dragon
                    Location spawnLoc = deathLocation.clone().add(0, 20, 0);
                    EnderDragon resurrected = (EnderDragon) world.spawnEntity(spawnLoc, EntityType.ENDER_DRAGON);
                    
                    resurrected.setCustomName(RESURRECTED_NAME);
                    resurrected.setCustomNameVisible(true);
                    
                    // Enhanced stats for resurrected form with difficulty scaling
                    double resurrectedHealth = 600.0 * getHealthMultiplier(difficulty);
                    resurrected.getAttribute(Attribute.MAX_HEALTH).setBaseValue(resurrectedHealth);
                    resurrected.setHealth(resurrectedHealth);
                    
                    int strengthLevel = getEffectLevel(difficulty, 3);
                    resurrected.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, strengthLevel));
                    resurrected.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, Integer.MAX_VALUE, 2));
                    
                    // Resurrection effects
                    world.playSound(spawnLoc, Sound.ENTITY_WITHER_SPAWN, 3.0f, 0.5f);
                    world.spawnParticle(Particle.SOUL_FIRE_FLAME, spawnLoc, 200, 10, 10, 10, 0.3);
                    
                    // Testament-style resurrection announcements
                    Bukkit.broadcastMessage("§5§l[Testament] §fThe Fallen Aspect has been RESURRECTED!");
                    Bukkit.broadcastMessage("§d§lLocation: §f" + spawnLoc.getBlockX() + ", " + spawnLoc.getBlockY() + ", " + spawnLoc.getBlockZ() + " in " + world.getName());
                    Bukkit.broadcastMessage("§c§lThe guardian of the Veil God returns with greater fury!");
                    
                    startDragonAbilities(resurrected, difficulty);
                }
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("testament"), 6000L); // 5 minutes
    }
}