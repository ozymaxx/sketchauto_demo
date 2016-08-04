package sketchImpl;

/**
 * Created by ElifYagmur on 20.07.2016.
 */
public class Point implements JSONable {
    private double x, y;
    private long timestamp;
    private String pid;

    public Point(double x, double y, long timestamp) {
        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
        pid = "" + timestamp;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPid() {
        return pid;
    }

    public String jsonString() {
        return "{\"pointId\":\""+pid+"\",\"time\":"+timestamp+",\"x\":"+x+",\"y\":"+y+"}";
    }
}