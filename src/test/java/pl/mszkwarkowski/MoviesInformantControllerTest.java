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
                .andExpect(jsonPath("$.*", hasSize(30)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Kiler"))
                .andExpect(jsonPath("$[0].releaseDate").value("17-10-1997"))
                .andExpect(jsonPath("$[0].time").value(104))
                .andExpect(jsonPath("$[0].type").value("Comedy"))
                .andExpect(jsonPath("$[0].director").value("Juliusz Machulski"))
                .andExpect(jsonPath("$[0].actorList", hasSize(4)))
                .andExpect(jsonPath("$[0].actorList[*].name", Matchers.containsInAnyOrder("Cezary Pazura", "Małgorzata Kożuchowska", "Jerzy Stuhr", "Janusz Rewiński")))
                .andExpect(jsonPath("$[0].available").value(true))
                .andExpect(jsonPath("$[0].category").value("HIT"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Poranek kojota"))
                .andExpect(jsonPath("$[1].releaseDate").value("24-08-2001"))
                .andExpect(jsonPath("$[1].time").value(92))
                .andExpect(jsonPath("$[1].type").value("Comedy"))
                .andExpect(jsonPath("$[1].director").value("Olaf Lubaszenko"))
                .andExpect(jsonPath("$[1].actorList", hasSize(4)))
                .andExpect(jsonPath("$[1].actorList[*].name", Matchers.containsInAnyOrder("Maciej Stuhr", "Michał Milowicz", "Karolina Rosińska", "Edward Linde-Lubaszenko")))
                .andExpect(jsonPath("$[1].available").value(true))
                .andExpect(jsonPath("$[1].category").value("HIT"))
                .andExpect(jsonPath("$[9].id").value(10))
                .andExpect(jsonPath("$[9].title").value("Shutter Island"))
                .andExpect(jsonPath("$[9].director").value("Martin Scorsese"))
                .andExpect(jsonPath("$[10].director").value("Steven Spielberg"))
                .andExpect(jsonPath("$[11].actorList[*].name", Matchers.containsInAnyOrder("Edward Norton", "Brad Pitt", "Helena Bonham Carter")))
                .andExpect(jsonPath("$[13].title").value("The Dark Knight"))
                .andExpect(jsonPath("$[13].actorList[*].id", Matchers.containsInAnyOrder(33, 34, 35, 64)))
                .andExpect(jsonPath("$[13].category").value("OTHER"))
                .andExpect(jsonPath("$[16].id").value(17))
                .andExpect(jsonPath("$[16].title").value("The Bourne Identity"))
                .andExpect(jsonPath("$[20].releaseDate").value("12-10-2016"))
                .andExpect(jsonPath("$[20].type").value("Thriller"))
                .andExpect(jsonPath("$[28].id").value(29))
                .andExpect(jsonPath("$[28].title").value("Sully"))
                .andExpect(jsonPath("$[28].director").value("Clint Eastwood"));
    }

    @Test
    public void getActorsDataTest() throws Exception {
        mockMvc.perform(get("/actors").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Cezary Pazura"))
                .andExpect(jsonPath("$[12].id").value("13"))
                .andExpect(jsonPath("$[12].name").value("Marlon Brando"))
                .andExpect(jsonPath("$[18].id").value("19"))
                .andExpect(jsonPath("$[18].name").value("Joe Pesci"))
                .andExpect(jsonPath("$[27].id").value("28"))
                .andExpect(jsonPath("$[27].name").value("Ben Kingsley"))
                .andExpect(jsonPath("$[32].id").value("33"))
                .andExpect(jsonPath("$[32].name").value("Christian Bale"))
                .andExpect(jsonPath("$[38].id").value("39"))
                .andExpect(jsonPath("$[38].name").value("Amy Smart"))
                .andExpect(jsonPath("$[43].id").value("44"))
                .andExpect(jsonPath("$[43].name").value("Tom Hanks"))
                .andExpect(jsonPath("$[49].id").value("50"))
                .andExpect(jsonPath("$[49].name").value("Shailene Woodley"))
                .andExpect(jsonPath("$[60].id").value("61"))
                .andExpect(jsonPath("$[60].name").value("Amy Adams"))
                .andExpect(jsonPath("$[66].id").value("67"))
                .andExpect(jsonPath("$[66].name").value("Bradley Cooper"))
                .andExpect(jsonPath("$.*", hasSize(68)));
    }

    @Test
    public void addActorTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/actor").contentType(MediaType.APPLICATION_JSON).content("{\"id\":69,\"name\":\"Tadeusz Huk\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(69))
                .andExpect(jsonPath("$.name").value("Tadeusz Huk"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("{\"id\":69,\"name\":\"Tadeusz Huk\"}", content);
    }

    @Test
    public void addMovieTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/movie").contentType(MediaType.APPLICATION_JSON).content("{\"id\":31,\"title\":\"Some Title\",\"releaseDate\":\"18-12-2016\",\"time\":100,\"type\":\"Action\",\"director\":\"Some Director\",\"actorList\":[{\"id\":1,\"name\":\"Cezary Pazura\"},{\"id\":2,\"name\":\"Małgorzata Kożuchowska\"},{\"id\":8,\"name\":\"Edward Linde-Lubaszenko\"},{\"id\":9,\"name\":\"Actor With This Id Already Exists\"},{\"id\":70,\"name\":\"Somebody New\"}],\"category\":\"HIT\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(31))
                .andExpect(jsonPath("actorList[*].name", Matchers.containsInAnyOrder("Cezary Pazura", "Małgorzata Kożuchowska", "Edward Linde-Lubaszenko", "Somebody New")))
                .andExpect(jsonPath("$.title").value("Some Title"))
                .andExpect(jsonPath("$.time").value(100))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.category").value("HIT"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("{\"id\":31,\"title\":\"Some Title\",\"releaseDate\":\"18-12-2016\",\"time\":100,\"type\":\"Action\",\"director\":\"Some Director\",\"actorList\":[{\"id\":1,\"name\":\"Cezary Pazura\"},{\"id\":2,\"name\":\"Małgorzata Kożuchowska\"},{\"id\":8,\"name\":\"Edward Linde-Lubaszenko\"},{\"id\":70,\"name\":\"Somebody New\"}],\"available\":true,\"category\":\"HIT\"}", content);
    }

    @Test
    public void actorDataTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/actor/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cezary Pazura"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("{\"id\":1,\"name\":\"Cezary Pazura\"}", content);
    }

    @Test
    public void movieDataTest() throws Exception {
        mockMvc.perform(get("/movie/{id}", 2).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("Poranek kojota"))
                .andExpect(jsonPath("$.actorList[*].id", Matchers.containsInAnyOrder(5, 6, 7, 8)))
                .andExpect(jsonPath("$.director").value("Olaf Lubaszenko"));
    }

    @Test
    public void deleteActorTest() throws Exception {
        mockMvc.perform(delete("/actor/{id}", 7).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[20].id").value("22"))
                .andExpect(jsonPath("$[20].name").value("Jack Nicholson"))
                .andExpect(jsonPath("$.*", hasSize(69)));

        mockMvc.perform(delete("/actor/{id}", 2).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[20].id").value("23"))
                .andExpect(jsonPath("$[20].name").value("Mark Wahlberg"))
                .andExpect(jsonPath("$.*", hasSize(68)));
    }

    @Test
    public void deleteMovieTest() throws Exception {
        mockMvc.perform(delete("/movie/{id}", 2).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[22].id").value(24))
                .andExpect(jsonPath("$[22].title").value("Doctor Strange"))
                .andExpect(jsonPath("$.*", hasSize(30)));

        mockMvc.perform(delete("/movie/{id}", 3).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[22].id").value(25))
                .andExpect(jsonPath("$[22].title").value("The Accountant"))
                .andExpect(jsonPath("$.*", hasSize(29)));
    }

    @Test
    public void editActorTest() throws Exception {
        MvcResult result = mockMvc.perform(put("/actor/{id}", 1).contentType(MediaType.APPLICATION_JSON).content("{\"id\":1,\"name\":\"Jason Statham\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jason Statham"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("{\"id\":1,\"name\":\"Jason Statham\"}", content);
    }

    @Test
    public void editMovieTest() throws Exception {
        MvcResult result = mockMvc.perform(put("/movie/{id}", 1).contentType(MediaType.APPLICATION_JSON).content("{\"id\":1,\"title\":\"Some New Title\",\"releaseDate\":\"17-10-1997\",\"time\":321,\"type\":\"Comedy\",\"director\":\"Juliusz Machulski\",\"actorList\":[{\"id\":1,\"name\":\"Cezary Pazura\"},{\"id\":5,\"name\":\"Maciej Stuhr\"},{\"id\":8,\"name\":\"Edward Linde-Lubaszenko\"}],\"category\":\"HIT\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("actorList[*].name", Matchers.containsInAnyOrder("Cezary Pazura", "Maciej Stuhr", "Edward Linde-Lubaszenko")))
                .andExpect(jsonPath("$.title").value("Some New Title"))
                .andExpect(jsonPath("$.time").value(321))
                .andExpect(jsonPath("$.category").value("HIT"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("{\"id\":1,\"title\":\"Some New Title\",\"releaseDate\":\"17-10-1997\",\"time\":321,\"type\":\"Comedy\",\"director\":\"Juliusz Machulski\",\"actorList\":[{\"id\":1,\"name\":\"Cezary Pazura\"},{\"id\":5,\"name\":\"Maciej Stuhr\"},{\"id\":8,\"name\":\"Edward Linde-Lubaszenko\"}],\"available\":true,\"category\":\"HIT\"}", content);
    }

    @Test
    public void getSameCategoryMoviesTest() throws Exception {
        mockMvc.perform(get("/moviesByCategory/NEW"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(21))
                .andExpect(jsonPath("$[0].title").value("Inferno"))
                .andExpect(jsonPath("$[4].id").value(25))
                .andExpect(jsonPath("$[4].title").value("The Accountant"))
                .andExpect(jsonPath("$[9].id").value(30))
                .andExpect(jsonPath("$[9].title").value("War Dogs"))
                .andExpect(jsonPath("$.*", hasSize(10)));
    }

    @Test
    public void displayAvailableMoviesTest() throws Exception {
        mockMvc.perform(get("/availableMovies").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[10].id").value(12))
                .andExpect(jsonPath("$[10].title").value("Fight Club"))
                .andExpect(jsonPath("$[12].director").value("Quentin Tarantino"))
                .andExpect(jsonPath("$[1].available").value(true))
                .andExpect(jsonPath("$[7].available").value(true))
                .andExpect(jsonPath("$[13].available").value(true))
                .andExpect(jsonPath("$[19].available").value(true))
                .andExpect(jsonPath("$[22].available").value(true))
                .andExpect(jsonPath("$[27].available").value(true))
                .andExpect(jsonPath("$.*", hasSize(28)));
    }
}
