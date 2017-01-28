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
import pl.mszkwarkowski.model.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MoviesInformant.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ActorsControllerTest extends Mockito {
    @Autowired
    ActorsController actorsController;

    @Test
    public void getActorsDataTest() throws Exception {
        List<Actor> actorList = actorsController.getActorsData();
        assertEquals(68, actorList.size());
        assertEquals(new Integer(1), actorList.get(0).getId());
        assertEquals("Cezary Pazura", actorList.get(0).getName());
        assertEquals(new Integer(13), actorList.get(12).getId());
        assertEquals("Marlon Brando", actorList.get(12).getName());
        assertEquals(new Integer(19), actorList.get(18).getId());
        assertEquals("Joe Pesci", actorList.get(18).getName());
        assertEquals(new Integer(28), actorList.get(27).getId());
        assertEquals("Ben Kingsley", actorList.get(27).getName());
        assertEquals(new Integer(33), actorList.get(32).getId());
        assertEquals("Christian Bale", actorList.get(32).getName());
        assertEquals(new Integer(39), actorList.get(38).getId());
        assertEquals("Amy Smart", actorList.get(38).getName());
        assertEquals(new Integer(61), actorList.get(60).getId());
        assertEquals("Amy Adams", actorList.get(60).getName());
    }

    @Test
    public void addActorTest() throws Exception {
        Actor actor = actorsController.addNewActor(new Actor(69, "Tadeusz Huk"));
        assertEquals(new Integer(69), actor.getId());
        assertEquals("Tadeusz Huk", actor.getName());
        assertEquals(Actor.class, actor.getClass());
    }

    @Test
    public void actorDataTest() throws Exception {
        Actor actor = actorsController.actorData(1);
        assertEquals(new Integer(1), actor.getId());
        assertEquals("Cezary Pazura", actor.getName());
        assertEquals(Actor.class, actor.getClass());
    }

    @Test
    public void deleteActorTest() throws Exception {
        List<Actor> actorList = actorsController.deleteActor(7);
        assertEquals(new Integer(22), actorList.get(20).getId());
        assertEquals("Jack Nicholson", actorList.get(20).getName());
        assertEquals(67, actorList.size());

        actorList = actorsController.deleteActor(2);
        assertEquals(new Integer(23), actorList.get(20).getId());
        assertEquals("Mark Wahlberg", actorList.get(20).getName());
        assertEquals(66, actorList.size());
    }

    @Test
    public void editActorTest() throws Exception {
        Actor actor = actorsController.editActor(1, new Actor(1, "Jason Statham"));
        assertEquals(new Integer(1), actor.getId());
        assertEquals("Jason Statham", actor.getName());
    }
}
