package pl.mszkwarkowski.movie;

import javax.persistence.*;
import java.util.List;

/**
 * The "Movie" class represents a movie object. It has various attributes of movies.
 */
@Entity
public class Movie {
    @Id
    @Column
    private int id;
    private String title;
    private String releaseDate;
    private int duration;
    private String type;
    private String director;
    private Integer owner;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name="movie_actor",
            joinColumns=@JoinColumn(name="movie_id"),
            inverseJoinColumns=@JoinColumn(name="actor_id"))
    private List<Actor> actorList;
    @Enumerated(EnumType.STRING)
    private MovieCategory category;

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

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }
}
