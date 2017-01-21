package pl.mszkwarkowski.movie;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Spencer on 20.01.2017.
 */
@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {
    List<Movie> findMoviesByCategory(@Param("category") MovieCategory category);
    List<Movie> findMoviesByOwner(@Param("owner") Integer owner);
}
