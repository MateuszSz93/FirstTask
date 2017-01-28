package pl.mszkwarkowski.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * The "User" class represents an user object. It has various attributes of users.
 */
@ApiModel
@Entity
public class User {
    @Id
    @NotNull
    @ApiModelProperty(position = 1, required = true, notes = "User's id.")
    private Integer id;
    @NotNull
    @NotEmpty
    @ApiModelProperty(position = 2, required = true, notes = "User's name.")
    private String name;
    @ApiModelProperty(position = 3, hidden = true, required = false, notes = "User's debt.")
    private BigDecimal debt;

    @ApiModelProperty(position = 4, hidden = true, required = false, notes = "User's movie. Important during renting movies by user, ignored during creating new user.")
    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<Movie> moviesList;

    public User() {}

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getDebt() { return debt; }

    public void setDebt(BigDecimal debt) { this.debt = debt; }

    public List<Movie> getMoviesList() { return moviesList; }

    public void setMoviesList(List<Movie> moviesList) { this.moviesList = moviesList; }
}
