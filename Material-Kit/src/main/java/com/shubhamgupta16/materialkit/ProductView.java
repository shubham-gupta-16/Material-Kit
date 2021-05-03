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
    private PaginationHandler.OnScrolledListener onScrolledListener;
    private PaginationHandler handlePage;

    public void setOnScrolledListener(@NonNull PaginationHandler.OnScrolledListener onScrolledListener) {
        this.onScrolledListener = onScrolledListener;
    }

    public ProductView(Context context) {
        super(context);
        init();
    }

    public RecyclerView.Adapter getAdapter() {
        return recyclerView.getAdapter();
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

    public void setNoResultView(int noResViewRes) {
        setNoResultView(getViewFromRes(noResViewRes));
    }

    public void setLoadingView(View loadingView) {
        this.loadingView.removeAllViews();
        loadingView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.loadingView.addView(loadingView);
    }

    public void setNoResultView(View noResView) {
        this.noResView.removeAllViews();
        noResView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.noResView.addView(noResView);
    }

    public void setLayout(int orientation) {
        setLayout(orientation, gridCount, reverseLayout);
    }

    public void setLayout(int orientation, int gridCount, boolean reverseLayout) {
        if (orientation == VERTICAL)
            this.orientation = VERTICAL;
        else if (orientation == HORIZONTAL)
            this.orientation = HORIZONTAL;
        this.reverseLayout = reverseLayout;
        if (gridCount > 0 && gridCount < MAX_GRID_COL)
            this.gridCount = gridCount;

        GridLayoutManager manager = new GridLayoutManager(getContext(), gridCount, orientation, reverseLayout);
        handlePage = new PaginationHandler(recyclerView, manager, new PaginationHandler.OnScrolledListener() {
            @Override
            public void fetchThisPage(int page) {
                if (onScrolledListener != null)
                    onScrolledListener.fetchThisPage(page);
            }
        });
    }

    public void setAdapter(@NonNull RecyclerView.Adapter adapter) {
        Log.d("tagtag", "initialize");
        tryCreateNoResLayout();
        tryCreateLoadingLayout();
        loadingView.setVisibility(VISIBLE);
        noResView.setVisibility(GONE);
        if (handlePage == null)
            setLayout(orientation, gridCount, reverseLayout);
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

    public void fetchSuccess(int maxPage, int page){
        handlePage.fetchSuccess(maxPage, page);

        if (page == 1 && handlePage.getLastFetchPage() == 0)
            showNoResultLayout();
        else
            UtilsKit.fadeHideView(noResView);
    }
    public void fetchFailed(){
        handlePage.fetchFailed();
    }

    public void dataFetched(boolean isFetched, int maxPage, int page) {
        UtilsKit.fadeHideView(loadingView);

    }
    private void showNoResultLayout(){
        UtilsKit.fadeVisibleView(noResView);
    }

    public void reset(){
        handlePage.reset();
//        loadingView.setVisibility(VISIBLE);
        noResView.setVisibility(GONE);
    }


}
