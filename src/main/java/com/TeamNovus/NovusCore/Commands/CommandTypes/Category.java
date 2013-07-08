package com.TeamNovus.NovusCore.Commands.CommandTypes;

public abstract class Category extends AbstractCommand {
	public Category(String[] aliases) throws InstantiationException {
		super(aliases);
	}

	public Category(Category parent, String[] aliases) throws InstantiationException {
		super(parent, aliases);
	}
}
