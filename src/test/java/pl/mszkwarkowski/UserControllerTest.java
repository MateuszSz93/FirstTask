package pl.mszkwarkowski;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.mszkwarkowski.api.UserController;
import pl.mszkwarkowski.data.Creator;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @InjectMocks
    private Creator creator;

    @Configuration
    @EnableAutoConfiguration
    public static class Config {
        @Bean
        public UserController apiController() { return new UserController(); }
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        creator.startingObjectsCreator();
    }

    @Test
    public void addUserTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/newUser").contentType(MediaType.APPLICATION_JSON).content("{\"id\":2,\"name\":\"Some New User\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Some New User"))
                .andExpect(jsonPath("$.debt").value(new BigDecimal(0)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("{\"id\":2,\"name\":\"Some New User\",\"debt\":0}", content);
    }

    @Test
    public void userMoviesTest() throws Exception {
        mockMvc.perform(get("/userMovies/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(8))
                .andExpect(jsonPath("$[0].title").value("The Departed"))
                .andExpect(jsonPath("$[1].id").value(13))
                .andExpect(jsonPath("$[1].title").value("Once Upon a Time in America"))
                .andExpect(jsonPath("$[2].id").value(24))
                .andExpect(jsonPath("$[2].title").value("Doctor Strange"))
                .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    public void rentMoviesTest() throws Exception {
        MvcResult result = mockMvc.perform(put("/rentMovies/{userId}/{moviesId}", 1, "1, 25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Kiler"))
                .andExpect(jsonPath("$[1].id").value(25))
                .andExpect(jsonPath("$[1].title").value("The Accountant"))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("[{\"id\":1,\"title\":\"Kiler\",\"releaseDate\":\"17-10-1997\",\"time\":104,\"type\":\"Comedy\",\"director\":\"Juliusz Machulski\",\"actorList\":[{\"id\":1,\"name\":\"Cezary Pazura\"},{\"id\":2,\"name\":\"Małgorzata Kożuchowska\"},{\"id\":3,\"name\":\"Jerzy Stuhr\"},{\"id\":4,\"name\":\"Janusz Rewiński\"}],\"available\":false,\"category\":\"HIT\"},{\"id\":25,\"title\":\"The Accountant\",\"releaseDate\":\"06-10-2016\",\"time\":128,\"type\":\"Drama\",\"director\":\"Gavin O'Connor\",\"actorList\":[{\"id\":55,\"name\":\"Anna Kendrick\"},{\"id\":56,\"name\":\"Ben Affleck\"}],\"available\":false,\"category\":\"NEW\"}]", content);
    }

    @Test
    public void returnMoviesTest() throws Exception {
        MvcResult result = mockMvc.perform(delete("/returnMovies/{userId}/{moviesId}", 1, "8, 13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(24))
                .andExpect(jsonPath("$[0].title").value("Doctor Strange"))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("[{\"id\":24,\"title\":\"Doctor Strange\",\"releaseDate\":\"20-10-2016\",\"time\":115,\"type\":\"Action\",\"director\":\"Scott Derrickson\",\"actorList\":[{\"id\":53,\"name\":\"Benedict Cumberbatch\"},{\"id\":54,\"name\":\"Rachel McAdams\"}],\"available\":false,\"category\":\"NEW\"}]", content);
    }

    @Test
    public void fourMoviesDiscountsTest() throws Exception {
        mockMvc.perform(post("/newUser").contentType(MediaType.APPLICATION_JSON).content("{\"id\":3,\"name\":\"New User\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("New User"))
                .andExpect(jsonPath("$.debt").value(new BigDecimal(0)));

        mockMvc.perform(put("/rentMovies/{userId}/{moviesId}", 3, "1, 2, 12, 25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Kiler"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Poranek kojota"))
                .andExpect(jsonPath("$[2].id").value(12))
                .andExpect(jsonPath("$[2].title").value("Fight Club"))
                .andExpect(jsonPath("$[3].id").value(25))
                .andExpect(jsonPath("$[3].title").value("The Accountant"))
                .andExpect(jsonPath("$.*", hasSize(4)));

        mockMvc.perform(get("/user/{id}", 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("New User"))
                .andExpect(jsonPath("$.debt").value(34.85));
    }

    @Test
    public void twoNewDiscountsTest() throws Exception {
        mockMvc.perform(post("/newUser").contentType(MediaType.APPLICATION_JSON).content("{\"id\":4,\"name\":\"Next New User\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("Next New User"))
                .andExpect(jsonPath("$.debt").value(new BigDecimal(0)));

        mockMvc.perform(put("/rentMovies/{userId}/{moviesId}", 4, "27, 28"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(27))
                .andExpect(jsonPath("$[0].title").value("Hacksaw Ridge"))
                .andExpect(jsonPath("$[1].id").value(28))
                .andExpect(jsonPath("$[1].title").value("Arrival"))
                .andExpect(jsonPath("$.*", hasSize(2)));

        mockMvc.perform(get("/user/{id}", 4))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("Next New User"))
                .andExpect(jsonPath("$.debt").value(22.43));
    }
}
