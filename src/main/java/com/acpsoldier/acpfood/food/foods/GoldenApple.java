package com.acpsoldier.acpfood.food.foods;

import java.io.File;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import com.acpsoldier.acpfood.AcpFood;
import com.acpsoldier.acpfood.food.Food;
import com.acpsoldier.acpfood.food.FoodManager;

public class GoldenApple extends Food {

	AcpFood acpFood;

	// The default invincibility time when the plugin is first installed.
	public static int invincibilityTime = 60;
	// The default boolean when the plugin is first installed.
	public static boolean playExpireSound = true;

	public GoldenApple(AcpFood acpFood) {

		super("Golden Apple", 20, 20, "&8Consume for temporary invincibility!", "", "&6&lGolden Apple", true, 5, "acpfood.foods.goldenapple");
		this.acpFood = acpFood;
		File foodFile = foodManager.getFoodFile(this);
		FileConfiguration foodData = YamlConfiguration.loadConfiguration(foodFile);

		if (foodFile.exists()) {

			this.displayName = foodData.getString("Settings.DisplayName");
			this.healAmount = foodData.getInt("Settings.HealAmount");
			this.foodAmount = foodData.getInt("Settings.FoodAmount");
			this.itemLore = foodData.getString("Settings.ItemLore");
			this.eatMessage = foodData.getString("Settings.EatMessage");
			this.playSound = foodData.getBoolean("Settings.PlaySound");
			this.sound = foodData.getInt("Settings.Sound");

			GoldenApple.invincibilityTime = foodData.getInt("Settings.InvincibilityTime");
			GoldenApple.playExpireSound = foodData.getBoolean("Settings.PlayExpireSound");
		}
		else {

			foodManager.updateFoodFile(this);
		}
	}

	FoodManager foodManager = new FoodManager(acpFood);

	@Override
	public void eatFood(Player p) {

		if (!FoodManager.appleEaters.contains(p)) {
			foodManager.eatFood(p, FoodManager.goldenApple, acpFood);

			FoodManager.appleEaters.add(p);

			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleSyncDelayedTask(acpFood, new Runnable() {
				public void run() {
					//
					// TODO: Find a way to play an 'expire' sound without using
					// up like 2 billion lines of code.
					//
					FoodManager.appleEaters.remove(p);
					if (playSound && playExpireSound) {
						if (acpFood.broadcastFoodSounds) {

							World w = p.getWorld();

							switch (sound) {
								case 1:
									w.playSound(p.getLocation(), Sound.BURP, 1.0f, 0.4f);
									break;
								case 2:
									w.playSound(p.getLocation(), Sound.EAT, 1.0f, 0.4f);
									break;
								case 3:
									w.playSound(p.getLocation(), Sound.DRINK, 1.0f, 0.4f);
									break;
								case 4:
									w.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 0.4f);
									break;
								case 5:
									w.playSound(p.getLocation(), Sound.WITHER_SPAWN, 1.0f, 1.0f);
									break;
								default:
									w.playSound(p.getLocation(), Sound.BURP, 1.0f, 0.4f);
									break;
							}
						}
						else {
							switch (sound) {
								case 1:
									p.playSound(p.getLocation(), Sound.BURP, 1.0f, 0.4f);
									break;
								case 2:
									p.playSound(p.getLocation(), Sound.EAT, 1.0f, 0.4f);
									break;
								case 3:
									p.playSound(p.getLocation(), Sound.DRINK, 1.0f, 0.4f);
									break;
								case 4:
									p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 0.4f);
									break;
								case 5:
									p.playSound(p.getLocation(), Sound.WITHER_SPAWN, 1.0f, 1.0f);
									break;
								default:
									p.playSound(p.getLocation(), Sound.BURP, 1.0f, 0.4f);
									break;
							}
						}
					}
				}
			}, invincibilityTime);
		}
	}

	@Override
	public ItemStack getFood() {

		ItemStack is = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();

		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.displayName));
		lore.add(ChatColor.translateAlternateColorCodes('&', this.itemLore));

		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
}
