package by.epam.webpoject.ezmusic.service.author;

import by.epam.webpoject.ezmusic.dao.AuthorDAO;
import by.epam.webpoject.ezmusic.dao.factory.DAOFactory;
import by.epam.webpoject.ezmusic.exception.DAOException;
import by.epam.webpoject.ezmusic.exception.ServiceException;

/**
 * Created by Антон on 15.08.2016.
 */
public class DeleteAuthorService {
    public static void delete(Long authorId) throws ServiceException {

        AuthorDAO authorDAO = (AuthorDAO) DAOFactory.createAuthorDAO();

        try {
            authorDAO.delete(authorId);
        } catch (DAOException e) {
            throw new ServiceException("Delete author service exception", e);
        }
    }
}
