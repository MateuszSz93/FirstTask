package pl.mszkwarkowski.api;

import pl.mszkwarkowski.movie.Movie;
import pl.mszkwarkowski.movie.MovieCategory;
import pl.mszkwarkowski.user.*;

import java.math.*;
import java.util.*;

/**
 * This class manages the users and user's movies collections.
 */
public class UserInformantStorage {
    /**
     * Map, where the key is id of user and value is User object with the given id.
     */
    private static final Map<Integer, User> USERS = new HashMap<>();

    /**
     * Map, where the key is id of user and value is list of movies rented by this user.
     */
    private static final Map<Integer, List<Integer>> LIST_OF_USER_MOVIES = new HashMap<>();

    /**
     * @param id of the user.
     * @return User object.
     */
    public User getUser(int id) {
        return USERS.get(id);
    }

    /**
     * This method adds new user to USERS HashMap and adds new ArrayList as value to LIST_OF_USER_MOVIES HashMap where the key is user's id.
     *
     * @param user
     */
    public void addUser(User user) {
        user.setDebt(new BigDecimal(0));
        LIST_OF_USER_MOVIES.put(user.getId(), new ArrayList<>());
        USERS.put(user.getId(), user);
    }

    /**
     * This method returns all movies rented by user whose id is given as parameter.
     *
     * @param id of the user.
     * @return list of movie objects.
     */
    public List<Movie> getUserMovies(int id) {
        MoviesInformantStorage moviesInformantStorage = new MoviesInformantStorage();
        List<Movie> userMovies = new ArrayList<>();
        for (int movieId : LIST_OF_USER_MOVIES.get(id)) {
            userMovies.add(moviesInformantStorage.getMovie(movieId));
        }
        return userMovies;
    }

    /**
     * @param movieId
     */
    public void removeMovieFromUsersList(int movieId) {
        for (List<Integer> i : LIST_OF_USER_MOVIES.values()) {
            if (i.contains(movieId)) {
                i.remove(Integer.valueOf(movieId));
                break;
            }
        }
    }

    /**
     * This method rent movies by user whose id is given as userId. It adds movies to movies list, which is value in LIST_OF_USER_MOVIES. It also count value which user has to pay, including discounts for four movies or for two movies which has "NEW" category.
     *
     * @param userId
     * @param moviesId
     * @return list of movies objects.
     */
    public List<Movie> rentMovies(int userId, int[] moviesId) {
        MoviesInformantStorage moviesInformantStorage = new MoviesInformantStorage();
        List<Movie> availableMoviesList = new ArrayList<>();
        List<Movie> acceptedMovies = new ArrayList<>();
        int newMoviesAmount = 0;
        BigDecimal payment = new BigDecimal("0");
        boolean otherMovie = false;
        int moviesLeftFourDiscount = 7;

        for (int i : moviesId) {
            if ((moviesInformantStorage.getMovie(i) != null) && (moviesInformantStorage.getMovie(i).isAvailable())) {
                availableMoviesList.add(moviesInformantStorage.getMovie(i));
            }
        }

        for (Movie movie : availableMoviesList) {
            if (LIST_OF_USER_MOVIES.get(userId).size() != 10) {
                if (availableMoviesList.size() == 4 && movie.getCategory() == MovieCategory.OTHER && !otherMovie && LIST_OF_USER_MOVIES.get(userId).size() != moviesLeftFourDiscount++) {
                    otherMovie = true;
                } else if (availableMoviesList.size() == 2 && movie.getCategory() == MovieCategory.NEW) {
                    newMoviesAmount++;
                    payment = payment.add(movie.getCategory().value());
                } else {
                    payment = payment.add(movie.getCategory().value());
                }
                LIST_OF_USER_MOVIES.get(userId).add(movie.getId());
                movie.setAvailable(false);
                acceptedMovies.add(movie);
            }
        }
        if (newMoviesAmount == 2) {
            payment = payment.multiply(new BigDecimal(0.75));
        }
        getUser(userId).setDebt(getUser(userId).getDebt().add(payment).setScale(2, RoundingMode.CEILING));
        return acceptedMovies;
    }

    /**
     * This method remove movies from movies list, which is value in LIST_OF_USER_MOVIES and changes their availability to "true".
     *
     * @param userId
     * @param moviesId
     */
    public void returnMovies(int userId, int[] moviesId) {
        MoviesInformantStorage moviesInformantStorage = new MoviesInformantStorage();
        for (int id : moviesId) {
            if (moviesInformantStorage.getMovie(id) != null) {
                moviesInformantStorage.getMovie(id).setAvailable(true);
                LIST_OF_USER_MOVIES.get(userId).remove(Integer.valueOf(id));
            }
        }
    }
}
