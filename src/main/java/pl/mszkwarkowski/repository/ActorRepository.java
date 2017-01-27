package pl.mszkwarkowski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mszkwarkowski.model.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
