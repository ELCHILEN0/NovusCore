package com.TeamNovus.NovusFundamentals.Listeners;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.TeamNovus.NovusFundamentals.NovusFundamentals;

public class ServerListener implements Listener {
	@EventHandler
	public void onServerListPingEvent(ServerListPingEvent event) {
		if(NovusFundamentals.getPlugin().getConfig().getBoolean("motd.enabled")) {
			// Fetch the MOTDs from the config
			List<String> motds = NovusFundamentals.getPlugin().getConfig().getStringList("motd.messages");
			
			// Fetch a MOTD at random
			String motd = motds.get(0 + (int)(Math.random()*(motds.size())));
			
			// Set the MOTD parsing color appropriately
			if(NovusFundamentals.getPlugin().getConfig().getBoolean("motd.allow-color")) {
				event.setMotd(ChatColor.translateAlternateColorCodes("&".charAt(0), motd));
			} else {
				event.setMotd(motd);
			}
		}
	}
}
