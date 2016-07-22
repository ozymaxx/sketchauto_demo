package summerresearch.iui.ku.autocompletiondemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.LockableFileWriter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by ElifYagmur on 22.07.2016.
 */
public class CallAPI extends AsyncTask<String, String, String> {
    TextView textView;
    ImageView imV, imV2, imV3, imV4, imV5;
    public CallAPI(TextView view, ImageView image, ImageView image2, ImageView image3, ImageView image4, ImageView image5 ) {
        textView = view;
        imV = image;
        imV2 = image2;
        imV3 = image3;
        imV4 = image4;
        imV5 = image5;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... params) {

        String urlString = params[0]; // URL to call
        Log.d("server",params[0] );
        String resultToDisplay = "";
        InputStream in = null;
        try {
            Log.d("server", "try 1");
            URL url = new URL(urlString);
            Log.d("server", "after url");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            Log.d("server", "after urlConnection");
            in = new BufferedInputStream(urlConnection.getInputStream());
            Log.d("server", "after buff input stream");

        } catch (Exception e) {

            //System.out.println(e.getMessage());
            Log.d("server", "catch 1");
            return e.getMessage();

        }

        try {
            Log.d("server", "try 2");
            resultToDisplay = IOUtils.toString(in, "UTF-8");
            Log.d("server", resultToDisplay );
            //to [convert][1] byte stream to a string
        } catch (IOException e) {
            Log.d("server", "catch 2");
            e.printStackTrace();
        }
        Log.d("server", "before return");
        return resultToDisplay;
    }


    @Override
    protected void onPostExecute(String result) {
        //Update the UI
        Log.d("response", result);
        textView.setText( result );
        imV.setImageResource(R.mipmap.icon);

        imV2.setImageResource(R.mipmap.icon);
        imV3.setImageResource(R.mipmap.icon);
        imV4.setImageResource(R.mipmap.icon);
        imV5.setImageResource(R.mipmap.icon);
    }
}