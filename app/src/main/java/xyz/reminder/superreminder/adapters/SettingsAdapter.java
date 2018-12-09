package xyz.reminder.superreminder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import xyz.reminder.superreminder.R;
import xyz.reminder.superreminder.activities.MainActivity;
import xyz.reminder.superreminder.controllers.StyleController;

import java.util.ArrayList;
import java.util.List;

public class SettingsAdapter extends  ArrayAdapter<List<Integer>> {

    List<RadioButton> radioButtons;
    StyleController styleController;
    MainActivity act;

    public SettingsAdapter(@NonNull Context context, int resource, List<List<Integer>> items, MainActivity activity) {
        super(context, resource, items);
        this.radioButtons = new ArrayList<>(5);
        this.styleController = activity.getStyleController();
        this.act = activity;
    }

    @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            List<Integer> colors = getItem(position);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            CardView cardV = (CardView) inflater.inflate(R.layout.settings_card, parent, false);

            RadioButton radioButton = cardV.findViewById(R.id.radioButton);
            ImageView backround = cardV.findViewById(R.id.color0);
            ImageView primary = cardV.findViewById(R.id.color1);
            ImageView secondary = cardV.findViewById(R.id.color2);
            ImageView text_primary = cardV.findViewById(R.id.color3);
            ImageView text_secondary = cardV.findViewById(R.id.color4);

            radioButton.setOnClickListener((view) -> {
                int newColorsIdx = styleController.getColorsIdx();
                System.out.println(newColorsIdx);
                for(int i = 0; i < radioButtons.size(); i++) {
                    if(!view.equals(radioButtons.get(i))) {
                        radioButtons.get(i).setChecked(false);
                    } else {
                        radioButtons.get(i).setChecked(true);
                        newColorsIdx = i;
                    }
                }
                styleController.setColorsIdx(newColorsIdx);
                act.getSharedPreferences().edit().putInt("colors", newColorsIdx).commit();
                styleController.applyColorsRefresh(act, act.getBackgroundColorMap(), null, act.getImageColorMap());
            });

            radioButtons.add(radioButton);
            if(radioButtons.size() -1 == styleController.getColorsIdx())
                radioButton.setChecked(true);

            backround.setBackgroundColor(getContext().getColor(colors.get(StyleController.BACKGROUND)));
            primary.setBackgroundColor(getContext().getColor(colors.get(StyleController.PRIMARY)));
            secondary.setBackgroundColor(getContext().getColor(colors.get(StyleController.SECONDARY)));
            text_primary.setBackgroundColor(getContext().getColor(colors.get(StyleController.TEXT_PRIMARY)));
            text_secondary.setBackgroundColor(getContext().getColor(colors.get(StyleController.TEXT_SECONDARY)));

            return  cardV;
        }
}

