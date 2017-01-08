package pl.mszkwarkowski.api;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import pl.mszkwarkowski.movie.*;
import pl.mszkwarkowski.user.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class UserController {
    UserInformantStorage userInformantStorage = new UserInformantStorage();

    /**
     * This method creates new User object and adds it to the HashMap. If user with this id is already on the list, it will return "null".
     *
     * @param user - User object, created from received JSON code.
     * @return User object.
     */
    @PostMapping(value = "/newUser")
    public User addUser(@RequestBody User user) {
        if (userInformantStorage.getUser(user.getId()) != null) {
            return null;
        }
        userInformantStorage.addUser(user);
        return userInformantStorage.getUser(user.getId());
    }

    /**
     * @param id of the user.
     * @return User object.
     */
    @GetMapping(value = "/user/{id}")
    public User userData(@PathVariable int id) { return userInformantStorage.getUser(id); }

    /**
     * @param id of the user.
     * @return list of movies rented by user with given id.
     */
    @GetMapping(value = "/userMovies/{id}")
    public List<Movie> userMovies(@PathVariable int id) { return userInformantStorage.getUserMovies(id); }

    /**
     * This method adds new movies to user's movie's list. If movies are not available or they do not exist, it will return empty collection.
     *
     * @param userId
     * @param moviesId
     * @return list of just rented movies.
     */
    @PutMapping(value = "rentMovies/{userId}/{moviesId}")
    public List<Movie> rentMovies(@PathVariable("userId") int userId, @PathVariable("moviesId") int[] moviesId) {
        return userInformantStorage.rentMovies(userId, moviesId);
    }

    /**
     * This method removes movies from user's movie's list.
     *
     * @param userId
     * @param moviesId
     * @return list of user's movies.
     */
    @DeleteMapping(value = "returnMovies/{userId}/{moviesId}")
    public List<Movie> returnMovies(@PathVariable("userId") int userId, @PathVariable("moviesId") int[] moviesId) {
        userInformantStorage.returnMovies(userId, moviesId);
        return userInformantStorage.getUserMovies(userId);
    }
}
