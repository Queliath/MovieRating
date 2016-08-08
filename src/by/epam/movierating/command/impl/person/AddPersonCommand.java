package by.epam.movierating.command.impl.person;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.PersonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Владислав on 08.08.2016.
 */
public class AddPersonCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userStatus = (session == null) ? null : (String) session.getAttribute("userStatus");
        if(userStatus == null || !userStatus.equals("admin")){
            response.sendRedirect("/Controller?command=login&cause=timeout");
            return;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute("selectedLanguage", languageId);

        String personFormName= request.getParameter("personFormName");
        String personFormDateOfBirth = request.getParameter("personFormDateOfBirth");
        String personFormPlaceOfBirth = request.getParameter("personFormPlaceOfBirth");
        String personFormPhoto = request.getParameter("personFormPhoto");
        if(personFormName != null && personFormDateOfBirth != null && personFormPlaceOfBirth != null && personFormPhoto != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                PersonService personService = serviceFactory.getPersonService();
                personService.addPerson(personFormName, personFormDateOfBirth, personFormPlaceOfBirth, personFormPhoto);
                response.sendRedirect("/Controller?command=persons");
                return;
            } catch (ServiceException e) {
                request.setAttribute("serviceError", true);
                request.setAttribute("personFormName", personFormName);
                request.setAttribute("personFormDateOfBirth", personFormDateOfBirth);
                request.setAttribute("personFormPlaceOfBirth", personFormPlaceOfBirth);
                request.setAttribute("personFormPhoto", personFormPhoto);
            }
        }

        request.getRequestDispatcher("WEB-INF/jsp/person/add-person-form.jsp").forward(request, response);
    }
}
