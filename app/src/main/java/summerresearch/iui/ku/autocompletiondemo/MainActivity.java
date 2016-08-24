package summerresearch.iui.ku.autocompletiondemo;

import android.app.Activity;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.media.Image;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ScrollingTabContainerView;
import android.text.InputType;
import android.view.Gravity;
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
    LinearLayout scrollLayout;
    public String[] separated;
    public ImageMap im;
    private CallAPI callbackapi;

    private IntentFilter filter;
    private MyReceiver receiver;
    //private String IP = "172.31.67.89";
    private String IP = "172.31.67.189";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filter = new IntentFilter(MyReceiver.PROCESS_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new MyReceiver();
        registerReceiver( receiver, filter );

        getSupportActionBar().hide();

        frame = (FrameLayout)findViewById(R.id.frameLayout);
        sendbtn = (CircleButton) findViewById(R.id.send);
        drawbtn = (CircleButton) findViewById(R.id.draw);
        im = new ImageMap(this);
        scrollLayout = (LinearLayout) findViewById( R.id.scrollLayout );

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

        this.callbackapi = new CallAPI(this, this,  dv, dv.getSketch(), "http://" + IP + ":5000/?json=");
        this.callbackapi.execute();

        //startService( new Intent( MainActivity.this, BackgroundConnectionService.class).putExtra( "IN", new String[] {"http://" + IP + ":5000/", this.dv.getSketch()} ) );
    }

    public void send( Sketch sketch )
    {

        Log.d("background", "before call service");
        Log.d("background", "after call service");
        //new CallAPI( this, MainActivity.this, dv, sketch, "http://" + IP + ":5000/?json=").execute();
        //sendbtn.setVisibility(View.INVISIBLE);
        //drawbtn.setVisibility(View.VISIBLE);

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

    @Override
    public void onDestroy() {
        this.unregisterReceiver(receiver);
        super.onDestroy();
    }

    public class MyReceiver extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "com.as400samplecode.intent.action.PROCESS_RESPONSE";
        private String[] separated;
        private String result = "";
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("background", "here");
            result = intent.getStringExtra("myResponse");

            if (result == null){
                Log.d("background","result is null");
                return;
            }



            Log.d("background", result);
            separated = result.split("&");
            //DELETE ALL EXISTING VIEWS ON SCROLL
            scrollLayout.removeAllViews();
            //GET NAME OF ICONS HERE AND PUT INTO IMAGES

            scrollLayout.invalidate();
            for( int i = 0; i < separated.length/2; i++ ) {
                final ImageView image = new ImageView(context);
                image.setLayoutParams(new android.view.ViewGroup.LayoutParams(200, 200));
                image.setMaxHeight(40);
                image.setMaxWidth(40);
                image.setImageBitmap(im.getImageMap(separated[i]));
                Log.d("background", "sep : " + separated[i]);
                image.setClickable(true);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FrameLayout frame = (FrameLayout) findViewById(R.id.frameLayout);
                        frame.removeView(dv);
                        ImageView imgView = (ImageView) frame.findViewById(R.id.imageView6);
                        frame.findViewById(R.id.imageView6).setVisibility(View.VISIBLE);
                        Resources r = view.getResources();
                        imgView.setImageDrawable( ((ImageView)view).getDrawable() );
                        frame.invalidate();
                    }
                });
                scrollLayout.addView(image);

                TextView textView = new TextView(context);
                Float prob = Float.parseFloat(separated[separated.length/2 + i]);
                // to make it %
                prob *= 100;
                String text = String.format("%s\n%.2f%%", separated[i], prob);
                Log.d("background", "sep : " + text );
                textView.setLayoutParams(new android.view.ViewGroup.LayoutParams(200, 40));
                textView.setMaxHeight(40);
                textView.setMaxWidth(40);
                textView.setText( text );
                textView.setGravity(Gravity.CENTER);
                scrollLayout.addView(textView);
            }

        }


    }
}