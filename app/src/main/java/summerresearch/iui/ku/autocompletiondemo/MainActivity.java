package summerresearch.iui.ku.autocompletiondemo;

import java.io.InputStream;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import sketchImpl.Sketch;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawingView dv ;
    private Paint mPaint;
    private FrameLayout frame;
    private TextView textView;
    private Button btn;
    private ImageView image;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frame = (FrameLayout)findViewById(R.id.frameLayout);
        btn = (Button) findViewById(R.id.sendButton);
        textView = (TextView) findViewById(R.id.textView);

        image = (ImageView) findViewById(R.id.imageView);
        image2 = (ImageView) findViewById(R.id.imageView2);
        image3 = (ImageView) findViewById(R.id.imageView3);
        image4 = (ImageView) findViewById(R.id.imageView4);
        image5 = (ImageView) findViewById(R.id.imageView5);

        display = (ImageView) findViewById(R.id.imageView6);

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

        image.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);


    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.imageView:
                display.setImageResource(R.mipmap.icon);
                break;
            case R.id.imageView2:
                display.setImageResource(R.mipmap.icon);
                break;
            case R.id.imageView3:
                display.setImageResource(R.mipmap.icon);
                break;
            case R.id.imageView4:
                display.setImageResource(R.mipmap.icon);
                break;
            case R.id.imageView5:
                display.setImageResource(R.mipmap.icon);
                break;

        }
    }

    public void send( View v )
    {
        if( ( btn.getText() ).equals( "PAINT") ) {
            Log.d("Img", "in paint");
            btn.setText("Send");
            frame.findViewById(R.id.imageView6).setVisibility(View.INVISIBLE);
            dv = new DrawingView(this, mPaint);
            frame.addView(dv);
        }
        else if( ( btn.getText() ).equals( "Send" )) {
            Sketch sketch = dv.getSketch();
            if ( sketch.jsonString().length() > 0 )
            {
                Log.d("server", "if part");
                new CallAPI( textView, image, image2, image3, image4, image5 ).execute("http://172.31.155.112:5000/?json=" + sketch.jsonString() );
                Log.d("server", "after if part");
                dv.clear();
            }
            else
            {
                Log.d("server", "else part");
                Toast.makeText(getBaseContext(),"All field are required",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Log.d("Img", "does not equal to anything");
        }
    }

    public void imageClicked ( View v )
    {
        frame.removeView(dv);
        Log.d("Img", "1");
        ImageView imgView = (ImageView) frame.findViewById(R.id.imageView6);
        Log.d("Img", "2");
//bhjsdkifuesji
        //bjvhsdk
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
        btn.setText("PAINT");
        Log.d("Img", "3");
    }
}
