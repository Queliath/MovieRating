package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.GenreDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.domain.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public class MySQLGenreDAO implements GenreDAO {
    @Override
    public void addGenre(Genre genre) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO genre " +
                    "(name, description) VALUES (?, ?)");
            statement.setString(1, genre.getName());
            statement.setString(2, genre.getDescription());

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Error in DAO layer when adding genre", e);
        }
    }

    @Override
    public void updateGenre(Genre genre) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE genre " +
                    "SET name = ?, description = ? WHERE id = ?");
            statement.setString(1, genre.getName());
            statement.setString(2, genre.getDescription());
            statement.setInt(3, genre.getId());

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Error in DAO layer when updating genre", e);
        }
    }

    @Override
    public void deleteGenre(int id) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM genre WHERE id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Error in DAO layer when deleting genre", e);
        }
    }

    @Override
    public List<Genre> getAllGenres() throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM genre");

            mySQLConnectionPool.freeConnection(connection);

            List<Genre> allGenres = new ArrayList<>();
            while (resultSet.next()){
                Genre genre = new Genre();
                genre.setId(resultSet.getInt(1));
                genre.setName(resultSet.getString(2));
                genre.setDescription(resultSet.getString(3));

                allGenres.add(genre);
            }
            return allGenres;

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Error in DAO layer when getting genre", e);
        }
    }

    @Override
    public Genre getGenreById(int id) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            mySQLConnectionPool.freeConnection(connection);

            Genre genre = null;
            if(resultSet.next()){
                genre = new Genre();
                genre.setId(resultSet.getInt(1));
                genre.setName(resultSet.getString(2));
                genre.setDescription(resultSet.getString(3));
            }
            return genre;

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Error in DAO layer when getting genre", e);
        }
    }

    @Override
    public List<Genre> getGenresByMovie(int movieId) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT genre.* FROM genre " +
                    "INNER JOIN movie_genre ON genre.id = movie_genre.genre_id " +
                    "WHERE movie_genre.movie_id = ?");
            statement.setInt(1, movieId);
            ResultSet resultSet = statement.executeQuery();

            mySQLConnectionPool.freeConnection(connection);

            List<Genre> genresByMovie = new ArrayList<>();
            while (resultSet.next()){
                Genre genre = new Genre();
                genre.setId(resultSet.getInt(1));
                genre.setName(resultSet.getString(2));
                genre.setDescription(resultSet.getString(3));

                genresByMovie.add(genre);
            }
            return genresByMovie;

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Error in DAO layer when getting genre", e);
        }
    }
}
