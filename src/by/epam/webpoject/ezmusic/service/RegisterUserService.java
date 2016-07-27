package by.epam.webpoject.ezmusic.service;

import by.epam.webpoject.ezmusic.dao.UserDAO;
import by.epam.webpoject.ezmusic.dao.factory.DAOFactory;
import by.epam.webpoject.ezmusic.encryptor.MD5Encryptor;
import by.epam.webpoject.ezmusic.entity.User;
import by.epam.webpoject.ezmusic.exception.dao.DAOException;
import by.epam.webpoject.ezmusic.exception.service.ServiceException;

/**
 * Created by Антон on 24.07.2016.
 */
public class RegisterUserService {
    public static Long execute(User instance) throws ServiceException {
        UserDAO userDAO = (UserDAO) DAOFactory.createUserDao();
        String userPassword = instance.getPassword();
        String md5hash = MD5Encryptor.getMD5(userPassword);
        instance.setPassword(md5hash);
        try {
            return userDAO.create(instance);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
