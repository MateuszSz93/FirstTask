package pl.mszkwarkowski.movie;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The "Actor" class represents an actor object. It has various attributes of actors.
 */
@Entity
public class Actor {

    @Id
    private Integer id;
    private String name;

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
}
