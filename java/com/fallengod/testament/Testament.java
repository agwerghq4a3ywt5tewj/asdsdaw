package com.fallengod.testament;

import com.fallengod.testament.commands.DragonSummonCommand;
import com.fallengod.testament.bosses.EnderDragonManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Testament.java
 * 
 * Main plugin class for the Fallen God Testament plugin.
 * Handles initialization, command registration, and event listener setup.
 */
public class Testament extends JavaPlugin {
    
    @Override
    public void onEnable() {
        // Register event listeners
        getServer().getPluginManager().registerEvents(new EnderDragonManager(), this);
        
        // Register commands
        DragonSummonCommand dragonCommand = new DragonSummonCommand(this);
        getCommand("summondragon").setExecutor(dragonCommand);
        getCommand("summondragon").setTabCompleter(dragonCommand);
        
        getLogger().info("FallenGodTestament has been enabled!");
        getLogger().info("Custom Ender Dragon system loaded successfully!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("FallenGodTestament has been disabled!");
    }
}