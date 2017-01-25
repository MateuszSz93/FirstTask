package pl.mszkwarkowski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mszkwarkowski.model.*;

import java.util.List;

/**
 * Created by Spencer on 20.01.2017.
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByCategory(MovieCategory category);
    List<Movie> findByOwner(User owner);
    List<Movie> findByOwnerIsNull();
    List<Movie> findByOwnerIsNotNull();
}
