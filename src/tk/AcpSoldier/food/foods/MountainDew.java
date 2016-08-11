package tk.AcpSoldier.food.foods;

import java.io.File;
import java.util.ArrayList;
import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

import net.md_5.bungee.api.ChatColor;
import tk.AcpSoldier.AcpFood;
import tk.AcpSoldier.food.Food;
import tk.AcpSoldier.food.FoodManager;

public class MountainDew extends Food {

	AcpFood acpFood;

	public MountainDew(AcpFood acpFood) {

		super("Mountain Dew", 20, 20, "&8Right click to drink!", "", "&aMountain Dew", true, 3, "acpfood.foods.mountaindew");
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
		}
		else {
			foodManager.updateFoodFile(this);
		}
	}

	FoodManager foodManager = new FoodManager(acpFood);

	@Override
	public void eatFood(Player p) {
		foodManager.eatFood(p, FoodManager.mountainDew, acpFood);
	}

	@Override
	public ItemStack getFood() {

		Dye orange = new Dye();
		orange.setColor(DyeColor.WHITE);

		ItemStack is = orange.toItemStack();
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();

		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.displayName));
		lore.add(ChatColor.translateAlternateColorCodes('&', this.itemLore));

		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
}
