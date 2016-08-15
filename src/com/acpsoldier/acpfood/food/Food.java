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
	public int category;

	//Only special foods use these.
	public String broadcastMessage;
	public String itemId;
	public int effect;
	
	public Food(String fileName, int healAmount, int foodAmount, String itemLore, String eatMessage, String displayName, boolean playSound, int sound, String permission, int category, String broadcastMessage, String itemId, int effect) {

		this.fileName = fileName;
		this.healAmount = healAmount;
		this.foodAmount = foodAmount;
		this.itemLore = itemLore;
		this.eatMessage = eatMessage;
		this.displayName = displayName;
		this.playSound = playSound;
		this.sound = sound;
		this.permission = permission;
		this.category = category;
		
		//Only special foods use these.
		this.broadcastMessage = broadcastMessage;
		this.itemId = itemId;
		this.effect = effect;
	}

	public abstract void eatFood(Player p);

	public abstract ItemStack getFood();

}
