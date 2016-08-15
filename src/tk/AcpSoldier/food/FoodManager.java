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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import tk.AcpSoldier.AcpFood;
import tk.AcpSoldier.food.foods.CannedBeans;
import tk.AcpSoldier.food.foods.CannedFish;
import tk.AcpSoldier.food.foods.CannedPasta;
import tk.AcpSoldier.food.foods.MountainDew;
import tk.AcpSoldier.food.foods.Pepsi;
import tk.AcpSoldier.food.foods.Sugar;

public class FoodManager {

	AcpFood acpFood;

	public FoodManager(AcpFood acpFood) {
		this.acpFood = acpFood;
	}

	public static CannedBeans cannedBeans;
	public static CannedPasta cannedPasta;
	public static CannedFish cannedFish;
	public static Pepsi pepsi;
	public static MountainDew mountainDew;
	public static Sugar sugar;

	public static ArrayList<Food> foods = new ArrayList<Food>();
	public static ArrayList<Player> playersThatCantEat = new ArrayList<Player>();
	public static ArrayList<Player> appleEaters = new ArrayList<Player>();

	public void setup() {

		if (foods.isEmpty()) {

			cannedBeans = new CannedBeans(acpFood);
			cannedPasta = new CannedPasta(acpFood);
			cannedFish = new CannedFish(acpFood);
			pepsi = new Pepsi(acpFood);
			mountainDew = new MountainDew(acpFood);
			sugar = new Sugar(acpFood);

			foods.add(cannedBeans);
			foods.add(cannedPasta);
			foods.add(cannedFish);
			foods.add(pepsi);
			foods.add(mountainDew);
			foods.add(sugar);
		}
		else {

			cannedBeans = new CannedBeans(acpFood);
			cannedPasta = new CannedPasta(acpFood);
			cannedFish = new CannedFish(acpFood);
			pepsi = new Pepsi(acpFood);
			mountainDew = new MountainDew(acpFood);
			sugar = new Sugar(acpFood);

			foods.clear();
			foods.add(cannedBeans);
			foods.add(cannedPasta);
			foods.add(cannedFish);
			foods.add(pepsi);
			foods.add(mountainDew);
			foods.add(sugar);
		}
		acpFood.reloadConfiguration(); //TODO: For some reason I have to run this method again for the main config to update.
	}

	public void eatFood(Player p, Food food, AcpFood acpFood) {

		if (!FoodManager.playersThatCantEat.contains(p) && p.hasPermission(food.permission)) {

			if (p.getHealth() + food.healAmount > 20) {
				p.setHealth(20);
			}
			else {
				p.setHealth(p.getHealth() + food.healAmount);
			}
			if (p.getFoodLevel() + food.foodAmount > acpFood.maxHunger) {
				p.setFoodLevel(acpFood.maxHunger);
			}
			else {
				p.setFoodLevel(p.getFoodLevel() + food.foodAmount);
			}

			if (p.getItemInHand().getAmount() == 1) {

				p.setItemInHand(new ItemStack(Material.AIR));

				if (acpFood.autoRefillFood) {
					if (p.hasPermission("acpfood.other.refill")) {

						if (p.getInventory().contains(food.getFood().getType(), 1)) {

							ItemStack newFood = p.getInventory().getItem(p.getInventory().first(food.getFood().getType()));
							p.getInventory().remove(newFood);
							p.setItemInHand(newFood);

							p.sendMessage(ChatColor.translateAlternateColorCodes('&', acpFood.autoRefillMessage));
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
					case 4:
						p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 0.5f, 1.5f);
						break;
					case 5:
						p.playSound(p.getLocation(), Sound.WITHER_SPAWN, 0.5f, 1.5f);
						break;
					default:
						p.playSound(p.getLocation(), Sound.BURP, 1.0f, 1.0f);
						break;
				}
			}

			switch (food.effect) {
				default:
					break;
				case 1:
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0));
					if (food.broadcastMessage.length() > 0) {
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', food.broadcastMessage));
					}
					break;
				case 2:
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0));
					if (food.broadcastMessage.length() > 0) {
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', food.broadcastMessage));
					}
					break;
			}

			FoodManager.playersThatCantEat.add(p);

			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleSyncDelayedTask(acpFood, new Runnable() {
				public void run() {
					FoodManager.playersThatCantEat.remove(p);
				}
			}, acpFood.eatDelay);
		}
		else if (!p.hasPermission(food.permission)) {
			p.sendMessage(ChatColor.RED + "You don't have permission to eat " + food.fileName + ".");
		}

	}

	public void updateFoodFile(Food food) {

		File foodFile = getFoodFile(food);
		FileConfiguration foodData = YamlConfiguration.loadConfiguration(foodFile);

		switch (food.category) {
			case 1:
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
				break;
			case 2:
				try {
					foodData.set("Settings.DisplayName", food.displayName);
					foodData.set("Settings.HealAmount", food.healAmount);
					foodData.set("Settings.FoodAmount", food.foodAmount);
					foodData.set("Settings.ItemLore", food.itemLore);
					foodData.set("Settings.EatMessage", food.eatMessage);
					foodData.set("Settings.PlaySound", food.playSound);
					foodData.set("Settings.Sound", food.sound);

					foodData.set("Settings.BroadcastMessage", food.broadcastMessage);
					foodData.set("Settings.Effect", food.effect);
					foodData.save(foodFile);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case 3:
				try {
					foodData.set("Settings.DisplayName", food.displayName);
					foodData.set("Settings.HealAmount", food.healAmount);
					foodData.set("Settings.FoodAmount", food.foodAmount);
					foodData.set("Settings.ItemLore", food.itemLore);
					foodData.set("Settings.EatMessage", food.eatMessage);
					foodData.set("Settings.PlaySound", food.playSound);
					foodData.set("Settings.Sound", food.sound);

					foodData.set("Settings.ItemId", food.itemId);
					foodData.save(foodFile);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
	}

	public File getFoodFile(Food food) {

		File dataFolder = ((AcpFood) Bukkit.getPluginManager().getPlugin("AcpFood")).getDataFolder();
		File playerFile = new File(dataFolder + File.separator + "Foods", food.fileName + ".yml");
		return playerFile;
	}
}