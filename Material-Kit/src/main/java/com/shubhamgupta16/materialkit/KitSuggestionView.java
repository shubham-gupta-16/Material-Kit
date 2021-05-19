package com.shubhamgupta16.materialkit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KitSuggestionView extends RecyclerView {
    public KitSuggestionView(@NonNull Context context) {
        super(context);
        init();
    }

    public KitSuggestionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KitSuggestionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SugAdapter adapter;
    public ArrayList<SuggestionModel> models;
    private SQLiteDatabase database;
    private int _limit = 20;

    private void init() {
        initSqlDB();
        models = new ArrayList<>();
        adapter = new SugAdapter(getContext(), models);
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(adapter);
        filterSuggestion("", null);
    }

    public void setLimit(int limit) {
        this._limit = limit;
        ArrayList<SuggestionModel> suggestionModels = new ArrayList<>(models);
        _filter(suggestionModels);
    }

    public void setOnSuggestionListener(OnSuggestionListener onSuggestionListener) {
        adapter.setOnSuggestionListener(onSuggestionListener);
    }

    static class SuggestionModel {
        private final String text;
        private final boolean isHistory;

        public SuggestionModel(String text, boolean isHistory) {
            this.text = text;
            this.isHistory = isHistory;
        }

        public String getText() {
            return text;
        }

        public boolean isHistory() {
            return isHistory;
        }
    }

    public interface OnSuggestionListener {
        void onClick(String suggestion, int position, boolean isHistory, boolean isArrowButtonClicked);

        boolean onLongPress(String suggestion, int position, boolean isHistory);
    }

    public void filterSuggestion(@NonNull String newText, @Nullable ArrayList<String> strings) {
        ArrayList<SuggestionModel> suggestionModels = new ArrayList<>();
        int localLimit = _limit;
        Cursor c;
        if (newText.isEmpty())
            c = database.rawQuery("SELECT text FROM history ORDER BY id DESC", null);
        else
            c = database.rawQuery("SELECT text FROM history WHERE text LIKE '%" + newText + "%' ORDER BY id DESC", null);
        while (c.moveToNext()) {
            suggestionModels.add(new SuggestionModel(c.getString(0), true));
            localLimit--;
        }
        c.close();

        if (strings != null)
            for (int i = 0; i < Math.min(localLimit, strings.size()); i++) {
                suggestionModels.add(new SuggestionModel(strings.get(i), false));
            }
        _filter(suggestionModels);
    }



    public void removeHistory(int position){
        if (models.size() <= position) return;
        String text = models.get(position).getText();
        removeHistory(text);
        models.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, models.size());
    }

    public void removeHistory(String text){
        database.execSQL("DELETE FROM history WHERE text = '" + text + "';");
    }

    public void addHistory(String text) {
        try {
            database.execSQL("INSERT INTO history (text) VALUES ('" + text + "');");
        } catch (Exception ignored){

        }
    }

    private void initSqlDB() {
        database = getContext().openOrCreateDatabase("kit-history2", Context.MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS history (id INTEGER PRIMARY KEY AUTOINCREMENT, text VARCHAR UNIQUE);");
    }

    private void _filter(ArrayList<SuggestionModel> suggestionModels) {
        models.clear();
        for (int i = 0; i < Math.min(_limit, suggestionModels.size()); i++) {
            models.add(suggestionModels.get(i));
        }
        adapter.notifyDataSetChanged();
    }
}
