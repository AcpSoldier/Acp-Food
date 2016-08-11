package tk.AcpSoldier;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.AcpSoldier.food.foods.CannedBeans;
import tk.AcpSoldier.food.foods.CannedPasta;
import tk.AcpSoldier.food.foods.Pepsi;

public class Commands implements CommandExecutor {

	AcpFood acpFood;

	public Commands(AcpFood acpFood) {
		this.acpFood = acpFood;
	}

	CannedBeans cannedBeans = new CannedBeans(acpFood);
	CannedPasta cannedPasta = new CannedPasta(acpFood);
	Pepsi pepsi = new Pepsi(acpFood);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String Label, String[] args) {
		
		String cmd = command.getName();
		Player p = (Player) sender;
		
		if (cmd.equalsIgnoreCase("acpfood")) {
			if (args.length == 1) {

				if (args[0].equalsIgnoreCase("reload")) {
					
					p.sendMessage(ChatColor.GOLD + "Reloading Acp Food settings...");
					acpFood.reload();
					p.sendMessage(ChatColor.GOLD + "Acp Food settings have been updated!");
				}
				else {
					
					p.sendMessage(ChatColor.GOLD + "Uhhh, you wut m8? (Unknown command)");
				}
			}
			else {
				
				p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "AcpFood v" + acpFood.version + " made by AcpSoldier.");
				p.sendMessage(ChatColor.GOLD + "Need custom plugins? www.AcpSoldier.TK");
			}
		}
		return true;
	}
}
