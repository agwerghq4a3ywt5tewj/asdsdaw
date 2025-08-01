package com.fallengod.testament;

import com.fallengod.testament.api.TestamentAPI;
import com.fallengod.testament.commands.*;
import com.fallengod.testament.listeners.*;
import com.fallengod.testament.managers.*;
import com.fallengod.testament.managers.BossManager;
import com.fallengod.testament.managers.BountyManager;
import com.fallengod.testament.items.ArmorTrims;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestamentPlugin extends JavaPlugin {

    public static TestamentPlugin plugin; // ✅ Static reference for use in other classes

    private PlayerDataManager playerDataManager;
    private FragmentManager fragmentManager;
    private RewardManager rewardManager;
    private AscensionManager ascensionManager;
    private TitleManager titleManager;
    private ConfigManager configManager;
    private WeaponAbilityManager weaponAbilityManager;
    private ConvergenceManager convergenceManager;
    private HeartVeilListener heartVeilListener;
    private BossManager bossManager;
    private BountyManager bountyManager;

    @Override
    public void onEnable() {
        plugin = this; // ✅ Assign plugin reference

        // Initialize managers
        this.configManager = new ConfigManager(this);
        this.playerDataManager = new PlayerDataManager(this);
        this.fragmentManager = new FragmentManager(this);
        this.rewardManager = new RewardManager(this);
        this.ascensionManager = new AscensionManager(this);
        this.titleManager = new TitleManager(this);
        this.weaponAbilityManager = new WeaponAbilityManager(this);
        this.convergenceManager = new ConvergenceManager(this);
        this.bossManager = new BossManager(this);
        this.bountyManager = new BountyManager(this);

        // Initialize API
        TestamentAPI.initialize(this);

        // Register commands
        registerCommands();

        // Register listeners
        registerListeners();

        // Apply effects to online players on startup
        getServer().getScheduler().runTaskLater(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                ascensionManager.applyAscensionEffects(player);
                titleManager.updatePlayerDisplayName(player,
                        titleManager.getPlayerTitle(player.getUniqueId()));
            }
        }, 20L);

        // Start armor trim rotation
        ArmorTrims.startTrimRotation(this);

        getLogger().info("Fallen God Testament plugin enabled!");
        getLogger().info("Epic fragment collection system loaded!");
        getLogger().info("Divine convergence system ready!");
        getLogger().info("Boss battle system initialized!");
    }

    @Override
    public void onDisable() {
        // Save all player data
        if (playerDataManager != null) {
            playerDataManager.saveAllData();
        }

        getLogger().info("Fallen God Testament plugin disabled!");
    }

    private void registerCommands() {
        getCommand("testament").setExecutor(new TestamentCommand(this));
        getCommand("fragment").setExecutor(new FragmentCommand(this));
        getCommand("datapack").setExecutor(new DatapackCommand(this));
        getCommand("godlex").setExecutor(new GodlexCommand(this));
        getCommand("altar").setExecutor(new AltarCommand(this));
        getCommand("bounty").setExecutor(new BountyCommand(this));

        if (getCommand("boss") != null) {
            getCommand("boss").setExecutor(new BossCommand(this));
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChestListener(this), this);
        getServer().getPluginManager().registerEvents(new MobListener(this), this);
        getServer().getPluginManager().registerEvents(new AltarListener(this), this);
        getServer().getPluginManager().registerEvents(new ToxicityListener(this), this);
        getServer().getPluginManager().registerEvents(new ItemEffectListener(this), this);
        getServer().getPluginManager().registerEvents(new HealthManagementListener(this), this);
        getServer().getPluginManager().registerEvents(new WeaponAbilityListener(this), this);
        getServer().getPluginManager().registerEvents(new ConvergenceEffectListener(this), this);
        getServer().getPluginManager().registerEvents(new ConvergenceListener(this, convergenceManager), this);
        getServer().getPluginManager().registerEvents(new BossListener(this), this);
        getServer().getPluginManager().registerEvents(new AscensionEffectListener(this), this);
        getServer().getPluginManager().registerEvents(new PvpBalanceListener(this), this);

        // Register bounty listener (ensure BountyManager is initialized first)
        getServer().getPluginManager().registerEvents(new BountyListener(this), this);
        
        this.heartVeilListener = new HeartVeilListener(this);
        getServer().getPluginManager().registerEvents(heartVeilListener, this);
    }

    // Getters for managers
    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public RewardManager getRewardManager() {
        return rewardManager;
    }

    public AscensionManager getAscensionManager() {
        return ascensionManager;
    }

    public TitleManager getTitleManager() {
        return titleManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public WeaponAbilityManager getWeaponAbilityManager() {
        return weaponAbilityManager;
    }

    public ConvergenceManager getConvergenceManager() {
        return convergenceManager;
    }

    public HeartVeilListener getHeartVeilListener() {
        return heartVeilListener;
    }

    public BossManager getBossManager() {
        return bossManager;
    }

    public BountyManager getBountyManager() {
        return bountyManager;
    }
}