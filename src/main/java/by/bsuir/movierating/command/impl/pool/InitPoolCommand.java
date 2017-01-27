package by.bsuir.movierating.command.impl.pool;

import by.bsuir.movierating.command.Command;
import by.bsuir.movierating.service.exception.ServiceException;
import by.bsuir.movierating.service.factory.ServiceFactory;
import by.bsuir.movierating.service.inter.PoolService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Services the request to destroy the Connection Pool.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class InitPoolCommand implements Command {
    private static final String WELCOME_PAGE = "/Controller?command=welcome";
    private static final int ERROR_CODE = 500;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            PoolService poolService = serviceFactory.getPoolService();
            poolService.init();
            response.sendRedirect(WELCOME_PAGE);
        } catch (ServiceException e) {
            response.sendError(ERROR_CODE);
        }
    }
}
