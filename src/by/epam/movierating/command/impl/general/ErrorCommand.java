package by.epam.movierating.command.impl.general;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Services the request to show an error page.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class ErrorCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/error.jsp";

    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
