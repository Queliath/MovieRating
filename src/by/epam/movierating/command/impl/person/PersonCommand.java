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
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Владислав on 25.07.2016.
 */
public class PersonCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);

        if(id == -1){
            response.sendRedirect("/Controller?command=welcome");
            return;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        setLocaleAttributes(request, languageId);

        try{
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            PersonService personService = serviceFactory.getPersonService();
            Person person = personService.getPersonById(id, languageId);
            if(person != null){
                request.setAttribute("person", person);
            }
        } catch (ServiceException e) {
            request.setAttribute("serviceError", true);
        }

        request.getRequestDispatcher("WEB-INF/jsp/person/person.jsp").forward(request, response);
    }

    private void setLocaleAttributes(HttpServletRequest request, String languageId){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("locale", new Locale(languageId));

        request.setAttribute("siteName", resourceBundle.getString("locale.siteName"));
        request.setAttribute("mainPageName", resourceBundle.getString("locale.mainPageName"));
        request.setAttribute("catalogPageName", resourceBundle.getString("locale.catalogPageName"));
        request.setAttribute("registrationPageName", resourceBundle.getString("locale.registrationPageName"));
        request.setAttribute("loginPageName", resourceBundle.getString("locale.loginPageName"));
        request.setAttribute("profilePageName", resourceBundle.getString("locale.profilePageName"));
        request.setAttribute("logoutName", resourceBundle.getString("locale.logoutName"));
        request.setAttribute("personsPageName", resourceBundle.getString("locale.personsPageName"));
        request.setAttribute("usersPageName", resourceBundle.getString("locale.usersPageName"));
        request.setAttribute("localeOther", resourceBundle.getString("locale.other"));
        request.setAttribute("genresPageName", resourceBundle.getString("locale.genresPageName"));
        request.setAttribute("countriesPageName", resourceBundle.getString("locale.countriesPageName"));
        request.setAttribute("localeMoviesTotal", resourceBundle.getString("locale.moviesTotal"));
        request.setAttribute("localeDateOfBirth", resourceBundle.getString("locale.dateOfBirth"));
        request.setAttribute("localePlaceOfBirth", resourceBundle.getString("locale.placeOfBirth"));
        request.setAttribute("localeCountry", resourceBundle.getString("locale.country"));
        request.setAttribute("localeGenre", resourceBundle.getString("locale.genre"));
        request.setAttribute("localeYear", resourceBundle.getString("locale.year"));
        request.setAttribute("localeBudget", resourceBundle.getString("locale.budget"));
        request.setAttribute("localePremiere", resourceBundle.getString("locale.premiere"));
        request.setAttribute("localeDirector", resourceBundle.getString("locale.director"));
        request.setAttribute("localeLasting", resourceBundle.getString("locale.lasting"));
        request.setAttribute("localeMinute", resourceBundle.getString("locale.minute"));
        request.setAttribute("localeRating", resourceBundle.getString("locale.rating"));
        request.setAttribute("localeServiceError", resourceBundle.getString("locale.serviceError"));
        request.setAttribute("localeNoPerson", resourceBundle.getString("locale.noPerson"));
        request.setAttribute("localeAsActor", resourceBundle.getString("locale.asActor"));
        request.setAttribute("localeAsDirector", resourceBundle.getString("locale.asDirector"));
        request.setAttribute("localeAsProducer", resourceBundle.getString("locale.asProducer"));
        request.setAttribute("localeAsWriter", resourceBundle.getString("locale.asWriter"));
        request.setAttribute("localeAsPainter", resourceBundle.getString("locale.asPainter"));
        request.setAttribute("localeAsOperator", resourceBundle.getString("locale.asOperator"));
        request.setAttribute("localeAsEditor", resourceBundle.getString("locale.asEditor"));
        request.setAttribute("localeAsComposer", resourceBundle.getString("locale.asComposer"));
        request.setAttribute("localeEdit", resourceBundle.getString("locale.edit"));
        request.setAttribute("localeDelete", resourceBundle.getString("locale.delete"));
        request.setAttribute("localeAddMovie", resourceBundle.getString("locale.addMovie"));
        request.setAttribute("localeDeleteTitle", resourceBundle.getString("locale.deleteTitle"));
        request.setAttribute("localeDeleteBody", resourceBundle.getString("locale.deleteBody"));
        request.setAttribute("localeCancel", resourceBundle.getString("locale.cancel"));

        request.setAttribute("selectedLanguage", languageId);
    }
}
