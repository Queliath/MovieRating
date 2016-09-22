package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.inter.GenreDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.domain.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a DAO-logic for the Genre entity for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
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

    /**
     * Adds a genre to the data storage (in the default language).
     *
     * @param genre a genre object
     * @throws DAOException
     */
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
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }

    /**
     * Updates a genre or adds/updates a localization of a genre in the data storage.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a genre. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a genre (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param genre a genre object
     * @param languageId a language id like 'EN', "RU' etc.
     * @throws DAOException
     */
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
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }

    /**
     * Deletes a genre from the data storage (with all of the localizations).
     *
     * @param id an id of a deleting genre
     * @throws DAOException
     */
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
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }

    /**
     * Returns all the genres from the data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the genres
     * @throws DAOException
     */
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
                if (statement != null) {
                    statement.close();
                }
                if (preparedStatement != null){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }

    /**
     * Returns a genre by id from the data storage.
     *
     * @param id an id of a needed genre
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genre by id
     * @throws DAOException
     */
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
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }

    /**
     * Returns a genres belonging to the movie from the data storage.
     *
     * @param movieId an id of the movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres belonging to the movie
     * @throws DAOException
     */
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
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }

    /**
     * Returns a genres ordered by a position number from the data storage.
     *
     * @param amount a needed amount of genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres ordered by a position number
     * @throws DAOException
     */
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
                if (statement != null) {
                    statement.close();
                }
                if (preparedStatement != null){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }

    /**
     * Returns a genres from the data storage.
     *
     * @param from a start position in the genres list (started from 0)
     * @param amount a needed amount of genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres
     * @throws DAOException
     */
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
                if (statement != null) {
                    statement.close();
                }
                if (preparedStatement != null){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }

    /**
     * Returns an amount of genres in the data storage.
     *
     * @return an amount of genres in the data storage
     * @throws DAOException
     */
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
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }
}
