package summerresearch.iui.ku.autocompletiondemo;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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
    InputStream inputStream = null;
    String result = "";
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

    public void send( View v )
    {
        Sketch sketch = dv.getSketch();
        // make sure the fields are not empty
        if (sketch.jsonString().length()>0)
        {
            Log.d("server", "if part");
            new CallAPI().execute("http://172.31.155.112:5000/?json=" + sketch.jsonString() );
            Log.d("server", "after if part");
            dv.clear();
        }
        else
        {
            Log.d("server", "else part");
            // display message if text fields are empty
            Toast.makeText(getBaseContext(),"All field are required",Toast.LENGTH_SHORT).show();
        }
    }
}
