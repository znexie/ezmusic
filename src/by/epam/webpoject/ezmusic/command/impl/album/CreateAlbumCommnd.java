package by.epam.webpoject.ezmusic.command.impl.album;

import by.epam.webpoject.ezmusic.command.Command;
import by.epam.webpoject.ezmusic.exception.command.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Антон on 10.08.2016.
 */
public class CreateAlbumCommnd implements Command{
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
