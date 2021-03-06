package by.epam.webpoject.ezmusic.filter;

import by.epam.webpoject.ezmusic.command.Command;
import by.epam.webpoject.ezmusic.command.CommandManager;
import by.epam.webpoject.ezmusic.constant.JspPageName;
import by.epam.webpoject.ezmusic.constant.MessageKey;
import by.epam.webpoject.ezmusic.constant.RequestParameter;
import by.epam.webpoject.ezmusic.entity.User;
import by.epam.webpoject.ezmusic.exception.CommandException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Антон on 11.09.2016.
 */
public class CommandSecurityFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = ((HttpServletRequest) req).getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute(RequestParameter.USER);
        }
        Command command = null;
        try {
            command = CommandManager.getCommand(request.getParameter(RequestParameter.COMMAND));
        } catch (CommandException e) {
            request.setAttribute(RequestParameter.MESSAGE, "Invalid command");
        }

        if (command != null) {
            if (CommandManager.isUserCommand(command)) {
                if (user != null) {
                    if (!user.getIsAdmin()) {
                        chain.doFilter(req, resp);
                    } else {
                        request.setAttribute(RequestParameter.MESSAGE, MessageKey.USER_LOGIN);
                        request.getServletContext().getRequestDispatcher(JspPageName.LOGIN).forward(request, response);
                    }
                } else {
                    request.setAttribute(RequestParameter.MESSAGE, MessageKey.USER_LOGIN);
                    request.getServletContext().getRequestDispatcher(JspPageName.LOGIN).forward(request, response);
                }
            } else if (CommandManager.isAdminCommand(command)) {
                if (user != null) {
                    if (user.getIsAdmin()) {
                        chain.doFilter(req, resp);
                    } else {
                        request.setAttribute(RequestParameter.MESSAGE, MessageKey.ADMIN_LOGIN);
                        request.getServletContext().getRequestDispatcher(JspPageName.LOGIN).forward(request, response);
                    }
                } else {
                    request.setAttribute(RequestParameter.MESSAGE, MessageKey.ADMIN_LOGIN);
                    request.getServletContext().getRequestDispatcher(JspPageName.LOGIN).forward(request, response);
                }
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
