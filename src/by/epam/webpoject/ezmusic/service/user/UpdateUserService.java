package by.epam.webpoject.ezmusic.service.user;

import by.epam.webpoject.ezmusic.dao.UserDAO;
import by.epam.webpoject.ezmusic.dao.factory.DAOFactory;
import by.epam.webpoject.ezmusic.entity.User;
import by.epam.webpoject.ezmusic.exception.DAOException;
import by.epam.webpoject.ezmusic.exception.ServiceException;

/**
 * Created by Антон on 25.07.2016.
 */
public class UpdateUserService {
    public static void update(User instance) throws ServiceException {

        UserDAO userDAO = (UserDAO) DAOFactory.createUserDAO();

        try {
            userDAO.update(instance);
        } catch (DAOException e) {
            throw new ServiceException("Update user service exception", e);
        }
    }
}
