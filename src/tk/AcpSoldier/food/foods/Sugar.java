package tk.AcpSoldier.food.foods;

import java.io.File;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.AcpSoldier.AcpFood;
import tk.AcpSoldier.food.Food;
import tk.AcpSoldier.food.FoodManager;

public class Sugar extends Food {

	AcpFood acpFood;

	public Sugar(AcpFood acpFood) {

		super("Sugar", 0, 0, "&8Right click to eat!", "", "&9Speed Sugar", true, 4, "acpfood.foods.sugar", 2, "", "", 1);
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
			
			this.effect = foodData.getInt("Settings.Effect");
			this.broadcastMessage = foodData.getString("Settings.BroadcastMessage");
		}
		else {
			
			foodManager.updateFoodFile(this);
		}
	}

	FoodManager foodManager = new FoodManager(acpFood);

	@Override
	public void eatFood(Player p) {
		
		foodManager.eatFood(p, FoodManager.sugar, acpFood);
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
