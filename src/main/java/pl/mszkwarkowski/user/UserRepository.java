package pl.mszkwarkowski.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Spencer on 20.01.2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
