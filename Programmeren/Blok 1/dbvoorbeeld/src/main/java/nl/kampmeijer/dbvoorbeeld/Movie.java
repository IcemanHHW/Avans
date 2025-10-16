package nl.kampmeijer.dbvoorbeeld;

public class Movie {
    private int movie_id;
    private String title;
    private String year;
    private int director_id;

    public Movie() {

    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getDirector_id() {
        return director_id;
    }

    public void setDirector_id(int director_id) {
        this.director_id = director_id;
    }

    @Override
    public String toString() {
        return title;
    }
}
