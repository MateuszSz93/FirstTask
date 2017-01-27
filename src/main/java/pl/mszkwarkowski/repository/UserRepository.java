package pl.mszkwarkowski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mszkwarkowski.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
