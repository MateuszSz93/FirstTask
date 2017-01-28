package pl.mszkwarkowski.api;

import pl.mszkwarkowski.model.Movie;
import pl.mszkwarkowski.model.MovieCategory;
import pl.mszkwarkowski.model.User;
import pl.mszkwarkowski.repository.MovieRepository;
import pl.mszkwarkowski.repository.UserRepository;

import java.math.*;
import java.util.*;

/**
 * This class manages the user and his movies.
 */
public class UserInformantStorage {
    /**
     * Amount in percent to pay after discount.
     */
    private static final BigDecimal AFTER_DISCOUNT = new BigDecimal(0.75);

    /**
     * This method rents movies by user whose id is given as userId. It also count value which user has to pay, including discounts.
     *
     * @param userId
     * @param moviesId
     * @param userRepository
     * @param movieRepository
     * @return list of movies objects.
     */
    public BigDecimal rentMovies(int userId, int[] moviesId, UserRepository userRepository, MovieRepository movieRepository) {
        List<Movie> availableMoviesList = new ArrayList<>();
        boolean otherMovie = false;
        int newMoviesAmount = 0;
        BigDecimal payment = new BigDecimal("0");
        User user = userRepository.findOne(userId);

        for (int i : moviesId) {
            if ((movieRepository.findOne(i) != null) && (movieRepository.findOne(i).getOwner() == null)) {
                availableMoviesList.add(movieRepository.findOne(i));
            }
        }
        for (Movie movie : availableMoviesList) {
            if (availableMoviesList.size() >= 4 && movie.getCategory() == MovieCategory.OTHER && !otherMovie) {
                otherMovie = true;
            } else if (movie.getCategory() == MovieCategory.NEW) {
                newMoviesAmount++;
                payment = payment.add(movie.getCategory().value());
            } else {
                payment = payment.add(movie.getCategory().value());
            }
            movie.setOwner(userRepository.findOne(userId));
            movieRepository.save(movie);
        }
        if (newMoviesAmount >= 2) {
            payment = payment.multiply(AFTER_DISCOUNT);
        }

        user.setDebt(user.getDebt().add(payment).setScale(2, RoundingMode.CEILING));
        userRepository.save(user);
        return payment.setScale(2, RoundingMode.CEILING);
    }

    /**
     * This method returns movies to rental by user whose id is given as userId.
     *
     * @param moviesId
     * @param movieRepository
     * @param userId
     */
    public void returnMovie(int[] moviesId, MovieRepository movieRepository, int userId) {
        for (int id : moviesId) {
            Movie movie = movieRepository.findOne(id);
            if (movie.getOwner().getId() == userId) {
                movie.setOwner(null);
                movieRepository.save(movie);
            }
        }
    }
}
