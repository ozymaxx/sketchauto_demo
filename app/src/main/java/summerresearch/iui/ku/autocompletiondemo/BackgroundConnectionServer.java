package summerresearch.iui.ku.autocompletiondemo;

import android.app.IntentService;
import android.content.Intent;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by ElifYagmur on 11.08.2016.
 */
public class BackgroundConnectionService extends IntentService {

    public BackgroundConnectionService() {
        // Need this to name the service
        super ("ConnectionServices");
    }

    @Override
    protected void onHandleIntent(Intent arg0) {
        // Do stuff that you want to happen asynchronously here
        DefaultHttpClient httpclient = new DefaultHttpClient ();
        HttpRequest httpget = new HttpGet ("http://www.google.com");
        // Some try and catch that I am leaving out
        httpclient.execute( httpget );
    }
}