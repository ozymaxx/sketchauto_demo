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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by ElifYagmur on 22.07.2016.
 */
public class CallAPI extends AsyncTask<String, String, String> {
    TextView textView, textView1, textView2, textView3, textView4, textView5;
    ImageView imV, imV2, imV3, imV4, imV5;
    String[] separated;

    public CallAPI(TextView view, TextView view1, TextView view2, TextView view3, TextView view4, TextView view5,ImageView image, ImageView image2, ImageView image3, ImageView image4, ImageView image5 ) {
        textView = view;
        textView1 = view1;
        textView2 = view2;
        textView3 = view3;
        textView4 = view4;
        textView5 = view5;

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

        try {
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return resultToDisplay;
    }


    @Override
    protected void onPostExecute(String result) {
        //Update the UI
        Log.d("response", result);

        //PARSE THE STRING WHICH SERVER SENDS US
        separated = result.split("&");

        Log.d("split", separated[0]);
        Log.d("split", separated[1]);
        Log.d("split", separated[2]);
        Log.d("split", separated[3]);
        Log.d("split", separated[4]);


        //GET NAME OF ICONS HERE AND PUT INTO IMAGES
        ImageMap im = new ImageMap();

        imV.setImageResource( im.getImageMap().get(separated[0]) );
        imV2.setImageResource( im.getImageMap().get(separated[1]) );
        imV3.setImageResource( im.getImageMap().get(separated[2]) );
        imV4.setImageResource( im.getImageMap().get(separated[3]) );
        imV5.setImageResource( im.getImageMap().get(separated[4]) );

        textView1.setText(separated[0]);
        textView2.setText(separated[1]);
        textView3.setText(separated[2]);
        textView4.setText(separated[3]);
        textView5.setText(separated[4]);
    }
}