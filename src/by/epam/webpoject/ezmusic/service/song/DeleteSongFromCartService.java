package by.epam.webpoject.ezmusic.service.song;

import by.epam.webpoject.ezmusic.dao.OrderDAO;
import by.epam.webpoject.ezmusic.dao.SongDAO;
import by.epam.webpoject.ezmusic.dao.factory.DAOFactory;
import by.epam.webpoject.ezmusic.entity.Order;
import by.epam.webpoject.ezmusic.exception.dao.DAOException;
import by.epam.webpoject.ezmusic.exception.service.ServiceException;

/**
 * Created by Антон on 02.09.2016.
 */
public class DeleteSongFromCartService {
    public static void delete(Order cart, Long songId) throws ServiceException {
        SongDAO songDao = (SongDAO) DAOFactory.createSongDAO();
        OrderDAO orderDao = (OrderDAO) DAOFactory.createOrderDAO();
        try {
            songDao.deleteSongOrder(songId, cart.getOrderId());
            cart = orderDao.findCartByUserId(cart.getUserId());
        } catch (DAOException e) {
            throw new ServiceException("Delete song from cart service exception", e);
        }
    }
}
