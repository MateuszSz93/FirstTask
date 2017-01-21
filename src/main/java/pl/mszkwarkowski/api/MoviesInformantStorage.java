package pl.mszkwarkowski.api;

import pl.mszkwarkowski.movie.Actor;
import pl.mszkwarkowski.movie.ActorRepository;
import pl.mszkwarkowski.movie.Movie;

import java.util.*;

public class MoviesInformantStorage {
    public List<Actor> createUniqueActorsList(Movie movie, ActorRepository actorRepository) {
        List<Actor> actorsWithUniqueId = new ArrayList<>();
        for (Actor actor : movie.getActorList()) {
            if (!actorRepository.exists(actor.getId())) {
                actorRepository.save(actor);
                actorsWithUniqueId.add(actor);
            } else if (actorRepository.findOne(actor.getId()).getName().equals(actor.getName())) {
                actorsWithUniqueId.add(actor);
            }
        }
        return actorsWithUniqueId;
    }
}
