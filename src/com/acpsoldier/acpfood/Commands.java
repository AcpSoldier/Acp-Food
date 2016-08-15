package com.acpsoldier.acpfood;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	AcpFood acpFood;

	public Commands(AcpFood acpFood) {
		this.acpFood = acpFood;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String Label, String[] args) {

		String c = command.getName();

		if (c.equalsIgnoreCase("acpfood")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {

					if (sender instanceof Player) {

						Player p = (Player) sender;
						p.sendMessage(ChatColor.GOLD + "Reloading Acp Food settings...");
						acpFood.reload();
						p.sendMessage(ChatColor.GOLD + "Acp Food settings have been updated!");
					}
					else {

						acpFood.getLogger().info("Reloading Acp Food settings...");
						acpFood.reload();
						acpFood.getLogger().info("Acp Food settings have been updated!");
					}
				}
				else {
					if (sender instanceof Player) {

						Player p = (Player) sender;
						p.sendMessage(ChatColor.GOLD + "Uhhhh, you wut m8? (Unknown command)");
					}
					else {
						acpFood.getLogger().info("Uhhhh, you wut m8? (Unknown command)");
					}
				}
			}
			else {
				if (sender instanceof Player) {

					Player p = (Player) sender;
					p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "AcpFood v" + acpFood.version + " made by AcpSoldier.");
				}
					else {
					acpFood.getLogger().info("AcpFood v" + acpFood.version + " made by AcpSoldier.");
				}
			}
		}
		return true;
	}
}
