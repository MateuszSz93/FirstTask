package pl.mszkwarkowski.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import pl.mszkwarkowski.api.*;
import pl.mszkwarkowski.model.*;
import pl.mszkwarkowski.other.Error;
import pl.mszkwarkowski.repository.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;

@RestController
@EnableAutoConfiguration
@Api(description = "Users management")
@RequestMapping("/user")
public class UserController {
    UserInformantStorage userInformantStorage = new UserInformantStorage();
    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;

    /**
     * @param user - User object, created from received JSON code.
     * @return User object.
     * @throws ForbiddenException and status code 403 if user with this id already exists.
     */
    @ApiOperation(httpMethod = "POST", value = "Add new user.", notes = "Add new user and return his details.", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added new user.", response = User.class),
            @ApiResponse(code = 403, message = "Tried to add user with id which is already taken by other user.", response = Error.class)}
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = {MediaType.APPLICATION_JSON}, consumes = {MediaType.APPLICATION_JSON})
    public User addUser(@ApiParam(name = "user", value = "New user's data.", required = true) @RequestBody User user) throws ForbiddenException {
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
     * @throws NotFoundException and status code 404 if user with this id does not exist.
     */
    @ApiIgnore
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = {MediaType.APPLICATION_JSON}, params = "userId")
    public User userData(@RequestParam(value = "userId", required = true) int id) throws NotFoundException {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new NotFoundException("User with this id does not exist.");
        }
        return user;
    }

    /**
     * @param id of the user.
     * @return movies rented by user with given id.
     */
    @ApiOperation(value = "Return user movies.", notes = "Return a complete list of movies rented by user with their details.", response = Movie.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved movie's list.", response = Movie.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "User with this id can not be found.", response = Error.class)}
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = {MediaType.APPLICATION_JSON}, params = "id")
    public List<Movie> userMovies(@ApiParam(name = "id", value = "User's id. All movies of this user will be returned.", required = true) @RequestParam(value = "id", required = true) int id) {
        return movieRepository.findByOwner(userRepository.getOne(id));
    }

    /**
     * This method rents movies by user. If movies are not available or they do not exist, it will return zero as cost information.
     *
     * @param userId
     * @param moviesId
     * @return cost information.
     * @throws NotFoundException   and status code 404 if user with this id does not exist.
     * @throws BadRequestException and status code 400 if user want to have more than 10 movies.
     */
    @ApiOperation(value = "Rent movies by user.", notes = "Rent movies from rental by user and return cost information.", response = BigDecimal.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved cost information.", response = BigDecimal.class),
            @ApiResponse(code = 404, message = "User with this id can not be found.", response = Error.class),
            @ApiResponse(code = 400, message = "Tried to rent amount of movies which together with amount of movies owned by user has exceed 10.", response = Error.class)}
    )
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(params = {"userId", "moviesId"})
    public BigDecimal rentMovies(@ApiParam(name = "userId", value = "Id of user whom wants to rent some movies.", required = true) @RequestParam(value = "userId", required = true) int userId, @ApiParam(name = "moviesId", value = "Id of movie which user wants to rent.", required = true, allowMultiple = true) @RequestParam(value = "moviesId", required = true) int[] moviesId) throws NotFoundException, BadRequestException {
        if (userRepository.findOne(userId) == null) {
            throw new NotFoundException("User with this id does not exist.");
        }
        if (((List<Movie>) movieRepository.findByOwner(userRepository.findOne(userId))).size() + moviesId.length > 10) {
            throw new BadRequestException("User can not have more than 10 movies.");
        }
        return userInformantStorage.rentMovies(userId, moviesId, userRepository, movieRepository);
    }

    /**
     * This method returns movies to rental by user.
     *
     * @param userId
     * @param moviesId
     * @return movies which still belong to user.
     * @throws NotFoundException and status code 404 if user with this id does not exist.
     */
    @ApiOperation(value = "Return movies by user.", notes = "Return movies to rental by user and return list of movies still rented by user.", response = Movie.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved movie's list.", response = Movie.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "User with this id can not be found.", response = Error.class)}
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(produces = {MediaType.APPLICATION_JSON}, params = {"userId", "moviesId"})
    public List<Movie> returnMovies(@ApiParam(name = "userId", value = "Id of user whom wants to return some movies to rental.", required = true) @RequestParam(value = "userId", required = true) int userId, @ApiParam(name = "moviesId", value = "Id of movie which user wants to return to rental.", required = true, allowMultiple = true) @RequestParam(value = "moviesId", required = true) int[] moviesId) throws NotFoundException {
        if (userRepository.findOne(userId) == null) {
            throw new NotFoundException("User with this id does not exist.");
        }
        userInformantStorage.returnMovie(moviesId, movieRepository, userId);
        return movieRepository.findByOwner(userRepository.getOne(userId));
    }
}
