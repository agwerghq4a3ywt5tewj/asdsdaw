package com.fallengod.testament.managers;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * CooldownManager.java
 * 
 * Manages cooldowns for various plugin features including:
 * - Fragment spawning cooldowns
 * - Command usage cooldowns
 * - Special ability cooldowns
 */
public class CooldownManager {
    
    private final Map<UUID, Long> fragmentCooldowns = new HashMap<>();
    private final Map<UUID, Long> mobDropCooldowns = new HashMap<>();
    private final Map<UUID, Long> commandCooldowns = new HashMap<>();
    
    // Cooldown durations in milliseconds
    private static final long FRAGMENT_COOLDOWN = 2 * 60 * 60 * 1000; // 2 hours
    private static final long MOB_DROP_COOLDOWN = 60 * 60 * 1000; // 1 hour
    private static final long COMMAND_COOLDOWN = 5 * 1000; // 5 seconds
    
    /**
     * Checks if a player is on fragment cooldown
     * @param playerId The player's UUID
     * @return true if on cooldown, false otherwise
     */
    public boolean isOnFragmentCooldown(UUID playerId) {
        Long lastUse = fragmentCooldowns.get(playerId);
        if (lastUse == null) return false;
        
        return (System.currentTimeMillis() - lastUse) < FRAGMENT_COOLDOWN;
    }
    
    /**
     * Sets fragment cooldown for a player
     * @param playerId The player's UUID
     */
    public void setFragmentCooldown(UUID playerId) {
        fragmentCooldowns.put(playerId, System.currentTimeMillis());
    }
    
    /**
     * Gets remaining fragment cooldown time in seconds
     * @param playerId The player's UUID
     * @return remaining time in seconds, or 0 if no cooldown
     */
    public long getRemainingFragmentCooldown(UUID playerId) {
        Long lastUse = fragmentCooldowns.get(playerId);
        if (lastUse == null) return 0;
        
        long elapsed = System.currentTimeMillis() - lastUse;
        long remaining = FRAGMENT_COOLDOWN - elapsed;
        
        return remaining > 0 ? remaining / 1000 : 0;
    }
    
    /**
     * Checks if a player is on mob drop cooldown
     * @param playerId The player's UUID
     * @return true if on cooldown, false otherwise
     */
    public boolean isOnMobDropCooldown(UUID playerId) {
        Long lastUse = mobDropCooldowns.get(playerId);
        if (lastUse == null) return false;
        
        return (System.currentTimeMillis() - lastUse) < MOB_DROP_COOLDOWN;
    }
    
    /**
     * Sets mob drop cooldown for a player
     * @param playerId The player's UUID
     */
    public void setMobDropCooldown(UUID playerId) {
        mobDropCooldowns.put(playerId, System.currentTimeMillis());
    }
    
    /**
     * Checks if a player is on command cooldown
     * @param playerId The player's UUID
     * @return true if on cooldown, false otherwise
     */
    public boolean isOnCommandCooldown(UUID playerId) {
        Long lastUse = commandCooldowns.get(playerId);
        if (lastUse == null) return false;
        
        return (System.currentTimeMillis() - lastUse) < COMMAND_COOLDOWN;
    }
    
    /**
     * Sets command cooldown for a player
     * @param playerId The player's UUID
     */
    public void setCommandCooldown(UUID playerId) {
        commandCooldowns.put(playerId, System.currentTimeMillis());
    }
    
    /**
     * Clears all cooldowns for a player (admin function)
     * @param playerId The player's UUID
     */
    public void clearAllCooldowns(UUID playerId) {
        fragmentCooldowns.remove(playerId);
        mobDropCooldowns.remove(playerId);
        commandCooldowns.remove(playerId);
    }
    
    /**
     * Clears expired cooldowns to prevent memory leaks
     */
    public void cleanupExpiredCooldowns() {
        long currentTime = System.currentTimeMillis();
        
        fragmentCooldowns.entrySet().removeIf(entry -> 
            (currentTime - entry.getValue()) > FRAGMENT_COOLDOWN);
        
        mobDropCooldowns.entrySet().removeIf(entry -> 
            (currentTime - entry.getValue()) > MOB_DROP_COOLDOWN);
        
        commandCooldowns.entrySet().removeIf(entry -> 
            (currentTime - entry.getValue()) > COMMAND_COOLDOWN);
    }
    
    /**
     * Formats time duration into human-readable format
     * @param seconds Time in seconds
     * @return Formatted string (e.g., "1h 30m 45s")
     */
    public static String formatTime(long seconds) {
        if (seconds <= 0) return "0s";
        
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        
        StringBuilder sb = new StringBuilder();
        if (hours > 0) sb.append(hours).append("h ");
        if (minutes > 0) sb.append(minutes).append("m ");
        if (secs > 0) sb.append(secs).append("s");
        
        return sb.toString().trim();
    }
}