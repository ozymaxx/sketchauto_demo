package summerresearch.iui.ku.autocompletiondemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;

import at.markushi.ui.CircleButton;
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
    private Path mPath2;
    private Paint mBitmapPaint;
    Context context;
    private Paint circlePaint;
    private Path circlePath;
    Paint mPaint;
    Stroke stroke;
    Sketch sketch;
    CircleButton  drawbtn;
    CircleButton  sendbtn;
    private ArrayList<Path> paths;
    DecimalFormat decimalFormat;
    ArrayList<Integer> removedPathIndex = null;
    boolean httpReady;
    MainActivity ma;
    private boolean eraseMode;
    private int thrshld = 30;


    public DrawingView(MainActivity c, CircleButton sendbtn, CircleButton drawbtn, Paint p) {
        super((Context)c);
        this.ma = c;
        paths = new ArrayList<Path>();
        context = c;
        mPath = new Path();
        mPath2 = new Path();
        mPaint = p;
        this.drawbtn = drawbtn;
        this.sendbtn = sendbtn;
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        circlePaint = new Paint();
        circlePath = new Path();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(4f);
        sketch = new Sketch();
        decimalFormat = new DecimalFormat("#.0000");
        removedPathIndex = new ArrayList<Integer>();
        httpReady = true;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath(mPath, mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth((float)(mPaint.getStrokeWidth()*2));
        for (Integer index : removedPathIndex)
        {
            canvas.drawPath(paths.get(index), mPaint);
        }

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth((float)(mPaint.getStrokeWidth()/2));
        for (int index = 0; index < paths.size(); index++){
            if (!removedPathIndex.contains(new Integer(index))){
                canvas.drawPath(paths.get(index), mPaint);
            }
        }
        canvas.drawPath(circlePath, circlePaint);

    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 0.4f;

    public long getTime() {
        long time= System.nanoTime();
        return time;
    }

    private void touch_start(float x, float y)
    {
        if(!eraseMode) {
            mPath = new Path();
            paths.add(mPath);
            stroke = new Stroke(width);
            drawbtn.setVisibility(View.INVISIBLE);
            sendbtn.setVisibility(View.VISIBLE);
            //mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
            long currTime = getTime();
            stroke.addPoint(x, mCanvas.getHeight() - y, currTime);
        }else{
            mX = x;
            mY = y;
        }

    }

    private void touch_move(float x, float y)
    {
        if(!eraseMode) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;

                long currTime = getTime();
                stroke.addPoint(x, mCanvas.getHeight() - y, currTime);
                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }
    }

    private void touch_up() {

        if(eraseMode) {
            Log.d("del",""+paths.size() + "     " + mX + "     " + mY );


            for (int i = 0; i < paths.size() ; i++) {

                if (removedPathIndex.contains(i)){
                    continue;
                }

                int recogFlag = 0;
                PathMeasure pm = new PathMeasure(paths.get(i), false);
                float aCoordinates[] = {0f, 0f};


                for (int j = 0; j < pm.getLength() ; j++) {
//                    Log.d("del",sketch.getStrokeList().get(i).getPointList().get(j).getX() + "        x      " + mX);
//                    Log.d("del",sketch.getStrokeList().get(i).getPointList().get(j).getY() + "        y      " + (mCanvas.getHeight() - mY));

                    pm.getPosTan(j, aCoordinates, null);

                    //find the points which are in touched area
                    float xx = aCoordinates[0];
                    float yy = aCoordinates[1];


                    if ((xx > (mX - thrshld)) &&
                            (xx < (mX + thrshld)) &&
                            (yy > (mCanvas.getHeight() - mY - thrshld)) &&
                            (yy < (mCanvas.getHeight() - mY + thrshld))) {


                        recogFlag = 1;
                        Log.d("del", "3" + "            "  + i);

                    }
                    if (recogFlag == 1) {
                        removedPathIndex.add(i);
                    }

                    //find a point in the stroke
                    if (recogFlag == 1)
                        break;
                }


                if (recogFlag == 1) {
                    int counter = 0;
                    for (int j= 0 ; j < i+1 ;j++){
                        Log.d("del", "444444444444444444444" + "            " + counter+"    "+ i);
                        if (!(removedPathIndex.contains(j))) {
                            counter++;
                        }
                    }
                    Log.d("del", "33333333333333333333333333333" + "            " + counter);
                    //sketch.delete(counter);
                }


                pm.isClosed();
                aCoordinates = null;
            }

            Log.d("del", "5");
            eraseMode = false;
            invalidate();
        }else {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // paths.add(mPath);
            sketch.addStroke(stroke);
            // kill this so we don't double draw
            //mPath.reset();
            Log.d("Stroke", sketch.jsonString());
        }
        if (httpReady) {
            ma.send(this);
            httpReady = false;
        }
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
        //mPath.reset();
        mPath = new Path();
        mBitmap.eraseColor(Color.WHITE);
        sketch = new Sketch();
        removedPathIndex.clear();
        paths.clear();
        invalidate();
    }

    public Canvas getCanvas() {
        return mCanvas;
    }

    public void undo()
    {
        if (!paths.isEmpty() && !(paths.size() == removedPathIndex.size())){
            // find the first non-deleted index from path
            int index = paths.size() -1 ;
            while (removedPathIndex.contains(index)) {
                index--;
            }
            removedPathIndex.add(index);
            //paths.remove(paths.size()-1);

            sketch.undo();
            invalidate();

            if (httpReady)  {
                ma.send(this);
                httpReady = false;
            }
        }
        else {
            clear();
        }
    }

    public void eraseStrk (boolean b){
        eraseMode = b;
        invalidate();
        return;
    }

    public void HttpResult(){
        httpReady = true;
    }
}
