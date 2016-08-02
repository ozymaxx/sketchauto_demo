package summerresearch.iui.ku.autocompletiondemo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    Context context;
    LinearLayout scrollLayout;
    String[] separated;
    Activity activity;
    DrawingView dv;
    String link;
    Sketch sketch;


    public CallAPI(Activity activity, Context context, DrawingView dv, Sketch sketch, String link) {
        this.activity = activity;
        this.context = context;
        this.scrollLayout = (LinearLayout) activity.findViewById(R.id.scrollLayout);
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

        scrollLayout.removeAllViews();
        for( int i = 0; i < separated.length; i++ ) {
            Log.d("separated", "" + separated[i]);
        }

        for( int i = 0; i < separated.length/2; i++ ) {
            ImageView image = new ImageView(context);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(200, 200));
            image.setMaxHeight(40);
            image.setMaxWidth(40);
            image.setBackgroundResource(im.getImageMap().get(separated[i]));
            image.getId();
            image.setClickable(true);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("drawble", "" + view.getClass().getName());
                    FrameLayout frame = (FrameLayout) activity.findViewById(R.id.frameLayout);
                    frame.removeView(dv);
                    ImageView imgView = (ImageView) frame.findViewById(R.id.imageView6);
                    frame.findViewById(R.id.imageView6).setVisibility(View.VISIBLE);
                    if( ((ImageView)view).getDrawable() != null ) {
                        Log.d("drawable", "not null");
                    }
                    else {
                        imgView.setImageDrawable(((ImageView) view).getDrawable());
                        Log.d("drawable", "null");
                        Log.d("drawable", "" + ((ImageView)view).getId());
                    }
                    frame.invalidate();
                }
            });

            scrollLayout.addView(image);

            TextView textView = new TextView(context);
            Float prob = Float.parseFloat(separated[separated.length/2 + i]);
            // to make it %
            prob *= 100;
            String text = String.format("%s\n%.2f%%", separated[i], prob);
            Log.d("separeted", "sep : " + text );
            textView.setLayoutParams(new android.view.ViewGroup.LayoutParams(200, 40));
            textView.setMaxHeight(40);
            textView.setMaxWidth(40);
            textView.setText( text );
            textView.setGravity(Gravity.CENTER);

            scrollLayout.addView(textView);
        }
        dv.HttpResult();
    }
}