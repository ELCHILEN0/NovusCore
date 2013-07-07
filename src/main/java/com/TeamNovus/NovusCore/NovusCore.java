package com.TeamNovus.NovusCore;

import org.bukkit.plugin.java.JavaPlugin;

import com.TeamNovus.NovusCore.Commands.Common.TestCommand;
import com.TeamNovus.NovusCore.Commands.Common.TestSubCommand;

public class NovusCore extends JavaPlugin {
	public static NovusCore plugin;
	
	public void onEnable() {
		plugin = this;
		System.out.println("enabling!");
		
		try {
			new TestCommand().register();
			new TestSubCommand().register();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		
	}
	
	public void onDisable() {
		plugin = null;
	}

}
