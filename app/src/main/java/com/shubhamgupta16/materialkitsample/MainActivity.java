package com.shubhamgupta16.materialkitsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.shubhamgupta16.materialkit.PageView;
import com.shubhamgupta16.materialkit.PaginationHandler;
import com.shubhamgupta16.materialkit.ProductView;
import com.shubhamgupta16.materialkit.TextInputView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProductView productView;
    private ArrayList<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextInputView textInputView = findViewById(R.id.textInputView);
        textInputView.setOnInputChangeListener(new TextInputView.OnInputChangeListener() {
            @Override
            public void onChange(CharSequence input, int count) {
                Log.d("Typed Data", input.toString());
            }
        });

        PageView pageView = findViewById(R.id.pageView);
        productView = findViewById(R.id.productView);
        pageView.setDefaultToolbarTitle("Default Title"); // if not set in xml

        pageView.getToolbar().inflateMenu(R.menu.main);
        pageView.getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        strings = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(this, strings);

        productView.setLayout(ProductView.VERTICAL, new PaginationHandler.OnScrolledListener() {
            @Override
            public void onScrolledToBottom(int page) {
                fetch(page);
                Toast.makeText(MainActivity.this, "Here", Toast.LENGTH_SHORT).show();
            }
        });
        productView.setNoResView(R.layout.no_res_layout);
        productView.setAdapter(adapter);
        fetch(1);
//        productView.showNoResLayout();
    }

    public void fetch(int page){
        productView.fetchStart();
        /*strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo");
        strings.add("Heelo " + page);*/
        productView.dataFetched(false, 0, page);
    }
}