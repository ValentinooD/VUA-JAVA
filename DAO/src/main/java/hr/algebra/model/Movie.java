package hr.algebra.model;

import hr.algebra.model.adapters.DateAdapter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
 
@XmlAccessorType(XmlAccessType.FIELD)
public class Movie {
    @XmlAttribute
    private int id = -1; // default
    private String title;
    private String description;
    
    @XmlElementWrapper
    @XmlElement(name = "directors")
    private Set<Director> directors;
    
    @XmlElementWrapper
    @XmlElement(name = "actors")
    private Set<Actor> actors;
    
    private String bannerPath;
    private String link;
    
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date publishDate;
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date showingDate;

    public Movie() {
        this.actors = new HashSet<>();
        this.directors = new HashSet<>();
    }

    public Movie(int id, String title, String description, Set<Director> directors, Set<Actor> actors, String bannerPath, String link, Date pubishDate, Date showingDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.directors = directors;
        this.actors = actors;
        this.bannerPath = bannerPath;
        this.link = link;
        this.publishDate = pubishDate;
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
    
    public Set<Director> getDirectors() {
        return directors;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public String getBannerPath() {
        return bannerPath;
    }

    public String getLink() {
        return link;
    }

    public Date getPublishDate() {
        return publishDate;
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
    
    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }
    
    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPubishDate(Date pubishDate) {
        this.publishDate = pubishDate;
    }

    public void setShowingDate(Date showingDate) {
        this.showingDate = showingDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        return this.id == other.id;
    }
}
