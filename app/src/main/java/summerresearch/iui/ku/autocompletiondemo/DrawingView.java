package summerresearch.iui.ku.autocompletiondemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import sketchImpl.Sketch;
import sketchImpl.Stroke;

/**
 * Created by ElifYagmur on 20.07.2016.
 */
public class DrawingView extends View {

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mBitmapPaint;
    Context context;
    private Paint circlePaint;
    private Path circlePath;
    Paint mPaint;
    Stroke stroke;
    Sketch sketch;


    public DrawingView(Context c, Paint p) {
        super(c);
        context = c;
        mPath = new Path();
        mPaint = p;
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        circlePaint = new Paint();
        circlePath = new Path();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(4f);
        sketch = new Sketch();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath( mPath,  mPaint);
        canvas.drawPath( circlePath,  circlePaint);

        Log.d("canvas", "" + canvas.getHeight() );
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 0.1f;

    public long getTime() {
        long time= System.nanoTime();
        return time;
    }

    private void touch_start(float x, float y) {
        stroke = new Stroke( width );
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
        long currTime = getTime();
        stroke.addPoint( x, mCanvas.getHeight() - y, currTime );
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;

            long currTime = getTime();
            stroke.addPoint( x, mCanvas.getHeight() - y, currTime );
            circlePath.reset();
            circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
        }
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);
        circlePath.reset();
        // commit the path to our offscreen
        mCanvas.drawPath(mPath,  mPaint);
        // kill this so we don't double draw
        mPath.reset();
        sketch.addStroke(stroke);
        Log.d("Stroke", sketch.jsonString());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

    public Sketch getSketch() {
        return sketch;
    }

    public void clear() {
        mPath.reset();
        mBitmap.eraseColor(Color.WHITE);
        sketch = new Sketch();
        invalidate();
    }
}
