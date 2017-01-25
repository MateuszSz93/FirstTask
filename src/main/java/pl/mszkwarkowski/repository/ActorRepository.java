package pl.mszkwarkowski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mszkwarkowski.model.Actor;

/**
 * Created by Spencer on 20.01.2017.
 */
@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
