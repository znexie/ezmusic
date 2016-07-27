package by.epam.webpoject.ezmusic.service;

import by.epam.webpoject.ezmusic.dao.UserDAO;
import by.epam.webpoject.ezmusic.dao.factory.DAOFactory;
import by.epam.webpoject.ezmusic.encryptor.MD5Encryptor;
import by.epam.webpoject.ezmusic.entity.User;
import by.epam.webpoject.ezmusic.exception.dao.DAOException;
import by.epam.webpoject.ezmusic.exception.service.ServiceException;

/**
 * Created by Антон on 20.07.2016.
 */
public class LoginUserService {

    public static User execute(String username, String password) throws ServiceException {
        UserDAO userDAO = (UserDAO) DAOFactory.createUserDao();
        try {
            String md5Hash = MD5Encryptor.getMD5(password);
            return userDAO.login(username, md5Hash);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }
}
