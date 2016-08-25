package summerresearch.iui.ku.autocompletiondemo;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Random;
import summerresearch.iui.ku.autocompletiondemo.MainActivity;

/**
 * Created by ElifYagmur on 23.08.2016.
 */
public class LocalService extends Service {

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    public static final String RESPONSE_STRING = "myResponse";
    public static final String RESPONSE_MESSAGE = "myResponseMessage";
    private String URL = "";
    private String response;
    private Intent intent;
    private static final int REGISTRATION_TIMEOUT = 3 * 100000;
    private static final int WAIT_TIMEOUT = 30 * 100000;
    private HttpPost httpPost;
    private HttpClient httpclient;
    public DownloadImageTask task;
    public boolean isReady;

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        LocalService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocalService.this;
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d("background", "on Local service start");
        URL = intent.getStringExtra("URL");
        try {
            Log.d("background", "2");
            String simpleURL = new String( URL.getBytes(),"UTF-8");
            Log.d( "background", simpleURL );
            httpclient = new DefaultHttpClient();
            HttpParams params = httpclient.getParams();

            Log.d("background", "3");
            HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
            ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);

            Log.d("background", "4");
            httpPost = new HttpPost( URL );

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        this.intent = intent;
        return mBinder;
    }

    /** method for clients */
    public void getResponseFromServer( String JSON  ) {
        Object[] object = new Object[]{JSON};
        Log.d("background", "getResponseFromServer");

         if( task != null ) {
             task.cancel( true );
             Log.d("background", "status " + task.getStatus().toString());

         }

        task = new DownloadImageTask();
        task.execute( JSON );
    }

    private class DownloadImageTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            String responseMessage;
            Log.d("background", "1");
            try {
                Log.d("background", "try1");
                StringEntity stringEntity = new StringEntity( (String) objects[0] );
                stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity( stringEntity );

                Log.d("background", "try2");
                ResponseHandler responseHandler = new BasicResponseHandler();
                response = (String) httpclient.execute( httpPost, responseHandler );
                Log.d("background", response );

                Log.d("background", "5");

            } catch (ClientProtocolException e) {
                Log.w("HTTP2:",e );
                responseMessage = e.getMessage();
            } catch (IOException e) {
                Log.w("HTTP3:",e );
                responseMessage = e.getMessage();
            }catch (Exception e) {
                Log.w("HTTP4:",e );
                responseMessage = e.getMessage();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Object o) {
            //super.onPostExecute(o);
            if (o == null){
                Log.d("background", "onPOSTEXE, Null Response. No Internet Connection?" );
            }
            else {
                Log.d("background", "onPOSTEXE" + o.toString());
                MainActivity.refreshScroll((String) o);
                task = null;
            }
        }


    }
}