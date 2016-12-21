package pl.mszkwarkowski.api;

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
     * This method returns all Movie's object which are in MOVIES.
     *
     * @return collection of Movie's objects.
     */
    public Collection<Movie> getMovies() {
        return MOVIES.values();
    }

    /**
     * This method returns Movie object which has given id.
     *
     * @param movieId Id of the movie.
     * @return Movie object.
     */
    public Movie getMovie(int movieId) {
        return MOVIES.get(movieId);
    }

    /**
     * This method adds new object to MOVIES.
     *
     * @param movie Movie object.
     */
    public void addMovie(Movie movie) {
        MOVIES.put(movie.getId(), movie);
    }

    /**
     * This method returns all Actor's object which are in ACTORS.
     *
     * @return collection of Actor's object.
     */
    public Collection<Actor> getActors() {
        return ACTORS.values();
    }

    /**
     * This method returns Actor object which has given id.
     *
     * @param actorId Id of the actor.
     * @return Actor object.
     */
    public Actor getActor(int actorId) {
        return ACTORS.get(actorId);
    }

    /**
     * This method adds new object to ACTORS.
     *
     * @param actor
     */
    public void addActor(Actor actor) {
        ACTORS.put(actor.getId(), actor);
    }

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
     * This method edits values in Actor object which has given id.
     *
     * @param actorId Id of actor.
     * @param name of actor.
     */
    public void editActor(int actorId, String name) {
        Actor actor = ACTORS.get(actorId);
        if (name != null) {
            actor.setName(name);
        }
    }

    /**
     * This method edits values in Movie object which has given id.
     *
     * @param movieId Id of movie.
     * @param title of movie.
     * @param releaseData of movie.
     * @param time of movie.
     * @param type of movie.
     * @param director of movie.
     * @param actorList List of actor's id.
     */
    public void editMovie(int movieId, String title, String releaseData, Integer time, String type, String director, List<Actor> actorList) {
        Movie movie = MOVIES.get(movieId);
        if (title != null) {
            movie.setTitle(title);
        }
        if (releaseData != null) {
            movie.setReleaseData(title);
        }
        if (time != null) {
            movie.setTime(time);
        }
        if (type != null) {
            movie.setType(type);
        }
        if (director != null) {
            movie.setDirector(director);
        }
        if (actorList != null) {
            List<Actor> currentActors = movie.getActorList();
            for (Actor actor : currentActors) {
                if (!actorList.contains(actor)) {
                    List<Integer> moviesOfActorList = MOVIES_OF_ACTOR.get(actor.getId());
                    moviesOfActorList.remove(Integer.valueOf(movieId));
                    MOVIES_OF_ACTOR.put(actor.getId(), moviesOfActorList);
                }
            }
            for (Actor actor : actorList) {
                if (!currentActors.contains(actor)) {
                    addMovieToActor(actor.getId(), movieId);
                }
            }
            movie.setActorList(actorList);
        }
    }

    /**
     * This method creates list of Actor objects for each movie. This get list of String and later find the Actor object for each id given in string.
     *
     * @param id of movie.
     * @param listOfActorsId String which contains ids of actors.
     * @return
     */
    public List<Actor> addActorToActorList(int id, List<String> listOfActorsId) {
        List<Actor> actorList = new ArrayList<Actor>();
        for (String actorId : listOfActorsId) {
            MoviesInformantStorage moviesInformantStorage = new MoviesInformantStorage();
            Actor actor = moviesInformantStorage.getActor(Integer.parseInt(actorId));
            if (actor != null && !actorList.contains(actor)) {
                actorList.add(actor);
                moviesInformantStorage.addMovieToActor(actor.getId(), id);
            }
        }
        return actorList;
    }
}
