package by.epam.movierating.controller;

import by.epam.movierating.command.Command;
import by.epam.movierating.controller.exception.CommandHelperInitException;
import by.epam.movierating.controller.exception.CommandNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Владислав on 14.07.2016.
 */
public class Controller extends HttpServlet {
    private static final String COMMAND = "command";

    private static final int PAGE_NOT_FOUND_ERROR = 404;
    private static final int SERVER_ERROR = 500;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        try {
            Command command = CommandHelper.getInstance().getCommand(commandName);
            command.execute(request, response);
        } catch (CommandNotFoundException e) {
            response.sendError(PAGE_NOT_FOUND_ERROR);
        } catch (CommandHelperInitException e) {
            response.sendError(SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        try {
            Command command = CommandHelper.getInstance().getCommand(commandName);
            command.execute(request, response);
        } catch (CommandNotFoundException e) {
            response.sendError(PAGE_NOT_FOUND_ERROR);
        } catch (CommandHelperInitException e) {
            response.sendError(SERVER_ERROR);
        }
    }
}
