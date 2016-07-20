package sketchImpl;

import android.graphics.Color;

import java.util.ArrayList;


import java.util.ArrayList;

/**
 * Created by ElifYagmur on 20.07.2016.
 */
public class Stroke implements JSONable {
    private ArrayList<Point> points;
    private String sid;
    private int width;
    private int colorr,colorg,colorb,colora;

    public Stroke(int width,int colorr,int colorg,int colorb,int colora) {
        this.width = width;
        this.colorr = colorr;
        this.colorg = colorg;
        this.colorb = colorb;
        this.colora = colora;

        points = new ArrayList<Point>();
        sid = System.currentTimeMillis() + "";
    }

    public void addPoint(double x, double y, long timestamp) {
        points.add( new Point(x,y,timestamp) );
    }

    public String jsonString() {
        String result = "";

        for (int i = 0; i < points.size(); i++) {
            result += points.get(i).jsonString();

            if ( i < points.size() - 1) {
                result += ",";
            }
        }

        result = "[" + result + "]";
        result = "{\"id\":\""+sid+"\",\"points\":"+result+"}";

        return result;
    }

    public ArrayList<Point> getPointList() {
        return points;
    }

    public int getWidth() {
        return width;
    }

    public int getColor() {
        int c = Color.argb(colora, colorr, colorg, colorb);
        return c;
    }
}
