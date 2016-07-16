package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.MovieDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.domain.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public class MySQLMovieDAO implements MovieDAO {
    private static final String DEFAULT_LANGUAGE_ID = "EN";

    @Override
    public void addMovie(Movie movie, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(
                        "INSERT INTO movie (name, year, tagline, budget, premiere," +
                                "lasting, annotation, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, movie.getName());
                statement.setInt(2, movie.getYear());
                statement.setString(3, movie.getTagline());
                statement.setInt(4, movie.getBudget());
                statement.setDate(5, new Date(movie.getPremiere().getTime()));
                statement.setInt(6, movie.getLasting());
                statement.setString(7, movie.getAnnotation());
                statement.setString(8, movie.getImage());
            }
            else {
                statement = connection.prepareStatement(
                        "INSERT INTO tmovie (language_id, id, name, tagline, " +
                                "annotation, image) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(1, languageId);
                statement.setInt(2, movie.getId());
                statement.setString(3, movie.getName());
                statement.setString(4, movie.getTagline());
                statement.setString(5, movie.getAnnotation());
                statement.setString(6, movie.getImage());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when adding movie", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void updateMovie(Movie movie, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(
                        "UPDATE movie SET name = ?, year = ?, tagline = ?," +
                                "budget = ?, premiere = ?, lasting = ?, annotation = ?, image = ? WHERE id = ?");
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
                statement = connection.prepareStatement("UPDATE tmovie SET name = ?, tagline = ?, " +
                        "annotation = ?, image = ? WHERE language_id = ? AND id = ?");
                statement.setString(1, movie.getName());
                statement.setString(2, movie.getTagline());
                statement.setString(3, movie.getAnnotation());
                statement.setString(4, movie.getImage());
                statement.setString(5, languageId);
                statement.setInt(6, movie.getId());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when updating movie", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void deleteMovie(int id) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM movie WHERE id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when deleting movie", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Movie> getAllMovies(String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM movie");
            }
            else {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT m.id, " +
                        "coalesce(t.name, m.name), m.year, coalesce(t.tagline, m.tagline), " +
                        "m.budget, m.premiere, m.lasting, coalesce(t.annotation, m.annotation), " +
                        "coalesce(t.image, m.image) FROM movie AS m LEFT JOIN (SELECT * FROM tmovie " +
                        "WHERE language_id = ?) AS t USING(id)");
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
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public Movie getMovieById(int id, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement("SELECT * FROM movie WHERE id = ?");
                statement.setInt(1, id);
            }
            else {
                statement = connection.prepareStatement("SELECT m.id, " +
                        "coalesce(t.name, m.name), m.year, coalesce(t.tagline, m.tagline), " +
                        "m.budget, m.premiere, m.lasting, coalesce(t.annotation, m.annotation), " +
                        "coalesce(t.image, m.image) FROM movie AS m LEFT JOIN (SELECT * FROM tmovie " +
                        "WHERE language_id = ?) AS t USING(id) WHERE m.id = ?");
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
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement("SELECT movie.* FROM movie " +
                        "INNER JOIN movie_genre ON movie.id = movie_genre.movie_id WHERE movie_genre.genre_id = ? ");
                statement.setInt(1, genreId);
            }
            else {
                statement = connection.prepareStatement("SELECT m.id, coalesce(t.name, m.name), m.year, " +
                        "coalesce(t.tagline, m.tagline), m.budget, m.premiere, m.lasting, " +
                        "coalesce(t.annotation, m.annotation), coalesce(t.image, m.image) " +
                        "FROM movie AS m INNER JOIN movie_genre AS mg ON m.id = mg.movie_id " +
                        "LEFT JOIN (SELECT * FROM tmovie WHERE language_id = ?) AS t USING(id) " +
                        "WHERE mg.genre_id = ?");
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
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Movie> getMoviesByCountry(int countryId, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement("SELECT movie.* FROM movie " +
                        "INNER JOIN movie_country ON movie.id = movie_country.movie_id WHERE movie_country.country_id = ? ");
                statement.setInt(1, countryId);
            }
            else {
                statement = connection.prepareStatement("SELECT m.id, coalesce(t.name, m.name), m.year, " +
                        "coalesce(t.tagline, m.tagline), m.budget, m.premiere, m.lasting, " +
                        "coalesce(t.annotation, m.annotation), coalesce(t.image, m.image) " +
                        "FROM movie AS m INNER JOIN movie_country AS mc ON m.id = mc.movie_id " +
                        "LEFT JOIN (SELECT * FROM tmovie WHERE language_id = ?) AS t USING(id) " +
                        "WHERE mc.country_id = ?;");
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
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Movie> getMoviesByPersonAndRelationType(int personId, int relationType, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement("SELECT movie.* FROM movie " +
                        "INNER JOIN movie_person_relation ON movie.id = movie_person_relation.movie_id WHERE " +
                        "movie_person_relation.person_id = ? AND movie_person_relation.relation_type = ?");
                statement.setInt(1, personId);
                statement.setInt(2, relationType);
            }
            else {
                statement = connection.prepareStatement("SELECT m.id, coalesce(t.name, m.name), m.year, " +
                        "coalesce(t.tagline, m.tagline), m.budget, m.premiere, m.lasting, " +
                        "coalesce(t.annotation, m.annotation), coalesce(t.image, m.image) " +
                        "FROM movie AS m INNER JOIN movie_person_relation AS mpr ON m.id = mpr.movie_id " +
                        "LEFT JOIN (SELECT * FROM tmovie WHERE language_id = ?) AS t USING(id) " +
                        "WHERE mpr.person_id = ? AND mpr.relation_type = ?;");
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
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

}
