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
import tk.AcpSoldier.Main;
import tk.AcpSoldier.food.Food;
import tk.AcpSoldier.food.FoodManager;

public class CannedPasta extends Food {

	Main main;

	public CannedPasta(Main main) {

		super("Canned Pasta", 7, 7, "&8Right click to eat!", "", "&9Canned Pasta", true, 2, "acpfood.foods.cannedpasta");
		this.main = main;
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

	FoodManager foodManager = new FoodManager(main);

	@Override
	public void eatFood(Player p) {
		foodManager.eatFood(p, FoodManager.cannedPasta, main);
	}

	@Override
	public ItemStack getFood() {

		Dye yellow = new Dye();
		yellow.setColor(DyeColor.YELLOW);

		ItemStack is = yellow.toItemStack();
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();

		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.displayName));
		lore.add(ChatColor.translateAlternateColorCodes('&', this.itemLore));

		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
}
