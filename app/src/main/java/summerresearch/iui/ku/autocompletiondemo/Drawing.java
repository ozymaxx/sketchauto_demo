package summerresearch.iui.ku.autocompletiondemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by amir on 20.07.2016.
 */
public class Drawing extends View {



    Paint paint = new Paint();

    public Drawing(Context context){
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);


        paint.setColor(Color.RED);
        canvas.drawCircle(150,150,100,paint);
    }


}
