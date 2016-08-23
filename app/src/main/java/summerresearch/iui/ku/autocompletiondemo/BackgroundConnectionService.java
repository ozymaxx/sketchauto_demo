package summerresearch.iui.ku.autocompletiondemo;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by ElifYagmur on 11.08.2016.
 */
public class BackgroundConnectionService extends IntentService {

    public static final String REQUEST_STRING = "URL";
    public static final String RESPONSE_STRING = "myResponse";
    public static final String RESPONSE_MESSAGE = "myResponseMessage";

    private String URL = "";
    private String JSON = "";
    private String response;
    private static final int REGISTRATION_TIMEOUT = 3 * 100000;
    private static final int WAIT_TIMEOUT = 30 * 100000;

    public BackgroundConnectionService() {
        // Need this to name the service
        super ("BackgroundConnectionServer");
        Log.d("background", "constructor");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        URL = intent.getStringArrayExtra("IN")[0];
        JSON = intent.getStringArrayExtra("IN")[1];
        String responseMessage = "";
       // SystemClock.sleep(100); // Wait 10 seconds

        // Do some really cool here
        // I am making web request here as an example...

        Log.d("background", "1");
        try {
            Log.d("background", "2");
            String simpleURL = new String( URL.getBytes(),"UTF-8");
            Log.d( "background", simpleURL );
            HttpClient httpclient = new DefaultHttpClient();
            HttpParams params = httpclient.getParams();

            Log.d("background", "3");
            HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
            ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);

            Log.d("background", "4");
            HttpPost httpPost = new HttpPost( URL );
            HttpGet httpGet = new HttpGet( URL );
            StringEntity stringEntity = new StringEntity( JSON );
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity( stringEntity );
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");
            ResponseHandler responseHandler = new BasicResponseHandler();
           // HttpResponse response = (HttpResponse) httpclient.execute( httpPost, responseHandler );
            response = (String) httpclient.execute( httpPost, responseHandler );
            Log.d("background", response );

            Log.d("background", "5");
           /* StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                Log.d("background", "6");
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseMessage = out.toString();

                Log.d("background", responseMessage );
                Log.d("background", "7");
            }

            else{
                //Closes the connection.
                Log.d("background", "8");
                Log.w("HTTP1:",statusLine.getReasonPhrase());
                response.getEntity().getContent().close();
                Log.d("background", "9");
                Log.d("background", JSON);
                throw new IOException(statusLine.getReasonPhrase());
            }
*/

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


        Intent boardcastIntent = new Intent();
        boardcastIntent.setAction(MainActivity.MyReceiver.PROCESS_RESPONSE);
        boardcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        Log.d("background", "res" + response);
        boardcastIntent.putExtra(RESPONSE_STRING, response);
        boardcastIntent.putExtra(RESPONSE_MESSAGE, responseMessage);
        sendBroadcast(boardcastIntent);
        stopSelf();
    }



}