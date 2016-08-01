package summerresearch.iui.ku.autocompletiondemo;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sketchImpl.Sketch;

/**
 * Created by ElifYagmur on 22.07.2016.
 */
public class CallAPI extends AsyncTask<String, String, String> {
    TextView[] textViews;
    ImageView [] imageViews;
    String[] separated;
    DrawingView dv;
    String link;
    Sketch sketch;

    public CallAPI(TextView [] textViews, ImageView [] imageViews, DrawingView dv, Sketch sketch, String link) {
        this.textViews = textViews;
        this.imageViews = imageViews;
        this.dv = dv;
        this.sketch = sketch;
        this.link = link;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = this.link + this.sketch.jsonString();
        String resultToDisplay = "";
        InputStream in = null;
        Log.d("server", "try 1");

        URL url = null;
        try {
            Log.d("server", "try 1");
            url = new URL(urlString);
            Log.d("server", "after url");
        }

        catch (MalformedURLException e){
            Log.d("server", "catch 1");
            return e.getMessage();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
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
        urlConnection.disconnect();

        try
        {
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return resultToDisplay;
    }


    @Override
    protected void onPostExecute(String result)
    {
        //Update the UI
        Log.d("response", result);
        //PARSE THE STRING WHICH SERVER SENDS US
        separated = result.split("&");
        //GET NAME OF ICONS HERE AND PUT INTO IMAGES
        ImageMap im = new ImageMap();


        int i = 0;
        for (; i < separated.length/2 && i < imageViews.length && i < textViews.length; i++) {

            imageViews[i].setImageResource(im.getImageMap().get(separated[i]));

            Float prob = Float.parseFloat(separated[separated.length/2 + i]);
            // to make it %
            prob *= 100;
            String text = String.format("%s %.2f%%", separated[i], prob);
            textViews[i].setText(text);
        }
        /*
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        dv.HttpResult();
    }
}