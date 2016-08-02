package summerresearch.iui.ku.autocompletiondemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ScrollingTabContainerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import at.markushi.ui.CircleButton;
import sketchImpl.Sketch;

public class MainActivity extends AppCompatActivity {

    private DrawingView dv ;
    private Paint mPaint;
    private FrameLayout frame;
    private CircleButton sendbtn;
    private CircleButton drawbtn;

    private LinearLayout scrollLayout;
    private String IP = "172.31.0.134"; //Semih's


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        frame = (FrameLayout)findViewById(R.id.frameLayout);
        scrollLayout = (LinearLayout)findViewById(R.id.scrollLayout);
        sendbtn = (CircleButton) findViewById(R.id.send);
        drawbtn = (CircleButton) findViewById(R.id.draw);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(8);

        dv = new DrawingView(this, sendbtn, drawbtn, mPaint);
        dv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        frame.addView(dv);
        checkInternetConnection();

    }

    public void send(DrawingView dv )
    {
        Sketch sketch = dv.getSketch();
        new CallAPI( this, MainActivity.this, dv, sketch, "http://" + IP + ":5000/?json=").execute();
        sendbtn.setVisibility(View.INVISIBLE);
        drawbtn.setVisibility(View.VISIBLE);
    }

    private void showFinishingAlertDialog(String title, String message)
    {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    }
                }).show();
    }

    public void setip(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set IP");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IP = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void draw()
    {
        //CLEAR LINEAR LAYOUT ON SCROLL EYE
        frame.findViewById(R.id.imageView6).setVisibility(View.INVISIBLE);
        dv = new DrawingView(this, sendbtn, drawbtn, mPaint);
        frame.addView(dv);
    }

    public void imageClicked (View v)
    {
        frame.removeView(dv);
        ImageView imgView = (ImageView) frame.findViewById(R.id.imageView6);

        frame.findViewById(R.id.imageView6).setVisibility(View.VISIBLE);
        frame.invalidate();
    }

    public void undo(View view)
    {
        dv.undo();
    }

    public void eraseStrk (View view) { dv.eraseStrk( true ); }

    public void erase(View view)
    {
        dv.clear();
    }

    private boolean checkInternetConnection() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        Log.d( "ConnectivityManager", "" + connec.getActiveNetworkInfo());
        // Check for network connections
        if ( connec.getActiveNetworkInfo() != null) {
            return true;
        }else {
            showFinishingAlertDialog("Internet Connection Error", "Device does not have active internet connection!!!");
            return false;
        }
    }
}