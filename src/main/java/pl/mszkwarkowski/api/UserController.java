package pl.mszkwarkowski.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import pl.mszkwarkowski.movie.*;
import pl.mszkwarkowski.other.Error;
import pl.mszkwarkowski.user.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class UserController {
    UserInformantStorage userInformantStorage = new UserInformantStorage();
    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;

    /**
     * This method creates new user. If user with this id is already on the list, it will return "null".
     *
     * @param user - User object, created from received JSON code.
     * @return User object.
     */
    @PostMapping(value = "/user")
    public User addUser(@RequestBody User user) {
        if (userRepository.findOne(user.getId()) != null) {
            return null;
        }
        user.setDebt(new BigDecimal("0"));
        userRepository.save(user);
        return userRepository.findOne(user.getId());

    }

    /**
     * @param id of the user.
     * @return User object.
     */
    @GetMapping(value = "/user/{id}")
    public User userData(@PathVariable int id) {
        return userRepository.findOne(id);
    }

    /**
     * @param id of the user.
     * @return list of movies rented by user with given id.
     */
    @GetMapping(value = "/userMovies/{id}")
    public List<Movie> userMovies(@PathVariable int id) {
        return movieRepository.findMoviesByOwner(id);
    }

    /**
     * This method rents movies by user. If movies are not available or they do not exist, it will return empty collection.
     *
     * @param userId
     * @param moviesId
     * @return list of just rented movies.
     */
    @PutMapping(value = "userMovies/{userId}/{moviesId}")
    public BigDecimal rentMovies(@PathVariable("userId") int userId, @PathVariable("moviesId") int[] moviesId) throws Exception {
        if (userRepository.findOne(userId) == null) {
            return null;
        }
        if (((List<Movie>) movieRepository.findMoviesByOwner(userId)).size() + moviesId.length > 10) {
            throw new Exception("User can not have more than 10 movies.");
        }
        return userInformantStorage.rentMovies(userId, moviesId, userRepository, movieRepository);
    }

    /**
     * This method removes movies by user.
     *
     * @param userId
     * @param moviesId
     * @return list of user's movies.
     */
    @DeleteMapping(value = "userMovies/{userId}/{moviesId}")
    public List<Movie> returnMovies(@PathVariable("userId") int userId, @PathVariable("moviesId") int[] moviesId) {
        userInformantStorage.returnMovie(moviesId, movieRepository, userId);
        return movieRepository.findMoviesByOwner(userId);
    }

    /**
     * This method returns the exception message.
     *
     * @param ex Exception.
     * @return Message contained in exception.
     */
    @ExceptionHandler(Exception.class)
    public Error displayExceptionMessage(Exception ex) {
        Error error = new Error(ex.getMessage());
        return error;
    }
}
