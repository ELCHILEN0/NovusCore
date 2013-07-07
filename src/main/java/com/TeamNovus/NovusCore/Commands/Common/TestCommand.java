package com.TeamNovus.NovusCore.Commands.Common;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.TeamNovus.NovusCore.Commands.Command;

public class TestCommand extends Command {

	public TestCommand() throws InstantiationException {		
		super("test", "t");
		
		setDescription("A simple test command.");
		setPermission("permission.command.test");
		setPermissionMessage(ChatColor.RED + "You do not have permission for this command! /{{ parent }} {{ label }}");
		setUsage("<Required> [Optional]");
		setUsageMessage(ChatColor.RED + "Usage: /{{ label }} " + getUsage());	
	}

	@Override
	public void onCommand(String[] labels, CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		sender.sendMessage("base!");
		
		System.out.println(this.hasSubCommand());
		
	}

	@Override
	public List<String> onTabComplete() {
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}

}
