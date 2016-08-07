package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.GenreDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.domain.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public class MySQLGenreDAO implements GenreDAO {
    private static final String DEFAULT_LANGUAGE_ID = "EN";

    @Override
    public void addGenre(Genre genre, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement("INSERT INTO genre " +
                        "(name, position) VALUES (?, ?, ?)");
                statement.setString(1, genre.getName());
                statement.setInt(2, genre.getPosition());
            }
            else {
                statement = connection.prepareStatement("INSERT INTO tgenre " +
                        "(language_id, id, name) VALUES (?, ?, ?, ?)");
                statement.setString(1, languageId);
                statement.setInt(2, genre.getId());
                statement.setString(3, genre.getName());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when adding genre", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void updateGenre(Genre genre, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement("UPDATE genre " +
                        "SET name = ?, position = ? WHERE id = ?");
                statement.setString(1, genre.getName());
                statement.setInt(2, genre.getPosition());
                statement.setInt(3, genre.getId());
            }
            else {
                statement = connection.prepareStatement("UPDATE tgenre " +
                        "SET name = ? WHERE language_id = ? AND id = ?");
                statement.setString(1, genre.getName());
                statement.setString(2, languageId);
                statement.setInt(3, genre.getId());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when updating genre", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void deleteGenre(int id) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM genre WHERE id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when deleting genre", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Genre> getAllGenres(String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM genre");
            }
            else {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT g.id, " +
                        "coalesce(t.name, g.name), g.position FROM genre AS g " +
                        "LEFT JOIN (SELECT * FROM tgenre WHERE language_id = ?) AS t USING(id)");
                preparedStatement.setString(1, languageId);
                resultSet = preparedStatement.executeQuery();
            }

            List<Genre> allGenres = new ArrayList<>();
            while (resultSet.next()){
                Genre genre = new Genre();
                genre.setId(resultSet.getInt(1));
                genre.setName(resultSet.getString(2));
                genre.setPosition(resultSet.getInt(3));

                allGenres.add(genre);
            }
            return allGenres;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting genre", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public Genre getGenreById(int id, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement("SELECT * FROM genre WHERE id = ?");
                statement.setInt(1, id);
            }
            else {
                statement = connection.prepareStatement("SELECT g.id, coalesce(t.name, g.name), " +
                        " g.position FROM genre AS g LEFT JOIN " +
                        "(SELECT * FROM tgenre WHERE language_id = ?) AS t USING(id) WHERE g.id = ?");
                statement.setString(1, languageId);
                statement.setInt(2, id);
            }
            ResultSet resultSet = statement.executeQuery();

            Genre genre = null;
            if(resultSet.next()){
                genre = new Genre();
                genre.setId(resultSet.getInt(1));
                genre.setName(resultSet.getString(2));
                genre.setPosition(resultSet.getInt(3));
            }
            return genre;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting genre", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Genre> getGenresByMovie(int movieId, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement("SELECT genre.* FROM genre " +
                        "INNER JOIN movie_genre ON genre.id = movie_genre.genre_id " +
                        "WHERE movie_genre.movie_id = ?");
                statement.setInt(1, movieId);
            }
            else {
                statement = connection.prepareStatement("SELECT g.id, coalesce(t.name, g.name), " +
                        " g.position FROM genre AS g INNER JOIN movie_genre AS mg " +
                        "ON g.id = mg.genre_id LEFT JOIN (SELECT * FROM tgenre WHERE language_id = ?) AS t " +
                        "USING(id) WHERE mg.movie_id = ?");
                statement.setString(1, languageId);
                statement.setInt(2, movieId);
            }
            ResultSet resultSet = statement.executeQuery();

            List<Genre> genresByMovie = new ArrayList<>();
            while (resultSet.next()){
                Genre genre = new Genre();
                genre.setId(resultSet.getInt(1));
                genre.setName(resultSet.getString(2));
                genre.setPosition(resultSet.getInt(3));

                genresByMovie.add(genre);
            }
            return genresByMovie;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting genre", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Genre> getTopPositionGenres(int amount, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM genre ORDER BY position LIMIT " + amount);
            }
            else {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT g.id, " +
                        "coalesce(t.name, g.name), g.position FROM genre AS g " +
                        "LEFT JOIN (SELECT * FROM tgenre WHERE language_id = ?) AS t USING(id) " +
                        "ORDER BY g.position LIMIT " + amount);
                preparedStatement.setString(1, languageId);
                resultSet = preparedStatement.executeQuery();
            }

            List<Genre> allGenres = new ArrayList<>();
            while (resultSet.next()){
                Genre genre = new Genre();
                genre.setId(resultSet.getInt(1));
                genre.setName(resultSet.getString(2));
                genre.setPosition(resultSet.getInt(3));

                allGenres.add(genre);
            }
            return allGenres;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting genre", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }
}
