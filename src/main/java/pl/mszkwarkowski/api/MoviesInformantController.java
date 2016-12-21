package pl.mszkwarkowski.api;

import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import pl.mszkwarkowski.movie.Actor;
import pl.mszkwarkowski.movie.Movie;

import java.util.*;

/**
 * The "MoviesInformantController" is controller class. This class is responsible for processing user's requests.
 */
@RestController
@EnableAutoConfiguration
public class MoviesInformantController {
    private MoviesInformantStorage moviesInformantStorage = new MoviesInformantStorage();

    /**
     * @return simple string.
     */
    @RequestMapping("/")
    String home() {
        return "Welcome in Movies REST API!";
    }

    /**
     * This method returns all Movie's object. They are presented to user as one simple string in JSON format.
     *
     * @return collection of Movie's objects.
     * @throws Exception if there is not any movie in the collection.
     */
    @GetMapping(value = "/movies")
    public Collection getMoviesData() throws Exception {
        Collection<Movie> movieCollection = moviesInformantStorage.getMovies();
        if (movieCollection.isEmpty()) {
            throw new Exception("There is not any movie on the list.");
        }

        return movieCollection;
    }

    /**
     * This method returns all Actor's object. They are presented to user as one simple string in JSON format.
     *
     * @return collection of Actor's objects.
     * @throws Exception if there is not any actor in the collection.
     */
    @GetMapping(value = "/actors")
    public Collection getActorsData() throws Exception {
        Collection<Actor> actorCollection = moviesInformantStorage.getActors();
        if (actorCollection.isEmpty()) {
            throw new Exception("There is not any actor on the list.");
        }

        return actorCollection;
    }

    /**
     * This method creates new Actor object and add it to the HashMap.
     *
     * @param id of the actor.
     * @param name of the actor.
     * @return actor object.
     * @throws Exception if actor with this id is already on the list.
     */
    @PostMapping(value = "/newActor")
    public Actor addActor(@RequestHeader("id") int id, @RequestHeader("name") String name) throws Exception {
        Actor actor = new Actor(id, name);
        if (moviesInformantStorage.getActor(id) != null) {
            throw new Exception("Actor with this id is already on the list.");
        }

        moviesInformantStorage.addActor(actor);

        return moviesInformantStorage.getActor(id);
    }

    /**
     * This method creates new Movie object and add it to the HashMap.
     *
     * @param id of the movie.
     * @param title of the movie.
     * @param releaseData of the movie.
     * @param time of the movie.
     * @param type of the movie.
     * @param director's name.
     * @param actors Id's of actors given in one string, separated by comma.
     * @return movie object.
     * @throws Exception if movie with this id is already on the list.
     */
    @PostMapping(value = "/newMovie")
    public Movie addMovie(@RequestHeader("id") int id, @RequestHeader("title") String title, @RequestHeader("releaseData") String releaseData, @RequestHeader("time") int time, @RequestHeader("type") String type, @RequestHeader("director") String director, @RequestHeader("actors") String actors) throws Exception {

        if (moviesInformantStorage.getMovie(id) != null) {
            throw new Exception("Movie with this id is already on the list.");
        }

        List<String> listOfActorsId = Arrays.asList(actors.replaceAll("\\s+", "").split(","));
        List<Actor> actorList = moviesInformantStorage.addActorToActorList(id, listOfActorsId);

        if (actorList.size() == 0) {
            throw new Exception("Movie has to have at least one actor. Actor which you tried to add to movie probably does not exists. Please create actor first.");
        }

        Movie movie = new Movie(id, title, releaseData, time, type, director, actorList);
        moviesInformantStorage.addMovie(movie);

        return movie;
    }

    /**
     * This method returns the exception message.
     *
     * @param ex Exception.
     * @return Message contained in exception.
     */
    @ExceptionHandler(Exception.class)
    public String displayExceptionMessage(Exception ex) {
        return ex.getMessage();
    }

