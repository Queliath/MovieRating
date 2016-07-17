package by.epam.movierating.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Владислав on 17.07.2016.
 */
public class LocaleFilter implements Filter {
    private static final String CHANGE_LANGUAGE_PARAMETER = "changeLanguageId";
    private static final String SESSION_LANGUAGE_ID = "languageId";

    private ArrayList<String> availableLanguages = new ArrayList<>();
    private static final String ENGLISH = "EN";
    private static final String RUSSIAN = "RU";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String languageId = req.getParameter(CHANGE_LANGUAGE_PARAMETER);
        if(languageId != null){
            if(!availableLanguages.contains(languageId)){
                languageId = ENGLISH;
            }

            HttpServletRequest request = (HttpServletRequest) req;
            HttpSession session = request.getSession(true);
            session.setAttribute(SESSION_LANGUAGE_ID, languageId);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        availableLanguages.add(ENGLISH);
        availableLanguages.add(RUSSIAN);
    }

}
