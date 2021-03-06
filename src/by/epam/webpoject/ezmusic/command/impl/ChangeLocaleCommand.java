package by.epam.webpoject.ezmusic.command.impl;

import by.epam.webpoject.ezmusic.command.Command;
import by.epam.webpoject.ezmusic.constant.RequestParameter;
import by.epam.webpoject.ezmusic.exception.CommandException;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Антон on 09.08.2016.
 */
public class ChangeLocaleCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String locale = request.getParameter(RequestParameter.LOCALE);
        HttpSession session = request.getSession(false);
        session.setAttribute(RequestParameter.LOCALE, locale);
        return new Gson().toJson(locale);
    }
}
