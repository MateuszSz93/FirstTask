package pl.mszkwarkowski.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The "Movie" class represents a movie object. It has various attributes of movies.
 */
@ApiModel
@Entity
public class Movie {
    @ApiModelProperty(position = 1, required = true, notes = "Movie's id.")
    @Id
    @NotNull
    private Integer id;

    @ApiModelProperty(position = 2, required = true, notes = "Movie's title.")
    @NotNull
    @NotEmpty
    private String title;

    @ApiModelProperty(position = 3, required = true, notes = "Movie's release date.")
    @NotNull
    @NotEmpty
    private String releaseDate;

    @ApiModelProperty(position = 4, required = true, notes = "Movie's duration time.")
    @NotNull
    private Integer duration;

    @ApiModelProperty(position = 5, required = true, notes = "Movie's type.")
    @NotNull
    @NotEmpty
    private String type;

    @ApiModelProperty(position = 6, required = true, notes = "Movie's director.")
    @NotNull
    @NotEmpty
    private String director;

    @ApiModelProperty(position = 7, required = true, notes = "List of actors which play in the movie.")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_actor", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actorList;

    @ApiModelProperty(position = 8, required = true, notes = "Movie's price category.")
    @Enumerated(EnumType.STRING)
    @NotNull
    private MovieCategory category;

    @ApiModelProperty(position = 9, hidden = true, required = false, notes = "Movie's owner. Important during renting movies by user, ignored during creating new movie.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner")
    @JsonBackReference
    private User owner;

    public Movie() {}

    public Movie(Integer id, String title, String releaseDate, Integer duration, String type, String director, List<Actor> actorList, MovieCategory category) {
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

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getReleaseDate() { return releaseDate; }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public Integer getDuration() { return duration; }

    public void setDuration(Integer duration) { this.duration = duration; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDirector() { return director; }

    public void setDirector(String director) { this.director = director; }

    public List<Actor> getActorList() { return actorList; }

    public void setActorList(List<Actor> actorList) { this.actorList = actorList; }

    public MovieCategory getCategory() { return category; }

    public void setCategory(MovieCategory category) { this.category = category; }

    public User getOwner() { return owner; }

    public void setOwner(User owner) { this.owner = owner; }
}