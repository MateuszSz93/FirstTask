package pl.mszkwarkowski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mszkwarkowski.model.Movie;
import pl.mszkwarkowski.model.MovieCategory;
import pl.mszkwarkowski.model.User;

import java.util.List;

/**
 * Created by Spencer on 20.01.2017.
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findMoviesByCategory(@Param("category") MovieCategory category);
    List<Movie> findMoviesByOwner(@Param("owner") User owner);
}
