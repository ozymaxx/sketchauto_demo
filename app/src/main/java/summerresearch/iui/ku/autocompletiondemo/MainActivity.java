package summerresearch.iui.ku.autocompletiondemo;

import java.io.InputStream;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import sketchImpl.Sketch;

public class MainActivity extends AppCompatActivity {

    private DrawingView dv ;
    private Paint mPaint;
    private FrameLayout frame;
    private Button btn;

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
        if ( sketch.jsonString().length() > 0 )
        {
            Log.d("server", "if part");
            new CallAPI( (TextView) findViewById(R.id.textView) ).execute("http://172.31.155.112:5000/?json=" + sketch.jsonString() );
            Log.d("server", "after if part");
            dv.clear();
        }
        else
        {
            Log.d("server", "else part");
            Toast.makeText(getBaseContext(),"All field are required",Toast.LENGTH_SHORT).show();
        }
    }
}
