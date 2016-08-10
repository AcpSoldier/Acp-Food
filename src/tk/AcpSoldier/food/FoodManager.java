package tk.AcpSoldier.food;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import tk.AcpSoldier.Main;
import tk.AcpSoldier.food.foods.CannedBeans;
import tk.AcpSoldier.food.foods.CannedFish;
import tk.AcpSoldier.food.foods.CannedPasta;
import tk.AcpSoldier.food.foods.MountainDew;
import tk.AcpSoldier.food.foods.Pepsi;

public class FoodManager {

	Main main;

	public FoodManager(Main main) {
		this.main = main;
	}

	public static CannedBeans cannedBeans;
	public static CannedPasta cannedPasta;
	public static CannedFish cannedFish;
	public static Pepsi pepsi;
	public static MountainDew mountainDew;
	
	public static ArrayList<Food> foods = new ArrayList<Food>();
	public static ArrayList<Player> playersThatCantEat = new ArrayList<Player>();

	public void setup() {
		cannedBeans = new CannedBeans(main);
		cannedPasta = new CannedPasta(main);
		cannedFish = new CannedFish(main);
		pepsi = new Pepsi(main);
		mountainDew = new MountainDew(main);

		foods.add(cannedBeans);
		foods.add(cannedPasta);
		foods.add(cannedFish);
		foods.add(pepsi);
		foods.add(mountainDew);
	}

	public void eatFood(Player p, Food food, Main main) {

		if (!FoodManager.playersThatCantEat.contains(p) && p.hasPermission(food.permission)) {

			if (p.getHealth() + food.healAmount > 20) {
				p.setHealth(20);
			}
			else {
				p.setHealth(p.getHealth() + food.healAmount);
			}
			if (p.getFoodLevel() + food.foodAmount > main.maxHunger) {
				p.setFoodLevel(main.maxHunger);
			}
			else {
				p.setFoodLevel(p.getFoodLevel() + food.foodAmount);
			}

			if (p.getItemInHand().getAmount() == 1) {

				p.setItemInHand(new ItemStack(Material.AIR));

				if (main.config.getBoolean("Settings.AutoRefillFood")) {
					if (p.hasPermission("acpfood.other.refill")) {
						
						if (p.getInventory().contains(food.getFood().getType(), 1)) {
							
							ItemStack newFood = p.getInventory().getItem(p.getInventory().first(food.getFood().getType()));
							p.getInventory().remove(newFood);
							p.setItemInHand(newFood);
							
							String message = main.config.getString("Settings.AutoRefillMessage");
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
						}
					}
				}
			}
			else {
				p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
			}

			if (food.eatMessage.length() > 0) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', food.eatMessage));
			}

			if (food.playSound) {
				switch (food.sound) {
				case 1:
					p.playSound(p.getLocation(), Sound.BURP, 1.0f, 1.0f);
					break;
				case 2:
					p.playSound(p.getLocation(), Sound.EAT, 1.0f, 1.0f);
					break;
				case 3:
					p.playSound(p.getLocation(), Sound.DRINK, 1.0f, 1.0f);
					break;
				default:
					p.playSound(p.getLocation(), Sound.BURP, 1.0f, 1.0f);
					break;
				}
			}
			FoodManager.playersThatCantEat.add(p);

			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleSyncDelayedTask(main, new Runnable() {
				public void run() {
					FoodManager.playersThatCantEat.remove(p);
				}
			}, main.eatDelay);
		}
		else if (!p.hasPermission(food.permission)) {
			p.sendMessage(ChatColor.RED + "You don't have permission to eat " + food.fileName + ".");
		}

	}

	public void updateFoodFile(Food food) {

		File foodFile = getFoodFile(food);
		FileConfiguration foodData = YamlConfiguration.loadConfiguration(foodFile);

		try {
			foodData.set("Settings.DisplayName", food.displayName);
			foodData.set("Settings.HealAmount", food.healAmount);
			foodData.set("Settings.FoodAmount", food.foodAmount);
			foodData.set("Settings.ItemLore", food.itemLore);
			foodData.set("Settings.EatMessage", food.eatMessage);
			foodData.set("Settings.PlaySound", food.playSound);
			foodData.set("Settings.Sound", food.sound);
			foodData.save(foodFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getFoodFile(Food food) {

		File dataFolder = ((Main) Bukkit.getPluginManager().getPlugin("AcpFood")).getDataFolder();
		File playerFile = new File(dataFolder + File.separator + "Foods", food.fileName + ".yml");
		return playerFile;
	}
}