package pl.mszkwarkowski.api;

import org.springframework.beans.BeanUtils;
import pl.mszkwarkowski.movie.Actor;
import pl.mszkwarkowski.movie.Movie;
import pl.mszkwarkowski.movie.MovieCategory;

import java.util.*;

/**
 * This class manages the actors and movies collections. It also is responsible for editing Actors and Movies objects.
 */
public class MoviesInformantStorage {
    /**
     * Map, where the key is id of movie and value is Movie object with the given id.
     */
    private static final Map<Integer, Movie> MOVIES = new HashMap<>();
    /**
     * Map, where the key is id of actor and value is Actor object with the given id.
     */
    private static final Map<Integer, Actor> ACTORS = new HashMap<>();
    /**
     * Map, where the key is id of actor and value is list of movies's id in which the actor with the given id was playing.
     */
    private static final Map<Integer, List<Integer>> MOVIES_OF_ACTOR = new HashMap<>();

    /**
     * @return collection of Movie's objects.
     */
    public Collection<Movie> getMovies() {
        return MOVIES.values();
    }

    /**
     * @param movieId Id of the movie.
     * @return Movie object.
     */
    public Movie getMovie(int movieId) {
        return MOVIES.get(movieId);
    }

    /**
     * @param movie Movie object.
     */
    public void addMovie(Movie movie) { MOVIES.put(movie.getId(), movie); }

    /**
     * @return collection of Actor's object.
     */
    public Collection<Actor> getActors() {
        return ACTORS.values();
    }

    /**
     * @param actorId Id of the actor.
     * @return Actor object.
     */
    public Actor getActor(int actorId) {
        return ACTORS.get(actorId);
    }

    /**
     * @param actor
     */
    public void addActor(Actor actor) { ACTORS.put(actor.getId(), actor); }

    /**
     * This method removes the actor.
     *
     * @param id of the actor.
     */
    public void deleteActor(int id) {
        Actor actor = ACTORS.get(id);
        List<Integer> moviesList = MOVIES_OF_ACTOR.get(id);

        if (moviesList.size() > 0) {
            for (int movieId : moviesList) {
                Movie movie = MOVIES.get(movieId);
                List<Actor> actorList = movie.getActorList();
                actorList.remove(actor);
                movie.setActorList(actorList);
            }
        }
        MOVIES_OF_ACTOR.remove(id);
        ACTORS.remove(id);
    }

    /**
     * This method removes the movie.
     *
     * @param id of movie
     */
    public void deleteMovie(int id) {
        Movie movie = MOVIES.get(id);
        UserInformantStorage userInformantStorage = new UserInformantStorage();
        if (!movie.isAvailable()) {
            userInformantStorage.removeMovieFromUsersList(id);
        }

        List<Actor> actorList = movie.getActorList();
        if (actorList.size() > 0) {
            for (Actor actor : actorList) {
                List<Integer> moviesList = MOVIES_OF_ACTOR.get(actor.getId());
                moviesList.remove(Integer.valueOf(id));
                MOVIES_OF_ACTOR.put(actor.getId(), moviesList);
            }
        }
        MOVIES.remove(id);
    }

    /**
     * This method adds id of movie to moviesList to connect the movie with the actor.
     *
     * @param actorId Id of the actor.
     * @param movieId Id of the movie.
     */
    public void addMovieToActor(int actorId, int movieId) {
        List<Integer> moviesList = MOVIES_OF_ACTOR.get(actorId);
        if (moviesList != null) {
            if (!moviesList.contains(movieId)) {
                moviesList.add(movieId);
                MOVIES_OF_ACTOR.put(actorId, moviesList);
            }
        } else {
            moviesList = new ArrayList<>();
            moviesList.add(movieId);
            MOVIES_OF_ACTOR.put(actorId, moviesList);
        }
    }

    /**
     * This method copies values from "Actor" object given as parameter to "Actor" object on the list, which has the same id like in parameter.
     *
     * @param id    of the actor.
     * @param actor is Actor object which has new data for Actor object which already exists on the list.
     */
    public void editActor(int id, Actor actor) {
        BeanUtils.copyProperties(actor, ACTORS.get(id));
    }

    /**
     * This method copies values from "Movie" object given as parameter to "Movie" object on the list, which has the same id like in parameter.
     *
     * @param id    of the movie
     * @param movie is Movie object which has new data for Movie object which already exists on the list.
     */
    public void editMovie(int id, Movie movie) {
        movie.setAvailable(MOVIES.get(id).isAvailable());
        List<Actor> currentActors = MOVIES.get(id).getActorList();
        for (Actor actor : currentActors) {
            if (!movie.getActorList().contains(actor)) {
                List<Integer> moviesOfActorList = MOVIES_OF_ACTOR.get(actor.getId());
                moviesOfActorList.remove(Integer.valueOf(id));
                MOVIES_OF_ACTOR.put(actor.getId(), moviesOfActorList);
            }
        }
        for (Actor actor : movie.getActorList()) {
            if (!currentActors.contains(actor)) {
                addMovieToActor(actor.getId(), id);
            }
        }
        movie.setActorList(movie.getActorList());
        BeanUtils.copyProperties(movie, MOVIES.get(id));
    }

    /**
     * This method takes this list of actors which play in the movie. For each actor it checks if actor with this id already exists. If not, it creates new actor. After that, it checks if given actor has the same name as actor with the same id on the list. If yes, it connects the movie with the actor.
     *
     * @param actorList - List of Actor objects.
     * @param movieId
     * @return actorList List of actors id which play in the movie whose id is given as parameter.
     */
    public List<Actor> addActorToActorsList(List<Actor> actorList, int movieId) {
        List<Actor> actorsWithUniqueId = new ArrayList<>();
        for (Actor actor : actorList) {
            if (!ACTORS.containsKey(actor.getId())) {
                addActor(actor);
            }
            if (ACTORS.get(actor.getId()).getName().equals(actor.getName())) {
                addMovieToActor(actor.getId(), movieId);
                actorsWithUniqueId.add(actor);
            }
        }
        return actorsWithUniqueId;
    }

    /**
     * This method returns movies which have the same category.
     *
     * @param category of movies.
     * @return list of Movie objects.
     */
    public List<Movie> getMoviesByCategory(MovieCategory category) {
        List<Movie> sameCategoryMovies = new ArrayList<>();
        for (Movie movie : MOVIES.values()) {
            if (movie.getCategory().equals(category)) {
                sameCategoryMovies.add(movie);
            }
        }
        return sameCategoryMovies;
    }

    /**
     * This method returns available movies.
     *
     * @return list of Movie objects.
     */
    public List<Movie> getAvailableMovies() {
        List<Movie> sameAvailabilityMovies = new ArrayList<>();
        for (Movie movie : MOVIES.values()) {
            if (movie.isAvailable()) {
                sameAvailabilityMovies.add(movie);
            }
        }
        return sameAvailabilityMovies;
    }
}
