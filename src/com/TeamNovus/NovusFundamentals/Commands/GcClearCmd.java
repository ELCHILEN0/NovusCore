package com.TeamNovus.NovusFundamentals.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.TeamNovus.NovusFundamentals.NovusFundamentals;

public class GcClearCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("gcclear") || commandLabel.equalsIgnoreCase("gcc")) {
			if(!(sender.hasPermission("novusfundamentals.gcclear"))) {
				sender.sendMessage(ChatColor.RED + "You do not have permission for this command!");
				return true;
			}
			
			sender.sendMessage(ChatColor.GREEN + "Performing GC tasks on the server...");

			List<String> ignoredEntities = NovusFundamentals.getPlugin().getConfig().getStringList("garbage.ignored-entites");
			List<String> ignoredWorlds = NovusFundamentals.getPlugin().getConfig().getStringList("garbage.ignored-worlds");
			
			for(World world : Bukkit.getServer().getWorlds()) {	
				int removedEntities = 0;
				int unloadedChunks = 0;
							
				if(ignoredWorlds.contains(world.getName())) continue;
				
				for(Entity e : world.getEntities()) {
					if((e instanceof Player)) continue;
					if(!(ignoredEntities.contains(e.getType().toString()))) continue;
					
					e.remove();
					removedEntities++;
				}
				
				List<Chunk> noUnloadChunks = new ArrayList<Chunk>();
				for(Player p : world.getPlayers()) {
					int X = p.getLocation().getChunk().getX();
					int Z = p.getLocation().getChunk().getZ();
					
					for(int x = X-Bukkit.getServer().getViewDistance(); x<=X+Bukkit.getServer().getViewDistance(); x++) {
						for(int z = Z-Bukkit.getServer().getViewDistance(); z<=Z+Bukkit.getServer().getViewDistance(); z++) {
							noUnloadChunks.add(world.getChunkAt(x, z));
						}
					}
				}
				
				for(Chunk chunk : world.getLoadedChunks()) {
					if(!(noUnloadChunks.contains(chunk))) {
						chunk.unload();
						unloadedChunks++;
					}
				}
				
				sender.sendMessage(ChatColor.BLUE +"World: '" + ChatColor.RED + world.getName() + ChatColor.BLUE + "': "+ ChatColor.RED + removedEntities + ChatColor.BLUE + " entites removed, "+ ChatColor.RED + unloadedChunks + ChatColor.BLUE + " unloaded chunks!");
			}
			
			sender.sendMessage(ChatColor.GREEN + "GC tasks completed sucessfully!");
			
		}
		return true;
	}
	
}
