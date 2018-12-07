package xyz.reminder.superreminder.controllers;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;
import xyz.reminder.superreminder.R;

import java.util.*;

public class StyleController {

    public static final int BACKGROUND = 0;
    public static final int PRIMARY = 1;
    public static final int SECONDARY = 2;
    public static final int TEXT_PRIMARY = 3;
    public static final int TEXT_SECONDARY = 4;

    public static final List<Integer> OFFLINE = new ArrayList<>(Arrays.asList(new Integer[]
            { R.color.offlineBack, R.color.offlinePrim, R.color.offlineSec, R.color.offlineTextPrim, R.color.offlineTextSec }));
    public static final List<ArrayList<Integer> > POSSIBLE_COLORS = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(new Integer[]
                    { R.color.redBack, R.color.redPrim, R.color.redSec, R.color.redTextPrim, R.color.redTextSec })),
            new ArrayList<>(Arrays.asList(new Integer[]
                    { R.color.greenBack, R.color.greenPrim, R.color.greenSec, R.color.greenTextPrim, R.color.greenTextSec }))
    ));

    private int colorsIdx = 0;
    private boolean isOffline;

    public StyleController(boolean isOffline) {
        this.isOffline = isOffline;
    }

    StyleController(boolean isOffline, int colorsIdx) {
        this.isOffline = isOffline;
        this.colorsIdx = colorsIdx;
    }

    public int getColorsIdx() { return colorsIdx; }
    public List<Integer> getColors() { return (isOffline)?  OFFLINE : POSSIBLE_COLORS.get(colorsIdx); }
    public Integer getColor(int key) { return getColors().get(key); }

    public void setColorsIdx(int colorsIdx) { this.colorsIdx = colorsIdx; }
    public void setColors(int colorsIdx) { this.setColorsIdx(colorsIdx); }
    public void setOffline(boolean offline) { isOffline = true; }
    public void setOnline() { isOffline = false; }

    public void applyColors(Activity act, Map<Integer, Integer> backgroundColorMap, Map<Integer, Integer> textColorMap, Map<Integer, Integer> filterColorMap) {
        if(backgroundColorMap != null)
            for(Integer key: backgroundColorMap.keySet()) {
                Integer color = act.getResources().getColor(this.getColor(backgroundColorMap.get(key)));
                act.findViewById(key).setBackgroundColor(color);
            }

        if(textColorMap != null)
            for(Integer key: textColorMap.keySet()) {
                Integer color = act.getResources().getColor(this.getColor(textColorMap.get(key)));
                ((TextView)act.findViewById(key)).setTextColor(color);
            }

        if(filterColorMap != null)
            for(Integer key: filterColorMap.keySet()) {
                Integer color = act.getResources().getColor(this.getColor(filterColorMap.get(key)));
                ((ImageView)act.findViewById(key)).setColorFilter(color);
            }
    }
}
