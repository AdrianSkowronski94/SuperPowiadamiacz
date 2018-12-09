package xyz.reminder.superreminder.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import xyz.reminder.superreminder.R;
import xyz.reminder.superreminder.activities.MainActivity;
import xyz.reminder.superreminder.adapters.SettingsAdapter;
import xyz.reminder.superreminder.controllers.StyleController;

import java.util.*;

public class SettingsFragment extends StyleFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StyleController styleController = ((MainActivity) getActivity()).getStyleController();
        List<List<Integer>> cards = new ArrayList<>(2);
        for(List<Integer> colors: StyleController.POSSIBLE_COLORS)
            cards.add(colors);

        ArrayAdapter<List<Integer>> adapter = new SettingsAdapter(getContext(), R.layout.settings_card, cards, (MainActivity) getActivity());

        ListView cardsVG = view.findViewById(R.id.cards);
        cardsVG.setAdapter(adapter);
    }

    protected void fillColorMaps() {
        backgroundColorMap = new HashMap<>(1);
        backgroundColorMap.put(R.id.fragment_layout, StyleController.BACKGROUND);
    }
}