    /**
     * This method deletes actor from the list of actors and from the movie were he/she was playing.
     *
     * @param id of actor
     * @return list of Actor objects.
     * @throws Exception if actor with id given as parameter does not exists.
     */
    @DeleteMapping(value = "/deleteActor/{id}")
    public Collection deleteActor(@PathVariable int id) throws Exception {
        if (moviesInformantStorage.getActor(id) != null) {
            moviesInformantStorage.deleteActor(id);
        } else {
            throw new Exception("Actor can not be deleted, because actor with this id does not exists.");
        }
        return moviesInformantStorage.getActors();
    }

    /**
     * This method deletes movie from the list of movies and from the map which contains ids of actors and ids of movies where they were playing.
     *
     * @param id of movie.
     * @return list of Movie objects.
     * @throws Exception if movies with id given as parameter does not exists.
     */
    @DeleteMapping(value = "/deleteMovie/{id}")
    public Collection deleteMovie(@PathVariable int id) throws Exception {
        if (moviesInformantStorage.getMovie(id) != null) {
            moviesInformantStorage.deleteMovie(id);
        } else {
            throw new Exception("Movie can not be deleted because movie with this id does not exists.");
        }
        return moviesInformantStorage.getMovies();
    }

    /**
     * This method returns Actor object which has given id.
     *
     * @param id of actor.
     * @return Actor object.
     * @throws Exception if actor with id given as parameter does not exists.
     */
    @GetMapping(value = "/actor/{id}")
    public Actor actorData(@PathVariable int id) throws Exception {
        Actor actor = moviesInformantStorage.getActor(id);
        if (actor == null) {
            throw new Exception("Actor with this id does not exists.");
        }

        return actor;
    }

    /**
     * This method returns Movie object which has given id.
     *
     * @param id of movie.
     * @return Movie object.
     * @throws Exception if movie with id given as parameter does not exists.
     */
    @GetMapping(value = "/movie/{id}")
    public Movie movieData(@PathVariable int id) throws Exception {
        Movie movie = moviesInformantStorage.getMovie(id);
        if (movie == null) {
            throw new Exception("Movie with this id does not exists.");
        }

        return movie;
    }

    /**
     * This method edits values in Actor object which has given id.
     *
     * @param id of actor.
     * @param name of actor.
     * @return Actor object.
     * @throws Exception if actor with id given as parameter does not exists.
     */
    @PostMapping(value = "/editActor/{id}")
    public Actor editActor(@PathVariable int id, @RequestHeader(value = "name", required = false) String name) throws Exception {
        Actor actor = moviesInformantStorage.getActor(id);
        if (actor == null) {
            throw new Exception("Actor with this id does not exists.");
        }
        moviesInformantStorage.editActor(id, name);

        return actor;
    }

    /**
     * This method edits values in Movie object which has given id.
     *
     * @param id of movie.
     * @param title of movie.
     * @param releaseData of movie.
     * @param time of movie.
     * @param type of movie.
     * @param director of movie.
     * @param actors Id's of actors given in one string, separated by comma.
     * @return Movie object.
     * @throws Exception if movie with id given as parameter does not exists or if actor list is empty.
     */
    @PostMapping(value = "editMovie/{id}")
    public Movie editMovie(@PathVariable int id, @RequestHeader(value = "title", required = false) String title, @RequestHeader(value = "releaseData", required = false) String releaseData, @RequestHeader(value = "time", required = false) Integer time, @RequestHeader(value = "type", required = false) String type, @RequestHeader(value = "director", required = false) String director, @RequestHeader(value = "actors", required = false) String actors) throws Exception {
        Movie movie = moviesInformantStorage.getMovie(id);
        if (movie == null) {
            throw new Exception("Movie with this id does not exists.");
        }

        List<Actor> actorList = null;
        if (actors != null) {
            List<String> listOfActorsId = Arrays.asList(actors.replaceAll("\\s+", "").split(","));
            if (listOfActorsId.isEmpty()){
                throw new Exception("Movie has to have at least one actor. Actor which you tried to add to movie probably does not exists. Please create actor first.");
            }
            actorList = moviesInformantStorage.addActorToActorList(id, listOfActorsId);
        }
        moviesInformantStorage.editMovie(id, title, releaseData, time, type, director, actorList);
        return movie;
    }
}