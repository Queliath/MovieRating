package by.epam.movierating.controller;

import by.epam.movierating.command.Command;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        Command command  = CommandHelper.getInstance().getCommand(commandName);
        command.execute(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        Command command  = CommandHelper.getInstance().getCommand(commandName);
        command.execute(request, response);
    }
}
