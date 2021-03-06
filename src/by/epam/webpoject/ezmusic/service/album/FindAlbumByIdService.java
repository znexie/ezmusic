package by.epam.webpoject.ezmusic.service.album;

import by.epam.webpoject.ezmusic.dao.AlbumDAO;
import by.epam.webpoject.ezmusic.dao.factory.DAOFactory;
import by.epam.webpoject.ezmusic.entity.Album;
import by.epam.webpoject.ezmusic.exception.DAOException;
import by.epam.webpoject.ezmusic.exception.ServiceException;

/**
 * Created by Антон on 10.08.2016.
 */
public class FindAlbumByIdService {
    public static Album find(Long albumId) throws ServiceException {
        Album album = null;

        AlbumDAO albumDAO = (AlbumDAO) DAOFactory.createAlbumDAO();


        try {
            album =  albumDAO.find(albumId);
            album.setRewardList(albumDAO.findRewardsByAlbumId(albumId));
            return album;
        } catch (DAOException e) {
            throw new ServiceException("Find album by id service exception", e);
        }
    }
}
