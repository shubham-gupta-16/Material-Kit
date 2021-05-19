package com.shubhamgupta16.materialkitsample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.shubhamgupta16.materialkit.KitSuggestionView;
import com.shubhamgupta16.materialkit.KitSearchView;
import com.shubhamgupta16.materialkit.UtilsKit;

public class MainActivity extends AppCompatActivity {

    private KitSearchView searchView;
    private KitSuggestionView suggestionView;
    private View searchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchLayout = findViewById(R.id.searchLayout);
        searchView = findViewById(R.id.searchView);
        suggestionView = findViewById(R.id.suggestionView);

//        Search View
        searchView.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsKit.circleReveal(searchLayout, false, searchView.getMeasuredWidth() - searchView.getMeasuredHeight() / 2, searchView.getMeasuredHeight() / 2);
                UtilsKit.hideKeyboard(MainActivity.this);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        suggestionView.setOnSuggestionListener(new KitSuggestionView.OnSuggestionListener() {
            @Override
            public void onClick(String suggestion, int position, boolean isHistory, boolean isArrowButtonClicked) {
                searchView.setQuery(suggestion, !isArrowButtonClicked);
            }

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


    public void searchButtonClick(View view) {
        UtilsKit.circleReveal(searchLayout, true, searchView.getMeasuredWidth() - searchView.getMeasuredHeight() / 2, searchView.getMeasuredHeight() / 2);
        UtilsKit.showKeyboard(this, searchView);
    }
}