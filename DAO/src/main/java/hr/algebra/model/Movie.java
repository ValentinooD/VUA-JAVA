package hr.algebra.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie {
    private int id;
    private String title;
    private String description;
    private Director director;
    private List<Actor> actors;
    private String bannerPath;
    private String link;
    private Date pubishDate;
    private Date showingDate;

    public Movie() {
        this.actors = new ArrayList<>();
    }

    public Movie(int id, String title, String description, Director director, List<Actor> actors, String bannerPath, String link, Date pubishDate, Date showingDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.director = director;
        this.actors = actors;
        this.bannerPath = bannerPath;
        this.link = link;
        this.pubishDate = pubishDate;
        this.showingDate = showingDate;
    }

    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Director getDirector() {
        return director;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public String getBannerPath() {
        return bannerPath;
    }

    public String getLink() {
        return link;
    }

    public Date getPubishDate() {
        return pubishDate;
    }

    public Date getShowingDate() {
        return showingDate;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPubishDate(Date pubishDate) {
        this.pubishDate = pubishDate;
    }

    public void setShowingDate(Date showingDate) {
        this.showingDate = showingDate;
    }
}
