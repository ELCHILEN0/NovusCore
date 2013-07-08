package com.TeamNovus.NovusCore.Commands.CommandTypes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.TeamNovus.NovusCore.Commands.Commands;

public abstract class AbstractCommand {
	private AbstractCommand 	parent;
	private String[] 	aliases;
	private String		description;
	private String 		permission;
	private String		permissionMessage;
	private boolean		allowPlayer = true;
	private boolean 	allowConsole = true;
	private String		playerDisallowedMessage;
	private String		consoleDisallowedMessage;
	
	public AbstractCommand(AbstractCommand parent, String... aliases) throws InstantiationException {
		this.parent = parent;
		
		if (aliases.length == 0) {
			throw new InstantiationException("The command must have 1 or more alias.");
		}
		
		for (String alias : aliases) {
			if (alias.contains(" ")) {
				throw new InstantiationException("Invalid characters.  An alias cannot contain spaces.");
			}
		}
		
		this.aliases = aliases;
	}
	
	public AbstractCommand(String... aliases) throws InstantiationException {
		this(null, aliases);
	}
	
	public AbstractCommand getParent() {
		return parent;
	}
	
	public String[] getAliases() {
		return aliases;
	}
	
	public String getDescription() {
		return description;
	}
	
	protected AbstractCommand setDescription(String description) {
		this.description = description;
		
		return this;
	}
	
	public String getPermission() {
		return permission;
	}
	
	protected AbstractCommand setPermission(String permission) {
		this.permission = permission;
		
		return this;
	}
	
	public String getPermissionMessage() {
		return permissionMessage;
	}

	protected AbstractCommand setPermissionMessage(String permissionMessage) {
		this.permissionMessage = permissionMessage;
		
		return this;
	}
	
	public boolean doesAllowPlayer() {
		return allowPlayer;
	}
	
	public boolean doesntAllowPlayer() {
		return !(allowPlayer);
	}
	
	protected AbstractCommand setAllowPlayer(boolean allowPlayer) {
		this.allowPlayer = allowPlayer;
		
		return this;
	}
	
	public String getPlayerDisallowedMessage() {
		return playerDisallowedMessage;
	}
	
	protected AbstractCommand setPlayerDisallowedMessage(String playerDisallowedMessage) {
		this.playerDisallowedMessage = playerDisallowedMessage;
		
		return this;
	}
	
	public boolean doesAllowConsole() {
		return allowConsole;
	}
	
	public boolean doesntAllowConsole() {
		return !(allowConsole);
	}	
	
	protected AbstractCommand setAllowConsole(boolean allowConsole) {
		this.allowConsole = allowConsole;
		
		return this;
	}
	
	public String getConsoleDisallowedMessage() {
		return consoleDisallowedMessage;
	}
	
	protected AbstractCommand setConsoleDisallowedMessage(String consoleDisallowedMessage) {
		this.consoleDisallowedMessage = consoleDisallowedMessage;
		
		return this;
	}

	public List<AbstractCommand> getSubCommands() {
		ArrayList<AbstractCommand> commands = new ArrayList<AbstractCommand>();
		
		for(AbstractCommand command : Commands.getCommands()) {
			if(command.getParent() == this) {
				commands.add(command);
			}
		}
		
		return commands;
	}
	
	public boolean hasSubCommand() {
		for(AbstractCommand command : Commands.getCommands()) {
			if(command.getParent() == this) {
				return true;
			}
		}

		return false;
	}
	
	public boolean isSubCommand(AbstractCommand command) {
		return command.getParent() == this;
	}

	public boolean hasParentCommand() {
		return this.getParent() != null;
	}
	
	public boolean isParentCommand(AbstractCommand command) {
		return this.getParent() == command;
	}
	
	public void register() {
		Commands.register(this);
	}
	
	public abstract void onCommand(String[] labels, CommandSender sender, String[] args);
	public abstract List<String> onTabComplete();

}
