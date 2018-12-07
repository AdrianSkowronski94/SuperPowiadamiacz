package xyz.reminder.superreminder.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import xyz.reminder.superreminder.R;
import xyz.reminder.superreminder.activities.MainActivity;
import xyz.reminder.superreminder.controllers.StyleController;

import java.util.HashMap;
import java.util.Map;

public class ListFragment extends StyleFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    protected void fillColorMaps() {
        backgroundColorMap = new HashMap<>(1);
        backgroundColorMap.put(R.id.fragment_layout, StyleController.BACKGROUND);

        textColorMap = new HashMap<>(1);
        textColorMap.put(R.id.list_text, StyleController.TEXT_PRIMARY);
    }
}
