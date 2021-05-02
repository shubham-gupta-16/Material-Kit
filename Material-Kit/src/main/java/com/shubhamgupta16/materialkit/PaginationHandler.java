package com.shubhamgupta16.materialkit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PaginationHandler {
    private boolean isScrollLoad = false;
    private int currentItems, totalItems, scrollOutItems;
    private int pageCount = 0, lastFetch = 0;

    public interface OnScrolledListener{
        void fetchThisPage(int page);
    }

    public PaginationHandler(RecyclerView recyclerView, final LinearLayoutManager manager, final OnScrolledListener onScrolledListener) {
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if (isScrollLoad && (currentItems + scrollOutItems == totalItems)) {
                    if (lastFetch < pageCount) {
                        isScrollLoad = false;
                        onScrolledListener.fetchThisPage(lastFetch + 1);
                    }
                }
            }
        });
    }

    public void fetchSuccess(int pageCount, int page){
        isScrollLoad = true;
            this.pageCount = pageCount;
            lastFetch = page;
    }

    public void fetchFailed(){
        isScrollLoad = true;
    }

    public void reset(){
        lastFetch = 0;
        pageCount = 0;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getLastFetchPage() {
        return lastFetch;
    }
}
