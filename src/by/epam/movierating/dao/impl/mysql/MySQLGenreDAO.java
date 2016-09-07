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
    private static final String ADD_GENRE_QUERY = "INSERT INTO genre " +
            "(name, position) VALUES (?, ?)";
    private static final String UPDATE_GENRE_QUERY = "UPDATE genre " +
            "SET name = ?, position = ? WHERE id = ?";
    private static final String TGENRE_CHECK_QUERY = "SELECT id FROM tgenre " +
            "WHERE language_id = ? AND id = ?";
    private static final String ADD_TGENRE_QUERY = "INSERT INTO tgenre " +
            "(language_id, id, name) VALUES (?, ?, ?)";
    private static final String UPDATE_TGENRE_QUERY = "UPDATE tgenre " +
            "SET name = ? WHERE language_id = ? AND id = ?";
    private static final String DELETE_GENRE_QUERY = "DELETE FROM genre WHERE id = ?";
    private static final String GET_ALL_GENRES_QUERY = "SELECT * FROM genre";
    private static final String GET_ALL_GENRES_NOT_DEFAULT_LANG_QUERY = "SELECT g.id, " +
            "coalesce(t.name, g.name), g.position FROM genre AS g " +
            "LEFT JOIN (SELECT * FROM tgenre WHERE language_id = ?) AS t USING(id)";
    private static final String GET_GENRE_BY_ID_QUERY = "SELECT * FROM genre WHERE id = ?";
    private static final String GET_GENRE_BY_ID_NOT_DEFAULT_LANG_QUERY = "SELECT g.id, coalesce(t.name, g.name), " +
            " g.position FROM genre AS g LEFT JOIN " +
            "(SELECT * FROM tgenre WHERE language_id = ?) AS t USING(id) WHERE g.id = ?";
    private static final String GET_GENRES_BY_MOVIE_QUERY = "SELECT genre.* FROM genre " +
            "INNER JOIN movie_genre ON genre.id = movie_genre.genre_id " +
            "WHERE movie_genre.movie_id = ?";
    private static final String GET_GENRES_BY_MOVIE_NOT_DEFAULT_LANG_QUERY = "SELECT g.id, coalesce(t.name, g.name), " +
            " g.position FROM genre AS g INNER JOIN movie_genre AS mg " +
            "ON g.id = mg.genre_id LEFT JOIN (SELECT * FROM tgenre WHERE language_id = ?) AS t " +
            "USING(id) WHERE mg.movie_id = ?";
    private static final String GET_TOP_POSITION_GENRES_QUERY = "SELECT * FROM genre ORDER BY position LIMIT ";
    private static final String GET_TOP_POSITION_GENRES_NOT_DEFAULT_LANG_QUERY = "SELECT g.id, " +
            "coalesce(t.name, g.name), g.position FROM genre AS g " +
            "LEFT JOIN (SELECT * FROM tgenre WHERE language_id = ?) AS t USING(id) " +
            "ORDER BY g.position LIMIT ";
    private static final String GET_GENRES_QUERY = "SELECT * FROM genre LIMIT ";
    private static final String GET_GENRES_NOT_DEFAULT_LANG_QUERY = "SELECT g.id, " +
            "coalesce(t.name, g.name), g.position FROM genre AS g " +
            "LEFT JOIN (SELECT * FROM tgenre WHERE language_id = ?) AS t USING(id) LIMIT ";
    private static final String GET_GENRES_COUNT_QUERY = "SELECT COUNT(*) FROM genre";

    private static final String DEFAULT_LANGUAGE_ID = "EN";

    @Override
    public void addGenre(Genre genre) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(ADD_GENRE_QUERY);
            statement.setString(1, genre.getName());
            statement.setInt(2, genre.getPosition());

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when adding genre", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void updateGenre(Genre genre, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(UPDATE_GENRE_QUERY);
                statement.setString(1, genre.getName());
                statement.setInt(2, genre.getPosition());
                statement.setInt(3, genre.getId());
            }
            else {
                PreparedStatement checkStatement = connection.prepareStatement(TGENRE_CHECK_QUERY);
                checkStatement.setString(1, languageId);
                checkStatement.setInt(2, genre.getId());
                ResultSet resultSet = checkStatement.executeQuery();
                if(resultSet.next()){
                    statement = connection.prepareStatement(UPDATE_TGENRE_QUERY);
                    statement.setString(1, genre.getName());
                    statement.setString(2, languageId);
                    statement.setInt(3, genre.getId());
                }
                else {
                    statement = connection.prepareStatement(ADD_TGENRE_QUERY);
                    statement.setString(1, languageId);
                    statement.setInt(2, genre.getId());
                    statement.setString(3, genre.getName());
                }
            }

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when updating genre", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void deleteGenre(int id) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(DELETE_GENRE_QUERY);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when deleting genre", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Genre> getAllGenres(String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.createStatement();
                resultSet = statement.executeQuery(GET_ALL_GENRES_QUERY);
            }
            else {
                preparedStatement = connection.prepareStatement(GET_ALL_GENRES_NOT_DEFAULT_LANG_QUERY);
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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting genre", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    if(preparedStatement != null){
                        preparedStatement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public Genre getGenreById(int id, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(GET_GENRE_BY_ID_QUERY);
                statement.setInt(1, id);
            }
            else {
                statement = connection.prepareStatement(GET_GENRE_BY_ID_NOT_DEFAULT_LANG_QUERY);
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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting genre", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Genre> getGenresByMovie(int movieId, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(GET_GENRES_BY_MOVIE_QUERY);
                statement.setInt(1, movieId);
            }
            else {
                statement = connection.prepareStatement(GET_GENRES_BY_MOVIE_NOT_DEFAULT_LANG_QUERY);
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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting genre", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Genre> getTopPositionGenres(int amount, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.createStatement();
                resultSet = statement.executeQuery(GET_TOP_POSITION_GENRES_QUERY + amount);
            }
            else {
                preparedStatement = connection.prepareStatement(GET_TOP_POSITION_GENRES_NOT_DEFAULT_LANG_QUERY + amount);
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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting genre", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    if(preparedStatement != null){
                        preparedStatement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Genre> getGenres(int from, int amount, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.createStatement();
                resultSet = statement.executeQuery(GET_GENRES_QUERY + from + ", " + amount);
            }
            else {
                preparedStatement = connection.prepareStatement(GET_GENRES_NOT_DEFAULT_LANG_QUERY + from + ", " + amount);
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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting genre", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    if(preparedStatement != null){
                        preparedStatement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public int getGenresCount() throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_GENRES_COUNT_QUERY);

            int genresCount = 0;
            if(resultSet.next()){
                genresCount = resultSet.getInt(1);
            }
            return genresCount;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("DAO layer: cannot get countries count", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }
}
