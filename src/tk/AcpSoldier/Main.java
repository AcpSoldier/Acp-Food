package tk.AcpSoldier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import tk.AcpSoldier.food.FoodManager;

public class Main extends JavaPlugin {

	public boolean isPluginEnabled;
	public int maxHunger;
	public boolean autoRefillFood;
	public String autoRefillMessage;
	public int eatDelay;
	public boolean turnItemsIntoFood;

	public FileConfiguration config;

	public void onEnable() {

		config = this.getConfig();
		config.addDefault("Settings.Enabled", true);
		config.addDefault("Settings.MaxHunger", 20);
		config.addDefault("Settings.AutoRefillFood", true);
		config.addDefault("Settings.AutoRefillMessage", "&6Food refilled!");
		config.addDefault("Settings.EatDelay", 20);
		config.addDefault("Settings.TurnItemsIntoFood", true);
		config.options().copyDefaults(true);
		saveConfig();

		Commands commands = new Commands(this);
		FoodManager foodManager = new FoodManager(this);

		foodManager.setup();
		this.getCommand("acpfood").setExecutor(commands);

		isPluginEnabled = config.getBoolean("Settings.Enabled");
		maxHunger = config.getInt("Settings.MaxHunger");
		autoRefillFood = config.getBoolean("Settings.AutoRefillFood");
		autoRefillMessage = config.getString("Settings.AutoRefillMessage");
		eatDelay = config.getInt("Settings.EatDelay");
		turnItemsIntoFood = config.getBoolean("Settings.TurnItemsIntoFood");
		
		Bukkit.getPluginManager().registerEvents(new Events(this), this);
	}
}
