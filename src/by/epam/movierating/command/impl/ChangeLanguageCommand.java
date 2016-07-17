package by.epam.movierating.command.impl;

import by.epam.movierating.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Владислав on 17.07.2016.
 */
public class ChangeLanguageCommand implements Command {
    private static final String CHANGE_LANGUAGE_PARAMETER = "changeLanguageId";
    private static final String SESSION_LANGUAGE_ID = "languageId";
    private static final String SESSION_PREV_QUERY = "prevQuery";
    private static final String WELCOME_PAGE = "/Controller?command=welcome";

    private ArrayList<String> availableLanguages = new ArrayList<>();
    private static final String ENGLISH = "EN";
    private static final String RUSSIAN = "RU";

    public ChangeLanguageCommand(){
        availableLanguages.add(ENGLISH);
        availableLanguages.add(RUSSIAN);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String languageId = request.getParameter(CHANGE_LANGUAGE_PARAMETER);

        HttpSession session = request.getSession(true);
        if(languageId != null){
            if(!availableLanguages.contains(languageId)){
                languageId = ENGLISH;
            }

            session.setAttribute(SESSION_LANGUAGE_ID, languageId);
        }

        String prevQuery = (String) session.getAttribute(SESSION_PREV_QUERY);
        if(prevQuery == null){
            prevQuery = WELCOME_PAGE;
        }
        response.sendRedirect(prevQuery);
    }
}
