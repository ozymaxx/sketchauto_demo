package summerresearch.iui.ku.autocompletiondemo;

import android.content.pm.ActivityInfo;
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


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {

                    //generate a server socket
                    serverSocket = new DatagramSocket(9000);

                    //send initialization message
                    InetAddress serverIp = InetAddress.getByName("192.168.56.103");
                    String messageStr = "*" + "192.168.56.102" + "*2";
                    int msg_length = messageStr.length();
                    byte[] message = messageStr.getBytes();
                    DatagramPacket p = new DatagramPacket(message, msg_length, serverIp, 9000);
                    serverSocket.send(p);

                    //prepare for receiving message
                    byte[] receiveData = new byte[100];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                    //receive message in while loop
                    while (true) {
                        serverSocket.receive(receivePacket);
                        String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());

                        //show message
                        //log(sentence);

                    }
                }
                catch (SocketException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    shutdown();
                }

            }



        });


    }

    private void shutdown() {
        try {
            if (socket != null) {
                socket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
