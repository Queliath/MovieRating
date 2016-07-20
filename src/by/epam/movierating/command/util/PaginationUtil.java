package by.epam.movierating.command.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Владислав on 20.07.2016.
 */
public class PaginationUtil {
    private static final String PAGE_PARAMETER_NAME = "page";

    private static final char QUERY_SEPARATOR = '?';

    public static String createLinkForPage(HttpServletRequest request, int pageNumber){
        StringBuilder queryString = new StringBuilder();
        queryString.append(request.getRequestURI());
        queryString.append(QUERY_SEPARATOR);
        queryString.append(request.getQueryString());

        int indexOfPageParam = queryString.indexOf(PAGE_PARAMETER_NAME);
        if(indexOfPageParam == -1){
            queryString.append('&');
            queryString.append(PAGE_PARAMETER_NAME);
            queryString.append('=');
            queryString.append(pageNumber);
        }
        else {
            int indexOfValue = queryString.indexOf("&", indexOfPageParam);
            if(indexOfValue == -1){
                queryString = queryString.replace(indexOfPageParam + PAGE_PARAMETER_NAME.length() + 1, queryString.length(), Integer.toString(pageNumber));
            }
            else {
                queryString = queryString.replace(indexOfPageParam + PAGE_PARAMETER_NAME.length() + 1, indexOfValue, Integer.toString(pageNumber));
            }
        }
        return queryString.toString();
    }
}
