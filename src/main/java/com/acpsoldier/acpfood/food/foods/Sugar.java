package com.acpsoldier.acpfood.food.foods;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import com.acpsoldier.acpfood.AcpFood;
import com.acpsoldier.acpfood.food.Food;
import com.acpsoldier.acpfood.food.FoodManager;

public class Sugar extends Food {

	AcpFood acpFood;
	
	//The default speed time when the plugin is first installed.
	public static int speedTime = 20;
	//The default speed level when the plugin is first installed.
	public static int speedLevel = 0;

	public Sugar(AcpFood acpFood) {

		super("Sugar", 0, 0, "&8Right click to eat!", "", "&9Speed Sugar", true, 4, "acpfood.foods.sugar");
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
			
			Sugar.speedTime = foodData.getInt("Settings.SpeedTime");
			Sugar.speedLevel = foodData.getInt("Settings.SpeedLevel");
		}
		else {
			
			foodManager.updateFoodFile(this);
		}
	}

	FoodManager foodManager = new FoodManager(acpFood);

	@Override
	public void eatFood(Player p) {
		
		if(!FoodManager.sugarEaters.contains(p)) {
			foodManager.eatFood(p, FoodManager.sugar, acpFood);
			
			FoodManager.sugarEaters.add(p);
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleSyncDelayedTask(acpFood, new Runnable() {
				public void run() {
					FoodManager.sugarEaters.remove(p);
				}
			}, speedTime);
		}
	}

	@Override
	public ItemStack getFood() {

		ItemStack is = new ItemStack(Material.SUGAR);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();

		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.displayName));
		lore.add(ChatColor.translateAlternateColorCodes('&', this.itemLore));

		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
}
