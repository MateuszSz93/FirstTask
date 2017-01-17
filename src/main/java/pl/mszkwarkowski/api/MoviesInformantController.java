package pl.mszkwarkowski.api;

import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import pl.mszkwarkowski.movie.Actor;
import pl.mszkwarkowski.movie.Movie;
import pl.mszkwarkowski.movie.MovieCategory;
import pl.mszkwarkowski.other.Error;

import java.util.*;


@RestController
@EnableAutoConfiguration
public class MoviesInformantController {
    private MoviesInformantStorage moviesInformantStorage = new MoviesInformantStorage();

    @RequestMapping("/")
    String home() {
        return "Welcome in Movies REST API!";
    }

    /**
     * @return all movies.
     */
    @GetMapping(value = "/movies")
    public Collection getMoviesData() { return moviesInformantStorage.getMovies(); }

    /**
     * @return all actors.
     */
    @GetMapping(value = "/actors")
    public Collection getActorsData() { return moviesInformantStorage.getActors(); }

    /**
     * This method creates new actor. If actor with this id is already on the list, it will return null.
     *
     * @param actor - Actor object, created from received JSON code.
     * @return Actor object.
     */
    @PostMapping(value = "/actor")
    public Actor addActor(@RequestBody Actor actor) {
        if (moviesInformantStorage.getActor(actor.getId()) != null) {
            return null;
        }
        moviesInformantStorage.addActor(actor);
        return moviesInformantStorage.getActor(actor.getId());
    }

    /**
     * This method creates new movie. It also check if actors added to movie has existed earlier. In other case it checks if actor has unique id and if yes, it creates new actor.
     *
     * @return movie object.
     */
    @PostMapping(value = "/movie")
    public Movie addMovie(@RequestBody Movie movie) {
        if (moviesInformantStorage.getMovie(movie.getId()) != null) {
            return null;
        }

        List<Actor> actorsWithUniqueId = moviesInformantStorage.addActorToActorsList(movie.getActorList(), movie.getId());
        movie.setActorList(actorsWithUniqueId);
        if (actorsWithUniqueId.isEmpty()) {
            return null;
        }
        movie.setAvailable(true);
        moviesInformantStorage.addMovie(movie);
        return movie;
    }

    /**
     * This method deletes actor from the list of actors and from the movie were he/she was playing.
     *
     * @param id of actor
     * @return list of Actor objects.
     */
    @DeleteMapping(value = "/actor/{id}")
    public Collection deleteActor(@PathVariable int id) {
        if (moviesInformantStorage.getActor(id) != null) {
            moviesInformantStorage.deleteActor(id);
        }
        return moviesInformantStorage.getActors();
    }

    /**
     * This method deletes movie from the list of movies and from the map which contains ids of actors and ids of movies where they were playing.
     *
     * @param id of movie.
     * @return list of Movie objects.
     */
    @DeleteMapping(value = "/movie/{id}")
    public Collection deleteMovie(@PathVariable int id) {
        if (moviesInformantStorage.getMovie(id) != null) {
            moviesInformantStorage.deleteMovie(id);
        }
        return moviesInformantStorage.getMovies();
    }

    /**
     * @param id of actor.
     * @return Actor object.
     */
    @GetMapping(value = "/actor/{id}")
    public Actor actorData(@PathVariable int id) { return moviesInformantStorage.getActor(id); }

    /**
     * @param id of movie.
     * @return Movie object.
     */
    @GetMapping(value = "/movie/{id}")
    public Movie movieData(@PathVariable int id) { return moviesInformantStorage.getMovie(id); }

    /**
     * This method edits actor's values which has given id.
     *
     * @param id    of actor.
     * @param actor - Actor object, created from received JSON code.
     * @return Actor object.
     */
    @PutMapping(value = "/actor/{id}")
    public Actor editActor(@PathVariable int id, @RequestBody Actor actor) {
        if (moviesInformantStorage.getActor(id) == null || (id != actor.getId())) {
            return null;
        }
        moviesInformantStorage.editActor(id, actor);
        return actor;
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
        if (moviesInformantStorage.getMovie(id) == null || (id != movie.getId())) {
            return null;
        }

        List<Actor> actorsWithUniqueId = moviesInformantStorage.addActorToActorsList(movie.getActorList(), movie.getId());
        movie.setActorList(actorsWithUniqueId);
        if (actorsWithUniqueId.isEmpty()) {
            return null;
        }
        moviesInformantStorage.editMovie(id, movie);
        return movie;
    }

    /**
     * @param category of movies.
     * @return list of Movie objects.
     */
    @GetMapping(value = "moviesByCategory/{category}")
    public Collection getSameCategoryMovies(@PathVariable MovieCategory category) { return moviesInformantStorage.getMoviesByCategory(category); }

    /**
     * @return list of Movie objects.
     */
    @GetMapping(value = "availableMovies")
    public Collection displayAvailableMovies() { return moviesInformantStorage.getAvailableMovies(); }
}