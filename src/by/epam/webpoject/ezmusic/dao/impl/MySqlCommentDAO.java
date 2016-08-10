package by.epam.webpoject.ezmusic.dao.impl;

import by.epam.webpoject.ezmusic.connection.ConnectionPool;
import by.epam.webpoject.ezmusic.connection.ProxyConnection;
import by.epam.webpoject.ezmusic.dao.CommentDAO;
import by.epam.webpoject.ezmusic.entity.Comment;
import by.epam.webpoject.ezmusic.exception.dao.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Антон on 08.08.2016.
 */
public class MySqlCommentDAO extends CommentDAO {
    
    private static final MySqlCommentDAO instance = new MySqlCommentDAO();
    private static final String CREATE_COMMENT_QUERY = "INSERT INTO COMMENT (comment_user_id, comment_rating, comment_text, comment_song_id) VALUES (?, ?, ?, ?)";
    private static final String FIND_COMMENT_QUERY = "SELECT comment_id, comment_user_id, comment_rating, comment_text, comment_song_id FROM COMMENT WHERE comment_id = ?";
    private static final String DELETE_COMMENT_QUERY = "DELETE FROM COMMENT WHERE comment_id = ?";
    private static final String UPDATE_COMMENT_QUERY ="UPDATE COMMENT SET  comment_user_id = ?, comment_rating = ?, comment_text = ?, comment_song_id = ? WHERE comment_id = ?";
    private static final String FIND_COMMENT_BY_SONG_ID = "SELECT comment_id, comment_user_id, comment_rating, comment_text, comment_song_id FROM COMMENT WHERE comment_song_id = ?";

    private MySqlCommentDAO(){}

    public static MySqlCommentDAO getInstance(){return instance;}

    @Override
    public boolean create(Comment instance) throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(CREATE_COMMENT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, instance.getUserId());
            statement.setInt(2, instance.getRating());
            statement.setString(3, instance.getText());
            statement.setLong(4, instance.getSongId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Creating comment error", e);
        }finally {
            closeStatement(statement);
            connection.close();
        }
        return false;
    }

    @Override
    public Comment find(Long id) throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
        Comment comment = null;
        try {
            statement = connection.prepareStatement(FIND_COMMENT_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                comment = new Comment();
                comment.setCommentId(resultSet.getLong(1));
                comment.setUserId(resultSet.getLong(2));
                comment.setRating(resultSet.getInt(3));
                comment.setText(resultSet.getString(4));
                comment.setSongId(resultSet.getLong(5));
            }
            
        }catch (SQLException e){
            throw new DAOException("Finding comment error", e);
        }finally {
            closeStatement(statement);
            connection.close();
        }
        return comment;

    }

    @Override
    public void delete(Long id) throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_COMMENT_QUERY);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Deleting comment error", e);
        }finally {
            closeStatement(statement);
            connection.close();
        }
    }

    @Override
    public void update(Comment instance) throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_COMMENT_QUERY);
            statement.setLong(1, instance.getUserId());
            statement.setInt(2, instance.getRating());
            statement.setString(3, instance.getText());
            statement.setLong(4, instance.getSongId());
            statement.setLong(5, instance.getCommentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Deleting comment error");
        }finally {
            closeStatement(statement);
            connection.close();
        }
    }

    @Override
    public ArrayList<Comment> findBySongId(Long songId) throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
        ArrayList<Comment> commentList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(FIND_COMMENT_BY_SONG_ID);
            statement.setLong(1, songId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Comment comment = new Comment();
                comment.setCommentId(resultSet.getLong(1));
                comment.setUserId(resultSet.getLong(2));
                comment.setRating(resultSet.getInt(3));
                comment.setText(resultSet.getString(4));
                comment.setSongId(resultSet.getLong(5));
                commentList.add(comment);
            }

        }catch (SQLException e){
            throw new DAOException("Finding comment error", e);
        }finally {
            closeStatement(statement);
            connection.close();
        }
        return commentList;

    }

}