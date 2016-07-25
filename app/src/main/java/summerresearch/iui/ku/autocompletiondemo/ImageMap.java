package summerresearch.iui.ku.autocompletiondemo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ElifYagmur on 25.07.2016.
 */
public class ImageMap {
    private Map <String, Integer> imageMap;

    public ImageMap() {
        imageMap = new HashMap<String,Integer>() {{
            put("airplane", R.mipmap.airplane );
            put("alarm clock", R.mipmap.alarmclock );
            put("angel", R.mipmap.angel );
            put("ant", R.mipmap.ant );
            put("apple", R.mipmap.apple );
        }};
    }

    public Map<String, Integer> getImageMap() {
        return imageMap;
    }
}
