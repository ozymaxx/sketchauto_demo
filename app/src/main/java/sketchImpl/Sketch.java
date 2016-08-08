package sketchImpl;

/**
 * Created by ElifYagmur on 20.07.2016.
 */

import android.util.Log;

import java.util.ArrayList;

public class Sketch implements JSONable {
    private ArrayList<Stroke> strokes;
    private String skid;
    private String strokesString;
    private ArrayList<Integer> startIndexes;
    private ArrayList<Integer> sizes;

    public Sketch() {
        skid = System.currentTimeMillis() + "";
        strokes = new ArrayList<Stroke>();
        strokesString = "";
        startIndexes = new ArrayList<Integer>();
        sizes = new ArrayList<Integer>();
    }

    public void addStroke( Stroke stroke ) {
        strokes.add(stroke);
        if( strokes.size() <= 1 ) {
            strokesString += stroke.jsonString();
            startIndexes.add(0);
            sizes.add(stroke.jsonString().length());
        }
        else {
            strokesString += ",";
            startIndexes.add(strokesString.length());
            strokesString += stroke.jsonString();
            sizes.add(stroke.jsonString().length());
        }
    }

    public String getJsonString() {
        Log.d("tag", "getJsonString: " + "{\"id\":\""+skid+"\",\"strokes\":["+ strokesString +"]}" );
        return "{\"id\":\""+skid+"\",\"strokes\":["+ strokesString +"]}";
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
            deleteStrokeFromJsonString( strokes.size() - 1  );
        }
    }

    public void deleteStrokeFromJsonString( String sId ) {
        //Find index of the stroke to delete
        int index = -1;
        for( int i = 0; i < strokes.size(); i++ ) {
            if( strokes.get(i).getStrokeId().equals( sId ) ) {
                Log.d("tag", "inside if" + sId );
                index = i;
            }
        }
        deleteStrokeFromJsonString( index );
    }

    public void deleteStrokeFromJsonString( int index ) {

        String firstPart = "";
        String secondPart = "";

        if( index != 0 ) {
            firstPart = strokesString.substring( 0, startIndexes.get( index ) - 1 );
        }

        if( index != ( strokes.size() - 1 ) ) {
            secondPart = strokesString.substring( startIndexes.get(index + 1) );

            for(  int i = index; i < strokes.size() - 1; i++ ) {
                startIndexes.set( i + 1, startIndexes.get(i) );
            }
        }
        Log.d("tag", "begin" + strokesString );
        strokesString = firstPart + secondPart;
        Log.d("tag", "end  " + strokesString );
        startIndexes.remove( index );
        sizes.remove( index );
        strokes.remove( index );
    }

    public void delete(int i){
        if (!strokes.isEmpty()) {
            deleteStrokeFromJsonString( i );
        }
    }

    public void addPositionStroke( int num , Stroke stroke ) {
        strokes.add(stroke);
        if( strokes.size() <= 1 ) {
            strokesString += stroke.jsonString();
            startIndexes.add(0);
            sizes.add(stroke.jsonString().length());
        }
        else {
            strokesString += ",";
            startIndexes.add(strokesString.length());
            strokesString += stroke.jsonString();
            sizes.add(stroke.jsonString().length());
        }
    }
}
