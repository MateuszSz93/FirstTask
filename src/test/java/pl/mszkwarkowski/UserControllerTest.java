package pl.mszkwarkowski;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.mszkwarkowski.api.*;
import pl.mszkwarkowski.controller.UserController;
import pl.mszkwarkowski.model.*;

import java.util.*;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MoviesInformant.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @Test
    public void addUserTest() throws Exception {
        User user = userController.addUser(new User(2, "Some User"));
        assertEquals(new Integer(2), user.getId());
        assertEquals("Some User", user.getName());
        assertEquals(new BigDecimal(0), user.getDebt());
    }

    @Test
    public void userMoviesTest() throws Exception {
        List<Movie> movieList = userController.userMovies(1);
        assertEquals(new Integer(8), movieList.get(0).getId());
        assertEquals("The Departed", movieList.get(0).getTitle());
        assertEquals(new Integer(24), movieList.get(2).getId());
        assertEquals("Doctor Strange", movieList.get(2).getTitle());
        assertEquals(3, movieList.size());
    }

    @Test
    public void rentMoviesTest() throws Exception {
        BigDecimal payment = userController.rentMovies(1, new int[]{1, 25});
        assertEquals(new BigDecimal("24.90"), payment);
    }

    @Test
    public void returnMoviesTest() throws Exception {
        List<Movie> movieList = userController.returnMovies(1, new int[]{8, 13});
        assertEquals(new Integer(24), movieList.get(0).getId());
        assertEquals("Doctor Strange", movieList.get(0).getTitle());
        assertEquals(1, movieList.size());
    }

    @Test
    public void fourMoviesDiscountsTest() throws Exception {
        User user = userController.addUser(new User(3, "New User"));
        assertEquals(new BigDecimal("0"), user.getDebt());
        BigDecimal payment = userController.rentMovies(3, new int[]{2, 3, 18, 29});
        assertEquals(new BigDecimal("34.85"), payment);
    }

    @Test
    public void twoNewDiscountsTest() throws Exception {
        BigDecimal payment = userController.rentMovies(1, new int[]{28, 30});
        assertEquals(new BigDecimal("22.43"), payment);
    }
}
