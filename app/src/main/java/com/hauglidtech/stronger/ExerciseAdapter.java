package com.hauglidtech.stronger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by haugl on 01.04.2017.
 */

public class ExerciseAdapter extends ArrayAdapter<String> {

    public ExerciseAdapter(Context context, String[] exercise) {
        super(context,R.layout.exercise_item, exercise);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater exerciseInflater = LayoutInflater.from(getContext());
        View customView = exerciseInflater.inflate(R.layout.exercise_item, parent, false);

        String singleExercise = getItem(position);
        TextView exerciseText = (TextView) customView.findViewById(R.id.exercise_text);
        ImageView exerciseImage = (ImageView) customView.findViewById(R.id.exercise_image);
        exerciseText.setText(singleExercise);
        exerciseImage.setImageResource(R.drawable.ic_fitness_center_black_24dp);
        return customView;
    }
}
