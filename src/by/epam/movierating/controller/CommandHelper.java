package by.epam.movierating.controller;

import by.epam.movierating.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Владислав on 14.07.2016.
 */
public class CommandHelper {
    private static final CommandHelper instance = new CommandHelper();

    private Map<CommandName, Command> commands = new HashMap<>();

    private CommandHelper(){

    }

    public Command getCommand(String name) {
        name = name.replace('-', '_');
        CommandName commandName = CommandName.valueOf(name.toUpperCase());

        Command command = commands.get(commandName);

        return command;
    }

    public static CommandHelper getInstance() {
        return instance;
    }
}
