package by.epam.webpoject.ezmusic.command.impl.song;

import by.epam.webpoject.ezmusic.command.Command;
import by.epam.webpoject.ezmusic.constant.JspPageName;
import by.epam.webpoject.ezmusic.constant.RequestParameter;
import by.epam.webpoject.ezmusic.entity.Order;
import by.epam.webpoject.ezmusic.exception.command.CommandException;
import by.epam.webpoject.ezmusic.exception.service.ServiceException;
import by.epam.webpoject.ezmusic.parser.ParameterParser;
import by.epam.webpoject.ezmusic.service.song.DeleteSongFromCartService;
import by.epam.webpoject.ezmusic.validator.SongParametersValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Антон on 02.09.2016.
 */
public class DeleteSongFromCartCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = null;

        String  songId = request.getParameter(RequestParameter.SONG_ID);
        Order cart = (Order) request.getSession().getAttribute(RequestParameter.CART);
        boolean isValidRequest = SongParametersValidator.validateDeleteParameters(songId);
        if(isValidRequest) {
            try {
                DeleteSongFromCartService.delete(cart, ParameterParser.parseLong(songId));
                request.setAttribute(RequestParameter.MESSAGE, "Successfully deleted song from cart");
                page = JspPageName.USER_CART;
            } catch (ServiceException e) {
                throw new CommandException("Delete song from cart command exception", e);
            }
        }else {
            request.setAttribute(RequestParameter.MESSAGE, "Oops! Something is wrong");
            page = JspPageName.USER_HOME;
        }
        return page;
    }
}
