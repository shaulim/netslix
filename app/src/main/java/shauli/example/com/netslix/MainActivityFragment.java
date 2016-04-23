package shauli.example.com.netslix;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import shauli.example.com.netslix.model.Movie;
import shauli.example.com.netslix.model.MovieListResponse;
import shauli.example.com.netslix.model.SortType;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ObjectMapper mapper = new ObjectMapper();

    final String TAG_NAME = this.getClass().getSimpleName();

    ObjectMapper om = new ObjectMapper();

    ArrayAdapter<Movie> arrayAdapter;
    Fragment frag = this;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);

        List<Movie> movieList = new ArrayList<>();

        arrayAdapter = new MovieThumbArrayAdapter(getActivity(), R.layout.movie_item, R.id.movie_thumbnail, movieList);
        //arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.movie_item, R.id.movie_item_name, movieList);

        final GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(arrayAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie item = (Movie) gridView.getItemAtPosition(position);

                String message = "";
                try {
                    message = mapper.writeValueAsString(item);
                }
                catch (Exception e){
                    Log.e(this.getClass().getSimpleName(), e.getMessage());
                }

                Intent detailIntent = new Intent(frag.getActivity(), DetailActivity.class).putExtra("text", message);
                detailIntent.setData(Uri.parse(""));
                startActivity(detailIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateThumbNails(SortType.TOP_RATED);
    }

    private void updateThumbNails(SortType sortType) {

        Log.d(TAG_NAME, "updating movies from movie db");

        FetchMoviesFromMovieDB task = new FetchMoviesFromMovieDB();
        task.execute(sortType);
    }

    public class FetchMoviesFromMovieDB extends AsyncTask<SortType, Void, List<Movie>> {

        final String TAG_NAME = this.getClass().getSimpleName();

        final String API_KEY = "98b66dce1dbff14fa4aec0a5f01bef68";
        final String TMDB_PREF= "https://api.themoviedb.org/3/movie/";
        final String TOP_RATED_URI = TMDB_PREF + "top_rated?api_key=" + API_KEY;
        final String MOST_POPULAR_URI = TMDB_PREF + "popular?api_key=" + API_KEY;
        public final String THUMBNAIL_PREF = "https://image.tmdb.org/t/p/w300/";

        @Override
        protected List<Movie> doInBackground(SortType... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            SortType sortType = params[0];
            Uri uri;

            switch (sortType) {
                case TOP_RATED : uri = Uri.parse(TOP_RATED_URI); break;
                case MOST_POPULAR : uri = Uri.parse(MOST_POPULAR_URI); break;
                default : uri = Uri.parse(TOP_RATED_URI);
            }

            try {
                Log.d(TAG_NAME, "uri created : " + uri);
                URL url = new URL(uri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();

                MovieListResponse response = om.readValue(inputStream, MovieListResponse.class);

                Log.d(TAG_NAME, "Results are : " + response.getResults());

                return response.getResults();

            }
            catch (MalformedURLException e) {
                Log.e(TAG_NAME, "couldn't parse url" + uri);
            }
            catch (IOException e) {
                Log.e(TAG_NAME, e.getMessage());
            }

            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);

            if (movies.size() > 0) {
                arrayAdapter.clear();
                arrayAdapter.addAll(movies);
                Log.d(TAG_NAME, "changed items to : " + movies);
            }
        }
    }
}
