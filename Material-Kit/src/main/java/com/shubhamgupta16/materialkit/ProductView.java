package com.shubhamgupta16.materialkit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductView extends RelativeLayout {

    private static final int MAX_GRID_COL = 11;
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;
    private RecyclerView recyclerView;
    private RelativeLayout loadingView, noResView;
    private int appBackgroundColor = Color.WHITE;
    private int orientation = VERTICAL;
    private int gridCount = 1;
    boolean reverseLayout = false;
    private PaginationHandler handlePage;

    public ProductView(Context context) {
        super(context);
        init();
    }

    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProductView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setAppBackgroundColor(int appBackgroundColor) {
        this.appBackgroundColor = appBackgroundColor;
    }

    public ProductView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Log.d("tagtag", "init");
        recyclerView = new RecyclerView(getContext());
        setMatchParentToView(recyclerView);
        addView(recyclerView);
        initLoadingLayout();
        initNoResLayout();
        addView(noResView);
        addView(loadingView);
    }

    public void setLoadingView(int loadingViewRes) {
        setLoadingView(getViewFromRes(loadingViewRes));
    }

    public void setNoResView(int noResViewRes) {
        Log.d("tagtag", "setNoResView");
        setNoResView(getViewFromRes(noResViewRes));
    }

    public void setLoadingView(View loadingView) {
        this.loadingView.removeAllViews();
        loadingView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.loadingView.addView(loadingView);
    }

    public void setNoResView(View noResView) {
        this.noResView.removeAllViews();
        noResView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.noResView.addView(noResView);
    }

    public void setLayout(int orientation, PaginationHandler.OnScrolledListener onScrolledListener) {
        setLayout(orientation, gridCount, reverseLayout, onScrolledListener);
    }

    public void setLayout(int orientation, int gridCount, boolean reverseLayout, PaginationHandler.OnScrolledListener onScrolledListener) {
        if (orientation == VERTICAL)
            this.orientation = VERTICAL;
        else if (orientation == HORIZONTAL)
            this.orientation = HORIZONTAL;
        this.reverseLayout = reverseLayout;
        if (gridCount > 0 && gridCount < MAX_GRID_COL)
            this.gridCount = gridCount;

        GridLayoutManager manager = new GridLayoutManager(getContext(), gridCount, orientation, reverseLayout);
        handlePage = new PaginationHandler(recyclerView, manager, onScrolledListener);
    }

    public RecyclerView.Adapter getAdapter() {
        return recyclerView.getAdapter();
    }

    public void setAdapter(@NonNull RecyclerView.Adapter adapter) {
        Log.d("tagtag", "initialize");
        tryCreateNoResLayout();
        tryCreateLoadingLayout();
        loadingView.setVisibility(VISIBLE);
        noResView.setVisibility(GONE);
        if (handlePage == null)
            setLayout(orientation, gridCount, reverseLayout, null);
        recyclerView.setAdapter(adapter);
    }

    private View getViewFromRes(int res) {
        return LayoutInflater.from(getContext()).inflate(res, null, false);
    }

    private void initLoadingLayout(){
        if (loadingView != null)return;
        RelativeLayout layout = new RelativeLayout(getContext());
        setMatchParentToView(layout);
        layout.setBackgroundColor(appBackgroundColor);
        loadingView = layout;
    }

    private void tryCreateLoadingLayout(){
        if (loadingView.getChildCount() > 0) return;
        ProgressBar progressBar = new ProgressBar(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        progressBar.setLayoutParams(params);
        loadingView.addView(progressBar);
    }

    @SuppressLint("SetTextI18n")
    private void initNoResLayout(){
        if (noResView != null)return;
        RelativeLayout layout = new RelativeLayout(getContext());
        setMatchParentToView(layout);
        layout.setBackgroundColor(appBackgroundColor);
        noResView = layout;
    }
    @SuppressLint("SetTextI18n")
    private void tryCreateNoResLayout(){
        if (noResView.getChildCount() > 0)return;
        TextView progressBar = new TextView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        progressBar.setLayoutParams(params);
        progressBar.setText("No Products Found!");
        noResView.addView(progressBar);
    }

    private void setMatchParentToView(View view){
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }

    public void fetchStart(){
        UtilsKit.fadeVisibleView(loadingView);
    }

    public void dataFetched(boolean isFetched, int maxPage, int page) {
        UtilsKit.fadeHideView(loadingView);
        handlePage.dataFetched(isFetched, maxPage, page);

        if (page == 1 && !isFetched)
            showNoResLayout();
        else
            UtilsKit.fadeHideView(noResView);
    }
    public void showNoResLayout(){
        UtilsKit.fadeVisibleView(noResView);
    }

    public void reset(){
        handlePage.reset();
//        loadingView.setVisibility(VISIBLE);
        noResView.setVisibility(GONE);
    }


}
