package by.epam.webpoject.ezmusic.service.song;

import by.epam.webpoject.ezmusic.dao.SongDAO;
import by.epam.webpoject.ezmusic.dao.factory.DAOFactory;
import by.epam.webpoject.ezmusic.entity.Album;
import by.epam.webpoject.ezmusic.entity.Author;
import by.epam.webpoject.ezmusic.entity.Song;
import by.epam.webpoject.ezmusic.exception.dao.DAOException;
import by.epam.webpoject.ezmusic.exception.service.ServiceException;

import java.util.Date;

/**
 * Created by Антон on 10.08.2016.
 */
public class CreateSongService {
    public static Long create(Song instance) throws ServiceException {
        SongDAO dao = (SongDAO) DAOFactory.createSongDAO();
        Long generatedId = null;
        try {
            Date javaDate = new Date();
            instance.setPublicationDate(new java.sql.Date(javaDate.getTime()));
            generatedId = dao.create(instance);
            if(instance.getAlbumList() != null) {
                for (Album album : instance.getAlbumList()) {
                    dao.createSongAlbum(generatedId, album.getAlbumId());
                }
            }
            if(instance.getAuthorList() != null) {
                for (Author author : instance.getAuthorList()   ) {
                    dao.createSongAuthor(generatedId, author.getAuthorId());
                }
            }
        } catch (DAOException e) {
            throw new ServiceException("Create song service exception", e);
        }
        return generatedId;
    }
}
