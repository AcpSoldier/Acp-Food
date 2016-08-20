package com.acpsoldier.acpfood;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.acpsoldier.acpfood.food.FoodManager;
import net.gravitydevelopment.updater.Updater;

public class AcpFood extends JavaPlugin {

	public String version;

	public boolean isPluginEnabled;
	public int maxHunger;
	public boolean autoRefillFood;
	public String autoRefillMessage;
	public int eatDelay;
	public boolean turnItemsIntoFood;
	public boolean broadcastFoodSounds;
	public boolean autoUpdate;

	public FileConfiguration config;

	FoodManager foodManager;
	Commands commands;
	Events events;

	public void onEnable() {

		config = this.getConfig();
		this.saveDefaultConfig();

		version = this.getDescription().getVersion();

		commands = new Commands(this);
		foodManager = new FoodManager(this);
		events = new Events(this);

		this.getCommand("acpfood").setExecutor(commands);
		Bukkit.getPluginManager().registerEvents(events, this);
		reload();

		if (autoUpdate) {
			Updater updater = new Updater(this, 87415, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
			if (updater.getResult() == Updater.UpdateResult.NO_UPDATE) {
				this.getLogger().info("You have the latest version of AcpFood. :)");
			}
			else if (updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE) {
				this.getLogger().info("----------------------------------------");
				this.getLogger().info("There is an update avaliable for AcpFood!");
				this.getLogger().info("Type 'acpfood update' to download!");
				this.getLogger().info("----------------------------------------");
			}
		}
	}

	public void reload() {

		reloadConfiguration();
		this.reloadConfig();
		config = this.getConfig();
		foodManager.setup();
	}

	public void reloadConfiguration() {

		isPluginEnabled = config.getBoolean("Settings.Enabled");
		maxHunger = config.getInt("Settings.MaxHunger");
		autoRefillFood = config.getBoolean("Settings.AutoRefillFood");
		autoRefillMessage = config.getString("Settings.AutoRefillMessage");
		eatDelay = config.getInt("Settings.EatDelay");
		turnItemsIntoFood = config.getBoolean("Settings.TurnItemsIntoFood");
		broadcastFoodSounds = config.getBoolean("Settings.BroadcastFoodSounds");
		autoUpdate = config.getBoolean("Settings.AutoUpdate");
	}
}