package by.epam.movierating.controller;

import by.epam.movierating.command.Command;
import by.epam.movierating.controller.exception.CommandHelperInitException;
import by.epam.movierating.controller.exception.CommandNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by Владислав on 14.07.2016.
 */
public final class CommandHelper {
    private static final String RESOURCE_BUNDLE_NAME = "commands";

    private static final String DEFAULT_COMMAND_NAME = "welcome";

    private static CommandHelper instance;

    private Map<String, Command> commands = new HashMap<>();

    private CommandHelper() throws CommandHelperInitException {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);
            Set<String> commandNameSet = resourceBundle.keySet();
            for (String commandName : commandNameSet) {
                String commandClassName = resourceBundle.getString(commandName);
                Command command = (Command) Class.forName(commandClassName).newInstance();
                commands.put(commandName, command);
            }
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new CommandHelperInitException("Cannot init CommandHelper", e);
        }
    }

    public Command getCommand(String commandName) throws CommandNotFoundException {
        if(commandName == null){
            return commands.get(DEFAULT_COMMAND_NAME);
        }

        try {
            return commands.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new CommandNotFoundException("Wrong or empty command name", e);
        }
    }

    public static synchronized CommandHelper getInstance() throws CommandHelperInitException {
        if(instance == null){
            instance = new CommandHelper();
        }
        return instance;
    }
}
