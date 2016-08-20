package com.acpsoldier.acpfood.food;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import com.acpsoldier.acpfood.AcpFood;
import com.acpsoldier.acpfood.food.foods.CannedBeans;
import com.acpsoldier.acpfood.food.foods.CannedFish;
import com.acpsoldier.acpfood.food.foods.CannedPasta;
import com.acpsoldier.acpfood.food.foods.GoldenApple;
import com.acpsoldier.acpfood.food.foods.MountainDew;
import com.acpsoldier.acpfood.food.foods.Pepsi;
import com.acpsoldier.acpfood.food.foods.Sugar;

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
	public static GoldenApple goldenApple;

	public static ArrayList<Food> foods = new ArrayList<Food>();
	public static ArrayList<Player> playersThatCantEat = new ArrayList<Player>();
	public static ArrayList<Player> appleEaters = new ArrayList<Player>();
	public static ArrayList<Player> sugarEaters = new ArrayList<Player>();

	public void setup() {

		if (foods.isEmpty()) {

			cannedBeans = new CannedBeans(acpFood);
			cannedPasta = new CannedPasta(acpFood);
			cannedFish = new CannedFish(acpFood);
			pepsi = new Pepsi(acpFood);
			mountainDew = new MountainDew(acpFood);
			sugar = new Sugar(acpFood);
			goldenApple = new GoldenApple(acpFood);

			foods.add(cannedBeans);
			foods.add(cannedPasta);
			foods.add(cannedFish);
			foods.add(pepsi);
			foods.add(mountainDew);
			foods.add(sugar);
			foods.add(goldenApple);
		}
		else {

			cannedBeans = new CannedBeans(acpFood);
			cannedPasta = new CannedPasta(acpFood);
			cannedFish = new CannedFish(acpFood);
			pepsi = new Pepsi(acpFood);
			mountainDew = new MountainDew(acpFood);
			sugar = new Sugar(acpFood);
			goldenApple = new GoldenApple(acpFood);

			foods.clear();
			foods.add(cannedBeans);
			foods.add(cannedPasta);
			foods.add(cannedFish);
			foods.add(pepsi);
			foods.add(mountainDew);
			foods.add(sugar);
			foods.add(goldenApple);
		}
		acpFood.reloadConfiguration(); // TODO: For some reason I have to run
										// this method again for the main config
										// to update.
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

							ItemStack newFood = p.getInventory()
									.getItem(p.getInventory().first(food.getFood().getType()));
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
				if (acpFood.broadcastFoodSounds) {

					World w = p.getWorld();

					switch (food.sound) {
						case 1:
							w.playSound(p.getLocation(), Sound.BURP, 1.0f, 1.0f);
							break;
						case 2:
							w.playSound(p.getLocation(), Sound.EAT, 1.0f, 1.0f);
							break;
						case 3:
							w.playSound(p.getLocation(), Sound.DRINK, 1.0f, 1.0f);
							break;
						case 4:
							w.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.5f);
							break;
						case 5:
							w.playSound(p.getLocation(), Sound.WITHER_SPAWN, 1.0f, 3.5f);
							break;
						default:
							w.playSound(p.getLocation(), Sound.BURP, 1.0f, 1.0f);
							break;
					}
				}
				else {
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
							p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.5f);
							break;
						case 5:
							p.playSound(p.getLocation(), Sound.WITHER_SPAWN, 1.0f, 3.5f);
							break;
						default:
							p.playSound(p.getLocation(), Sound.BURP, 1.0f, 1.0f);
							break;
					}
				}
			}

			// Special foods get special features! :) It pays to be special.
			switch (food.fileName) {
				default:
					break;
				case "Sugar":
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Sugar.speedTime, Sugar.speedLevel));
					break;
				case "Golden Apple":
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, GoldenApple.invincibilityTime, 0));
					ItemStack iteminHand = p.getItemInHand();
					p.getItemInHand().setType(Material.AIR);
					p.setItemInHand(iteminHand); // This should fix the actual eating of the golden apple. I don't think people want apple eaters to get absorption.
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

		foodData.set("Settings.DisplayName", food.displayName);
		foodData.set("Settings.HealAmount", food.healAmount);
		foodData.set("Settings.FoodAmount", food.foodAmount);
		foodData.set("Settings.ItemLore", food.itemLore);
		foodData.set("Settings.EatMessage", food.eatMessage);
		foodData.set("Settings.PlaySound", food.playSound);
		foodData.set("Settings.Sound", food.sound);

		// Special foods get special settings :) Don't you wish you were a special food?
		switch (food.fileName) {
			case "Sugar":
				foodData.set("Settings.SpeedTime", Sugar.speedTime);
				foodData.set("Settings.SpeedLevel", Sugar.speedLevel);
				break;
			case "Golden Apple":
				foodData.set("Settings.InvincibilityTime", GoldenApple.invincibilityTime);
				foodData.set("Settings.PlayExpireSound", GoldenApple.playExpireSound);
				break;	
		}

		try {
			foodData.save(foodFile);
		}
		catch (IOException e) {
			acpFood.getLogger().severe("Failed to edit the '" + food.fileName + "' file. This should never happen. If it does happen, I'm pretty sure it's not my fault! :(");
			e.printStackTrace();
		}

	}

	public File getFoodFile(Food food) {

		File dataFolder = ((AcpFood) Bukkit.getPluginManager().getPlugin("AcpFood")).getDataFolder();
		File playerFile = new File(dataFolder + File.separator + "Foods", food.fileName + ".yml");
		return playerFile;
	}
}