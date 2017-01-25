package pl.mszkwarkowski.movie;

import com.fasterxml.jackson.annotation.JsonBackReference;
import pl.mszkwarkowski.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The "Movie" class represents a movie object. It has various attributes of movies.
 */
@Entity
public class Movie {
    @Id
    @NotNull
    private int id;
    @NotNull
    private String title;
    @NotNull
    private String releaseDate;
    @NotNull
    private int duration;
    @NotNull
    private String type;
    @NotNull
    private String director;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_actor", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actorList;

    @Enumerated(EnumType.STRING)
    @NotNull
    private MovieCategory category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner")
    @JsonBackReference
    private User owner;

    public Movie() {}

    public Movie(int id, String title, String releaseDate, int duration, String type, String director, List<Actor> actorList, MovieCategory category) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.type = type;
        this.director = director;
        this.actorList = actorList;
        this.category = category;
        this.owner = null;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getReleaseDate() { return releaseDate; }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public int getDuration() { return duration; }

    public void setDuration(int duration) { this.duration = duration; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDirector() { return director; }

    public void setDirector(String director) { this.director = director; }

    public List<Actor> getActorList() { return actorList; }

    public void setActorList(List<Actor> actorList) { this.actorList = actorList; }

    public MovieCategory getCategory() { return category; }

    public void setCategory(MovieCategory category) { this.category = category; }


    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}