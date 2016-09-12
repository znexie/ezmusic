package by.epam.webpoject.ezmusic.filter;

import by.epam.webpoject.ezmusic.constant.FilterParameter;
import by.epam.webpoject.ezmusic.constant.RequestParameter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Антон on 03.08.2016.
 */
public class EncodingFilter implements Filter {
    private String encoding;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter(FilterParameter.ENCODING);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        String song = servletRequest.getParameter(RequestParameter.SONG_NAME);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
