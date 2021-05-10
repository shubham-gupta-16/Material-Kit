package com.shubhamgupta16.materialkitsample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final Activity activity;
    private final ArrayList<String> strings;

    public MyAdapter(Activity context, ArrayList<String> strings) {
        this.activity = context;
        this.strings = strings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.name.setText(strings.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, position + "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(android.R.id.text1);
        }
    }
}
