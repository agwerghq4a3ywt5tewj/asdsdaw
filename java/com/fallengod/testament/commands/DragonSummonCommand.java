package com.fallengod.testament.commands;

import com.fallengod.testament.bosses.EnderDragonManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * DragonSummonCommand.java
 * 
 * Handles the /summondragon command for spawning the custom Ender Dragon boss.
 * Provides safety checks, permission validation, and proper error handling.
 */
public class DragonSummonCommand implements CommandExecutor, TabCompleter {
    
    private final JavaPlugin plugin;
    
    public DragonSummonCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("summondragon")) {
            return false;
        }
        
        // Check permission
        if (!sender.hasPermission("testament.summon.dragon")) {
            sender.sendMessage("§cYou don't have permission to summon the dragon!");
            return true;
        }
        
        Player targetPlayer;
        Location spawnLocation;
        
        // Determine target player and spawn location
        if (args.length == 0) {
            // No arguments - sender must be a player
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cConsole must specify a player: /summondragon <player>");
                return true;
            }
            targetPlayer = (Player) sender;
        } else {
            // Player specified as argument
            targetPlayer = plugin.getServer().getPlayer(args[0]);
            if (targetPlayer == null) {
                sender.sendMessage("§cPlayer '" + args[0] + "' not found!");
                return true;
            }
        }
        
        // Get spawn location (50 blocks above target player)
        spawnLocation = targetPlayer.getLocation().add(0, 50, 0);
        World world = spawnLocation.getWorld();
        
        // Safety checks
        if (world == null) {
            sender.sendMessage("§cInvalid world for dragon summoning!");
            return true;
        }
        
        // Check if there's already a custom dragon in the world
        boolean hasCustomDragon = world.getEntitiesByClass(EnderDragon.class).stream()
                .anyMatch(dragon -> {
                    String name = dragon.getCustomName();
                    return name != null && (name.contains("Fallen Aspect") || name.contains("Resurrected"));
                });
        
        if (hasCustomDragon) {
            sender.sendMessage("§cA custom Ender Dragon already exists in this world!");
            return true;
        }
        
        try {
            // Spawn the dragon
            EnderDragon dragon = (EnderDragon) world.spawnEntity(spawnLocation, EntityType.ENDER_DRAGON);
            
            // Apply custom buffs and behaviors
            EnderDragonManager.buffDragon(dragon);
            
            // Success messages
            String targetName = targetPlayer.getName();
            if (sender.equals(targetPlayer)) {
                sender.sendMessage("§5§l[Testament] §fThe Fallen Aspect of the End has been summoned above you!");
            } else {
                sender.sendMessage("§5§l[Testament] §fThe Fallen Aspect of the End has been summoned above " + targetName + "!");
                targetPlayer.sendMessage("§5§l[Testament] §fThe Fallen Aspect of the End has been summoned above you!");
            }
            
            // Broadcast to server
            plugin.getServer().broadcastMessage("§5§l[Testament] §fThe Fallen Aspect of the End awakens in " + world.getName() + "!");
            
            // Log the event
            plugin.getLogger().info("Custom Ender Dragon summoned by " + sender.getName() + 
                                  " at " + spawnLocation.getBlockX() + ", " + 
                                  spawnLocation.getBlockY() + ", " + spawnLocation.getBlockZ() + 
                                  " in world " + world.getName());
            
        } catch (Exception e) {
            sender.sendMessage("§cFailed to summon dragon: " + e.getMessage());
            plugin.getLogger().severe("Error summoning custom dragon: " + e.getMessage());
            e.printStackTrace();
        }
        
        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            // Tab complete player names
            String partial = args[0].toLowerCase();
            plugin.getServer().getOnlinePlayers().forEach(player -> {
                if (player.getName().toLowerCase().startsWith(partial)) {
                    completions.add(player.getName());
                }
            });
        }
        
        return completions;
    }
}