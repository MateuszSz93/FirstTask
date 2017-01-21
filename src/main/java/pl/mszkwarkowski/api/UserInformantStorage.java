package pl.mszkwarkowski.api;

import pl.mszkwarkowski.movie.Movie;
import pl.mszkwarkowski.movie.MovieCategory;
import pl.mszkwarkowski.movie.MovieRepository;
import pl.mszkwarkowski.user.*;

import java.math.*;
import java.util.*;

/**
 * This class manages the users and user's movies collections.
 */
public class UserInformantStorage {
    /**
     * This method rents movies by user whose id is given as userId. It also count value which user has to pay, including discounts.
     *
     * @param userId
     * @param moviesId
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
            movie.setOwner(userId);
            movieRepository.save(movie);
        }
        if (newMoviesAmount >= 2) {
            payment = payment.multiply(new BigDecimal(0.75));
        }

        user.setDebt(user.getDebt().add(payment).setScale(2, RoundingMode.CEILING));
        userRepository.save(user);
        return payment.setScale(2, RoundingMode.CEILING);
    }

    /**
     * This method returns movies by user whose id is given as userId.
     *
     * @param moviesId
     */
    public void returnMovie(int[] moviesId, MovieRepository movieRepository, int userId) {
        for (int id : moviesId) {
            Movie movie = movieRepository.findOne(id);
            if (movie.getOwner() == userId) {
                movie.setOwner(null);
                movieRepository.save(movie);
            }
        }
    }
}
