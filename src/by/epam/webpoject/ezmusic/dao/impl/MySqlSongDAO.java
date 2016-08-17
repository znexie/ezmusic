package by.epam.webpoject.ezmusic.dao.impl;

import by.epam.webpoject.ezmusic.connection.ConnectionPool;
import by.epam.webpoject.ezmusic.connection.ProxyConnection;
import by.epam.webpoject.ezmusic.dao.SongDAO;
import by.epam.webpoject.ezmusic.entity.Song;
import by.epam.webpoject.ezmusic.exception.dao.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Антон on 04.08.2016.
 */
public class MySqlSongDAO extends SongDAO {

    private static final MySqlSongDAO instance = new MySqlSongDAO();
    private static final String CREATE_SONG_QUERY = "INSERT INTO ezmusicdb.song (song_name, song_year, song_file_path, song_publication_date, song_cost) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_SONG_QUERY = "SELECT song_id, song_name, song_year, song_file_path, song_publication_date, song_cost FROM ezmusicdb.song WHERE song_id = ?";
    private static final String DELETE_SONG_QUERY = "DELETE FROM ezmusicdb.song WHERE song_id = ?";
    private static final String UPDATE_SONG_QUERY = "UPDATE ezmusicdb.song SET song_name = ?, song_year = ?, song_file_path = ?, song_publication_date = ?, song_cost = ? WHERE song_id = ?";
    private static final String FIND_SONG_BY_USER_ID_QUERY = "SELECT s.song_id, s.song_name, s.song_year, s.song_file_path, s.song_publication_date, s.song_cost FROM ezmusicdb.song AS s INNER JOIN ezmusicdb.user_song as u_s ON s.song_id = u_s.id_song WHERE u_s.id_user = ?";
    private static final String FIND_SONG_BY_ALBUM_ID_QUERY = "SELECT s.song_id, s.song_name, s.song_year, s.song_file_path, s.song_publication_date, s.song_cost FROM ezmusicdb.song AS s INNER JOIN ezmusicdb.album_song as a_s ON s.song_id = a_s.id_song WHERE a_s.id_album = ?";
    private static final String FIND_ALL_SONGS = "SELECT song_id, song_name, song_year, song_file_path, song_publication_date, song_cost FROM ezmusicdb.song";
    private static final String CREATE_SONG_ALBUM_QUERY = "INSERT INTO ezmusicdb.album_song (id_album, id_song) VALUES (?, ?)";
    private static final String CREATE_SONG_AUTHOR_QUERY = "INSERT INTO ezmusicdb.author_song (id_author, id_song) VALUES (?, ?)";


    private MySqlSongDAO() {
    }

    public static MySqlSongDAO getInstance() {
        return instance;
    }


    @Override
    public boolean create(Song instance) throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(CREATE_SONG_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, instance.getName());
            statement.setInt(2, instance.getYear());
            statement.setString(3, instance.getFilePath());
            statement.setDate(4, instance.getPublicationDate());
            statement.setDouble(5, instance.getCost());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Creating song error", e);
        } finally {
            closeStatement(statement);
            connection.close();
        }
        return false;
    }

    @Override
    public Song find(Long id) throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
        Song song = null;
        try {
            statement = connection.prepareStatement(FIND_SONG_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                song = new Song();
                song.setSongId(resultSet.getLong(1));
                song.setName(resultSet.getString(2));
                song.setYear(resultSet.getInt(3));
                song.setFilePath(resultSet.getString(4));
                song.setPublicationDate(resultSet.getDate(5));
                song.setCost(resultSet.getDouble(6));
            }
        } catch (SQLException e) {
            throw new DAOException("Finding song error", e);
        } finally {
            closeStatement(statement);
            connection.close();
        }
        return song;
    }

    @Override
    public void delete(Long id) throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_SONG_QUERY);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Deleting song error", e);
        } finally {
            closeStatement(statement);
            connection.close();
        }
    }

    @Override
    public void update(Song instance) throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_SONG_QUERY);
            statement.setString(1, instance.getName());
            statement.setInt(2, instance.getYear());
            statement.setString(3, instance.getFilePath());
            statement.setDate(4, instance.getPublicationDate());
            statement.setDouble(5, instance.getCost());
            statement.setLong(6, instance.getSongId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Updating song error", e);
        } finally {
            closeStatement(statement);
            connection.close();
        }

    }

    @Override
    public ArrayList<Song> findByUserId(Long userId) throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
        ArrayList<Song> songList = null;
        try {
            statement = connection.prepareStatement(FIND_SONG_BY_USER_ID_QUERY);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            songList = new ArrayList<>();
            while (resultSet.next()) {
                Song song = new Song();
                song.setSongId(resultSet.getLong(1));
                song.setName(resultSet.getString(2));
                song.setYear(resultSet.getInt(3));
                song.setFilePath(resultSet.getString(4));
                song.setPublicationDate(resultSet.getDate(5));
                song.setCost(resultSet.getDouble(6));
                songList.add(song);
            }
        } catch (SQLException e) {
            throw new DAOException("Finding song error", e);
        } finally {
            closeStatement(statement);
            connection.close();
        }
        return songList;
    }

    @Override
    public ArrayList<Song> findByAlbumId(Long albumId) throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
        ArrayList<Song> songList = null;
        try {
            statement = connection.prepareStatement(FIND_SONG_BY_ALBUM_ID_QUERY);
            statement.setLong(1, albumId);
            ResultSet resultSet = statement.executeQuery();
            songList = new ArrayList<>();
            while (resultSet.next()) {
                Song song = new Song();
                song.setSongId(resultSet.getLong(1));
                song.setName(resultSet.getString(2));
                song.setYear(resultSet.getInt(3));
                song.setFilePath(resultSet.getString(4));
                song.setPublicationDate(resultSet.getDate(5));
                song.setCost(resultSet.getDouble(6));
                songList.add(song);
            }
        } catch (SQLException e) {
            throw new DAOException("Finding song error", e);
        } finally {
            closeStatement(statement);
            connection.close();
        }
        return songList;
    }

    @Override
    public ArrayList<Song> findAll() throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        Statement statement = null;
        ArrayList<Song> songList = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_SONGS);
            songList = new ArrayList<>();
            while (resultSet.next()) {
                Song song = new Song();
                song.setSongId(resultSet.getLong(1));
                song.setName(resultSet.getString(2));
                song.setYear(resultSet.getInt(3));
                song.setFilePath(resultSet.getString(4));
                song.setPublicationDate(resultSet.getDate(5));
                song.setCost(resultSet.getDouble(6));
                songList.add(song);
            }
        } catch (SQLException e) {
            throw new DAOException("Finding song error", e);
        } finally {
            closeStatement(statement);
            connection.close();
        }
        return songList;
    }

    @Override
    public boolean createSongAlbum(Long songId, Long albumId) throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CREATE_SONG_ALBUM_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, albumId);
            statement.setLong(2, songId);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Creating song-album error", e);
        }finally {
            closeStatement(statement);
            connection.close();
        }
        return false;
    }

    @Override
    public boolean createSongAuthor(Long songId, Long authorId) throws DAOException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CREATE_SONG_AUTHOR_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, authorId);
            statement.setLong(2, songId);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Creating song-author error", e);
        }finally {
            closeStatement(statement);
            connection.close();
        }
        return false;
    }

    @Override
    public void updateSongAlbum(Long songId, Long albumId) {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = null;
    }

    @Override
    public void updateSongAuthor(Long songId, Long authorId) {

    }

}