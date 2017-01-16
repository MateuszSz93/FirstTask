package pl.mszkwarkowski;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import pl.mszkwarkowski.api.UserInformantStorage;
import pl.mszkwarkowski.movie.Actor;
import pl.mszkwarkowski.movie.Movie;
import pl.mszkwarkowski.movie.MovieCategory;
import pl.mszkwarkowski.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserInformantStorageTest {
    @InjectMocks
    private UserInformantStorage userInformantStorage;

    @Before
    public void setup() {
        userInformantStorage = mock(UserInformantStorage.class);
    }

    @Test
    public void userTest() throws Exception{
        Actor actor = new Actor(1, "John Doe");
        Actor actor1 = new Actor(2, "Jane Doe");
        List<Actor> actors = new ArrayList<>();
        actors.add(actor);
        actors.add(actor1);
        Movie movie = new Movie(1, "First Title", "11-10-2015", 120, "Comedy", "First director", actors, MovieCategory.OTHER);
        Movie movie1 = new Movie(2, "Second Title", "04-01-1999", 186, "Action", "Second director", actors, MovieCategory.HIT);
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        movies.add(movie1);

        User user = new User(1, "Some User");
        userInformantStorage.addUser(user);

        when(userInformantStorage.getUserMovies(1)).thenReturn(movies);
        Collection result = userInformantStorage.getUserMovies(1);

        verify(userInformantStorage).getUserMovies(1);
        verify(userInformantStorage, times(1)).getUserMovies(1);

        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.contains(movie), true);

        Movie movie2 = new Movie(3, "Third Title", "07-11-1994", 112, "Action", "Third director", actors, MovieCategory.OTHER);
        BigDecimal payment = new BigDecimal("4.95");
        BigDecimal paymentResult = null;

        when(userInformantStorage.rentMovies(1, new int[]{3})).thenReturn(payment);
        paymentResult = userInformantStorage.rentMovies(1, new int[]{3});

        verify(userInformantStorage).rentMovies(1, new int[]{3});
        verify(userInformantStorage, times(1)).rentMovies(1, new int[]{3});
        Assert.assertEquals(result.contains(movie2), false);
    }
}
