package summerresearch.iui.ku.autocompletiondemo;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
    private TextView textView;
    private CircleButton sendbtn;
    private CircleButton undobtn;
    private CircleButton erasebtn;
    private CircleButton drawbtn;
    private ImageView image;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private Canvas recentCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frame = (FrameLayout)findViewById(R.id.frameLayout);
        sendbtn = (CircleButton) findViewById(R.id.send);
        undobtn = (CircleButton) findViewById(R.id.undo);
        erasebtn = (CircleButton) findViewById(R.id.erase);
        drawbtn = (CircleButton) findViewById(R.id.draw);

        textView = (TextView) findViewById(R.id.textView);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);

        image = (ImageView) findViewById(R.id.imageView);
        image2 = (ImageView) findViewById(R.id.imageView2);
        image3 = (ImageView) findViewById(R.id.imageView3);
        image4 = (ImageView) findViewById(R.id.imageView4);
        image5 = (ImageView) findViewById(R.id.imageView5);

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
    }

    public void send( View v )
    {
        Sketch sketch = dv.getSketch();
        if ( sketch.jsonString().length() > 0 )
        {
            new CallAPI( textView, textView1, textView2, textView3, textView4, textView5, image, image2, image3, image4, image5 ).execute("http://172.31.175.204:5000/?json=" + sketch.jsonString() );
            image.findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            image2.findViewById(R.id.imageView2).setVisibility(View.VISIBLE);
            image3.findViewById(R.id.imageView3).setVisibility(View.VISIBLE);
            image4.findViewById(R.id.imageView4).setVisibility(View.VISIBLE);
            image5.findViewById(R.id.imageView5).setVisibility(View.VISIBLE);
            recentCanvas = dv.getCanvas();
            dv.clear();
        }
        else
        {
            Log.d("server", "else part");
            Toast.makeText(getBaseContext(),"All field are required",Toast.LENGTH_SHORT).show();
        }
        sendbtn.setVisibility(View.INVISIBLE);
        drawbtn.setVisibility(View.VISIBLE);
    }

    public void draw( View v ) {
        image.findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
        image2.findViewById(R.id.imageView2).setVisibility(View.INVISIBLE);
        image3.findViewById(R.id.imageView3).setVisibility(View.INVISIBLE);
        image4.findViewById(R.id.imageView4).setVisibility(View.INVISIBLE);
        image5.findViewById(R.id.imageView5).setVisibility(View.INVISIBLE);

        textView1.findViewById(R.id.textView1).setVisibility(View.INVISIBLE);
        textView2.findViewById(R.id.textView2).setVisibility(View.INVISIBLE);
        textView3.findViewById(R.id.textView3).setVisibility(View.INVISIBLE);
        textView4.findViewById(R.id.textView4).setVisibility(View.INVISIBLE);
        textView5.findViewById(R.id.textView5).setVisibility(View.INVISIBLE);

        frame.findViewById(R.id.imageView6).setVisibility(View.INVISIBLE);
        dv = new DrawingView(this, sendbtn, drawbtn, mPaint);
        frame.addView(dv);
    }

    public void imageClicked ( View v )
    {
        frame.removeView(dv);
        ImageView imgView = (ImageView) frame.findViewById(R.id.imageView6);

        frame.findViewById(R.id.imageView6).setVisibility(View.VISIBLE);
        switch (v.getId()) {
            case R.id.imageView:
                imgView.setImageDrawable(image.getDrawable());
                break;
            case R.id.imageView2:
                imgView.setImageDrawable(image2.getDrawable());
                break;
            case R.id.imageView3:
                imgView.setImageDrawable(image3.getDrawable());
                break;
            case R.id.imageView4:
                imgView.setImageDrawable(image4.getDrawable());
                break;
            case R.id.imageView5:
                imgView.setImageDrawable(image5.getDrawable());
                break;
            default:
                Log.d("Img", "indefault");
        }
        frame.invalidate();
        //btn.setText("PAINT");
        Log.d("Img", "3");
    }

    public void undo( View v ) {
        dv.repaint();
    }

    public void erase( View v ) {

    }
}