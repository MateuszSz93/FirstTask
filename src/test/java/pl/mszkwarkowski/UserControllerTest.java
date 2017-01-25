package pl.mszkwarkowski;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.mszkwarkowski.api.*;
import pl.mszkwarkowski.controller.UserController;
import pl.mszkwarkowski.model.Movie;
import pl.mszkwarkowski.model.User;

import java.util.*;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MoviesInformant.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @Test
    public void t0AddUser() throws Exception {
        User user = userController.addUser(new User(2, "Some User"));
        assertEquals(2, user.getId());
        assertEquals("Some User", user.getName());
        assertEquals(new BigDecimal(0), user.getDebt());
    }

    @Test
    public void t1UserMovies() throws Exception {
        List<Movie> movieList = userController.userMovies(1);
        assertEquals(8, movieList.get(0).getId());
        assertEquals("The Departed", movieList.get(0).getTitle());
        assertEquals(24, movieList.get(2).getId());
        assertEquals("Doctor Strange", movieList.get(2).getTitle());
        assertEquals(3, movieList.size());
    }

    @Test
    public void t2RentMovies() throws Exception {
        BigDecimal payment = userController.rentMovies(1, new int[]{1, 25});
        assertEquals(new BigDecimal("24.90"), payment);
    }

    @Test
    public void t3ReturnMovies() throws Exception {
        List<Movie> movieList = userController.returnMovies(1, new int[]{8, 13});
        assertEquals(1, movieList.get(0).getId());
        assertEquals("Kiler", movieList.get(0).getTitle());
        assertEquals(3, movieList.size());
    }

    @Test
    public void t4FourMoviesDiscountsTest() throws Exception {
        User user = userController.addUser(new User(3, "New User"));
        assertEquals(new BigDecimal("0"), user.getDebt());
        BigDecimal payment = userController.rentMovies(3, new int[]{2, 3, 18, 29});
        assertEquals(new BigDecimal("34.85"), payment);
    }

    @Test
    public void t5TwoNewDiscountsTest() throws Exception {
        BigDecimal payment = userController.rentMovies(1, new int[]{28, 30});
        assertEquals(new BigDecimal("22.43"), payment);
    }
}
