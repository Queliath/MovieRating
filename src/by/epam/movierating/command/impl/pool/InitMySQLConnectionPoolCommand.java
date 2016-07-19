package by.epam.movierating.command.impl.pool;

import by.epam.movierating.command.Command;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.PoolService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Владислав on 19.07.2016.
 */
public class InitMySQLConnectionPoolCommand implements Command {
    private static final String WELCOME_PAGE = "/Controller?command=welcome";
    private static final int ERROR_CODE = 500;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        PoolService poolService = serviceFactory.getPoolService();
        try {
            poolService.init();
            response.sendRedirect(WELCOME_PAGE);
        } catch (ServiceException e) {
            response.sendError(ERROR_CODE);
        }
    }
}
