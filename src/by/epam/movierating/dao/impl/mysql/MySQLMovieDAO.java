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
 * Created by Владислав on 11.06.2016.
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

    private static final String DEFAULT_LANGUAGE_ID = "EN";

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
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    if (preparedStatement != null){
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
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    if (preparedStatement != null){
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
    public List<Movie> getMoviesByCriteria(MovieCriteria criteria, int from, int amount, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            StringBuilder query = new StringBuilder();
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append("SELECT DISTINCT m.* FROM movie AS m ");
            }
            else {
                query.append("SELECT DISTINCT m.id, coalesce(t.name, m.name), m.year, " +
                        "coalesce(t.tagline, m.tagline), m.budget, m.premiere, m.lasting, " +
                        "coalesce(t.annotation, m.annotation), coalesce(t.image, m.image) " +
                        "FROM movie AS m ");
            }
            if (criteria.getGenreIds() != null) {
                query.append("INNER JOIN movie_genre AS mg ON m.id = mg.movie_id ");
            }
            if (criteria.getCountryIds() != null) {
                query.append("INNER JOIN movie_country AS mc ON m.id = mc.movie_id ");
            }
            if(criteria.getMinRating() != 0 || criteria.getMaxRating() != 0){
                query.append("INNER JOIN rating AS r ON m.id = r.movie_id ");
            }
            if(!languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append("LEFT JOIN (SELECT * FROM tmovie WHERE language_id = '");
                query.append(languageId);
                query.append("') AS t USING(id) ");
            }

            boolean atLeastOneWhereCriteria = false;
            if (criteria.getName() != null) {
                if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                    query.append("WHERE m.name LIKE '%");
                    query.append(criteria.getName());
                    query.append("%' ");
                }
                else {
                    query.append("WHERE (m.name LIKE '%");
                    query.append(criteria.getName());
                    query.append("%' OR t.name LIKE '%");
                    query.append(criteria.getName());
                    query.append("%') ");
                }
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getMinYear() != 0) {
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" m.year > ");
                query.append(criteria.getMinYear());
                query.append(" ");
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getMaxYear() != 0) {
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" m.year < ");
                query.append(criteria.getMaxYear());
                query.append(" ");
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getGenreIds() != null) {
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" mg.genre_id IN (");
                for (Integer integer : criteria.getGenreIds()) {
                    query.append(integer);
                    query.append(',');
                }
                query.deleteCharAt(query.length() - 1);
                query.append(") ");
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getCountryIds() != null) {
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" mc.country_id IN (");
                for (Integer integer : criteria.getCountryIds()) {
                    query.append(integer);
                    query.append(',');
                }
                query.deleteCharAt(query.length() - 1);
                query.append(") ");
            }
            if(criteria.getMinRating() != 0 || criteria.getMaxRating() != 0){
                query.append("GROUP BY r.movie_id ");
                boolean atLeastOneHavingCriteria = false;
                if(criteria.getMinRating() != 0){
                    query.append("HAVING AVG(r.value) > ");
                    query.append(criteria.getMinRating());
                    query.append(" ");
                    atLeastOneHavingCriteria = true;
                }
                if(criteria.getMaxRating() != 0){
                    query.append(atLeastOneHavingCriteria ? "AND" : "HAVING");
                    query.append(" AVG(r.value) < ");
                    query.append(criteria.getMaxRating());
                    query.append(" ");
                }
            }
            if(amount != 0){
                query.append("LIMIT ");
                query.append(from);
                query.append(", ");
                query.append(amount);
                query.append(" ");
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
    public int getMoviesCountByCriteria(MovieCriteria criteria, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(*) FROM (SELECT DISTINCT m.* FROM movie AS m ");

            if (criteria.getGenreIds() != null) {
                query.append("INNER JOIN movie_genre AS mg ON m.id = mg.movie_id ");
            }
            if (criteria.getCountryIds() != null) {
                query.append("INNER JOIN movie_country AS mc ON m.id = mc.movie_id ");
            }
            if(criteria.getMinRating() != 0 || criteria.getMaxRating() != 0){
                query.append("INNER JOIN rating AS r ON m.id = r.movie_id ");
            }
            if(!languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append("LEFT JOIN (SELECT * FROM tmovie WHERE language_id = '");
                query.append(languageId);
                query.append("') AS t USING(id) ");
            }

            boolean atLeastOneWhereCriteria = false;
            if (criteria.getName() != null) {
                if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                    query.append("WHERE m.name LIKE '%");
                    query.append(criteria.getName());
                    query.append("%' ");
                }
                else {
                    query.append("WHERE (m.name LIKE '%");
                    query.append(criteria.getName());
                    query.append("%' OR t.name LIKE '%");
                    query.append(criteria.getName());
                    query.append("%') ");
                }
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getMinYear() != 0) {
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" m.year > ");
                query.append(criteria.getMinYear());
                query.append(" ");
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getMaxYear() != 0) {
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" m.year < ");
                query.append(criteria.getMaxYear());
                query.append(" ");
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getGenreIds() != null) {
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" mg.genre_id IN (");
                for (Integer integer : criteria.getGenreIds()) {
                    query.append(integer);
                    query.append(',');
                }
                query.deleteCharAt(query.length() - 1);
                query.append(") ");
                atLeastOneWhereCriteria = true;
            }
            if (criteria.getCountryIds() != null) {
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" mc.country_id IN (");
                for (Integer integer : criteria.getCountryIds()) {
                    query.append(integer);
                    query.append(',');
                }
                query.deleteCharAt(query.length() - 1);
                query.append(") ");
            }
            if(criteria.getMinRating() != 0 || criteria.getMaxRating() != 0){
                query.append("GROUP BY r.movie_id ");
                boolean atLeastOneHavingCriteria = false;
                if(criteria.getMinRating() != 0){
                    query.append("HAVING AVG(r.value) > ");
                    query.append(criteria.getMinRating());
                    query.append(" ");
                    atLeastOneHavingCriteria = true;
                }
                if(criteria.getMaxRating() != 0){
                    query.append(atLeastOneHavingCriteria ? "AND" : "HAVING");
                    query.append(" AVG(r.value) < ");
                    query.append(criteria.getMaxRating());
                    query.append(" ");
                }
            }
            query.append(") AS c");
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
