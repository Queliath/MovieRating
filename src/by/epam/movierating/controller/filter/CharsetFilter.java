package by.epam.movierating.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Владислав on 17.07.2016.
 */
public class CharsetFilter implements Filter {
    private static final String CHARSET = "UTF-8";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding(CHARSET);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
