package by.epam.webpoject.ezmusic.dao.impl;

import by.epam.webpoject.ezmusic.connection.ConnectionPool;
import by.epam.webpoject.ezmusic.dao.UserDAO;
import by.epam.webpoject.ezmusic.entity.User;
import by.epam.webpoject.ezmusic.exception.dao.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Антон on 16.07.2016.
 */
public class MySqlUserDAO extends UserDAO {

    private final static Logger LOGGER = LogManager.getLogger(MySqlUserDAO.class);

    private static final MySqlUserDAO instance = new MySqlUserDAO();
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY = "SELECT user_id, user_login, user_password, user_name, user_surname, user_email, user_phone, user_photo_path, user_balance, user_is_admin, user_is_banned FROM USER WHERE user_login = ? AND user_password = ?";
    private static final String CREATE_USER_QUERY = "INSERT INTO USER (user_name, user_surname, user_login, user_password, user_email, user_phone, user_photo_path, user_balance, user_is_admin, user_is_banned) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT user_id, user_login, user_password, user_name, user_surname, user_email, user_phone, user_photo_path, user_balance, user_is_admin, user_is_banned FROM USER WHERE user_id = ? ";
    private static final String DELETE_USER_BY_ID = "DELETE  FROM USER WHERE user_id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE USER SET user_name = ?, user_surname = ?, user_login = ?, user_password = ?, user_email = ?, user_phone = ?, user_photo_path = ?, user_balance = ?, user_is_admin = ?, user_is_banned = ? WHERE user_id = ?";
    private static final String FIND_USER_BY_LOGIN_QUERY = "SELECT user_id FROM USER WHERE user_login = ?";
    private MySqlUserDAO() {
    }

    public static MySqlUserDAO getInstance() {
        return instance;
    }

    @Override
    public boolean create(User instance) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            if(!isLoginExist(instance.getLogin())) {
                statement = connection.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setString(1, instance.getName());
                statement.setString(2, instance.getSurname());
                statement.setString(3, instance.getLogin());
                statement.setString(4, instance.getPassword());
                statement.setString(5, instance.getEmail());
                statement.setString(6, instance.getPhone());
                statement.setString(7, instance.getPhotoPath());
                statement.setDouble(8, instance.getBalance());
                statement.setBoolean(9, instance.isAdmin());
                statement.setBoolean(10, instance.isBanned());
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                   return true;
                }
            }else {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.returnConnection(connection);
        }
        return false;
    }

    @Override
    public User find(Long id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        User user = null;
        try {
            statement = connection.prepareStatement(FIND_USER_BY_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                user = new User();
                user.setUserId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setSurname(resultSet.getString(3));
                user.setLogin(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setEmail(resultSet.getString(6));
                user.setPhone(resultSet.getString(7));
                user.setPhotoPath(resultSet.getString(8));
                user.setBalance(resultSet.getDouble(9));
                user.setAdmin(resultSet.getBoolean(10));
                user.setBanned(resultSet.getBoolean(11));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public void delete(Long id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_USER_BY_ID);
            statement.executeUpdate();
        } catch (SQLException e) {
           throw new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void update(User instance) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, instance.getName());
            statement.setString(2, instance.getSurname());
            statement.setString(3, instance.getLogin());
            statement.setString(4, instance.getPassword());
            statement.setString(5, instance.getEmail());
            statement.setString(6, instance.getPhone());
            statement.setString(7, instance.getPhotoPath());
            statement.setDouble(8, instance.getBalance());
            statement.setBoolean(9, instance.isAdmin());
            statement.setBoolean(10, instance.isBanned());
            statement.setLong(11, instance.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public User login(String userLogin, String userPassword) throws DAOException {
        ConnectionPool connectionPool= ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        User user = null;
        try {
            statement = connection.prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY);
            statement.setString(1, userLogin);
            statement.setString(2, userPassword);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                user = new User();
                user.setUserId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setSurname(resultSet.getString(3));
                user.setLogin(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setEmail(resultSet.getString(6));
                user.setPhone(resultSet.getString(7));
                user.setPhotoPath(resultSet.getString(8));
                user.setBalance(resultSet.getDouble(9));
                user.setAdmin(resultSet.getBoolean(10));
                user.setBanned(resultSet.getBoolean(11));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    public boolean isLoginExist(String login) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(FIND_USER_BY_LOGIN_QUERY);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.returnConnection(connection);
        }
        return false;

    }
    private void closeStatement(PreparedStatement statement) {
        try {
            if(statement != null)
                statement.close();
        } catch (SQLException e) {
           LOGGER.error(e);
        }
    }
}
