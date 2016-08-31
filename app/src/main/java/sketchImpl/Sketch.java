package sketchImpl;

/**
 * Created by ElifYagmur on 20.07.2016.
 */

import android.util.Log;
import java.util.ArrayList;

public class Sketch implements JSONable {
    private ArrayList<Stroke>  strokes;
    private ArrayList<Integer> startIndexes;
    private ArrayList<Integer> sizes;
    private String skid;
    private String strokesString;

    public Sketch() {
        skid = System.currentTimeMillis() + "";
        strokes = new ArrayList<Stroke>();
        strokesString = "";
        startIndexes = new ArrayList<Integer>();
        sizes = new ArrayList<Integer>();
    }


    /*
    * The addStroke method adds new stroke to strokes in the sketch.
    * It edits strokesString by adding new stroke in strokesString.
    */
    public void addStroke( Stroke stroke ) {
        strokes.add(stroke);
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
    }

    /*
    * The getJsonString method returns json string format of the sketch without traversing strokes.
    */
    public String getJsonString() {
        return "{\"id\":\""+skid+"\",\"strokes\":["+ strokesString +"]}";
    }

    /*
   * The jsonString method returns json string format of the sketch with traversing strokes.
   */
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
    }

    /*
   * The deleteStrokeFromJsonString method deletes corresponding stroke from strokesString after undo or erase operations are computed.
   */
    public void deleteStrokeFromJsonString( String sId ) {
        //Find index of the stroke to delete.
        int index = -1;
        for( int i = 0; i < strokes.size(); i++ ) {
            if( strokes.get(i).getStrokeId().equals( sId ) ) {
                index = i;
            }
        }
        deleteStrokeFromJsonString( index );
    }

    public void deleteStrokeFromJsonString( int index ) {
        //The strokesString will be divided into two if the stroke from middle is deleted.
        String firstPart = "";
        String secondPart = "";

        if( index != 0 ) {                              //If first stroke will be deleted firstPart should remain as empty string.
            firstPart = strokesString.substring( 0, startIndexes.get( index ) );
        }
        if( index != ( strokes.size() - 1 ) ) {         //If last stroke will be deleted secondPart should remain as empty string.
            secondPart = strokesString.substring(startIndexes.get(index + 1));
            int l = sizes.get( index ) + 1;
            for(  int i = index + 1; i < strokes.size(); i++ ) {
                startIndexes.set( i, startIndexes.get(i) - l );
            }
        }
        else if( firstPart.length() > 0 ){
            firstPart = firstPart.substring( 0, firstPart.length()- 1 );

        }
        strokesString = firstPart + secondPart;
        startIndexes.remove( index );
        sizes.remove( index );
        strokes.remove( index );
    }

    public void delete(String id){
        if (!strokes.isEmpty()) {
            deleteStrokeFromJsonString( id );
        }
    }

}
