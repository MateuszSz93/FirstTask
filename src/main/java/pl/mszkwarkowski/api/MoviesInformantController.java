package pl.mszkwarkowski.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import pl.mszkwarkowski.movie.*;

import java.util.*;

@RestController
@EnableAutoConfiguration
public class MoviesInformantController {
    private MoviesInformantStorage moviesInformantStorage = new MoviesInformantStorage();
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
    @GetMapping(value = "/movies")
    public List<Movie> getMoviesData() {
        return (List<Movie>) movieRepository.findAll();
    }

    /**
     * @return all actors.
     */
    @GetMapping(value = "/actors")
    public List<Actor> getActorsData() {
        return (List<Actor>) actorRepository.findAll();
    }

    /**
     * This method creates new actor. If actor with this id is already on the list, it will return null.
     *
     * @param actor - Actor object, created from received JSON code.
     * @return Actor object.
     */
    @PostMapping(value = "/actor")
    public Actor addActor(@RequestBody Actor actor) {
        return saveActor(actor);
    }

    public Actor saveActor(@RequestBody Actor actor) {
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
    @PostMapping(value = "/movie")
    public Movie addMovie(@RequestBody Movie movie) {
        if (movieRepository.findOne(movie.getId()) != null) {
            return null;
        }
        List<Actor> actorsWithUniqueId = moviesInformantStorage.createUniqueActorsList(movie, actorRepository);
        movie.setActorList(actorsWithUniqueId);
        if (actorsWithUniqueId.isEmpty()) {
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
    @DeleteMapping(value = "/actor/{id}")
    public List<Actor> deleteActor(@PathVariable int id) {
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
    @DeleteMapping(value = "/movie/{id}")
    public List<Movie> deleteMovie(@PathVariable int id) {
        if (movieRepository.findOne(id) != null) {
            movieRepository.delete(id);
        }
        return (List<Movie>) movieRepository.findAll();
    }

    /**
     * @param id of actor.
     * @return Actor object.
     */
    @GetMapping(value = "/actor/{id}")
    public Actor actorData(@PathVariable int id) {
        return actorRepository.findOne(id);
    }

    /**
     * @param id of movie.
     * @return Movie object.
     */
    @GetMapping(value = "/movie/{id}")
    public Movie movieData(@PathVariable int id) {
        return movieRepository.findOne(id);
    }

    /**
     * This method edits actor's values which has given id.
     *
     * @param id    of actor.
     * @param actor - Actor object, created from received JSON code.
     * @return Actor object.
     */
    @PutMapping(value = "/actor/{id}")
    public Actor editActor(@PathVariable int id, @RequestBody Actor actor) {
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
    @PutMapping(value = "movie/{id}")
    public Movie editMovie(@PathVariable int id, @RequestBody Movie movie) {
        if (movieRepository.findOne(id) == null || (id != movie.getId())) {
            return null;
        }
        List<Actor> actorsWithUniqueId = moviesInformantStorage.createUniqueActorsList(movie, actorRepository);
        movie.setActorList(actorsWithUniqueId);
        if (actorsWithUniqueId.isEmpty()) {
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
    @GetMapping(value = "moviesByCategory/{category}")
    public List<Movie> getSameCategoryMovies(@PathVariable MovieCategory category) {
        return movieRepository.findMoviesByCategory(category);
    }

    /**
     * @return list of Movie objects.
     */
    @GetMapping(value = "availableMovies")
    public List<Movie> displayAvailableMovies() {
        return movieRepository.findMoviesByOwner(null);
    }
}