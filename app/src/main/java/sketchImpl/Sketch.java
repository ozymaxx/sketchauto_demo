package sketchImpl;

/**
 * Created by ElifYagmur on 20.07.2016.
 */
import java.util.ArrayList;

/**
 * Created by ozymaxx on 12.07.2016.
 */

public class Sketch implements JSONable {
    private ArrayList<Stroke> strokes;
    private String skid;
    private Stroke curStroke;

    public Sketch() {
        skid = System.currentTimeMillis() + "";
        strokes = new ArrayList<Stroke>();
    }

    public void newStroke(int width) {
        curStroke = new Stroke(width);
        strokes.add(curStroke);
    }

    public void addStroke( Stroke stroke ) {
        strokes.add(stroke);
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
        result = "{\"id\":\""+skid+"\",\"strokes\":"+result+"}";

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
}
