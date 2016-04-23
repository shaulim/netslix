package shauli.example.com.netslix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Calendar;

import shauli.example.com.netslix.model.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    ObjectMapper om = new ObjectMapper();

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        String text = getActivity().getIntent().getStringExtra("text");

        Movie movie = new Movie();

        try {
            movie = om.readValue(text, Movie.class);
        }
        catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }

        updateMovieDetails(view, movie);

        return view;
    }

    private void updateMovieDetails(View view, Movie movie) {
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        String imageUrl = "https://image.tmdb.org/t/p/w500/" + movie.getThumbnail();

        // show The Image in a ImageView
        new DownloadImageTask(imageView)
                .execute(imageUrl);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(movie.getTitle());

        TextView plot = (TextView) view.findViewById(R.id.plot);
        plot.setText(movie.getOverview());

        TextView releaseDate = (TextView) view.findViewById(R.id.releaseDate);

        //Calendar cal = Calendar.getInstance().get(movie.getReleaseDate().getTime());

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //Date dateWithoutTime = sdf.parse(movie.getReleaseDate());

        Calendar cal = Calendar.getInstance();
        cal.setTime(movie.getReleaseDate());

        releaseDate.setText("Released on: " + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR));

        TextView userRating = (TextView) view.findViewById(R.id.userRating);
        userRating.setText("User rating: " + movie.getRating());
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
