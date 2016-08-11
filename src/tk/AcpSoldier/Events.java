package tk.AcpSoldier;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import tk.AcpSoldier.food.Food;
import tk.AcpSoldier.food.FoodManager;

public class Events implements Listener {

	AcpFood acpFood;
	FoodManager foodManager;

	public Events(AcpFood acpFood) {
		this.acpFood = acpFood;
		foodManager = new FoodManager(acpFood);
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

							// Prevents Mountain Dew from behaving like bone meal.
							if (food.equals(FoodManager.mountainDew)) {
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {

		if (acpFood.isPluginEnabled && acpFood.turnItemsIntoFood) {

			Item item = e.getItem();

			for (Food food : FoodManager.foods) {
				if (item.getItemStack().getData().equals(food.getFood().getData())) {
					if(!item.getItemStack().hasItemMeta()) {
						
						item.getItemStack().setItemMeta(food.getFood().getItemMeta());
					}
				}
			}
		}
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {

		if (acpFood.isPluginEnabled && acpFood.turnItemsIntoFood) {
			for (ItemStack item : e.getInventory().getContents()) {
				if (item != null) {
					for (Food food : FoodManager.foods) {
						if (item.getData().equals(food.getFood().getData())) {
							if(!item.hasItemMeta()) {
								
								item.setItemMeta(food.getFood().getItemMeta());
							}
						}
					}
				}
			}
		}
	}
}
