package pl.mszkwarkowski.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import pl.mszkwarkowski.movie.Movie;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * The "User" class represents an user object. It has various attributes of users.
 */
@Entity
public class User {
    @Id
    @NotNull
    private int id;
    @NotNull
    private String name;
    private BigDecimal debt;

    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<Movie> moviesList;

    public User(){};

    public User(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getDebt() { return debt; }

    public void setDebt(BigDecimal debt) { this.debt = debt; }

    public List<Movie> getMoviesList() { return moviesList; }

    public void setMoviesList(List<Movie> moviesList) { this.moviesList = moviesList; }
}
