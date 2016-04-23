package shauli.example.com.netslix.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by shaulmizrachy on 21/02/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    private String id;
    @JsonProperty("original_title")
    private String title;
    @JsonProperty("poster_path")
    private String thumbnail;
    private String overview;
    @JsonProperty("vote_average")
    private String rating;
    @JsonProperty("release_date")
    private Date releaseDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", overview='" + overview + '\'' +
                ", rating='" + rating + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
