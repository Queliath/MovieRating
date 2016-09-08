package by.epam.movierating.service.factory;

import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.inter.*;

import java.util.ResourceBundle;

/**
 * Created by Владислав on 15.07.2016.
 */
public class ServiceFactory {
    private static final String RESOURCE_BUNDLE_NAME = "service-factory";
    private static final String SITE_SERVICE_IMPL_KEY = "site-service";
    private static final String COMMENT_SERVICE_IMPL_KEY = "comment-service";
    private static final String COUNTRY_SERVICE_IMPL_KEY = "country-service";
    private static final String GENRE_SERVICE_IMPL_KEY = "genre-service";
    private static final String MOVIE_SERVICE_IMPL_KEY = "movie-service";
    private static final String POOL_SERVICE_IMPL_KEY = "pool-service";
    private static final String RATING_SERVICE_IMPL_KEY = "rating-service";
    private static final String PERSON_SERVICE_IMPL_KEY = "person-service";
    private static final String USER_SERVICE_IMPL_KEY = "user-service";
    private static final String RELATION_SERVICE_IMPL_KEY = "relation-service";

    private static ServiceFactory instance;

    private SiteService siteService;
    private CommentService commentService;
    private CountryService countryService;
    private GenreService genreService;
    private MovieService movieService;
    private PoolService poolService;
    private RatingService ratingService;
    private PersonService personService;
    private UserService userService;
    private RelationService relationService;

    private ServiceFactory() throws ServiceException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);
        String siteServiceImplClassName = resourceBundle.getString(SITE_SERVICE_IMPL_KEY);
        String commentServiceImplClassName = resourceBundle.getString(COMMENT_SERVICE_IMPL_KEY);
        String countryServiceImplClassName = resourceBundle.getString(COUNTRY_SERVICE_IMPL_KEY);
        String genreServiceImplClassName = resourceBundle.getString(GENRE_SERVICE_IMPL_KEY);
        String movieServiceImplClassName = resourceBundle.getString(MOVIE_SERVICE_IMPL_KEY);
        String poolServiceImplClassName = resourceBundle.getString(POOL_SERVICE_IMPL_KEY);
        String ratingServiceImplClassName = resourceBundle.getString(RATING_SERVICE_IMPL_KEY);
        String personServiceImplClassName = resourceBundle.getString(PERSON_SERVICE_IMPL_KEY);
        String userServiceImplClassName = resourceBundle.getString(USER_SERVICE_IMPL_KEY);
        String relationServiceImplClassName = resourceBundle.getString(RELATION_SERVICE_IMPL_KEY);

        try {
            siteService = (SiteService) Class.forName(siteServiceImplClassName).newInstance();
            commentService = (CommentService) Class.forName(commentServiceImplClassName).newInstance();
            countryService = (CountryService) Class.forName(countryServiceImplClassName).newInstance();
            genreService = (GenreService) Class.forName(genreServiceImplClassName).newInstance();
            movieService = (MovieService) Class.forName(movieServiceImplClassName).newInstance();
            poolService = (PoolService) Class.forName(poolServiceImplClassName).newInstance();
            ratingService = (RatingService) Class.forName(ratingServiceImplClassName).newInstance();
            personService = (PersonService) Class.forName(personServiceImplClassName).newInstance();
            userService = (UserService) Class.forName(userServiceImplClassName).newInstance();
            relationService = (RelationService) Class.forName(relationServiceImplClassName).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new ServiceException("Cannot init ServiceFactory", e);
        }
    }

    public static synchronized ServiceFactory getInstance() throws ServiceException {
        if(instance == null){
            instance = new ServiceFactory();
        }
        return instance;
    }

    public SiteService getSiteService() {
        return siteService;
    }

    public CommentService getCommentService() {
        return commentService;
    }

    public CountryService getCountryService() {
        return countryService;
    }

    public GenreService getGenreService() {
        return genreService;
    }

    public MovieService getMovieService() {
        return movieService;
    }

    public PoolService getPoolService() {
        return poolService;
    }

    public RatingService getRatingService() {
        return ratingService;
    }

    public PersonService getPersonService() {
        return personService;
    }

    public UserService getUserService() {
        return userService;
    }

    public RelationService getRelationService() {
        return relationService;
    }
}
