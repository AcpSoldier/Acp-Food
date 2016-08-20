package com.acpsoldier.acpfood;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.acpsoldier.acpfood.food.Food;
import com.acpsoldier.acpfood.food.FoodManager;

public class Events implements Listener {

	AcpFood acpFood;

	public Events(AcpFood acpFood) {

		this.acpFood = acpFood;
	}

	@EventHandler

	public void onPlayerEat(PlayerInteractEvent e) {
		if (acpFood.isPluginEnabled) {

			Action a = e.getAction();
			Player p = e.getPlayer();

			if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
				for (Food food : FoodManager.foods) {

					if (p.getItemInHand().hasItemMeta()) {
						if (p.getItemInHand().getItemMeta().equals(food.getFood().getItemMeta())) {

							food.eatFood(p);

							// Prevents Mountain Dew from behaving like bone
							// meal.
							if (food.equals(FoodManager.mountainDew)) {
								e.setCancelled(true);
							}
						}
					}
				}
			}

			if (acpFood.turnItemsIntoFood) {
				for (ItemStack item : p.getInventory().getContents()) {
					if (item != null) {
						for (Food food : FoodManager.foods) {
							if (item.getData().equals(food.getFood().getData())) {
								if (!item.hasItemMeta()) {

									item.setItemMeta(food.getFood().getItemMeta());
								}
								else if (item.hasItemMeta()) {
									if (!item.getItemMeta().equals(food.getFood().getItemMeta())) {

										item.setItemMeta(food.getFood().getItemMeta());
									}
								}
							}
						}
					}
				}
			}
		}
	}

	// Turns Item entities into food.
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {

		if (acpFood.isPluginEnabled && acpFood.turnItemsIntoFood) {

			Item item = e.getItem();

			for (Food food : FoodManager.foods) {
				if (item.getItemStack().getData().equals(food.getFood().getData())) {
					if (!item.getItemStack().hasItemMeta()) {

						item.getItemStack().setItemMeta(food.getFood().getItemMeta());
					}
					else if (item.getItemStack().hasItemMeta()) {
						if (!item.getItemStack().getItemMeta().equals(food.getFood().getItemMeta())) {

							item.getItemStack().setItemMeta(food.getFood().getItemMeta());
						}
					}
				}
			}
		}
	}

	// Turns ItemStacks into food.
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {

		if (acpFood.isPluginEnabled && acpFood.turnItemsIntoFood) {
			for (ItemStack item : e.getInventory().getContents()) {
				if (item != null) {
					for (Food food : FoodManager.foods) {
						if (item.getData().equals(food.getFood().getData())) {
							if (!item.hasItemMeta()) {

								item.setItemMeta(food.getFood().getItemMeta());
							}
							else if (item.hasItemMeta()) {
								if (!item.getItemMeta().equals(food.getFood().getItemMeta())) {

									item.setItemMeta(food.getFood().getItemMeta());
								}
							}
						}
					}
				}
			}
		}
	}

	// Give apple eaters invincibility.
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		
		if (acpFood.isPluginEnabled) {
			Player p = (Player) e.getEntity();
			
			if (((e.getEntity() instanceof Player)) && (FoodManager.appleEaters.contains(p))) {
				e.setCancelled(true);
			}	
		}
	}
	
	// Disables absorption.
	@EventHandler
	public void onPlayerConsume(PlayerItemConsumeEvent e) {
		if (acpFood.isPluginEnabled) {
			
			Player p = e.getPlayer();
			
			if(p.hasPotionEffect(PotionEffectType.ABSORPTION) || p.hasPotionEffect(PotionEffectType.REGENERATION) || FoodManager.appleEaters.contains(p)) {
				p.removePotionEffect(PotionEffectType.ABSORPTION);
				p.removePotionEffect(PotionEffectType.REGENERATION);
				e.setCancelled(true);
			}
		}
	}
}
