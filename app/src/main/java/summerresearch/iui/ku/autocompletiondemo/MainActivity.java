package summerresearch.iui.ku.autocompletiondemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import sketchImpl.Sketch;

final public class MainActivity extends AppCompatActivity {

    public static DrawingView dv ;
    public static LinearLayout scrollLayout;
    public static ImageMap im;
    public static Activity main;
    private Paint mPaint;
    public static FrameLayout frame;
    LocalService mService;
    boolean mBound = false;
    private String IP = "172.31.121.187";
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        main = this;
        frame = (FrameLayout)findViewById(R.id.frameLayout);
        scrollLayout = (LinearLayout) findViewById( R.id.scrollLayout );

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

        serviceIntent = new Intent(this, LocalService.class).putExtra( "URL", "http://" + IP + ":5000/" );

        startService( serviceIntent );


        checkInternetConnection();
        frame.addView(dv);

        new AsyncTask<Context,Void,Void>() {
            protected void onPreExecute() {
                // Pre Code
            }
            protected Void doInBackground(Context... unused) {
                im  = new ImageMap(unused[0]);
                ((MainActivity)unused[0]).setImageMap(im);
                return null;
            }

            protected void onPostExecute(Context... unused) {

            }
        }.execute(this);



    }

    public void setImageMap(ImageMap im){
        this.im = im;
        this.runOnUiThread(new Runnable() {
            public void run() {

                ImageView imgView = (ImageView)findViewById(R.id.ivintro);
                imgView.setVisibility(View.INVISIBLE);

                ImageView undo = (ImageView)findViewById(R.id.undo);
                undo.setVisibility(View.VISIBLE);

                ImageView eraseStrk = (ImageView)findViewById(R.id.eraseStrk);
                eraseStrk.setVisibility(View.VISIBLE);

                ImageView erase = (ImageView)findViewById(R.id.erase);
                erase.setVisibility(View.VISIBLE);

                ImageView ip = (ImageView)findViewById(R.id.ip);
                ip.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void send( Sketch sketch )
    {
        if (mBound) {
            // Call a method from the LocalService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
             mService.getResponseFromServer( sketch.getJsonString() );
        }

    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

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
                mService.onRebind(new Intent( main, LocalService.class).putExtra( "URL", "http://" + input.getText().toString() + ":5000/" ));
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

    public static void refreshScroll ( String result )
    {
        String [] separated = new String[]{};
        Log.d("background", result);
        //DELETE ALL EXISTING VIEWS ON SCROLL
        scrollLayout.removeAllViews();
        //GET NAME OF ICONS HERE AND PUT INTO IMAGES
        scrollLayout.invalidate();
        separated = result.split("&");
        ImageView mainImage = (ImageView) frame.findViewById(R.id.imageView6);
        mainImage.setVisibility(View.INVISIBLE);

        if( separated.length > 0 ) {
            int lenght = separated.length/3;
            for( int i = 0; i < separated.length/3; i++ ) {
                final ImageView image = new ImageView( main.getApplicationContext() );
                image.setLayoutParams(new android.view.ViewGroup.LayoutParams(200, 200));
                image.setMaxHeight(40);
                image.setMaxWidth(40);
                try {
/*
                    if( im.contains(separated[i]) ) {
                        Log.d("EYE","here1");
                        image.setImageBitmap(im.getImageMap(separated[i]));
                    }
                    else {*/
                        Log.d("EYE","here2");
                        byte[] decodedString = Base64.decode(separated[i], Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        image.setImageBitmap( decodedByte );
                   // }
                }
                catch (NullPointerException e){

                }
                Log.d("img res name", "" + image.getDrawable());
                image.setClickable(true);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FrameLayout frame = (FrameLayout) main.findViewById(R.id.frameLayout);
                        frame.removeView(dv);
                        ImageView imgView = (ImageView) frame.findViewById(R.id.imageView6);
                        frame.findViewById(R.id.imageView6).setVisibility(View.VISIBLE);
                        Resources r = view.getResources();
                        imgView.setImageDrawable( ((ImageView)view).getDrawable() );
                        frame.invalidate();
                    }
                });
                scrollLayout.addView(image);

                TextView textView = new TextView( main.getApplicationContext() );
                Float prob = Float.parseFloat(separated[lenght + i]);
                // to make it %
                prob *= 100;
                String text = String.format("%s\n%.2f%%", separated[2*lenght + i], prob);
                Log.d("separeted", "sep : " + text );
                textView.setLayoutParams(new android.view.ViewGroup.LayoutParams(200, 40));
                textView.setMaxHeight(40);
                textView.setMaxWidth(40);
                textView.setText( text );
                textView.setGravity(Gravity.CENTER);
                scrollLayout.addView(textView);
            }
        }


    }

    public void drawingModeOn(View view) {
        frame.findViewById(R.id.imageView6).setVisibility(View.INVISIBLE);
        dv = new DrawingView(this, mPaint);
        frame.addView(dv);
    }

    public void undo(View view)
    {
        dv.undo();
    }

    public void eraseStrk (View view) { dv.eraseStrk( true ); }

    public void erase(View view)
    {
        dv.clear();
        refreshScroll("");
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