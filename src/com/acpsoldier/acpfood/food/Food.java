package com.acpsoldier.acpfood.food;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Food {

	public String fileName;
	public int healAmount;
	public int foodAmount;
	public String itemLore;
	public String eatMessage;
	public String displayName;
	public boolean playSound;
	public int sound;
	public String permission;
	
	public Food(String fileName, int healAmount, int foodAmount, String itemLore, String eatMessage, String displayName, boolean playSound, int sound, String permission) {

		this.fileName = fileName;
		this.healAmount = healAmount;
		this.foodAmount = foodAmount;
		this.itemLore = itemLore;
		this.eatMessage = eatMessage;
		this.displayName = displayName;
		this.playSound = playSound;
		this.sound = sound;
		this.permission = permission;
	}

	public abstract void eatFood(Player p);

	public abstract ItemStack getFood();

}
