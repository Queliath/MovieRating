package by.epam.movierating.command.impl.person;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Person;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.PersonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 25.07.2016.
 */
public class PersonsCommand implements Command {
    private static final int PERSONS_PER_PAGE = 10;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute("selectedLanguage", languageId);

        String pageStr = request.getParameter("page");
        int page = (pageStr == null) ? 1 : Integer.parseInt(pageStr);
        String searchFormNameParam = request.getParameter("searchFormName");
        String searchFormName = (searchFormNameParam == null || searchFormNameParam.isEmpty()) ? null : searchFormNameParam;

        request.setAttribute("searchFormName", searchFormNameParam);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            PersonService personService = serviceFactory.getPersonService();

            int personsCount = personService.getPersonsCountByCriteria(searchFormName, languageId);
            request.setAttribute("personsCount", personsCount);

            int from = (page - 1) * PERSONS_PER_PAGE;
            List<Person> persons = personService.getPersonsByCriteria(searchFormName, from, PERSONS_PER_PAGE, languageId);
            request.setAttribute("persons", persons);
            request.setAttribute("personsFrom", from + 1);
            request.setAttribute("personsTo", from + persons.size());

            List<Integer> pagination = new ArrayList<>();
            for(int i = 0; i < personsCount; i += PERSONS_PER_PAGE){
                int pageNumber = (i / PERSONS_PER_PAGE) + 1;
                pagination.add(pageNumber);
            }
            if(pagination.size() > 1){
                request.setAttribute("pagination", pagination);
                request.setAttribute("activePage", page);
            }
        } catch (ServiceException e) {
            request.setAttribute("serviceError", true);
        }
        request.getRequestDispatcher("WEB-INF/jsp/person/persons.jsp").forward(request, response);
    }
}
