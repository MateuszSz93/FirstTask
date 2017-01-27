package pl.mszkwarkowski.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pl.mszkwarkowski.model.*;
import pl.mszkwarkowski.repository.*;

import javax.ws.rs.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@EnableAutoConfiguration
public class MoviesInformantController {
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping("/")
    String home() {
        return "Welcome in Movies REST API!";
    }

    /**
     * @return all movies.
     */
    @GetMapping(value = "/movies", produces = {"application/json", "application/xml"})
    public ResponseEntity<List<Movie>> getMoviesData() {
        List<Movie> movieList = (List<Movie>) movieRepository.findAll();
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(300, TimeUnit.SECONDS)).body(movieList);
    }

    /**
     * @param page
     * @param limit
     * @return pages with specified amount of movies.
     */
    @GetMapping(value = "/movies", produces = {"application/json", "application/xml"}, params = {"page"})
    public ResponseEntity<Page<Movie>> getMoviesWithPagination(@RequestParam(value = "page", required = true) int page, @RequestParam(value = "limit", required = false, defaultValue = "5") int limit) {
        PageRequest request = new PageRequest(page - 1, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(300, TimeUnit.SECONDS)).body(movieRepository.findAll(request));
    }

    /**
     * @return all actors.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/actors", produces = {"application/json"})
    public List<Actor> getActorsData() {
        return (List<Actor>) actorRepository.findAll();
    }

    /**
     * @param actor - Actor object, created from received JSON code.
     * @return Actor object.
     * @throws ForbiddenException and status code 403 if actor with this id already exists.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/actors", produces = {"application/json"}, consumes = {"application/json"})
    public Actor addNewActor(@RequestBody Actor actor) throws ForbiddenException {
        if (actorRepository.findOne(actor.getId()) != null) {
            throw new ForbiddenException("Actor with this id already exists");
        }
        actorRepository.save(actor);
        return actorRepository.findOne(actor.getId());
    }

    /**
     * @param movie - Movie object, created from received JSON code.
     * @return movie object.
     * @throws ForbiddenException  and status code 403 if movie with this id already exists.
     * @throws BadRequestException and status code 400 if movie does not have any actor.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/movies", produces = {"application/json"}, consumes = {"application/json"})
    public Movie addNewMovie(@RequestBody Movie movie) throws ForbiddenException, BadRequestException {
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
     * @param id of actor
     * @return list of Actor objects.
     * @throws NotFoundException and status code 404 if actor can not be found before deletion.
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/actors", produces = {"application/json"}, params = "id")
    public List<Actor> deleteActor(@RequestParam(value = "id", required = true) int id) throws NotFoundException {
        if (actorRepository.findOne(id) != null) {
            actorRepository.delete(id);
        } else {
            throw new NotFoundException("Actor with this id can not be found.");
        }
        return (List<Actor>) actorRepository.findAll();
    }

    /**
     * @param id of movie.
     * @return list of Movie objects.
     * @throws NotFoundException and status code 404 if movie can not be found before deletion.
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/movies", produces = {"application/json"}, params = "id")
    public List<Movie> deleteMovie(@RequestParam(value = "id", required = true) int id) throws NotFoundException {
        if (movieRepository.findOne(id) != null) {
            movieRepository.delete(id);
        } else {
            throw new NotFoundException("Movie with this id can not be found.");
        }
        return (List<Movie>) movieRepository.findAll();
    }

    /**
     * @param id of actor.
     * @return Actor object.
     * @throws NotFoundException and status code 404 if actor with this id does not exist.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/actors", produces = {"application/json"}, params = "id")
    public Actor actorData(@RequestParam(value = "id", required = true) int id) throws NotFoundException {
        Actor actor = actorRepository.findOne(id);
        if (actor == null) {
            throw new NotFoundException("Actor with this id does not exist.");
        }
        return actor;
    }

    /**
     * @param id of movie.
     * @return Movie object.
     * @throws NotFoundException and status code 404 if movie with this id does not exist.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/movies", produces = {"application/json"}, params = "id")
    public Movie movieData(@RequestParam(value = "id", required = true) int id) throws NotFoundException {
        Movie movie = movieRepository.findOne(id);
        if (movie == null) {
            throw new NotFoundException("Movie with this id does not exist.");
        }
        return movie;
    }

    /**
     * @param id    of actor.
     * @param actor - Actor object, created from received JSON code.
     * @return Actor object.
     * @throws NotFoundException  and status code 404 if actor with this id does not exist.
     * @throws ForbiddenException and status code 403 if ids are not the same.
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/actors", produces = {"application/json"}, consumes = {"application/json"}, params = "id")
    public Actor editActor(@RequestParam(value = "id", required = true) int id, @RequestBody Actor actor) throws NotFoundException, ForbiddenException {
        if (actorRepository.findOne(id) == null) {
            throw new NotFoundException("Actor with this id does not exist.");
        }
        if (id != actor.getId()) {
            throw new ForbiddenException("Wrong object id.");
        }
        actorRepository.save(actor);
        return actorRepository.findOne(actor.getId());
    }

    /**
     * @param id    of movie.
     * @param movie - Movie object, created from received JSON code.
     * @return Movie object.
     * @throws ForbiddenException  and status code 403 if ids are not the same.
     * @throws BadRequestException and status code 400 if movie does not have any actor.
     * @throws NotFoundException   and status code 404 if movie with this id does not exist.
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/movies", produces = {"application/json"}, consumes = {"application/json"}, params = "id")
    public Movie editMovie(@RequestParam(value = "id", required = true) int id, @RequestBody Movie movie) throws ForbiddenException, BadRequestException, NotFoundException {
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
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/movies", produces = {"application/json"}, params = "category")
    public List<Movie> getSameCategoryMovies(@RequestParam(value = "category", required = true) MovieCategory category) {
        return movieRepository.findByCategory(category);
    }

    /**
     * @param available
     * @return list of Movie objects.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/movies", produces = {"application/json"}, params = "available")
    public List<Movie> displayAvailableMovies(@RequestParam(value = "available", required = true) boolean available) {
        if (available == false) {
            return movieRepository.findByOwnerIsNotNull();
        }
        return movieRepository.findByOwnerIsNull();
    }
}