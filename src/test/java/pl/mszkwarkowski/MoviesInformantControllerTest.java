package pl.mszkwarkowski;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import pl.mszkwarkowski.api.MoviesInformantController;
import pl.mszkwarkowski.data.Creator;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class MoviesInformantControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @InjectMocks
    private MoviesInformantController moviesInformantController;

    @InjectMocks
    private Creator creator;

    @Configuration
    @EnableAutoConfiguration
    public static class Config {
        @Bean
        public MoviesInformantController apiController() {
            return new MoviesInformantController();
        }
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(moviesInformantController).build();

        creator.startingObjectsCreator();
    }

    @Test
    public void getMoviesDataTest() throws Exception {
        mockMvc.perform(get("/movies").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Kiler"))
                .andExpect(jsonPath("$[0].releaseDate").value("17-10-1997"))
                .andExpect(jsonPath("$[0].time").value(104))
                .andExpect(jsonPath("$[0].type").value("Comedy"))
                .andExpect(jsonPath("$[0].director").value("Juliusz Machulski"))
                .andExpect(jsonPath("$[0].actorList", hasSize(4)))
                .andExpect(jsonPath("[0].actorList[*].name", Matchers.containsInAnyOrder("Cezary Pazura", "Małgorzata Kożuchowska", "Jerzy Stuhr", "Janusz Rewiński")))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Poranek kojota"))
                .andExpect(jsonPath("$[1].releaseDate").value("24-08-2001"))
                .andExpect(jsonPath("$[1].time").value(92))
                .andExpect(jsonPath("$[1].type").value("Comedy"))
                .andExpect(jsonPath("$[1].director").value("Olaf Lubaszenko"))
                .andExpect(jsonPath("$[1].actorList", hasSize(4)))
                .andExpect(jsonPath("[1].actorList[*].name", Matchers.containsInAnyOrder("Maciej Stuhr", "Michał Milowicz", "Karolina Rosińska", "Edward Linde-Lubaszenko")));
    }

    @Test
    public void getActorsDataTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/actors").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Cezary Pazura"))
                .andExpect(jsonPath("$.*", hasSize(8)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("[{\"id\":1,\"name\":\"Cezary Pazura\"},{\"id\":2,\"name\":\"Małgorzata Kożuchowska\"},{\"id\":3,\"name\":\"Jerzy Stuhr\"},{\"id\":4,\"name\":\"Janusz Rewiński\"},{\"id\":5,\"name\":\"Maciej Stuhr\"},{\"id\":6,\"name\":\"Karolina Rosińska\"},{\"id\":7,\"name\":\"Michał Milowicz\"},{\"id\":8,\"name\":\"Edward Linde-Lubaszenko\"}]", content);
    }

    @Test
    public void addActorTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/newActor").accept(MediaType.APPLICATION_JSON).header("id", 9).header("name", "Tadeusz Huk"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(9))
                .andExpect(jsonPath("$.name").value("Tadeusz Huk"))
                .andReturn();;

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("{\"id\":9,\"name\":\"Tadeusz Huk\"}", content);
    }

    @Test
    public void addMovieTest() throws Exception{
        mockMvc.perform(post("/newMovie").accept(MediaType.APPLICATION_JSON).header("id", 3).header("title", "Some Title").header("releaseDate", "18-12-2016").header("time", 100).header("type", "Action").header("director", "Some Director").header("actors", "1, 2, 8, 9"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("actorList[*].name", Matchers.containsInAnyOrder("Tadeusz Huk", "Cezary Pazura", "Małgorzata Kożuchowska", "Edward Linde-Lubaszenko")))
                .andExpect(jsonPath("$.title").value("Some Title"))
                .andExpect(jsonPath("$.time").value(100));
    }

    @Test
    public void actorDataTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/actor/{id}", 1).accept(MediaType.APPLICATION_JSON).header("id", 3).header("title", "Some Title").header("releaseDate", "18-12-2016").header("time", 100).header("type", "Action").header("director", "Some Director").header("actors", "1, 2, 8, 9"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cezary Pazura"))
                .andReturn();;

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("{\"id\":1,\"name\":\"Cezary Pazura\"}", content);
    }

    @Test
    public void movieDataTest() throws Exception {
        mockMvc.perform(get("/movie/{id}", 2).accept(MediaType.APPLICATION_JSON).header("id", 3).header("title", "Some Title").header("releaseDate", "18-12-2016").header("time", 100).header("type", "Action").header("director", "Some Director").header("actors", "1, 2, 8, 9"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("Poranek kojota"))
                .andExpect(jsonPath("$.actorList[*].id", Matchers.containsInAnyOrder(5, 6, 7, 8)))
                .andExpect(jsonPath("$.director").value("Olaf Lubaszenko"));
    }

    @Test
    public void deleteActorTest() throws Exception{
        mockMvc.perform(delete("/deleteActor/{id}", 7).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[*].name", Matchers.containsInAnyOrder("Tadeusz Huk", "Cezary Pazura", "Małgorzata Kożuchowska", "Edward Linde-Lubaszenko", "Jerzy Stuhr", "Maciej Stuhr", "Karolina Rosińska", "Janusz Rewiński")))
                .andExpect(jsonPath("$.*", hasSize(8)));

        MvcResult result = mockMvc.perform(delete("/deleteActor/{id}", 2).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[*].name", Matchers.containsInAnyOrder("Tadeusz Huk", "Cezary Pazura", "Edward Linde-Lubaszenko", "Jerzy Stuhr", "Maciej Stuhr", "Karolina Rosińska", "Janusz Rewiński")))
                .andExpect(jsonPath("$.*", hasSize(7)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("[{\"id\":1,\"name\":\"Cezary Pazura\"},{\"id\":3,\"name\":\"Jerzy Stuhr\"},{\"id\":4,\"name\":\"Janusz Rewiński\"},{\"id\":5,\"name\":\"Maciej Stuhr\"},{\"id\":6,\"name\":\"Karolina Rosińska\"},{\"id\":8,\"name\":\"Edward Linde-Lubaszenko\"},{\"id\":9,\"name\":\"Tadeusz Huk\"}]", content);
    }

    @Test
    public void deleteMovieTest() throws Exception {
        mockMvc.perform(delete("/deleteMovie/{id}", 2).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[*].title", Matchers.containsInAnyOrder("Kiler", "Some Title")))
                .andExpect(jsonPath("$[*].id", Matchers.containsInAnyOrder(1, 3)))
                .andExpect(jsonPath("$.*", hasSize(2)));

       MvcResult result = mockMvc.perform(delete("/deleteMovie/{id}", 3).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[*].title", Matchers.containsInAnyOrder("Kiler")))
                .andExpect(jsonPath("$[*].id", Matchers.containsInAnyOrder(1)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("[{\"id\":1,\"title\":\"Kiler\",\"releaseDate\":\"17-10-1997\",\"time\":104,\"type\":\"Comedy\",\"director\":\"Juliusz Machulski\",\"actorList\":[{\"id\":1,\"name\":\"Cezary Pazura\"},{\"id\":2,\"name\":\"Małgorzata Kożuchowska\"},{\"id\":3,\"name\":\"Jerzy Stuhr\"},{\"id\":4,\"name\":\"Janusz Rewiński\"}]}]", content);
    }

    @Test
    public void editActorTest() throws Exception{
        MvcResult result = mockMvc.perform(post("/editActor/{id}", 1).accept(MediaType.APPLICATION_JSON).header("name", "Jason Statham"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jason Statham"))
                .andReturn();;

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("{\"id\":1,\"name\":\"Jason Statham\"}", content);
    }

    @Test
    public void editMovieTest() throws Exception{
        MvcResult result = mockMvc.perform(post("/editMovie/{id}", 1).accept(MediaType.APPLICATION_JSON).header("title", "Some New Title").header("time", 321).header("actors", "1, 5, 8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("actorList[*].name", Matchers.containsInAnyOrder("Cezary Pazura", "Maciej Stuhr", "Edward Linde-Lubaszenko")))
                .andExpect(jsonPath("$.title").value("Some New Title"))
                .andExpect(jsonPath("$.time").value(321))
                .andReturn();;

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("{\"id\":1,\"title\":\"Some New Title\",\"releaseDate\":\"17-10-1997\",\"time\":321,\"type\":\"Comedy\",\"director\":\"Juliusz Machulski\",\"actorList\":[{\"id\":1,\"name\":\"Cezary Pazura\"},{\"id\":5,\"name\":\"Maciej Stuhr\"},{\"id\":8,\"name\":\"Edward Linde-Lubaszenko\"}]}", content);
    }
}
