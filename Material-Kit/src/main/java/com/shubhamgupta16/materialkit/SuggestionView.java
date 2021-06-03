package com.shubhamgupta16.materialkit;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SuggestionView extends RecyclerView {
    public SuggestionView(@NonNull Context context) {
        super(context);
    }

    public SuggestionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private TypedArray a;
    public _SuggestionAdapter adapter;
    public ArrayList<SuggestionModel> models;
    private SQLiteDatabase database;
    private int _limit;

    public SuggestionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a = context.obtainStyledAttributes(attrs, R.styleable.SuggestionView, defStyleAttr, 0);

        initialize();

        if (a.hasValue(R.styleable.SuggestionView_android_fontFamily))
            setItemFontFamily(a.getResourceId(R.styleable.SuggestionView_android_fontFamily, -1));
        if (a.hasValue(R.styleable.SuggestionView_historyIcon))
            setHistoryIcon(a.getResourceId(R.styleable.SuggestionView_historyIcon, -1));
        if (a.hasValue(R.styleable.SuggestionView_suggestionIcon))
            setSuggestionIcon(a.getResourceId(R.styleable.SuggestionView_suggestionIcon, -1));
        if (a.hasValue(R.styleable.SuggestionView_copyIcon))
            setCopyIcon(a.getResourceId(R.styleable.SuggestionView_copyIcon, -1));
        if (a.hasValue(R.styleable.SuggestionView_itemBackground))
            setItemBackground(a.getResourceId(R.styleable.SuggestionView_itemBackground, -1));
        if (a.hasValue(R.styleable.SuggestionView_iconTint))
            setIconTint(a.getColor(R.styleable.SuggestionView_iconTint, Color.BLACK));
        setDivider(a.getBoolean(R.styleable.SuggestionView_itemDivider, false));
        a.recycle();
    }

    public void setItemFontFamily(int fontRes) {
        adapter.setTypeface(ResourcesCompat.getFont(getContext(), fontRes));
    }

    public void setItemBackground(@DrawableRes int res) {
        adapter.setBackground(res);
    }

    public void setHistoryIcon(@DrawableRes int res) {
        Log.d("tagtag", "inhere");
        adapter.setHistoryIcon(res);
    }

    public void setIconTint(@ColorInt int colorRes) {
        adapter.setIconTint(colorRes);
    }

    public void setSuggestionIcon(@DrawableRes int res) {
        adapter.setSuggestionIcon(res);
    }

    public void setCopyIcon(@DrawableRes int res) {
        adapter.setCopyIcon(res);
    }

    public void setDivider(boolean divider) {
        if (divider)
            addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        else if (getItemDecorationCount() > 0)
            removeItemDecorationAt(0);

    }

    public void setLimit(int limit) {
        if (models == null) return;
        this._limit = limit;
        ArrayList<SuggestionModel> suggestionModels = new ArrayList<>(models);
        _filter(suggestionModels);
    }

    public void setOnSuggestionClickListener(OnSuggestionClickListener onSuggestionClickListener) {
        adapter.setOnSuggestionClickListener(onSuggestionClickListener);
    }

    public void setOnSuggestionLongPressListener(OnSuggestionLongPressListener onSuggestionLongPressListener) {
        adapter.setOnSuggestionLongPressListener(onSuggestionLongPressListener);
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


    public void removeHistory(int position) {
        if (models.size() <= position) return;
        String text = models.get(position).getText();
        removeHistory(text);
        models.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, models.size());
    }

    public void removeHistory(String text) {
        database.execSQL("DELETE FROM history WHERE text = '" + text + "';");
    }

    public void addHistory(String text) {
        try {
            database.execSQL("INSERT INTO history (text) VALUES ('" + text + "');");
        } catch (Exception ignored) {

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

    private void initialize() {
        initSqlDB();
        _limit = 20;
        models = new ArrayList<>();
        adapter = new _SuggestionAdapter(getContext(), models);
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(adapter);
        filterSuggestion("", null);
    }

    public interface OnSuggestionClickListener {
        void onClick(String suggestion, int position, boolean isHistory);

        void onCopyClick(String suggestion, int position, boolean isHistory);

    }

    public interface OnSuggestionLongPressListener {
        boolean onLongPress(String suggestion, int position, boolean isHistory);
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


}
