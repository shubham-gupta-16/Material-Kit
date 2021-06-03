package com.shubhamgupta16.materialkitsample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shubhamgupta16.materialkit.AnimUtils;
import com.shubhamgupta16.materialkit.SuggestionView;
import com.shubhamgupta16.materialkit.SearchView;
import com.shubhamgupta16.materialkit.PageView;
import com.shubhamgupta16.materialkit.UtilsKit;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private SuggestionView suggestionView;
    private View searchLayout;
    private PageView pageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pageView = findViewById(R.id.pageView);
        searchLayout = findViewById(R.id.searchLayout);
        searchView = findViewById(R.id.searchView);
        suggestionView = findViewById(R.id.suggestionView);

        pageView.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.actionSearch) {
                suggestionView.filterSuggestion(searchView.getQuery(), null);
                AnimUtils.circleReveal(searchLayout, searchView.getMeasuredWidth() - (int) UtilsKit.dpToPx(105, this), searchView.getMeasuredHeight() / 2).setDuration(400).start();
                UtilsKit.showKeyboard(MainActivity.this, searchView);
            }
            return false;
        });

//        Search View
        searchView.setOnBackButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.circleUnReveal(searchLayout,  searchView.getMeasuredWidth() - (int) UtilsKit.dpToPx(105, MainActivity.this), searchView.getMeasuredHeight() / 2).setDuration(400).start();;
                UtilsKit.hideKeyboard(MainActivity.this);
            }
        });
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Searched: " + query, Toast.LENGTH_SHORT).show();
                UtilsKit.hideKeyboard(MainActivity.this);
                suggestionView.addHistory(query);
                searchLayout.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                suggestionView.filterSuggestion(newText, null);
                return false;
            }
        });

//        Suggestion View
        suggestionView.setLimit(10);
        suggestionView.setOnSuggestionClickListener(new SuggestionView.OnSuggestionClickListener() {
            @Override
            public void onClick(String suggestion, int position, boolean isHistory) {
                searchView.setQuery(suggestion, true);
            }

            @Override
            public void onCopyClick(String suggestion, int position, boolean isHistory) {
                searchView.setQuery(suggestion, false);
            }
        });
        suggestionView.setOnSuggestionLongPressListener(new SuggestionView.OnSuggestionLongPressListener() {
            @Override
            public boolean onLongPress(String suggestion, int position, boolean isHistory) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(suggestion);
                builder.setMessage("Remove from search history?");
                builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        suggestionView.removeHistory(position);
                    }
                });
                builder.setNegativeButton("CANCEL", null);
                builder.create().show();
                return true;
            }
        });
    }

    int badge = 0;

    public void addBadge(View view) {
        pageView.setMenuBadge(R.id.actionCart, ++badge, true);
    }

    public void removeAllBadge(View view) {
        pageView.setMenuBadge(R.id.actionCart, badge = 0, true);
    }


//    public void searchButtonClick(View view) {
//        UtilsKit.circleReveal(searchLayout, true, searchView.getMeasuredWidth() - searchView.getMeasuredHeight() / 2, searchView.getMeasuredHeight() / 2);
//        UtilsKit.showKeyboard(this, searchView);
//    }
}