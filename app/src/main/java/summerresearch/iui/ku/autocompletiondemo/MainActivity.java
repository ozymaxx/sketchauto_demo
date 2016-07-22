package summerresearch.iui.ku.autocompletiondemo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

// import everything you need
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import sketchImpl.Sketch;
// the main activity of the application
// it'll include the drawing canvas

public class MainActivity extends AppCompatActivity {
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_main);
    }*/

    private DrawingView dv ;
    private Paint mPaint;
    private FrameLayout frame;
    private Button btn;
    private DatagramSocket serverSocket;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frame = (FrameLayout)findViewById(R.id.frameLayout);
        btn = (Button) findViewById(R.id.sendButton);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(8);
        dv = new DrawingView(this, mPaint);

        dv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        frame.addView(dv);
    }

    public void send( View v ) throws IOException {
        Sketch sketch = dv.getSketch();



/*
        try {

            //generate a server socket
            serverSocket = new DatagramSocket(5000);

            //send initialization message
            InetAddress serverIp = InetAddress.getByName("172.31.155.112");
            String messageStr = sketch.jsonString();
            int msg_length = messageStr.length();
            byte[] message = messageStr.getBytes();
            DatagramPacket p = new DatagramPacket(message, msg_length, serverIp, 5000);
            serverSocket.send(p);


            //prepare for receiving message
            byte[] receiveData = new byte[100];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            //receive message in while loop
            while (true)
            {
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());

            }

        }
        catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/

        URL url = new URL("http://192.168.43.212:5000/");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();



        try {
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);
        } finally {
            urlConnection.disconnect();
        }
        dv.clear();

/*
        // make sure the fields are not empty
        if (sketch.jsonString().length()>0)
        {
            Log.d("server", "before post");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.43.212:5000/");

            try {
                Log.d("server", "try post");
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("message", sketch.jsonString()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                dv.clear();
                httpclient.execute(httppost);
                Log.d("server", "after execute post");
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }
        else
        {
            Log.d("server", "else part");
            // display message if text fields are empty
            Toast.makeText(getBaseContext(),"All field are required",Toast.LENGTH_SHORT).show();
        }

*/
    }
}