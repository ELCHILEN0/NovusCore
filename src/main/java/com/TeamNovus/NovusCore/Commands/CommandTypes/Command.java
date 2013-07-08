package com.TeamNovus.NovusCore.Commands.CommandTypes;

public abstract class Command extends AbstractCommand {
	private String 		usage;
	private String		usageMessage;
	
	public Command(String[] aliases) throws InstantiationException {
		super(aliases);
	}

	public Command(Category parent, String[] aliases) throws InstantiationException {
		super(parent, aliases);
	}
	
	public String getUsage() {
		return usage;
	}
	
	protected Command setUsage(String usage) {
		this.usage = usage;
		
		return this;
	}
	
	public int getMinArgs() {
		String[] args = usage.split(" ");
		
		int count = 0;
		
		for (String arg : args) {
			if(arg.startsWith("<") && arg.endsWith(">")) 
				count++;
		}
		
		return count;
	}
	
	public int getMaxArgs() {
		String[] args = usage.split(" ");
		
		int count = 0;
		
		for (String arg : args) {
			if((arg.startsWith("<") && arg.endsWith(">")) || (arg.startsWith("[") && arg.endsWith("]"))) 
				count++;
		}
		
		return count;
	}
	
	public String getUsageMessage() {
		return usageMessage;
	}
	
	protected Command setUsageMessage(String usageMessage) {
		this.usageMessage = usageMessage;
		
		return this;
	}
}
