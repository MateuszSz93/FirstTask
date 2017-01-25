package pl.mszkwarkowski;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.mszkwarkowski.api.*;
import pl.mszkwarkowski.controller.MoviesInformantController;
import pl.mszkwarkowski.model.Actor;
import pl.mszkwarkowski.model.Movie;
import pl.mszkwarkowski.model.MovieCategory;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MoviesInformant.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MoviesInformantControllerTest extends Mockito {
    @Autowired
    MoviesInformantController moviesInformantController;

    @Test
    public void t00GetMoviesData() throws Exception {
        List<Movie> movieList = moviesInformantController.getMoviesData().getBody();
        assertEquals(30, movieList.size());
        assertEquals(1, movieList.get(0).getId());
        assertEquals("Kiler", movieList.get(0).getTitle());
        assertEquals("17-10-1997", movieList.get(0).getReleaseDate());
        assertEquals(104, movieList.get(0).getDuration());
        assertEquals("Comedy", movieList.get(0).getType());
        assertEquals("Juliusz Machulski", movieList.get(0).getDirector());
        assertEquals(4, movieList.get(0).getActorList().size());
        assertEquals("Cezary Pazura", movieList.get(0).getActorList().get(0).getName());
        assertEquals(null, movieList.get(0).getOwner());
        assertEquals(MovieCategory.HIT, movieList.get(0).getCategory());
        assertEquals(10, movieList.get(9).getId());
        assertEquals("Shutter Island", movieList.get(9).getTitle());
        assertEquals("Martin Scorsese", movieList.get(9).getDirector());
        assertEquals("Steven Spielberg", movieList.get(10).getDirector());
        assertEquals(MovieCategory.OTHER, movieList.get(13).getCategory());
        assertEquals("The Bourne Identity", movieList.get(16).getTitle());
        assertEquals("12-10-2016", movieList.get(20).getReleaseDate());
        assertEquals("Thriller", movieList.get(20).getType());
    }

    @Test
    public void t01GetActorsData() throws Exception {
        List<Actor> actorList = moviesInformantController.getActorsData();
        assertEquals(68, actorList.size());
        assertEquals(1, actorList.get(0).getId());
        assertEquals("Cezary Pazura", actorList.get(0).getName());
        assertEquals(13, actorList.get(12).getId());
        assertEquals("Marlon Brando", actorList.get(12).getName());
        assertEquals(19, actorList.get(18).getId());
        assertEquals("Joe Pesci", actorList.get(18).getName());
        assertEquals(28, actorList.get(27).getId());
        assertEquals("Ben Kingsley", actorList.get(27).getName());
        assertEquals(33, actorList.get(32).getId());
        assertEquals("Christian Bale", actorList.get(32).getName());
        assertEquals(39, actorList.get(38).getId());
        assertEquals("Amy Smart", actorList.get(38).getName());
        assertEquals(61, actorList.get(60).getId());
        assertEquals("Amy Adams", actorList.get(60).getName());
    }

    @Test
    public void t02AddActor() throws Exception {
        Actor actor = moviesInformantController.addActor(new Actor(69, "Tadeusz Huk"));
        assertEquals(69, actor.getId());
        assertEquals("Tadeusz Huk", actor.getName());
        assertEquals(Actor.class, actor.getClass());
    }

    @Test
    public void t03AddMovie() throws Exception {
        List<Actor> actorList = Arrays.asList(moviesInformantController.actorData(1), moviesInformantController.actorData(2));
        Movie movie = moviesInformantController.addMovie(new Movie(31, "Some Title", "18-12-2016", 100, "Action", "Some Director", actorList, MovieCategory.HIT));
        assertEquals(31, movie.getId());
        assertEquals("Some Title", movie.getTitle());
        assertEquals(MovieCategory.HIT, movie.getCategory());
        assertEquals("Cezary Pazura", movie.getActorList().get(0).getName());
        assertEquals(Actor.class, movie.getActorList().get(0).getClass());
        assertEquals(Movie.class, movie.getClass());
    }

    @Test
    public void t04ActorData() throws Exception {
        Actor actor = moviesInformantController.actorData(1);
        assertEquals(1, actor.getId());
        assertEquals("Cezary Pazura", actor.getName());
        assertEquals(Actor.class, actor.getClass());
    }

    @Test
    public void t05MovieData() throws Exception {
        Movie movie = moviesInformantController.movieData(2);
        assertEquals(2, movie.getId());
        assertEquals("Poranek kojota", movie.getTitle());
        assertEquals("Olaf Lubaszenko", movie.getDirector());
        assertEquals("Maciej Stuhr", movie.getActorList().get(0).getName());
    }

    @Test
    public void t06DeleteActor() throws Exception {
        List<Actor> actorList = moviesInformantController.deleteActor(7);
        assertEquals(22, actorList.get(20).getId());
        assertEquals("Jack Nicholson", actorList.get(20).getName());
        assertEquals(68, actorList.size());

        actorList = moviesInformantController.deleteActor(2);
        assertEquals(23, actorList.get(20).getId());
        assertEquals("Mark Wahlberg", actorList.get(20).getName());
        assertEquals(67, actorList.size());
    }

    @Test
    public void t07DeleteMovie() throws Exception {
        List<Movie> movieList = moviesInformantController.deleteMovie(2);
        assertEquals(24, movieList.get(22).getId());
        assertEquals("Doctor Strange", movieList.get(22).getTitle());
        assertEquals(30, movieList.size());

        movieList = moviesInformantController.deleteMovie(3);
        assertEquals(25, movieList.get(22).getId());
        assertEquals("The Accountant", movieList.get(22).getTitle());
        assertEquals(29, movieList.size());
    }

    @Test
    public void t08EditActor() throws Exception {
        Actor actor = moviesInformantController.editActor(1, new Actor(1, "Jason Statham"));
        assertEquals(1, actor.getId());
        assertEquals("Jason Statham", actor.getName());
    }

    @Test
    public void t09EditMovie() throws Exception {
        List<Actor> actorList = Arrays.asList(moviesInformantController.actorData(1), moviesInformantController.actorData(5), moviesInformantController.actorData(8));
        Movie movie = moviesInformantController.editMovie(1, new Movie(1, "Some New Title", "17-10-1997", 321, "Comedy", "Juliusz Machulski", actorList, MovieCategory.HIT));
        assertEquals(1, movie.getId());
        assertEquals("Maciej Stuhr", movie.getActorList().get(1).getName());
        assertEquals("Some New Title", movie.getTitle());
        assertEquals(MovieCategory.HIT, movie.getCategory());
    }

    @Test
    public void t10GetSameCategoryMovies() throws Exception {
        List<Movie> movieList = moviesInformantController.getSameCategoryMovies(MovieCategory.NEW);
        assertEquals(21, movieList.get(0).getId());
        assertEquals("Inferno", movieList.get(0).getTitle());
        assertEquals(25, movieList.get(4).getId());
        assertEquals("The Accountant", movieList.get(4).getTitle());
        assertEquals(30, movieList.get(9).getId());
        assertEquals("War Dogs", movieList.get(9).getTitle());
        assertEquals(10, movieList.size());
    }

    @Test
    public void t11DisplayAvailableMovies() throws Exception {
        List<Movie> movieList = moviesInformantController.displayAvailableMovies();
        assertEquals(15, movieList.get(10).getId());
        assertEquals("Inglourious Basterds", movieList.get(10).getTitle());
        assertEquals(null, movieList.get(10).getOwner());
        assertEquals("Doug Liman", movieList.get(12).getDirector());
        assertEquals(null, movieList.get(15).getOwner());
        assertEquals(null, movieList.get(19).getOwner());
        assertEquals(null, movieList.get(23).getOwner());
        assertEquals(26, movieList.size());
    }
}
