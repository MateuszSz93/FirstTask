package pl.mszkwarkowski.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pl.mszkwarkowski.model.*;
import pl.mszkwarkowski.repository.*;

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

    @GetMapping(value = "/movies", produces = {"application/json", "application/xml"}, params = {"page", "limit"})
    public ResponseEntity<Page<Movie>> getMoviesWithPagination(@RequestParam(value = "page", required = true) int page, @RequestParam(value = "limit", required = false, defaultValue = "5") int limit) {
        PageRequest request = new PageRequest(page - 1, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(300, TimeUnit.SECONDS)).body(movieRepository.findAll(request));
    }

    /**
     * @return all actors.
     */
    @GetMapping(value = "/actors", produces = {"application/json"})
    public List<Actor> getActorsData() {
        return (List<Actor>) actorRepository.findAll();
    }

    /**
     * This method creates new actor. If actor with this id is already on the list, it will return null.
     *
     * @param actor - Actor object, created from received JSON code.
     * @return Actor object.
     */
    @PostMapping(value = "/actors", produces = {"application/json"}, consumes = {"application/json"})
    public Actor addActor(@RequestBody Actor actor) {
        if (actorRepository.findOne(actor.getId()) != null) {
            return null;
        }
        actorRepository.save(actor);
        return actorRepository.findOne(actor.getId());
    }

    /**
     * This method creates new movie. It also check if actors added to movie has existed earlier. In other case it checks if actor has unique id and if yes, it creates new actor.
     *
     * @return movie object.
     */
    @PostMapping(value = "/movies", produces = {"application/json"}, consumes = {"application/json"})
    public Movie addMovie(@RequestBody Movie movie) {
        if (movieRepository.findOne(movie.getId()) != null) {
            return null;
        }
        if (movie.getActorList().isEmpty()) {
            return null;
        }
        movie.setOwner(null);
        movieRepository.save(movie);
        return movieRepository.findOne(movie.getId());
    }

    /**
     * This method deletes actor from the list of actors and from the movie were he/she was playing.
     *
     * @param id of actor
     * @return list of Actor objects.
     */
    @DeleteMapping(value = "/actors", produces = {"application/json"}, params = "id")
    public List<Actor> deleteActor(@RequestParam(value = "id", required = true) int id) {
        if (actorRepository.findOne(id) != null) {
            actorRepository.delete(id);
        }
        return (List<Actor>) actorRepository.findAll();
    }

    /**
     * This method deletes movie from the list of movies and from the map which contains ids of actors and ids of movies where they were playing.
     *
     * @param id of movie.
     * @return list of Movie objects.
     */
    @DeleteMapping(value = "/movies", produces = {"application/json"}, params = "id")
    public List<Movie> deleteMovie(@RequestParam(value = "id", required = true) int id) {
        if (movieRepository.findOne(id) != null) {
            movieRepository.delete(id);
        }
        return (List<Movie>) movieRepository.findAll();
    }

    /**
     * @param id of actor.
     * @return Actor object.
     */
    @GetMapping(value = "/actors", produces = {"application/json"}, params = "id")
    public Actor actorData(@RequestParam(value = "id", required = true) int id) {
        return actorRepository.findOne(id);
    }

    /**
     * @param id of movie.
     * @return Movie object.
     */
    @GetMapping(value = "/movies", produces = {"application/json"}, params = "id")
    public Movie movieData(@RequestParam(value = "id", required = true) int id) {
        return movieRepository.findOne(id);
    }

    /**
     * This method edits actor's values which has given id.
     *
     * @param id    of actor.
     * @param actor - Actor object, created from received JSON code.
     * @return Actor object.
     */
    @PutMapping(value = "/actors", produces = {"application/json"}, consumes = {"application/json"}, params = "id")
    public Actor editActor(@RequestParam(value = "id", required = true) int id, @RequestBody Actor actor) {
        if (actorRepository.findOne(id) == null || (id != actor.getId())) {
            return null;
        }
        actorRepository.save(actor);
        return actorRepository.findOne(actor.getId());
    }

    /**
     * This method edits movie's values which has given id. It also check if actors added to movie has existed earlier. In other case it checks if actor has unique id and if yes, it creates new actor.
     *
     * @param id    of movie.
     * @param movie - Movie object, created from received JSON code.
     * @return Movie object.
     */
    @PutMapping(value = "/movies", produces = {"application/json"}, consumes = {"application/json"}, params = "id")
    public Movie editMovie(@RequestParam(value = "id", required = true) int id, @RequestBody Movie movie) {
        if (movieRepository.findOne(id) == null || (id != movie.getId())) {
            return null;
        }
        if (movie.getActorList().isEmpty()) {
            return null;
        }
        movie.setOwner(movieRepository.findOne(id).getOwner());
        movieRepository.save(movie);
        return movieRepository.findOne(movie.getId());
    }

    /**
     * @param category of movies.
     * @return list of Movie objects.
     */
    @GetMapping(value = "/movies", produces = {"application/json"}, params = "category")
    public List<Movie> getSameCategoryMovies(@RequestParam(value = "category", required = true) MovieCategory category) {
        return movieRepository.findByCategory(category);
    }

    /**
     * @return list of Movie objects.
     */
    @GetMapping(value = "/movies", produces = {"application/json"}, params = "available")
    public List<Movie> displayAvailableMovies(@RequestParam(value = "available", required = true) boolean available) {
        if (available == false) {
            return movieRepository.findByOwnerIsNotNull();
        }
        return movieRepository.findByOwnerIsNull();
    }
}