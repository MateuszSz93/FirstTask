package pl.mszkwarkowski.movie;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The "Actor" class represents an actor object. It has various attributes of actors.
 */
@Entity
public class Actor {
    @Id
    @NotNull
    private int id;
    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "actorList")
    private List<Movie> movieList;

    public Actor(){
    }

    public Actor(int id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PreRemove
    private void removeActor() {
        for (Movie movie : movieList) {
            movie.getActorList().remove(this);
        }
    }
}