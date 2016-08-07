package by.epam.movierating.command.impl.rating;

import by.epam.movierating.command.Command;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.RatingService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Владислав on 07.08.2016.
 */
public class AddRatingCommand implements Command {
    private static final int SERVER_ERROR = 500;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = (session == null) ? null : (Integer) session.getAttribute("userId");
        String userStatus = (session == null) ? null : (String) session.getAttribute("userStatus");
        if(userId == null || userStatus == null){
            response.sendRedirect("/Controller?command=login&cause=timeout");
            return;
        }

        String ratingValue = request.getParameter("ratingValue");
        String movieId = request.getParameter("movieId");
        if(ratingValue != null && movieId != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                RatingService ratingService = serviceFactory.getRatingService();
                ratingService.addRating(Integer.parseInt(ratingValue), Integer.parseInt(movieId), userId);
            } catch (ServiceException e) {
                response.sendError(SERVER_ERROR);
            }
        }
        else {
            response.sendRedirect("/Controller?command=welcome");
        }
    }
}
