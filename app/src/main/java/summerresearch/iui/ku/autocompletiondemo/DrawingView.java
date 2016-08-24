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
import java.util.Arrays;
import java.util.List;

import at.markushi.ui.CircleButton;
import sketchImpl.Sketch;
import sketchImpl.Stroke;

/**
 * Created by ElifYagmur on 20.07.2016.
 */
public class DrawingView extends View {

    public int width;
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
    Sketch sketchShadow;
    CircleButton  drawbtn;
    CircleButton  sendbtn;
    private ArrayList<Path> paths;
    DecimalFormat decimalFormat;
    ArrayList<Integer> removedPathIndex = null;
    boolean httpReady;
    MainActivity mainActivity;
    private boolean eraseMode;
    private int thrshld = 20;
    ArrayList<Integer> change; // 1 for drawing and 0 for erasing
    List<List<Integer>> changeIndex; // each List means indexes of changed strokes


    public DrawingView(MainActivity mainActivity, CircleButton sendbtn, CircleButton drawbtn, Paint p) {
        super((Context) mainActivity);
        this.mainActivity = mainActivity;
        paths = new ArrayList<Path>();
        context = mainActivity;
        mPath = new Path();
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
        sketchShadow = new Sketch();
        decimalFormat = new DecimalFormat("#.0000");
        removedPathIndex = new ArrayList<Integer>();
        httpReady = true;
        change = new ArrayList<Integer>();
        changeIndex = new ArrayList<List<Integer>>();
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
        Log.d("size",""+mCanvas.getHeight());
        Log.d("size",""+mCanvas.getWidth());
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
//            Log.d("del",""+paths.size() + "     " + mX + "     " + mY );

            int changeFlag = 0;


            for (int i = 0; i < paths.size() ; i++) {

                if (removedPathIndex.contains(i)){
                    continue;
                }

                int recogFlag = 0;
                PathMeasure pm = new PathMeasure(paths.get(i), false);
                float aCoordinates[] = {0f, 0f};


                for (int j = 0; j < pm.getLength(); j++) {


                    pm.getPosTan(j, aCoordinates, null);



                    //find the points which are in touched area
                    float xx = aCoordinates[0];
                    float yy = aCoordinates[1];

                    if ((xx > (mX - thrshld)) &&
                            (xx < (mX + thrshld)) &&
                            (yy > (mY - thrshld)) &&
                            (yy < (mY + thrshld))) {


                        recogFlag = 1;

                        if (changeFlag == 0) {
                            change.add(0);
                            changeIndex.add(new ArrayList<Integer>());
                            changeIndex.get(changeIndex.size() - 1).add(i);
                            changeFlag = 1;
                        }else{
                            changeIndex.get(changeIndex.size() - 1).add(i);
                        }

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
                        if (!(removedPathIndex.contains(j))) {
                            counter++;
                        }
                    }
                    Log.d("check", "i   =    " + i);

                    sketch.delete( sketchShadow.getStrokeList().get( i ).getStrokeId() );

                }

                aCoordinates = null;
                pm.isClosed();
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
            sketchShadow.addStroke(stroke);
            change.add(1);
            changeIndex.add(Arrays.asList(paths.size() - 1));
//            Log.d("check", ""+ sketchShadow.getStrokeList().size());
            // kill this so we don't double draw
            //mPath.reset();
            //Log.d("Stroke", sketch.jsonString());
        }
        if (httpReady) {
            if(sketch.getStrokeList().size() > 0) {
                mainActivity.send(sketch);
                //httpReady = false;
            }
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
        sketchShadow = new Sketch();
        change.clear();
        changeIndex.clear();
        removedPathIndex.clear();
        paths.clear();
        invalidate();

        if (httpReady) {
            if(sketch.getStrokeList().size() > 0) {
                mainActivity.send(sketch);
                //httpReady = false;
            }
        }
    }

    public Canvas getCanvas() {
        return mCanvas;
    }

    public void undo()
    {
        if (change.size() != 0) {
            if (change.get(change.size() - 1) == 1) {
                if (!paths.isEmpty() && !(paths.size() == removedPathIndex.size())) {
                    // find the first non-deleted index from path
                    int index = paths.size() - 1;
                    while (removedPathIndex.contains(index)) {
                        index--;
                    }
                    removedPathIndex.add(index);
                    //paths.remove(paths.size()-1);
                    Log.d("check","index  =   " + paths.size());
                    sketch.delete( sketchShadow.getStrokeList().get( index ).getStrokeId() );

                } else {
                    clear();
                }
            } else {
                Log.d("undo",""+changeIndex.get(changeIndex.size() - 1).size());
                for(int i = 0 ; i < (changeIndex.get(changeIndex.size() - 1)).size() ; i++) {
                    int indexComeBack = (changeIndex.get(changeIndex.size() - 1).get(i));
                    removedPathIndex.remove(Integer.valueOf(indexComeBack));

//                    int prevNum = 0;
//                    for(int j = 0 ; j < removedPathIndex.size() ; j++){
//                        if(j < indexComeBack)
//                            prevNum++;
//                    }
                    Log.d("check","indexComeBack  =   " + indexComeBack);
                    sketch.addStroke( sketchShadow.getStrokeList().get( indexComeBack ) );
                }
            }
            change.remove(change.size() - 1);
            changeIndex.remove(changeIndex.size() - 1);
            //
        }
        invalidate();

        if (httpReady) {
            if(sketch.getStrokeList().size() > 0) {
                mainActivity.send(sketch);
                //httpReady = false;
            }
        }
    }

    public void eraseStrk (boolean b){
        eraseMode = b;
        invalidate();
        return;
    }

    //public void HttpResult(){
       // httpReady = true;
   // }
}
