package by.epam.webpoject.ezmusic.command.impl.album;

import by.epam.webpoject.ezmusic.command.Command;
import by.epam.webpoject.ezmusic.constant.JspPageName;
import by.epam.webpoject.ezmusic.constant.RequestParameter;
import by.epam.webpoject.ezmusic.entity.Album;
import by.epam.webpoject.ezmusic.exception.command.CommandException;
import by.epam.webpoject.ezmusic.exception.service.ServiceException;
import by.epam.webpoject.ezmusic.service.album.FindAllAlbumsService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Антон on 18.08.2016.
 */
public class FindAllAlbumsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = null;
        ArrayList<Album> allAlbums = null;
        try {
            allAlbums = FindAllAlbumsService.find();
            if (allAlbums != null) {
                if(allAlbums.isEmpty()){
                    request.setAttribute(RequestParameter.MESSAGE, "Not found any albums");
                }
                request.setAttribute(RequestParameter.ALL_ALBUMS, allAlbums);
                page = JspPageName.ADMIN_ALL_ALBUMS;
            }else {
                request.setAttribute(RequestParameter.MESSAGE, "Oops! Something is wrong");
                page = JspPageName.ADMIN_HOME;
            }
        } catch (ServiceException e) {
            throw new CommandException("Find albums command exception", e);
        }
        return page;
    }
}
