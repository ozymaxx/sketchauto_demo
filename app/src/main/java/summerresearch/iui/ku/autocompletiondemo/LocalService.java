package summerresearch.iui.ku.autocompletiondemo;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
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

/**
 * Created by ElifYagmur on 23.08.2016.
 * The LocalService is a service that runs in the background and provides constant server connection.
 */
public class LocalService extends Service {

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    private String URL = "";
    private String response;
    private Intent intent;
    private static final int REGISTRATION_TIMEOUT = 3 * 100000;
    private static final int WAIT_TIMEOUT = 30 * 100000;
    private HttpPost httpPost;
    private HttpClient httpclient;
    public ConnectToServer task;

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
        URL = intent.getStringExtra("URL");
        try {
            String simpleURL = new String( URL.getBytes(),"UTF-8");
            Log.d( "background", simpleURL );
            httpclient = new DefaultHttpClient();
            HttpParams params = httpclient.getParams();

            HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
            ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);

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

    @Override
    public void onRebind(Intent intent) {
        URL = intent.getStringExtra("URL");
        try {
            String simpleURL = new String( URL.getBytes(),"UTF-8");
            Log.d( "background", simpleURL );
            httpclient.getConnectionManager().shutdown();
            httpclient = new DefaultHttpClient();
            HttpParams params = httpclient.getParams();

            HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
            ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);

            httpPost = new HttpPost( URL );

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /** method for clients */
    public void getResponseFromServer( String JSON  ) {
        Object[] object = new Object[]{JSON};
        Log.d("background", "getResponseFromServer");

         if( task != null ) {
             task.cancel( true );
             Log.d("background", "status " + task.getStatus().toString());

         }

        task = new ConnectToServer();
        task.execute( JSON );
    }

    /**
    * ConnectToServer connects executes post request on Http connection.
    * Each time send() method in MainActivity called it calls getResponseFromServer() method in LocalService,
     * and that executes ConnectToServer as an asyncTask with input JSON string.
    */
    private class ConnectToServer extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            String responseMessage;
            try {
                StringEntity stringEntity = new StringEntity( (String) objects[0] );
                stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity( stringEntity );

                ResponseHandler responseHandler = new BasicResponseHandler();
                response = (String) httpclient.execute( httpPost, responseHandler );
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