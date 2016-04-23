package shauli.example.com.netslix;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.List;

import shauli.example.com.netslix.model.Movie;

/**
 * Created by shaulmizrachy on 23/04/2016.
 */
public class MovieThumbArrayAdapter extends ArrayAdapter {

    public MovieThumbArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    public MovieThumbArrayAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public MovieThumbArrayAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    public MovieThumbArrayAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public MovieThumbArrayAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public MovieThumbArrayAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object item = getItem(position);

        if (item != null) {
            String thumbnail = ((Movie) item).getThumbnail();

            LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.movie_item, null);
                ImageView imageview = (ImageView) convertView.findViewById(R.id.movie_thumbnail);
                String imageUrl = "https://image.tmdb.org/t/p/w300/" + thumbnail;

                // show The Image in a ImageView
                new DownloadImageTask(imageview)
                        .execute(imageUrl);
            }
        }

        return convertView;
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
