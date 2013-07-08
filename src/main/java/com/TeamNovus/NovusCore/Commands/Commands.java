package com.TeamNovus.NovusCore.Commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import com.TeamNovus.NovusCore.NovusCore;
import com.TeamNovus.NovusCore.Commands.CommandTypes.AbstractCommand;
import com.TeamNovus.NovusCore.Commands.CommandTypes.Category;

public class Commands {
	private static List<AbstractCommand> commands = new ArrayList<AbstractCommand>();
	private static CommandExecutor commandExecutor = new CommandExecutor();
	private static Plugin plugin = NovusCore.plugin;
	
	public static void register(AbstractCommand command) {
		commands.add(command);
		
		if(command.getParent() == null) {
			PluginCommand cmd = getCommand(command.getAliases()[0], plugin);
			
			cmd.setAliases(Arrays.asList(command.getAliases()));
			cmd.setExecutor(commandExecutor);
			
			getCommandMap().register(plugin.getDescription().getName(), cmd);
		}
	}

	public static AbstractCommand getCommand(String... labels) {
		AbstractCommand lastCommand = null;

		for (int i = 0; i < labels.length; i++) {
			String label = labels[i];
			AbstractCommand nextCommand = null;
			
			if(i == 0) {
				// If we are on a base level then iterate through every command that does not have a parent.
				for(AbstractCommand command : commands) {
					if(!(command.hasParentCommand())) {
						if(Arrays.asList(command.getAliases()).contains(label)) {
							nextCommand = command;
						}
					}
				}
			} else {
				// Otherwise iterate through every command that is a child of lastCommand.
				for(AbstractCommand command : lastCommand.getSubCommands()) {
					if(ArrayUtils.contains(command.getAliases(), label)) {
						nextCommand = command;
					}
				}
			}	
			
			// If no command was found then move on.
			if(nextCommand == null) {
				break;
			}
			
			lastCommand = nextCommand;
			
			// If there are no further subcommands.
			if(!(nextCommand.hasSubCommand())) {
				break;
			}
		}

		return lastCommand;
	}
	
	private static CommandMap getCommandMap() {
		CommandMap commandMap = null;

		try {
			if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
				Field f = SimplePluginManager.class.getDeclaredField("commandMap");
				f.setAccessible(true);

				commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return commandMap;
	}

	private static PluginCommand getCommand(String name, Plugin plugin) {
		PluginCommand command = null;

		try {
			Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			c.setAccessible(true);

			command = c.newInstance(name, plugin);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return command;
	}
	
	public static List<AbstractCommand> getCommands() {
		return commands;
	}

}
