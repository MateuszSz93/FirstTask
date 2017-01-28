package pl.mszkwarkowski.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The "Actor" class represents an actor object. It has various attributes of actors.
 */
@ApiModel
@Entity
public class Actor {
    @Id
    @NotNull
    @ApiModelProperty(position = 1, required = true, notes = "Actor's id.")
    private Integer id;
    @NotNull
    @NotEmpty
    @ApiModelProperty(position = 2, required = true, notes = "Actor's name.")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "actorList")
    private List<Movie> movieList;

    public Actor() {
    }

    public Actor(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @PreRemove
    private void removeActor() {
        for (Movie movie : movieList) {
            movie.getActorList().remove(this);
        }
    }
}