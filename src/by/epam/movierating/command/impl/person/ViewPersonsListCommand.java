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
import java.util.ArrayList;
import java.util.List;

/**
 * Services the request from the persons list page.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class ViewPersonsListCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/person/persons.jsp";

    private static final String PAGE_REQUEST_PARAM = "page";
    private static final String MOVIE_REQUEST_PARAM = "movie";
    private static final String RELATION_REQUEST_PARAM = "rel";
    private static final String SEARCH_FORM_NAME_PARAM = "searchFormName";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String MOVIE_ID_REQUEST_ATTR = "movieId";
    private static final String RELATION_TYPE_REQUEST_ATTR = "relationType";
    private static final String PERSONS_COUNT_REQUEST_ATTR = "personsCount";
    private static final String PERSONS_REQUEST_ATTR = "persons";
    private static final String PERSONS_FROM_REQUEST_ATTR = "personsFrom";
    private static final String PERSONS_TO_REQUEST_ATTR = "personsTo";
    private static final String PAGINATION_REQUEST_ATTR = "pagination";
    private static final String ACTIVE_PAGE_REQUEST_ATTR = "activePage";

    private static final int PERSONS_PER_PAGE = 10;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        String pageStr = request.getParameter(PAGE_REQUEST_PARAM);
        int page = (pageStr == null) ? 1 : Integer.parseInt(pageStr);
        String searchFormNameParam = request.getParameter(SEARCH_FORM_NAME_PARAM);
        String searchFormName = (searchFormNameParam == null || searchFormNameParam.isEmpty()) ? null : searchFormNameParam;
        String movieParam = request.getParameter(MOVIE_REQUEST_PARAM);
        Integer movieId = (movieParam == null || movieParam.isEmpty()) ? null : Integer.parseInt(movieParam);
        String relParam = request.getParameter(RELATION_REQUEST_PARAM);
        Integer relationType = (relParam == null || relParam.isEmpty()) ? null : Integer.parseInt(relParam);

        request.setAttribute(SEARCH_FORM_NAME_PARAM, searchFormNameParam);
        request.setAttribute(MOVIE_ID_REQUEST_ATTR, movieId);
        request.setAttribute(RELATION_TYPE_REQUEST_ATTR, relationType);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            PersonService personService = serviceFactory.getPersonService();

            int personsCount = personService.getPersonsCountByCriteria(searchFormName, languageId);
            request.setAttribute(PERSONS_COUNT_REQUEST_ATTR, personsCount);

            int from = (page - 1) * PERSONS_PER_PAGE;
            List<Person> persons = personService.getPersonsByCriteria(searchFormName, from, PERSONS_PER_PAGE, languageId);
            request.setAttribute(PERSONS_REQUEST_ATTR, persons);
            request.setAttribute(PERSONS_FROM_REQUEST_ATTR, from + 1);
            request.setAttribute(PERSONS_TO_REQUEST_ATTR, from + persons.size());

            List<Integer> pagination = new ArrayList<>();
            for(int i = 0; i < personsCount; i += PERSONS_PER_PAGE){
                int pageNumber = (i / PERSONS_PER_PAGE) + 1;
                pagination.add(pageNumber);
            }
            if(pagination.size() > 1){
                request.setAttribute(PAGINATION_REQUEST_ATTR, pagination);
                request.setAttribute(ACTIVE_PAGE_REQUEST_ATTR, page);
            }
        } catch (ServiceException e) {
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
