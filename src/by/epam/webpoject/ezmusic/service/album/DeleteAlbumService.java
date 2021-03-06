package by.epam.webpoject.ezmusic.service.album;

import by.epam.webpoject.ezmusic.dao.AlbumDAO;
import by.epam.webpoject.ezmusic.dao.factory.DAOFactory;
import by.epam.webpoject.ezmusic.exception.DAOException;
import by.epam.webpoject.ezmusic.exception.ServiceException;

/**
 * Created by Антон on 10.08.2016.
 */
public class DeleteAlbumService {
    public static void delete(Long albumId) throws ServiceException {

        AlbumDAO albumDAO = (AlbumDAO) DAOFactory.createAlbumDAO();

        try {
            albumDAO.delete(albumId);
        } catch (DAOException e) {
            throw new ServiceException("Delete album service exception", e);
        }
    }
}
