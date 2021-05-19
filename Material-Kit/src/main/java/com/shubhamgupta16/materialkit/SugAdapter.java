package com.shubhamgupta16.materialkit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

class SugAdapter extends RecyclerView.Adapter<SugAdapter.ViewHolder> {

    private final Context activity;
    private final ArrayList<KitSuggestionView.SuggestionModel> list;
    private KitSuggestionView.OnSuggestionListener onSuggestionListener;

    public SugAdapter(Context context, ArrayList<KitSuggestionView.SuggestionModel> list) {
        this.activity = context;
        this.list = list;
        this.onSuggestionListener = onSuggestionListener;
    }

    public void setOnSuggestionListener(KitSuggestionView.OnSuggestionListener onSuggestionListener) {
        this.onSuggestionListener = onSuggestionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.search_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        KitSuggestionView.SuggestionModel model = list.get(position);
        if (model.isHistory())
            holder.icon1.setImageResource(R.drawable.ic_kit_history_24);
        else
            holder.icon1.setImageResource(R.drawable.ic_kit_search_24);

        holder.text1.setText(model.getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSuggestionListener != null)
                    onSuggestionListener.onClick(model.getText(), position, model.isHistory(), false);
            }
        });
        holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSuggestionListener != null)
                    onSuggestionListener.onClick(model.getText(), position, model.isHistory(), true);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onSuggestionListener != null)
                    return onSuggestionListener.onLongPress(model.getText(), position, model.isHistory());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon1;
        private final ImageButton button1;
        private final TextView text1;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon1 = itemView.findViewById(R.id.image1);
            button1 = itemView.findViewById(R.id.button1);
            text1 = itemView.findViewById(R.id.text1);
        }
    }
}
