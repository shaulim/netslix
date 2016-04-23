package shauli.example.com.netslix.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by shaulmizrachy on 21/02/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieListResponse {
    List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MovieListResponse{" +
                "results=" + results +
                '}';
    }
}
