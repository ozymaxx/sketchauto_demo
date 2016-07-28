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

    public Stroke(int width) {
        this.width = width;
        points = new ArrayList<Point>();
        sid = System.currentTimeMillis() + "";
    }

    public void addPoint(double x, double y, long timestamp) {
        x = (double)Math.round(x * 10000d) / 10000;
        y = (double)Math.round(y * 10000d) / 10000;
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

/*
    public Color getColor() {
        return color;
    }
    */
}
