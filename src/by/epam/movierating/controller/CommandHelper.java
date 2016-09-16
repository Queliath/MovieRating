package by.epam.movierating.controller;

import by.epam.movierating.command.Command;
import by.epam.movierating.controller.exception.CommandHelperInitException;
import by.epam.movierating.controller.exception.CommandNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Provides a logic of instancing the Command objects.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public final class CommandHelper {
    private static final String RESOURCE_BUNDLE_NAME = "commands";

    private static final String DEFAULT_COMMAND_NAME = "welcome";

    private static CommandHelper instance;

    private Map<String, Command> commands = new HashMap<>();

    /**
     * Creates the concrete implementations of the Command interface.
     *
     * @throws CommandHelperInitException if there is instance initialization error
     */
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

    /**
     * Returns a Command object by the name.
     *
     * @param commandName a name of the needed command
     * @return a Command object
     * @throws CommandNotFoundException if there is no command with this name
     */
    public Command getCommand(String commandName) throws CommandNotFoundException {
        if(commandName == null){
            return commands.get(DEFAULT_COMMAND_NAME);
        }

        Command command = commands.get(commandName);
        if(command != null){
            return command;
        }
        else {
            throw new CommandNotFoundException("Wrong or empty command name");
        }
    }

    public static synchronized CommandHelper getInstance() throws CommandHelperInitException {
        if(instance == null){
            instance = new CommandHelper();
        }
        return instance;
    }
}
