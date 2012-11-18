package com.TeamNovus.NovusFundamentals;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import com.TeamNovus.NovusFundamentals.Commands.GcClearCmd;
import com.TeamNovus.NovusFundamentals.Commands.HatCmd;
import com.TeamNovus.NovusFundamentals.Commands.ReloadCmd;
import com.TeamNovus.NovusFundamentals.Listeners.ServerListener;

/**
 * This plugin is a collection of small plugins designed for NovusCraft.
 * It features: Hats, ColoredMOTD, Ranking, GarbageCollection
 */
public class NovusFundamentals extends JavaPlugin {
	private static NovusFundamentals plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
		
		reloadConfig();
		
		getCommand("reloadnovus").setExecutor(new ReloadCmd());
		getCommand("hat").setExecutor(new HatCmd());
		getCommand("unhat").setExecutor(new HatCmd());
		getCommand("gcclear").setExecutor(new GcClearCmd());
		
		getServer().getPluginManager().registerEvents(new ServerListener(), this);
	}
	
	
	@Override
	public void onDisable() {
		plugin = null;
	}
	
	public static NovusFundamentals getPlugin() {
		return plugin;
	}
}
