package xyz.reminder.superreminder.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import xyz.reminder.superreminder.activities.MainActivity;

import java.util.Map;

public abstract class StyleFragment extends Fragment {

    protected Map<Integer, Integer> backgroundColorMap;
    protected Map<Integer, Integer> textColorMap;
    protected Map<Integer, Integer> imageColorMap;

    public StyleFragment() {
        super();
        fillColorMaps();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        applyColors();
    }

    protected abstract void fillColorMaps();

    protected void applyColors() {
        MainActivity act = (MainActivity) getActivity();
        act.getStyleController().applyColors(getActivity(), backgroundColorMap, textColorMap, imageColorMap);
    }
}
