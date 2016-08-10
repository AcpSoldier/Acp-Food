package tk.AcpSoldier;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import tk.AcpSoldier.food.Food;
import tk.AcpSoldier.food.FoodManager;

public class Events implements Listener {

	Main main;
	FoodManager foodManager;

	public Events(Main main) {
		this.main = main;
		foodManager = new FoodManager(main);
	}

	@EventHandler
	public void onPlayerEat(PlayerInteractEvent e) {
		if (main.isPluginEnabled) {

			Action a = e.getAction();
			Player p = e.getPlayer();

			if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
				for (Food food : FoodManager.foods) {

					if (p.getItemInHand().hasItemMeta()) {

						if (p.getItemInHand().getItemMeta().equals(food.getFood().getItemMeta())) {
							food.eatFood(p);
							
							//Prevent Mountain Dew from behaving like bone meal.
							if(food.equals(FoodManager.mountainDew)) {
								e.setCancelled(true);
							}
						}
						else {
							System.out.println("Ayyyy lmao!");
						}
					}
				}
			}
		}
		else {
			e.getPlayer().sendMessage("Sorry, the plugin isn't enabled. :(");
		}
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {

		if (main.isPluginEnabled && main.turnItemsIntoFood) {

			Player p = e.getPlayer();
			Item item = e.getItem();

			for (Food food : FoodManager.foods) {
				if (item.getItemStack().getData().equals(food.getFood().getData())) {
					if (!item.getName().equals(food.displayName)) {
						
						p.sendMessage("Setting " + item.getName() + " to " + food.fileName + ".");
						ItemStack newFood = food.getFood();
						newFood.setAmount(item.getItemStack().getAmount());
						
						item.remove();
						p.getInventory().addItem(newFood);
						e.setCancelled(true);
					}
				}
			}
		}
	}
}
