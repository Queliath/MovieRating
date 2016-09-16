package by.epam.movierating.command.impl.person;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Person;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.PersonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Services the request to show a certain person by id.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class ViewPersonCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/person/person.jsp";

    private static final String WELCOME_PAGE = "/Controller?command=welcome";

    private static final String REQUEST_ID_PARAM = "id";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String PERSON_REQUEST_ATTR = "person";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(REQUEST_ID_PARAM);
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);

        if(id == -1){
            response.sendRedirect(WELCOME_PAGE);
            return;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try{
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            PersonService personService = serviceFactory.getPersonService();
            Person person = personService.getPersonById(id, languageId);
            if(person != null){
                request.setAttribute(PERSON_REQUEST_ATTR, person);
            }
        } catch (ServiceException e) {
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
