package by.epam.webpoject.ezmusic.service;

import by.epam.webpoject.ezmusic.dao.UserDAO;
import by.epam.webpoject.ezmusic.dao.factory.DAOFactory;
import by.epam.webpoject.ezmusic.entity.User;
import by.epam.webpoject.ezmusic.exception.dao.DAOException;
import by.epam.webpoject.ezmusic.exception.service.ServiceException;

/**
 * Created by Антон on 25.07.2016.
 */
public class UpdateUserService {
    public static boolean execute(User instance) throws ServiceException {
        UserDAO dao = (UserDAO) DAOFactory.createUserDao();
        try {
            return dao.update(instance);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
