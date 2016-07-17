package by.epam.movierating.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Владислав on 17.07.2016.
 */
public class CharsetFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
