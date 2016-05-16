package test;

import main.annotations.Column;
import main.annotations.KeyColumn;
import main.annotations.Table;

import java.util.Date;

@Table(table = "Movies")
public class Movie {

    @KeyColumn
    @Column(column = "ID")
    private Integer id;

    @Column(column = "title")
    private String title;

    @Column(column = "date")
    private Date date;

    @Column(column = "description")
    private String description;

    public Movie() {
    }

    public Movie(Integer id) {
        this.id = id;
    }

    public Movie(Integer id, String title, Date date, String description) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != null ? !id.equals(movie.id) : movie.id != null) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (date != null ? !date.equals(movie.date) : movie.date != null) return false;
        return !(description != null ? !description.equals(movie.description) : movie.description != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
