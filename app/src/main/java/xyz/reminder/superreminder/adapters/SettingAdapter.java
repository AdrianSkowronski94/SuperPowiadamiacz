package xyz.reminder.superreminder.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.reminder.superreminder.R;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder> {

    private String[] setColors;
    private int[] images;

    public class SettingViewHolder extends RecyclerView.ViewHolder{

        public TextView colorText;
        public ImageView colorImage;

        public SettingViewHolder(View itemView) {
            super(itemView);

            colorText = itemView.findViewById(R.id.colorText);
            colorImage = itemView.findViewById(R.id.colorImage);
        }
    }

    public SettingAdapter(String[] setColors, int[] images)
    {
        this.setColors = setColors;
        this.images = images;
    }

    @NonNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.setting_card, viewGroup, false);
        return new SettingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingViewHolder settingViewHolder, int i) {
        String set = setColors[i];
        settingViewHolder.colorText.setText(set);
        int image = images[i];
//        settingViewHolder.colorImage[]
    }

    @Override
    public int getItemCount() {
        return setColors.length;
    }

}
