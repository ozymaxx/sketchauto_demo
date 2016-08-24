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
    private boolean sketchupdated = false;

    public Sketch() {
        skid = System.currentTimeMillis() + "";
        strokes = new ArrayList<Stroke>();
        strokesString = "";
        startIndexes = new ArrayList<Integer>();
        sizes = new ArrayList<Integer>();
    }


    public void addStroke( Stroke stroke ) {
        strokes.add(stroke);
        Log.d("knocknock", "" + strokes.size() );
        String jsonString = stroke.jsonString();
        if( strokes.size() == 1 ) {
            strokesString = jsonString;
            startIndexes.add(0);
            sizes.add( jsonString.length() );
        }
        else {
            strokesString += ",";
            startIndexes.add(strokesString.length());
            strokesString += jsonString;
            sizes.add( jsonString.length() );
        }
        this.sketchupdated = true;
    }

    public String getJsonString() {
        Log.d("tag", "getJsonString: " + "{\"id\":\""+skid+"\",\"strokes\":["+ strokesString +"]}" );
        this.sketchupdated = false;
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

    public void undo() {
        if ( !strokes.isEmpty() ) {
            deleteStrokeFromJsonString( strokes.size() - 1  );
        }
        this.sketchupdated = true;
    }

    public void deleteStrokeFromJsonString( String sId ) {
        //Find index of the stroke to delete
        int index = -1;
        for( int i = 0; i < strokes.size(); i++ ) {
            if( strokes.get(i).getStrokeId().equals( sId ) ) {
                index = i;
            }
        }
        Log.d("json","index = " + index);
        deleteStrokeFromJsonString( index );
        this.sketchupdated = true;
    }

    public void deleteStrokeFromJsonString( int index ) {

        String firstPart = "";
        String secondPart = "";
        Log.d("StrokeIndex", "" + index);
        if( index != 0 ) {
            firstPart = strokesString.substring( 0, startIndexes.get( index ) );
        }
        Log.d("firstPart",""+ firstPart);
        if( index != ( strokes.size() - 1 ) ) {
            secondPart = strokesString.substring(startIndexes.get(index + 1));


            int l = sizes.get( index ) + 1;
            for(  int i = index + 1; i < strokes.size(); i++ ) {
                startIndexes.set( i, startIndexes.get(i) - l );
            }
        }
        else if( firstPart.length() > 0 ){
            firstPart = firstPart.substring( 0, firstPart.length()- 1 );

        }
        Log.d("MYStrokeIndex", "begin" + strokesString );
        strokesString = firstPart + secondPart;
        Log.d("MYStrokeIndex", "end  " + strokesString );
        startIndexes.remove( index );
        sizes.remove( index );
        strokes.remove( index );
        this.sketchupdated = true;
    }

    public void delete(String id){
        if (!strokes.isEmpty()) {
            deleteStrokeFromJsonString( id );
        }
        this.sketchupdated = true;
    }

    public boolean hasSketchUpdated(){
        return this.sketchupdated;
    }
}
