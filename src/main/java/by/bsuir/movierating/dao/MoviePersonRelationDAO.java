package by.bsuir.movierating.dao;

/**
 * Provides a DAO-logic for the relations between Movie and Person entities.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface MoviePersonRelationDAO {
    /**
     * Adds a relation between the movie and the person.
     *
     * @param movieId an id of the movie
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     */
    void addMovieToPersonWithRelation(int movieId, int personId, int relationType);

    /**
     * Deletes a relation between the movie and the person.
     *
     * @param movieId an id of the movie
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     */
    void deleteMovieFromPersonWithRelation(int movieId, int personId, int relationType);

    /**
     * Returns an amount of movies in which this person took part.
     *
     * @param personId an id of the person
     * @return an amount of movies in which this person took part
     */
    int getMoviesTotalByPerson(int personId);
}
