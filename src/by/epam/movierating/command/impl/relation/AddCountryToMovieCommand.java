package by.epam.movierating.command.impl.relation;

import by.epam.movierating.command.Command;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.RelationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Владислав on 10.08.2016.
 */
public class AddCountryToMovieCommand implements Command {
    private static final int SERVER_ERROR = 500;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userStatus = (session == null) ? null : (String) session.getAttribute("userStatus");
        if(userStatus == null || !userStatus.equals("admin")){
            response.sendRedirect("/Controller?command=login&cause=timeout");
            return;
        }

        String movieIdParam = request.getParameter("movieId");
        String countryIdParam = request.getParameter("countryId");
        if(movieIdParam == null || countryIdParam == null){
            response.sendRedirect("/Controller?command=welcome");
            return;
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            RelationService relationService = serviceFactory.getRelationService();
            relationService.addCountryToMovie(Integer.parseInt(movieIdParam), Integer.parseInt(countryIdParam));
            response.sendRedirect("/Controller?command=movie&id=" + movieIdParam);
        } catch (ServiceException e) {
            response.sendError(SERVER_ERROR);
        }
    }
}
