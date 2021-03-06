package by.epam.webpoject.ezmusic.service.user;

import by.epam.webpoject.ezmusic.constant.FilePath;
import by.epam.webpoject.ezmusic.dao.UserDAO;
import by.epam.webpoject.ezmusic.dao.factory.DAOFactory;
import by.epam.webpoject.ezmusic.util.MD5Encryptor;
import by.epam.webpoject.ezmusic.entity.User;
import by.epam.webpoject.ezmusic.exception.DAOException;
import by.epam.webpoject.ezmusic.exception.ServiceException;

/**
 * Created by Антон on 24.07.2016.
 */
public class RegisterUserService {
    public static Long register(User instance) throws ServiceException {

        UserDAO userDAO = (UserDAO) DAOFactory.createUserDAO();

        String userPassword = instance.getPassword();
        String md5hash = MD5Encryptor.getMD5(userPassword);

        instance.setPassword(md5hash);
        instance.setPhotoPath(FilePath.DEFAULT_USER_PHOTO);
        try {
            return userDAO.create(instance);
        } catch (DAOException e) {
            throw new ServiceException("Register user service exception", e);
        }
    }
}
