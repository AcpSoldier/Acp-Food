package tk.AcpSoldier;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tk.AcpSoldier.food.foods.CannedBeans;
import tk.AcpSoldier.food.foods.CannedPasta;
import tk.AcpSoldier.food.foods.Pepsi;

public class Commands implements CommandExecutor {

	Main main;

	public Commands(Main main) {
		this.main = main;
	}

	CannedBeans cannedBeans = new CannedBeans(main);
	CannedPasta cannedPasta = new CannedPasta(main);
	Pepsi pepsi = new Pepsi(main);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String Label, String[] args) {
		String cmd = command.getName();
		Player p = (Player) sender;
		if (cmd.equalsIgnoreCase("acpfood")) {
			if (args.length == 1) {

				if (args[0].equalsIgnoreCase("givebeans")) {
					ItemStack food = cannedBeans.getFood();
					food.setAmount(16);
					p.getInventory().addItem(food);
				}
				else if (args[0].equalsIgnoreCase("givepasta")) {
					ItemStack food = cannedPasta.getFood();
					food.setAmount(16);
					p.getInventory().addItem(food);
				}
				else if (args[0].equalsIgnoreCase("givepepsi")) {
					ItemStack food = pepsi.getFood();
					food.setAmount(16);
					p.getInventory().addItem(food);
				}
				else {
					p.sendMessage(ChatColor.BLUE + "Uhhh, you wut m8? (Unknown command)");
				}
			}
			else {
				p.sendMessage("Here are all the commands for AcpFood:");
				p.sendMessage("/acpfood givebeans");
			}
		}
		return true;
	}

}
