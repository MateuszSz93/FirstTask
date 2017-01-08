package pl.mszkwarkowski;

import org.junit.*;
import org.junit.Test;
import org.mockito.InjectMocks;
import pl.mszkwarkowski.api.MoviesInformantStorage;
import pl.mszkwarkowski.movie.Actor;
import pl.mszkwarkowski.movie.Movie;
import pl.mszkwarkowski.movie.MovieCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by Spencer on 18.12.2016.
 */
public class MoviesInformantStorageTest {
    @InjectMocks
    private MoviesInformantStorage moviesInformantStorage;

    @Before
    public void setup() {
        moviesInformantStorage = mock(MoviesInformantStorage.class);
    }

    @Test
    public void getActorsTest() {
        Actor actor = new Actor(1, "John Doe");
        Actor actor1 = new Actor(2, "Jane Doe");
        List<Actor> actors = new ArrayList<>();
        actors.add(actor);
        actors.add(actor1);

        when(moviesInformantStorage.getActors()).thenReturn(actors);
        Collection result = moviesInformantStorage.getActors();

        verify(moviesInformantStorage).getActors();
        verify(moviesInformantStorage, times(1)).getActors();

        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.contains(actor), true);
    }

    @Test
    public void getMoviesTest() {
        Actor actor = new Actor(1, "John Doe");
        Actor actor1 = new Actor(2, "Jane Doe");
        List<Actor> actors = new ArrayList<>();
        actors.add(actor);
        actors.add(actor1);

        Movie movie = new Movie(1, "First Title", "11-10-2015", 120, "Comedy", "First director", actors, MovieCategory.OTHER);
        Movie movie1 = new Movie(2, "Second Title", "04-01-1999", 186, "Action", "Second director", actors, MovieCategory.HIT);
        List<Movie> movies = new LinkedList();
        movies.add(movie);
        movies.add(movie1);

        when(moviesInformantStorage.getMovies()).thenReturn(movies);
        Collection result = moviesInformantStorage.getMovies();

        verify(moviesInformantStorage).getMovies();
        verify(moviesInformantStorage, times(1)).getMovies();

        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.contains(movie), true);
        Assert.assertEquals(result.iterator().next().getClass(), Movie.class);
    }

    @Test
    public void actorTests() {
        Actor actor = new Actor(1, "John Doe");
        Actor actor1 = new Actor(2, "Jane Doe");

        MoviesInformantStorage moviesInformantStorage1 = new MoviesInformantStorage();
        moviesInformantStorage1.addActor(actor);
        moviesInformantStorage1.addActor(actor1);

        Assert.assertEquals(moviesInformantStorage1.getActor(1), actor);
        Assert.assertEquals(moviesInformantStorage1.getActor(2).getName(), "Jane Doe");
        Assert.assertEquals(moviesInformantStorage1.getActors().size(), 2);

        Actor editedActor = new Actor(1, "Jack Doe");
        moviesInformantStorage1.editActor(1, editedActor);
        Assert.assertEquals(moviesInformantStorage1.getActor(1).getName(), "Jack Doe");
    }

    @Test
    public void moviesTests() {
        Actor actor = new Actor(1, "John Doe");
        Actor actor1 = new Actor(2, "Jane Doe");
        List<Actor> actorList = new ArrayList<>();
        Movie movie = new Movie(1, "First movie", "11-12-2015", 123, "Action", "First director", actorList, MovieCategory.NEW);
        Movie movie1 = new Movie(2, "Second movie", "01-02-1995", 182, "Comedy", "Second director", actorList, MovieCategory.HIT);

        MoviesInformantStorage moviesInformantStorage1 = new MoviesInformantStorage();
        moviesInformantStorage1.addMovie(movie);
        moviesInformantStorage1.addMovie(movie1);

        Assert.assertEquals(moviesInformantStorage1.getMovie(1), movie);
        Assert.assertEquals(moviesInformantStorage1.getMovie(2).getTitle(), "Second movie");
        Assert.assertEquals(moviesInformantStorage1.getMovies().size(), 2);

        Assert.assertEquals(moviesInformantStorage1.getMovie(1).getTime(), 123);
        Movie editedMovie = new Movie(1, "Other title", "11-12-2015", 111, "Action", "First director", actorList, MovieCategory.NEW);
        moviesInformantStorage1.editMovie(1, editedMovie);
        Assert.assertEquals(moviesInformantStorage1.getMovie(1).getTitle(), "Other title");
        Assert.assertEquals(moviesInformantStorage1.getMovie(1).getTime(), 111);
        Assert.assertEquals(moviesInformantStorage1.getMoviesByCategory(MovieCategory.HIT).size(), 1);
        Assert.assertEquals(moviesInformantStorage1.getAvailableMovies().size(), 2);
        movie1.setAvailable(false);
        Assert.assertEquals(moviesInformantStorage1.getAvailableMovies().size(), 1);
    }
}
