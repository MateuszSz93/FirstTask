package pl.mszkwarkowski.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import pl.mszkwarkowski.api.*;
import pl.mszkwarkowski.model.*;
import pl.mszkwarkowski.repository.*;

import javax.ws.rs.*;
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/user", produces = {"application/json"}, consumes = {"application/json"})
    public User addUser(@RequestBody User user) {
        if (userRepository.findOne(user.getId()) != null) {
            throw new ForbiddenException("User with this id already exists");
        }
        user.setDebt(new BigDecimal("0"));
        userRepository.save(user);
        return userRepository.findOne(user.getId());
    }

    /**
     * @param id of the user.
     * @return User object.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/user", produces = {"application/json"}, params = "userId")
    public User userData(@RequestParam(value = "userId", required = true) int id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new NotFoundException("User with this id does not exist.");
        }
        return user;
    }

    /**
     * @param id of the user.
     * @return list of movies rented by user with given id.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/user", produces = {"application/json"}, params = "id")
    public List<Movie> userMovies(@RequestParam(value = "id", required = true) int id) {
        return movieRepository.findByOwner(userRepository.getOne(id));
    }

    /**
     * This method rents movies by user. If movies are not available or they do not exist, it will return empty collection.
     *
     * @param userId
     * @param moviesId
     * @return list of just rented movies.
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/user", produces = {"application/json"}, consumes = {"application/json"}, params = {"userId", "moviesId"})
    public BigDecimal rentMovies(@RequestParam(value = "userId", required = true) int userId, @RequestParam(value = "moviesId", required = true) int[] moviesId) {
        if (userRepository.findOne(userId) == null) {
            throw new NotFoundException("User with this id does not exist.");
        }
        if (((List<Movie>) movieRepository.findByOwner(userRepository.findOne(userId))).size() + moviesId.length > 10) {
            throw new BadRequestException("User can not have more than 10 movies.");
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
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/user", produces = {"application/json"}, params = {"userId", "moviesId"})
    public List<Movie> returnMovies(@RequestParam(value = "userId", required = true) int userId, @RequestParam(value = "moviesId", required = true) int[] moviesId) throws BadRequestException {
        try {
            userInformantStorage.returnMovie(moviesId, movieRepository, userId);
        } catch (Exception ex) {
            throw new NotFoundException("Movie do not belong to user");
        }
        return movieRepository.findByOwner(userRepository.getOne(userId));
    }
}
