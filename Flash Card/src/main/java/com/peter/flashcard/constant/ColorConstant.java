package com.peter.flashcard.constant;

import com.peter.flashcard.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 5/23/2014.
 */
public class ColorConstant {
    public static Map<String,Integer> colorMap = new HashMap<String, Integer>();

    static {
        colorMap.put("n", R.color.flatui_alizarin);
        colorMap.put("v", R.color.flatui_turquoise);
        colorMap.put("adj", R.color.flatui_orange);
        colorMap.put("adv", R.color.flatui_belize_hole);
    }
}
