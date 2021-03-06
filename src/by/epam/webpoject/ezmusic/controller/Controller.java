package by.epam.webpoject.ezmusic.controller;

import by.epam.webpoject.ezmusic.command.Command;
import by.epam.webpoject.ezmusic.command.CommandManager;
import by.epam.webpoject.ezmusic.constant.JspPageName;
import by.epam.webpoject.ezmusic.constant.RequestParameter;
import by.epam.webpoject.ezmusic.exception.CommandException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * Created by Антон on 15.07.2016.
 */
@MultipartConfig
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        String commandName = req.getParameter(RequestParameter.COMMAND);
        try {
            Command command = CommandManager.getCommand(commandName);
            if (command != null) {
                page = command.execute(req);
            } else {
                page = JspPageName.INDEX;
            }
        } catch (CommandException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher(page).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String page = null;
        String commandName = req.getParameter(RequestParameter.COMMAND);
        try {
            Command command = CommandManager.getCommand(commandName);
            if (command != null) {
                page = command.execute(req);
            } else {
                page = JspPageName.INDEX;
            }
        } catch (CommandException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher(page).forward(req, resp);
    }

}
