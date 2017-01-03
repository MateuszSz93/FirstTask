package pl.mszkwarkowski.data;

import pl.mszkwarkowski.api.MoviesInformantStorage;
import pl.mszkwarkowski.movie.Actor;
import pl.mszkwarkowski.movie.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class contains method, which create objects of various types when starting the application.
 */
public class Creator {
    /**
     * This method create actors/movies and add them to the collections.
     */
    public void startingObjectsCreator() {
        MoviesInformantStorage moviesInformantStorage = new MoviesInformantStorage();
        Actor actor = new Actor(1, "Cezary Pazura");
        Actor actor1 = new Actor(2, "Małgorzata Kożuchowska");
        Actor actor2 = new Actor(3, "Jerzy Stuhr");
        Actor actor3 = new Actor(4, "Janusz Rewiński");
        Actor actor4 = new Actor(5, "Maciej Stuhr");
        Actor actor5 = new Actor(6, "Karolina Rosińska");
        Actor actor6 = new Actor(7, "Michał Milowicz");
        Actor actor7 = new Actor(8, "Edward Linde-Lubaszenko");

        List<Actor> actorList = new ArrayList<>();
        actorList.addAll(Arrays.asList(actor, actor1, actor2, actor3));
        Movie movie = new Movie(1, "Kiler", "17-10-1997", 104, "Comedy", "Juliusz Machulski", actorList);

        List<Actor> actorList1 = new ArrayList<>();
        actorList1.addAll(Arrays.asList(actor4, actor5, actor6, actor7));
        Movie movie1 = new Movie(2, "Poranek kojota", "24-08-2001", 92, "Comedy", "Olaf Lubaszenko", actorList1);

        moviesInformantStorage.addMovie(movie);
        moviesInformantStorage.addMovie(movie1);
        moviesInformantStorage.addActor(actor);
        moviesInformantStorage.addActor(actor1);
        moviesInformantStorage.addActor(actor2);
        moviesInformantStorage.addActor(actor3);
        moviesInformantStorage.addActor(actor4);
        moviesInformantStorage.addActor(actor5);
        moviesInformantStorage.addActor(actor6);
        moviesInformantStorage.addActor(actor7);
        moviesInformantStorage.addMovieToActor(actor.getId(), movie.getId());
        moviesInformantStorage.addMovieToActor(actor1.getId(), movie.getId());
        moviesInformantStorage.addMovieToActor(actor2.getId(), movie.getId());
        moviesInformantStorage.addMovieToActor(actor3.getId(), movie.getId());
        moviesInformantStorage.addMovieToActor(actor4.getId(), movie1.getId());
        moviesInformantStorage.addMovieToActor(actor5.getId(), movie1.getId());
        moviesInformantStorage.addMovieToActor(actor6.getId(), movie1.getId());
        moviesInformantStorage.addMovieToActor(actor7.getId(), movie1.getId());
    }
}
