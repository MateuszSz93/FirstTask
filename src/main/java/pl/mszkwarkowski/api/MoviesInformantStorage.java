package pl.mszkwarkowski.api;

import org.springframework.beans.BeanUtils;
import pl.mszkwarkowski.movie.Actor;
import pl.mszkwarkowski.movie.Movie;

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
     * This method removes the actor from ACTORS and MOVIES_OF_ACTOR. It also delete the actor from all movies where he was playing.
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
     * This method removes the movie from MOVIES. It also remove the movie from moviesList, which is value in MOVIES_OF_ACTOR.
     *
     * @param id of movie
     */
    public void deleteMovie(int id) {
        Movie movie = MOVIES.get(id);
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
     * This method adds id of movie to moviesList, which is value in MOVIES_OF_ACTOR.
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
     * This method takes this list of Actors which play in the movie. For each "Actor" object it checks if "Actor" with this id already exists. If not, it add him to Actors List. After that, it checks if given Actor object has the same name like Actor object about the same id on the list. If yes, it adds movie id and actor id to MOVIES_OF_ACTOR map.
     *
     * @param actorList
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
}
