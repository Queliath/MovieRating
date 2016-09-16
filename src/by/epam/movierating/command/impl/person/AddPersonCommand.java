package by.epam.movierating.command.impl.person;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.PersonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request from the adding person form.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class AddPersonCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/person/add-person-form.jsp";

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";
    private static final String SUCCESS_REDIRECT_PAGE = "/Controller?command=persons";

    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String ADMIN_USER_STATUS = "admin";

    private static final String PERSON_FORM_NAME_PARAM = "personFormName";
    private static final String PERSON_FORM_DATE_OF_BIRTH_PARAM = "personFormDateOfBirth";
    private static final String PERSON_FORM_PLACE_OF_BIRTH_PARAM = "personFormPlaceOfBirth";
    private static final String PERSON_FORM_PHOTO_PARAM = "personFormPhoto";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userStatus = (session == null) ? null : (String) session.getAttribute(USER_STATUS_SESSION_ATTRIBUTE);
        if(userStatus == null || !userStatus.equals(ADMIN_USER_STATUS)){
            response.sendRedirect(SESSION_TIMEOUT_PAGE);
            return;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        String personFormName= request.getParameter(PERSON_FORM_NAME_PARAM);
        String personFormDateOfBirth = request.getParameter(PERSON_FORM_DATE_OF_BIRTH_PARAM);
        String personFormPlaceOfBirth = request.getParameter(PERSON_FORM_PLACE_OF_BIRTH_PARAM);
        String personFormPhoto = request.getParameter(PERSON_FORM_PHOTO_PARAM);
        if(personFormName != null && personFormDateOfBirth != null && personFormPlaceOfBirth != null && personFormPhoto != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                PersonService personService = serviceFactory.getPersonService();
                personService.addPerson(personFormName, personFormDateOfBirth, personFormPlaceOfBirth, personFormPhoto);
                response.sendRedirect(SUCCESS_REDIRECT_PAGE);
                return;
            } catch (ServiceException e) {
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
                request.setAttribute(PERSON_FORM_NAME_PARAM, personFormName);
                request.setAttribute(PERSON_FORM_DATE_OF_BIRTH_PARAM, personFormDateOfBirth);
                request.setAttribute(PERSON_FORM_PLACE_OF_BIRTH_PARAM, personFormPlaceOfBirth);
                request.setAttribute(PERSON_FORM_PHOTO_PARAM, personFormPhoto);
            }
        }

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
