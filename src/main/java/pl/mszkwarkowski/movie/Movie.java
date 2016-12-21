package pl.mszkwarkowski.movie;

import java.util.List;

/**
 * The "Movie" class represents a movie object. It has various attributes of movies.
 */
public class Movie {
    private int id;
    private String title;
    private String releaseDate;
    private int time;
    private String type;
    private String director;
    private List<Actor> actorList;

    public Movie(){}

    public Movie(int id, String title, String releaseDate, int time, String type, String director, List<Actor> actorList){
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.time = time;
        this.type = type;
        this.director = director;
        this.actorList = actorList;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
