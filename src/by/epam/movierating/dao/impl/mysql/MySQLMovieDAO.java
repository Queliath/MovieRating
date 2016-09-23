package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.inter.MovieDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.domain.criteria.MovieCriteria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a DAO-logic for the Movie entity for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLMovieDAO implements MovieDAO {
    private static final String ADD_MOVIE_QUERY = "INSERT INTO movie (name, year, tagline, budget, premiere," +
            "lasting, annotation, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_MOVIE_QUERY = "UPDATE movie SET name = ?, year = ?, tagline = ?," +
            "budget = ?, premiere = ?, lasting = ?, annotation = ?, image = ? WHERE id = ?";
    private static final String TMOVIE_CHECK_QUERY = "SELECT id FROM tmovie " +
            "WHERE language_id = ? AND id = ?";
    private static final String ADD_TMOVIE_QUERY = "INSERT INTO tmovie (language_id, id, name, tagline, " +
            "annotation, image) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_TMOVIE_QUERY = "UPDATE tmovie SET name = ?, tagline = ?, " +
            "annotation = ?, image = ? WHERE language_id = ? AND id = ?";
    private static final String DELETE_MOVIE_QUERY = "DELETE FROM movie WHERE id = ?";
    private static final String GET_ALL_MOVIES_QUERY = "SELECT * FROM movie";
    private static final String GET_ALL_MOVIES_NOT_DEFAULT_LANG_QUERY = "SELECT m.id, " +
            "coalesce(t.name, m.name), m.year, coalesce(t.tagline, m.tagline), " +
            "m.budget, m.premiere, m.lasting, coalesce(t.annotation, m.annotation), " +
            "coalesce(t.image, m.image) FROM movie AS m LEFT JOIN (SELECT * FROM tmovie " +
            "WHERE language_id = ?) AS t USING(id)";
    private static final String GET_MOVIE_BY_ID_QUERY = "SELECT * FROM movie WHERE id = ?";
    private static final String GET_MOVIE_BY_ID_NOT_DEFAULT_LANG_QUERY = "SELECT m.id, " +
            "coalesce(t.name, m.name), m.year, coalesce(t.tagline, m.tagline), " +
            "m.budget, m.premiere, m.lasting, coalesce(t.annotation, m.annotation), " +
            "coalesce(t.image, m.image) FROM movie AS m LEFT JOIN (SELECT * FROM tmovie " +
            "WHERE language_id = ?) AS t USING(id) WHERE m.id = ?";
    private static final String GET_MOVIES_BY_COUNTRY_QUERY = "SELECT movie.* FROM movie " +
            "INNER JOIN movie_country ON movie.id = movie_country.movie_id WHERE movie_country.country_id = ? ";
    private static final String GET_MOVIES_BY_COUNTRY_NOT_DEFAULT_LANG_QUERY = "SELECT m.id, coalesce(t.name, m.name), m.year, " +
            "coalesce(t.tagline, m.tagline), m.budget, m.premiere, m.lasting, " +
            "coalesce(t.annotation, m.annotation), coalesce(t.image, m.image) " +
            "FROM movie AS m INNER JOIN movie_country AS mc ON m.id = mc.movie_id " +
            "LEFT JOIN (SELECT * FROM tmovie WHERE language_id = ?) AS t USING(id) " +
            "WHERE mc.country_id = ?;";
    private static final String GET_MOVIES_BY_GENRE_QUERY = "SELECT movie.* FROM movie " +
            "INNER JOIN movie_genre ON movie.id = movie_genre.movie_id WHERE movie_genre.genre_id = ? ";
    private static final String GET_MOVIES_BY_GENRE_NOT_DEFAULT_LANG_QUERY = "SELECT m.id, coalesce(t.name, m.name), m.year, " +
            "coalesce(t.tagline, m.tagline), m.budget, m.premiere, m.lasting, " +
            "coalesce(t.annotation, m.annotation), coalesce(t.image, m.image) " +
            "FROM movie AS m INNER JOIN movie_genre AS mg ON m.id = mg.movie_id " +
            "LEFT JOIN (SELECT * FROM tmovie WHERE language_id = ?) AS t USING(id) " +
            "WHERE mg.genre_id = ?";
    private static final String GET_MOVIES_BY_PERSON_QUERY = "SELECT movie.* FROM movie " +
            "INNER JOIN movie_person_relation ON movie.id = movie_person_relation.movie_id WHERE " +
            "movie_person_relation.person_id = ? AND movie_person_relation.relation_type = ?";
    private static final String GET_MOVIES_BY_PERSON_NOT_DEFAULT_LANG_QUERY = "SELECT m.id, coalesce(t.name, m.name), m.year, " +
            "coalesce(t.tagline, m.tagline), m.budget, m.premiere, m.lasting, " +
            "coalesce(t.annotation, m.annotation), coalesce(t.image, m.image) " +
            "FROM movie AS m INNER JOIN movie_person_relation AS mpr ON m.id = mpr.movie_id " +
            "LEFT JOIN (SELECT * FROM tmovie WHERE language_id = ?) AS t USING(id) " +
            "WHERE mpr.person_id = ? AND mpr.relation_type = ?;";
    private static final String GET_RECENT_ADDED_MOVIES_QUERY = "SELECT * FROM movie ORDER BY id DESC LIMIT ";
    private static final String GET_RECENT_ADDED_MOVIES_NOT_DEFAULT_LANG_QUERY = "SELECT m.id, " +
            "coalesce(t.name, m.name), m.year, coalesce(t.tagline, m.tagline), " +
            "m.budget, m.premiere, m.lasting, coalesce(t.annotation, m.annotation), " +
            "coalesce(t.image, m.image) FROM movie AS m LEFT JOIN (SELECT * FROM tmovie " +
            "WHERE language_id = ?) AS t USING(id) ORDER BY id DESC LIMIT ";
    private static final String GET_MOVIES_BY_CRITERIA_HEAD_QUERY = "SELECT DISTINCT m.* FROM movie AS m ";
    private static final String GET_MOVIES_BY_CRITERIA_NOT_DEFAULT_LANGUAGE_HEAD_QUERY = "SELECT DISTINCT m.id, coalesce(t.name, m.name), m.year, " +
            "coalesce(t.tagline, m.tagline), m.budget, m.premiere, m.lasting, " +
            "coalesce(t.annotation, m.annotation), coalesce(t.image, m.image) " +
            "FROM movie AS m ";
    private static final String GET_MOVIES_BY_CRITERIA_MG_JOIN_QUERY = "INNER JOIN movie_genre AS mg ON m.id = mg.movie_id ";
    private static final String GET_MOVIES_BY_CRITERIA_MC_JOIN_QUERY = "INNER JOIN movie_country AS mc ON m.id = mc.movie_id ";
    private static final String GET_MOVIES_BY_CRITERIA_RATING_JOIN_QUERY = "INNER JOIN rating AS r ON m.id = r.movie_id ";
    private static final String GET_MOVIES_BY_CRITERIA_TRANSLATE_JOIN_QUERY_FIRST_PART = "LEFT JOIN (SELECT * FROM tmovie WHERE language_id = '";
    private static final String GET_MOVIES_BY_CRITERIA_TRANSLATE_JOIN_QUERY_SECOND_PART = "') AS t USING(id) ";
    private static final String GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_QUERY_FIRST_PART = "WHERE m.name LIKE '%";
    private static final String GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_QUERY_SECOND_PART = "%' ";
    private static final String GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_FIRST_PART = "WHERE (m.name LIKE '%";
    private static final String GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_SECOND_PART = "%' OR t.name LIKE '%";
    private static final String GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_THIRD_PART = "%') ";
    private static final String WHERE_CRITERIA = "WHERE";
    private static final String AND_CRITERIA = "AND";
    private static final String HAVING_CRITERIA = "HAVING";
    private static final String SPACE_SEPARATOR = " ";
    private static final String COMA_SEPARATOR = ",";
    private static final String CLOSING_BRACKET = ") ";
    private static final String GET_MOVIES_BY_CRITERIA_MIN_YEAR_CRITERIA_QUERY = " m.year > ";
    private static final String GET_MOVIES_BY_CRITERIA_MAX_YEAR_CRITERIA_QUERY = " m.year < ";
    private static final String GET_MOVIES_BY_CRITERIA_GENRES_LIST_CRITERIA_QUERY = " mg.genre_id IN (";
    private static final String GET_MOVIES_BY_CRITERIA_COUNTRIES_LIST_CRITERIA_QUERY = " mc.country_id IN (";
    private static final String GET_MOVIES_BY_CRITERIA_GROUP_BY_ID_QUERY = "GROUP BY r.movie_id ";
    private static final String GET_MOVIES_BY_CRITERIA_MIN_RATING_CRITERIA_QUERY = "HAVING AVG(r.value) > ";
    private static final String GET_MOVIES_BY_CRITERIA_MAX_RATING_CRITERIA_QUERY = " AVG(r.value) < ";
    private static final String LIMIT_QUERY = "LIMIT ";
    private static final String GET_MOVIES_COUNT_BY_CRITERIA_HEAD_QUERY = "SELECT COUNT(*) FROM (SELECT DISTINCT m.* FROM movie AS m ";
    private static final String GET_MOVIES_COUNT_BY_CRITERIA_TAIL_QUERY = ") AS c";

    private static final String DEFAULT_LANGUAGE_ID = "EN";

    /**
     * Adds a movie to the data storage (in the default language).
     *
     * @param movie a movie object
     * @throws DAOException
     */
    @Override
    public void addMovie(Movie movie) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(ADD_MOVIE_QUERY);
            statement.setString(1, movie.getName());
            statement.setInt(2, movie.getYear());
            statement.setString(3, movie.getTagline());
            statement.setInt(4, movie.getBudget());
            statement.setDate(5, new Date(movie.getPremiere().getTime()));
            statement.setInt(6, movie.getLasting());
            statement.setString(7, movie.getAnnotation());
            statement.setString(8, movie.getImage());

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when adding movie", e);
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
     * Updates a movie or adds/updates a localization of a movie in the data storage.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a movie. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a movie (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param movie a movie object
     * @param languageId a language id like 'EN', "RU' etc.
     * @throws DAOException
     */
    @Override
    public void updateMovie(Movie movie, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(UPDATE_MOVIE_QUERY);
                statement.setString(1, movie.getName());
                statement.setInt(2, movie.getYear());
                statement.setString(3, movie.getTagline());
                statement.setInt(4, movie.getBudget());
                statement.setDate(5, new Date(movie.getPremiere().getTime()));
                statement.setInt(6, movie.getLasting());
                statement.setString(7, movie.getAnnotation());
                statement.setString(8, movie.getImage());
                statement.setInt(9, movie.getId());
            }
            else {
                PreparedStatement checkStatement = connection.prepareStatement(TMOVIE_CHECK_QUERY);
                checkStatement.setString(1, languageId);
                checkStatement.setInt(2, movie.getId());
                ResultSet resultSet = checkStatement.executeQuery();
                if(resultSet.next()){
                    statement = connection.prepareStatement(UPDATE_TMOVIE_QUERY);
                    statement.setString(1, movie.getName());
                    statement.setString(2, movie.getTagline());
                    statement.setString(3, movie.getAnnotation());
                    statement.setString(4, movie.getImage());
                    statement.setString(5, languageId);
                    statement.setInt(6, movie.getId());
                }
                else {
                    statement = connection.prepareStatement(ADD_TMOVIE_QUERY);
                    statement.setString(1, languageId);
                    statement.setInt(2, movie.getId());
                    statement.setString(3, movie.getName());
                    statement.setString(4, movie.getTagline());
                    statement.setString(5, movie.getAnnotation());
                    statement.setString(6, movie.getImage());
                }
            }

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when updating movie", e);
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
     * Deletes a movie from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting movie
     * @throws DAOException
     */
    @Override
    public void deleteMovie(int id) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(DELETE_MOVIE_QUERY);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when deleting movie", e);
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
     * Returns all the movies from the data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the movies
     * @throws DAOException
     */
    @Override
    public List<Movie> getAllMovies(String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.createStatement();
                resultSet = statement.executeQuery(GET_ALL_MOVIES_QUERY);
            }
            else {
                preparedStatement = connection.prepareStatement(GET_ALL_MOVIES_NOT_DEFAULT_LANG_QUERY);
                preparedStatement.setString(1, languageId);
                resultSet = preparedStatement.executeQuery();
            }

            List<Movie> allMovies = new ArrayList<>();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie.setId(resultSet.getInt(1));
                movie.setName(resultSet.getString(2));
                movie.setYear(resultSet.getInt(3));
                movie.setTagline(resultSet.getString(4));
                movie.setBudget(resultSet.getInt(5));
                movie.setPremiere(resultSet.getDate(6));
                movie.setLasting(resultSet.getInt(7));
                movie.setAnnotation(resultSet.getString(8));
                movie.setImage(resultSet.getString(9));

                allMovies.add(movie);
            }

            return allMovies;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
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
     * Returns a movie by id from the data storage.
     *
     * @param id an id of the needed movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movie by id
     * @throws DAOException
     */
    @Override
    public Movie getMovieById(int id, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(GET_MOVIE_BY_ID_QUERY);
                statement.setInt(1, id);
            }
            else {
                statement = connection.prepareStatement(GET_MOVIE_BY_ID_NOT_DEFAULT_LANG_QUERY);
                statement.setString(1, languageId);
                statement.setInt(2, id);
            }
            ResultSet resultSet = statement.executeQuery();

            Movie movie = null;
            if(resultSet.next()){
                movie = new Movie();
                movie.setId(resultSet.getInt(1));
                movie.setName(resultSet.getString(2));
                movie.setYear(resultSet.getInt(3));
                movie.setTagline(resultSet.getString(4));
                movie.setBudget(resultSet.getInt(5));
                movie.setPremiere(resultSet.getDate(6));
                movie.setLasting(resultSet.getInt(7));
                movie.setAnnotation(resultSet.getString(8));
                movie.setImage(resultSet.getString(9));
            }
            return movie;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
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
     * Returns a movies belonging to the genre from the data storage.
     *
     * @param genreId an id of the genre
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movies belonging to the genre
     * @throws DAOException
     */
    @Override
    public List<Movie> getMoviesByGenre(int genreId, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(GET_MOVIES_BY_GENRE_QUERY);
                statement.setInt(1, genreId);
            }
            else {
                statement = connection.prepareStatement(GET_MOVIES_BY_GENRE_NOT_DEFAULT_LANG_QUERY);
                statement.setString(1, languageId);
                statement.setInt(2, genreId);
            }
            ResultSet resultSet = statement.executeQuery();

            List<Movie> moviesByGenre = new ArrayList<>();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie.setId(resultSet.getInt(1));
                movie.setName(resultSet.getString(2));
                movie.setYear(resultSet.getInt(3));
                movie.setTagline(resultSet.getString(4));
                movie.setBudget(resultSet.getInt(5));
                movie.setPremiere(resultSet.getDate(6));
                movie.setLasting(resultSet.getInt(7));
                movie.setAnnotation(resultSet.getString(8));
                movie.setImage(resultSet.getString(9));

                moviesByGenre.add(movie);
            }

            return moviesByGenre;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
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
     * Returns a movies belonging to the country from the data storage.
     *
     * @param countryId an id of the country
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movies belonging to the country
     * @throws DAOException
     */
    @Override
    public List<Movie> getMoviesByCountry(int countryId, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(GET_MOVIES_BY_COUNTRY_QUERY);
                statement.setInt(1, countryId);
            }
            else {
                statement = connection.prepareStatement(GET_MOVIES_BY_COUNTRY_NOT_DEFAULT_LANG_QUERY);
                statement.setString(1, languageId);
                statement.setInt(2, countryId);
            }
            ResultSet resultSet = statement.executeQuery();

            List<Movie> moviesByCountry = new ArrayList<>();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie.setId(resultSet.getInt(1));
                movie.setName(resultSet.getString(2));
                movie.setYear(resultSet.getInt(3));
                movie.setTagline(resultSet.getString(4));
                movie.setBudget(resultSet.getInt(5));
                movie.setPremiere(resultSet.getDate(6));
                movie.setLasting(resultSet.getInt(7));
                movie.setAnnotation(resultSet.getString(8));
                movie.setImage(resultSet.getString(9));

                moviesByCountry.add(movie);
            }

            return moviesByCountry;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
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
     * Returns a movies in which the person took part in the certain role from the data storage.
     *
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movies in which the person took part in the certain role
     * @throws DAOException
     */
    @Override
    public List<Movie> getMoviesByPersonAndRelationType(int personId, int relationType, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(GET_MOVIES_BY_PERSON_QUERY);
                statement.setInt(1, personId);
                statement.setInt(2, relationType);
            }
            else {
                statement = connection.prepareStatement(GET_MOVIES_BY_PERSON_NOT_DEFAULT_LANG_QUERY);
                statement.setString(1, languageId);
                statement.setInt(2, personId);
                statement.setInt(3, relationType);
            }
            ResultSet resultSet = statement.executeQuery();

            List<Movie> moviesByPersonAndRelationType = new ArrayList<>();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie.setId(resultSet.getInt(1));
                movie.setName(resultSet.getString(2));
                movie.setYear(resultSet.getInt(3));
                movie.setTagline(resultSet.getString(4));
                movie.setBudget(resultSet.getInt(5));
                movie.setPremiere(resultSet.getDate(6));
                movie.setLasting(resultSet.getInt(7));
                movie.setAnnotation(resultSet.getString(8));
                movie.setImage(resultSet.getString(9));

                moviesByPersonAndRelationType.add(movie);
            }

            return moviesByPersonAndRelationType;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
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
     * Returns a recent added movies from the data storage.
     *
     * @param amount a needed amount of movies
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a recent added movies
     * @throws DAOException
     */
    @Override
    public List<Movie> getRecentAddedMovies(int amount, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.createStatement();
                resultSet = statement.executeQuery(GET_RECENT_ADDED_MOVIES_QUERY + amount);
            }
            else {
                preparedStatement = connection.prepareStatement(GET_RECENT_ADDED_MOVIES_NOT_DEFAULT_LANG_QUERY + amount);
                preparedStatement.setString(1, languageId);
                resultSet = preparedStatement.executeQuery();
            }

            List<Movie> allMovies = new ArrayList<>();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie.setId(resultSet.getInt(1));
                movie.setName(resultSet.getString(2));
                movie.setYear(resultSet.getInt(3));
                movie.setTagline(resultSet.getString(4));
                movie.setBudget(resultSet.getInt(5));
                movie.setPremiere(resultSet.getDate(6));
                movie.setLasting(resultSet.getInt(7));
                movie.setAnnotation(resultSet.getString(8));
                movie.setImage(resultSet.getString(9));

                allMovies.add(movie);
            }

            return allMovies;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
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
     * Returns a movies matching to the criteria from the data storage.
     *
     * @param criteria a MovieCriteria object
     * @param from a starting position in the movies list (starting from 0)
     * @param amount a needed amount of movies
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movies matching to the criteria
     * @throws DAOException
     */
    @Override
    public List<Movie> getMoviesByCriteria(MovieCriteria criteria, int from, int amount, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            StringBuilder query = new StringBuilder();
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append(GET_MOVIES_BY_CRITERIA_HEAD_QUERY);
            }
            else {
                query.append(GET_MOVIES_BY_CRITERIA_NOT_DEFAULT_LANGUAGE_HEAD_QUERY);
            }
            if (criteria.getGenreIds() != null) {
                query.append(GET_MOVIES_BY_CRITERIA_MG_JOIN_QUERY);
            }
            if (criteria.getCountryIds() != null) {
                query.append(GET_MOVIES_BY_CRITERIA_MC_JOIN_QUERY);
            }
            if(criteria.getMinRating() != 0 || criteria.getMaxRating() != 0){
                query.append(GET_MOVIES_BY_CRITERIA_RATING_JOIN_QUERY);
            }
            if(!languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append(GET_MOVIES_BY_CRITERIA_TRANSLATE_JOIN_QUERY_FIRST_PART);
                query.append(languageId);
                query.append(GET_MOVIES_BY_CRITERIA_TRANSLATE_JOIN_QUERY_SECOND_PART);
            }

            boolean atLeastOneWhereCriteria = false;
            if (criteria.getName() != null) {
                if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                    query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_QUERY_FIRST_PART);
                    query.append(criteria.getName());
                    query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_QUERY_SECOND_PART);
                }
                else {
                    query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_FIRST_PART);
                    query.append(criteria.getName());
                    query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_SECOND_PART);
                    query.append(criteria.getName());
                    query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_THIRD_PART);
                }
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getMinYear() != 0) {
                query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
                query.append(GET_MOVIES_BY_CRITERIA_MIN_YEAR_CRITERIA_QUERY);
                query.append(criteria.getMinYear());
                query.append(SPACE_SEPARATOR);
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getMaxYear() != 0) {
                query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
                query.append(GET_MOVIES_BY_CRITERIA_MAX_YEAR_CRITERIA_QUERY);
                query.append(criteria.getMaxYear());
                query.append(SPACE_SEPARATOR);
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getGenreIds() != null) {
                query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
                query.append(GET_MOVIES_BY_CRITERIA_GENRES_LIST_CRITERIA_QUERY);
                for (Integer integer : criteria.getGenreIds()) {
                    query.append(integer);
                    query.append(COMA_SEPARATOR);
                }
                query.deleteCharAt(query.length() - 1);
                query.append(CLOSING_BRACKET);
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getCountryIds() != null) {
                query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
                query.append(GET_MOVIES_BY_CRITERIA_COUNTRIES_LIST_CRITERIA_QUERY);
                for (Integer integer : criteria.getCountryIds()) {
                    query.append(integer);
                    query.append(COMA_SEPARATOR);
                }
                query.deleteCharAt(query.length() - 1);
                query.append(CLOSING_BRACKET);
            }
            if(criteria.getMinRating() != 0 || criteria.getMaxRating() != 0){
                query.append(GET_MOVIES_BY_CRITERIA_GROUP_BY_ID_QUERY);
                boolean atLeastOneHavingCriteria = false;
                if(criteria.getMinRating() != 0){
                    query.append(GET_MOVIES_BY_CRITERIA_MIN_RATING_CRITERIA_QUERY);
                    query.append(criteria.getMinRating());
                    query.append(SPACE_SEPARATOR);
                    atLeastOneHavingCriteria = true;
                }
                if(criteria.getMaxRating() != 0){
                    query.append(atLeastOneHavingCriteria ? AND_CRITERIA : HAVING_CRITERIA);
                    query.append(GET_MOVIES_BY_CRITERIA_MAX_RATING_CRITERIA_QUERY);
                    query.append(criteria.getMaxRating());
                    query.append(SPACE_SEPARATOR);
                }
            }
            if(amount != 0){
                query.append(LIMIT_QUERY);
                query.append(from);
                query.append(COMA_SEPARATOR);
                query.append(SPACE_SEPARATOR);
                query.append(amount);
                query.append(SPACE_SEPARATOR);
            }
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            List<Movie> allMovies = new ArrayList<>();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie.setId(resultSet.getInt(1));
                movie.setName(resultSet.getString(2));
                movie.setYear(resultSet.getInt(3));
                movie.setTagline(resultSet.getString(4));
                movie.setBudget(resultSet.getInt(5));
                movie.setPremiere(resultSet.getDate(6));
                movie.setLasting(resultSet.getInt(7));
                movie.setAnnotation(resultSet.getString(8));
                movie.setImage(resultSet.getString(9));

                allMovies.add(movie);
            }

            return allMovies;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Cannot get movies by criteria", e);
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
     * Returns an amount of movies matching to the criteria in the data storage.
     *
     * @param criteria a MovieCriteria object
     * @param languageId a language id like 'EN', "RU' etc.
     * @return an amount of movies matching to the criteria
     * @throws DAOException
     */
    @Override
    public int getMoviesCountByCriteria(MovieCriteria criteria, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            StringBuilder query = new StringBuilder();
            query.append(GET_MOVIES_COUNT_BY_CRITERIA_HEAD_QUERY);

            if (criteria.getGenreIds() != null) {
                query.append(GET_MOVIES_BY_CRITERIA_MG_JOIN_QUERY);
            }
            if (criteria.getCountryIds() != null) {
                query.append(GET_MOVIES_BY_CRITERIA_MC_JOIN_QUERY);
            }
            if(criteria.getMinRating() != 0 || criteria.getMaxRating() != 0){
                query.append(GET_MOVIES_BY_CRITERIA_RATING_JOIN_QUERY);
            }
            if(!languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append(GET_MOVIES_BY_CRITERIA_TRANSLATE_JOIN_QUERY_FIRST_PART);
                query.append(languageId);
                query.append(GET_MOVIES_BY_CRITERIA_TRANSLATE_JOIN_QUERY_SECOND_PART);
            }

            boolean atLeastOneWhereCriteria = false;
            if (criteria.getName() != null) {
                if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                    query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_QUERY_FIRST_PART);
                    query.append(criteria.getName());
                    query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_QUERY_SECOND_PART);
                }
                else {
                    query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_FIRST_PART);
                    query.append(criteria.getName());
                    query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_SECOND_PART);
                    query.append(criteria.getName());
                    query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_THIRD_PART);
                }
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getMinYear() != 0) {
                query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
                query.append(GET_MOVIES_BY_CRITERIA_MIN_YEAR_CRITERIA_QUERY);
                query.append(criteria.getMinYear());
                query.append(SPACE_SEPARATOR);
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getMaxYear() != 0) {
                query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
                query.append(GET_MOVIES_BY_CRITERIA_MAX_YEAR_CRITERIA_QUERY);
                query.append(criteria.getMaxYear());
                query.append(SPACE_SEPARATOR);
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getGenreIds() != null) {
                query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
                query.append(GET_MOVIES_BY_CRITERIA_GENRES_LIST_CRITERIA_QUERY);
                for (Integer integer : criteria.getGenreIds()) {
                    query.append(integer);
                    query.append(COMA_SEPARATOR);
                }
                query.deleteCharAt(query.length() - 1);
                query.append(CLOSING_BRACKET);
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getCountryIds() != null) {
                query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
                query.append(GET_MOVIES_BY_CRITERIA_COUNTRIES_LIST_CRITERIA_QUERY);
                for (Integer integer : criteria.getCountryIds()) {
                    query.append(integer);
                    query.append(COMA_SEPARATOR);
                }
                query.deleteCharAt(query.length() - 1);
                query.append(CLOSING_BRACKET);
            }
            if(criteria.getMinRating() != 0 || criteria.getMaxRating() != 0){
                query.append(GET_MOVIES_BY_CRITERIA_GROUP_BY_ID_QUERY);
                boolean atLeastOneHavingCriteria = false;
                if(criteria.getMinRating() != 0){
                    query.append(GET_MOVIES_BY_CRITERIA_MIN_RATING_CRITERIA_QUERY);
                    query.append(criteria.getMinRating());
                    query.append(SPACE_SEPARATOR);
                    atLeastOneHavingCriteria = true;
                }
                if(criteria.getMaxRating() != 0){
                    query.append(atLeastOneHavingCriteria ? AND_CRITERIA : HAVING_CRITERIA);
                    query.append(GET_MOVIES_BY_CRITERIA_MAX_RATING_CRITERIA_QUERY);
                    query.append(criteria.getMaxRating());
                    query.append(SPACE_SEPARATOR);
                }
            }
            query.append(GET_MOVIES_COUNT_BY_CRITERIA_TAIL_QUERY);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            int moviesCount = 0;
            if(resultSet.next()){
                moviesCount = resultSet.getInt(1);
            }
            return moviesCount;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Cannot get movies by criteria", e);
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
