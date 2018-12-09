package xyz.reminder.superreminder.fragments;

import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import xyz.reminder.superreminder.R;
import xyz.reminder.superreminder.activities.MainActivity;
import xyz.reminder.superreminder.adapters.SettingAdapter;
import xyz.reminder.superreminder.controllers.StyleController;

import java.util.*;

public class SettingsFragment extends StyleFragment {

    private SettingAdapter adapter;
    private RecyclerView recyclerView;
    private RadioGroup radioGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] setColors = {"Czerwony", "Zielony"};
        int[] listImages = new int[]{
                R.drawable.red_set, R.drawable.green_set,
        };

        radioGroup = view.findViewById(R.id.radio_group);

        recyclerView = view.findViewById(R.id.settingRecyclerView);
        adapter = new SettingAdapter(setColors, listImages);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    protected void fillColorMaps() {
        backgroundColorMap = new HashMap<>(1);
        backgroundColorMap.put(R.id.fragment_layout, StyleController.BACKGROUND);
    }
}
