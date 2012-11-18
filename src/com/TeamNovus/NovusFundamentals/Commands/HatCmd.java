package com.TeamNovus.NovusFundamentals.Commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.TeamNovus.NovusFundamentals.NovusFundamentals;

public class HatCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(!(NovusFundamentals.getPlugin().getConfig().getBoolean("hat.enabled"))) {
			sender.sendMessage(ChatColor.RED + "Hats are currently disabled!");
			return true;
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command cannot be run from the console!");
			return true;
		}
		
		if(commandLabel.equalsIgnoreCase("hat")) {
			performHat((Player) sender, commandLabel, args);	
		}
		
		if(commandLabel.equalsIgnoreCase("unhat")) {
			performUnhat((Player) sender, commandLabel, args);	
		}
		return true;
	}
	
	public boolean performHat(Player player, String commandLabel, String[] args) {
		PlayerInventory inventory = player.getInventory();
		ItemStack helmet = inventory.getHelmet();
		ItemStack block = new ItemStack(player.getItemInHand());		
		
		if(block.getTypeId() == 0) {
			return performUnhat(player, commandLabel, args);
		}
		
		if(!block.getType().isBlock()) {
			player.sendMessage(ChatColor.RED + "This is not a block silly!");
			return false;
		}
		
		// Check if the player has permission to use it.
		if(!(player.hasPermission("blockhat.hat." + block.getTypeId())) || NovusFundamentals.getPlugin().getConfig().getIntegerList("hat.restricted-blocks").contains(block.getTypeId())) {
			player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
			return false;
		}
						
		// Sets the helmet
		block.setAmount(1);
		inventory.setHelmet(block);
		player.sendMessage(ChatColor.GREEN + "Enjoy your new hat!");
		// Removes old block from inventory
		if(block != null) {
			inventory.removeItem(block);
		}
		
		// Puts old helmet in inventory if there is space
		// Otherwise its tossed on the ground
		if(helmet != null) {
			HashMap<Integer, ItemStack> leftOver = inventory.addItem(helmet);
	        if (!leftOver.isEmpty()) {
				player.getWorld().dropItem(player.getLocation(), helmet);
				player.sendMessage(ChatColor.YELLOW + "Unable to place your hat in your inventory so it was dropped at your feet.");
	        }
		}
        return true;
	}
	
	public boolean performUnhat(Player player, String commandLabel, String[] args) {
		PlayerInventory inventory = player.getInventory();
		ItemStack helmet = inventory.getHelmet();
		
		// Check if the player has permission to use it.
		if(!player.hasPermission("blockhat.unhat")) {
			player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
			return false;
		}

		inventory.setHelmet(null);
		player.sendMessage(ChatColor.GREEN + "Hat Removed.");

		// Puts old helmet in inventory if there is space
		// Otherwise its tossed on the ground
		if(helmet != null) {
			HashMap<Integer, ItemStack> leftOver = inventory.addItem(helmet);
	        if (!leftOver.isEmpty()) {
				player.getWorld().dropItem(player.getLocation(), helmet);
				player.sendMessage(ChatColor.YELLOW + "Unable to place your hat in your inventory so it was dropped at your feet.");
	        }
		}
		return true;
	}

	
}
