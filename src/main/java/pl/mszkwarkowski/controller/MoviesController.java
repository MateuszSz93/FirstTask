package pl.mszkwarkowski.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.http.CacheControl;
import org.springframework.web.bind.annotation.*;
import pl.mszkwarkowski.model.*;
import pl.mszkwarkowski.repository.*;
import springfox.documentation.annotations.ApiIgnore;
import pl.mszkwarkowski.other.Error;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@EnableAutoConfiguration
@Api(description = "Movies management")
@RequestMapping(value = "/movies")
public class MoviesController {
    private static final int MAX_AGE_TIME = 5;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieRepository movieRepository;

    /**
     * @return all movies.
     */
    @ApiOperation(value = "Return movies details.", notes = "Return a complete list of movies with their details. To get only one movie, it is needed to give movie's id as 'id' parameter. To get pagination, it is needed to give page number as 'page' parameter (During using pagination, it is possible also to add limit of objects per page by adding 'limit' parameter. 'limit' parameter can not exist alone, it must always cooperate with 'page' parameter.). Parameters do not work together (the exception are 'page' and 'limit' parameters).", response = Movie.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved details.", response = Movie.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Movie with this id can not be found.", response = Error.class)}
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Movie's id. Only one movie with the given id will be returned.", required = false, allowMultiple = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "page", value = "Page number. Pagination will be added and indicated page will be displayed.", required = false, allowMultiple = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "limit", value = "Objects limit on the page. Given number of objects will be displayed on each page. It must cooperate with 'page' parameter.", required = false, allowMultiple = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "category", value = "Movie's category. Movies with the same category will be returned.", required = false, allowMultiple = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "available", value = "Movie's availability. Movies with the same availability will be returned.", required = false, allowMultiple = false, paramType = "query", dataType = "boolean"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ResponseEntity<List<Movie>> getMoviesData() {
        List<Movie> movieList = (List<Movie>) movieRepository.findAll();
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(MAX_AGE_TIME, TimeUnit.MINUTES)).body(movieList);
    }

    /**
     * @param page
     * @param limit
     * @return pages with specified amount of movies.
     */
    @ApiIgnore
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML}, params = {"page"})
    public ResponseEntity<Page<Movie>> getMoviesWithPagination(@RequestParam(value = "page", required = true) int page, @RequestParam(value = "limit", required = false, defaultValue = "5") int limit) {
        PageRequest request = new PageRequest(page - 1, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(MAX_AGE_TIME, TimeUnit.MINUTES)).body(movieRepository.findAll(request));
    }

    /**
     * @param movie - Movie object, created from received JSON code.
     * @return movie object.
     * @throws ForbiddenException  and status code 403 if movie with this id already exists.
     * @throws BadRequestException and status code 400 if movie does not have any actor.
     */
    @ApiOperation(httpMethod = "POST", value = "Add new movie.", notes = "Add new movie and return his details.", response = Movie.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added new movie.", response = Movie.class),
            @ApiResponse(code = 403, message = "Tried to add movie with id which is already taken by other movie.", response = Error.class),
            @ApiResponse(code = 400, message = "Tried to add movie without any actor.", response = Error.class)}
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = {MediaType.APPLICATION_JSON}, consumes = {MediaType.APPLICATION_JSON})
    public Movie addNewMovie(@ApiParam(name = "movie", value = "New movie's data.", required = true) @RequestBody Movie movie) throws ForbiddenException, BadRequestException {
        if (movieRepository.findOne(movie.getId()) != null) {
            throw new ForbiddenException("Movie with this id already exists");
        }
        if (movie.getActorList().isEmpty()) {
            throw new BadRequestException("Movie must to have an actor.");
        }
        movie.setOwner(null);
        movieRepository.save(movie);
        return movieRepository.findOne(movie.getId());
    }

    /**
     * @param id of movie.
     * @return list of Movie objects.
     * @throws NotFoundException and status code 404 if movie can not be found before deletion.
     */
    @ApiOperation(httpMethod = "DELETE", value = "Remove movie.", notes = "Remove movie and return list of other movies with their details.", response = Movie.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully removed movie.", response = Movie.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Tried to remove movie which can not be found.", response = Error.class)}
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(produces = {MediaType.APPLICATION_JSON}, params = "id")
    public List<Movie> deleteMovie(@ApiParam(name = "id", value = "Id of movie which has to be removed.", required = true) @RequestParam(value = "id", required = true) int id) throws NotFoundException {
        if (movieRepository.findOne(id) != null) {
            movieRepository.delete(id);
        } else {
            throw new NotFoundException("Movie with this id can not be found.");
        }
        return (List<Movie>) movieRepository.findAll();
    }

    /**
     * @param id of movie.
     * @return Movie object.
     * @throws NotFoundException and status code 404 if movie with this id does not exist.
     */
    @ApiIgnore
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML}, params = "id")
    public Movie movieData(@RequestParam(value = "id", required = true) int id) throws NotFoundException {
        Movie movie = movieRepository.findOne(id);
        if (movie == null) {
            throw new NotFoundException("Movie with this id does not exist.");
        }
        return movie;
    }

    /**
     * @param id    of movie.
     * @param movie - Movie object, created from received JSON code.
     * @return Movie object.
     * @throws ForbiddenException  and status code 403 if ids are not the same.
     * @throws BadRequestException and status code 400 if movie does not have any actor.
     * @throws NotFoundException   and status code 404 if movie with this id does not exist.
     */
    @ApiOperation(httpMethod = "PUT", value = "Edit movie.", notes = "Edit movie and return this movie with his details.", response = Movie.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully edited movie.", response = Movie.class),
            @ApiResponse(code = 404, message = "Tried to edit movie which can not be found.", response = Error.class),
            @ApiResponse(code = 403, message = "Tried to edit movie with incompatible id.", response = Error.class),
            @ApiResponse(code = 400, message = "Edited movie does not have actors.", response = Error.class)}
    )
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(produces = {MediaType.APPLICATION_JSON}, consumes = {MediaType.APPLICATION_JSON}, params = "id")
    public Movie editMovie(@ApiParam(name = "id", value = "Id of movie which has to be edited.", required = true) @RequestParam(value = "id", required = true) int id, @ApiParam(name = "movie", value = "Movie's data after edition.", required = true) @RequestBody Movie movie) throws ForbiddenException, BadRequestException, NotFoundException {
        if (movieRepository.findOne(id) == null) {
            throw new NotFoundException("Movie with this id does not exist.");
        }
        if (id != movie.getId()) {
            throw new ForbiddenException("Wrong object id.");
        }
        if (movie.getActorList().isEmpty()) {
            throw new BadRequestException("Movie must to have an actor.");
        }
        movie.setOwner(movieRepository.findOne(id).getOwner());
        movieRepository.save(movie);
        return movieRepository.findOne(movie.getId());
    }

    /**
     * @param category of movies.
     * @return list of Movie objects.
     */
    @ApiIgnore
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML}, params = "category")
    public List<Movie> getSameCategoryMovies(@RequestParam(value = "category", required = true) MovieCategory category) {
        return movieRepository.findByCategory(category);
    }

    /**
     * @param available
     * @return list of Movie objects.
     */
    @ApiIgnore
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML}, params = "available")
    public List<Movie> displayAvailableMovies(@RequestParam(value = "available", required = true) boolean available) {
        if (available == false) {
            return movieRepository.findByOwnerIsNotNull();
        }
        return movieRepository.findByOwnerIsNull();
    }
}