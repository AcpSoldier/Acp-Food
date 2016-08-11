package tk.AcpSoldier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import tk.AcpSoldier.food.FoodManager;

public class AcpFood extends JavaPlugin {
	
	public String version;

	public boolean isPluginEnabled;
	public int maxHunger;
	public boolean autoRefillFood;
	public String autoRefillMessage;
	public int eatDelay;
	public boolean turnItemsIntoFood;

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
	}
	
	public void reload() {
		
		isPluginEnabled = config.getBoolean("Settings.Enabled");
		maxHunger = config.getInt("Settings.MaxHunger");
		autoRefillFood = config.getBoolean("Settings.AutoRefillFood");
		autoRefillMessage = config.getString("Settings.AutoRefillMessage");
		eatDelay = config.getInt("Settings.EatDelay");
		turnItemsIntoFood = config.getBoolean("Settings.TurnItemsIntoFood");
		
		foodManager.setup();
	}
}