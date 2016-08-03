package sketchImpl;

/**
 * Created by ElifYagmur on 20.07.2016.
 */

import java.util.ArrayList;

public class Sketch implements JSONable {
    private ArrayList<Stroke> strokes;
    private String skid;
    private Stroke curStroke;
    private String strokesString;

    public Sketch() {
        skid = System.currentTimeMillis() + "";
        strokes = new ArrayList<Stroke>();
        strokesString = "";
    }

    public void addStroke( Stroke stroke ) {
        strokes.add(stroke);
        if( strokes.size() == 1 ) {
            strokesString += stroke.jsonString();
        }
        else {
            strokesString += ", " + stroke.jsonString();
        }
    }

    public String getJsonString() {
        return "{\"id\":\""+skid+"\",\"strokes\": ["+ strokesString +"] }";
    }

    public void addPoint(double x, double y, long timestamp) {
        curStroke.addPoint(x,y,timestamp);
    }

    public String jsonString() {
        String result = "";

        for (int i = 0; i < strokes.size(); i++) {
            result += strokes.get(i).jsonString();

            if (i < strokes.size() - 1) {
                result += ",";
            }
        }

        result = "[" + result + "]";
        result = "{\"id\":\""+skid+"\",\"strokes\":"+ result +"}";

        return result;
    }


    public ArrayList<Stroke> getStrokeList() {
        return strokes;
    }

    public void erase() {
        skid = System.currentTimeMillis() + "";
        strokes = new ArrayList<Stroke>();
    }

    public void undo() {
        if (!strokes.isEmpty()) {
            strokes.remove(strokes.size() - 1);
        }
    }

    public void delete(int i){
        if (!strokes.isEmpty()) {
            strokes.remove(i);
        }
    }

    public void addPositionStroke( int num , Stroke stroke ) {
        strokes.add(num , stroke);
        String[] sprtdStr = strokesString.split(",");
        sprtdStr[num] = stroke.jsonString()+ ", " + sprtdStr[num];
    }
}
