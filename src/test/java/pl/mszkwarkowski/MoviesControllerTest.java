package pl.mszkwarkowski;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.mszkwarkowski.api.*;
import pl.mszkwarkowski.controller.ActorsController;
import pl.mszkwarkowski.controller.MoviesController;
import pl.mszkwarkowski.model.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MoviesInformant.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MoviesControllerTest extends Mockito {
    @Autowired
    MoviesController moviesController;
    @Autowired
    ActorsController actorsController;

    @Test
    public void getMoviesDataTest() throws Exception {
        List<Movie> movieList = moviesController.getMoviesData().getBody();
        assertEquals(30, movieList.size());
        assertEquals(new Integer(1), movieList.get(0).getId());
        assertEquals("Kiler", movieList.get(0).getTitle());
        assertEquals("17-10-1997", movieList.get(0).getReleaseDate());
        assertEquals(new Integer(104), movieList.get(0).getDuration());
        assertEquals("Comedy", movieList.get(0).getType());
        assertEquals("Juliusz Machulski", movieList.get(0).getDirector());
        assertEquals(4, movieList.get(0).getActorList().size());
        assertEquals("Cezary Pazura", movieList.get(0).getActorList().get(0).getName());
        assertEquals(null, movieList.get(0).getOwner());
        assertEquals(MovieCategory.HIT, movieList.get(0).getCategory());
        assertEquals(new Integer(10), movieList.get(9).getId());
        assertEquals("Shutter Island", movieList.get(9).getTitle());
        assertEquals("Martin Scorsese", movieList.get(9).getDirector());
        assertEquals("Steven Spielberg", movieList.get(10).getDirector());
        assertEquals(MovieCategory.OTHER, movieList.get(13).getCategory());
        assertEquals("The Bourne Identity", movieList.get(16).getTitle());
        assertEquals("12-10-2016", movieList.get(20).getReleaseDate());
        assertEquals("Thriller", movieList.get(20).getType());
    }

    @Test
    public void addMovieTest() throws Exception {
        List<Actor> actorList = Arrays.asList(actorsController.actorData(1), actorsController.actorData(2));
        Movie movie = moviesController.addNewMovie(new Movie(31, "Some Title", "18-12-2016", 100, "Action", "Some Director", actorList, MovieCategory.HIT));
        assertEquals(new Integer(31), movie.getId());
        assertEquals("Some Title", movie.getTitle());
        assertEquals(MovieCategory.HIT, movie.getCategory());
        assertEquals("Cezary Pazura", movie.getActorList().get(0).getName());
        assertEquals(Actor.class, movie.getActorList().get(0).getClass());
        assertEquals(Movie.class, movie.getClass());
    }

    @Test
    public void movieDataTest() throws Exception {
        Movie movie = moviesController.movieData(2);
        assertEquals(new Integer(2), movie.getId());
        assertEquals("Poranek kojota", movie.getTitle());
        assertEquals("Olaf Lubaszenko", movie.getDirector());
        assertEquals("Maciej Stuhr", movie.getActorList().get(0).getName());
    }

    @Test
    public void deleteMovieTest() throws Exception {
        List<Movie> movieList = moviesController.deleteMovie(2);
        assertEquals(new Integer(24), movieList.get(22).getId());
        assertEquals("Doctor Strange", movieList.get(22).getTitle());
        assertEquals(29, movieList.size());

        movieList = moviesController.deleteMovie(3);
        assertEquals(new Integer(25), movieList.get(22).getId());
        assertEquals("The Accountant", movieList.get(22).getTitle());
        assertEquals(28, movieList.size());
    }

    @Test
    public void editMovieTest() throws Exception {
        List<Actor> actorList = Arrays.asList(actorsController.actorData(1), actorsController.actorData(5), actorsController.actorData(8));
        Movie movie = moviesController.editMovie(1, new Movie(1, "Some New Title", "17-10-1997", 321, "Comedy", "Juliusz Machulski", actorList, MovieCategory.HIT));
        assertEquals(new Integer(1), movie.getId());
        assertEquals("Maciej Stuhr", movie.getActorList().get(1).getName());
        assertEquals("Some New Title", movie.getTitle());
        assertEquals(MovieCategory.HIT, movie.getCategory());
    }

    @Test
    public void getSameCategoryMoviesTest() throws Exception {
        List<Movie> movieList = moviesController.getSameCategoryMovies(MovieCategory.NEW);
        assertEquals(new Integer(21), movieList.get(0).getId());
        assertEquals("Inferno", movieList.get(0).getTitle());
        assertEquals(new Integer(25), movieList.get(4).getId());
        assertEquals("The Accountant", movieList.get(4).getTitle());
        assertEquals(new Integer(30), movieList.get(9).getId());
        assertEquals("War Dogs", movieList.get(9).getTitle());
        assertEquals(10, movieList.size());
    }

    @Test
    public void displayAvailableMoviesTest() throws Exception {
        List<Movie> movieList = moviesController.displayAvailableMovies(true);
        assertEquals(new Integer(12), movieList.get(10).getId());
        assertEquals("Fight Club", movieList.get(10).getTitle());
        assertEquals(null, movieList.get(10).getOwner());
        assertEquals("Quentin Tarantino", movieList.get(12).getDirector());
        assertEquals(null, movieList.get(15).getOwner());
        assertEquals(null, movieList.get(19).getOwner());
        assertEquals(null, movieList.get(23).getOwner());
        assertEquals(27, movieList.size());
    }
}
