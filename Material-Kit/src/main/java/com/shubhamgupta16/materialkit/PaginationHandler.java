package com.shubhamgupta16.materialkit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PaginationHandler {
    private boolean isScrollLoad = false;
    private int currentItems, totalItems, scrollOutItems;
    private int maxPage = 0, lastFetch = 0;

    public interface OnScrolledListener {
        void onScrolledToBottom(int page);
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
                    if (lastFetch < maxPage) {
                        isScrollLoad = false;
                        if (onScrolledListener != null)
                            onScrolledListener.onScrolledToBottom(lastFetch + 1);
                    }
                }
            }
        });

    }

    public void dataFetched(boolean isFetched, int maxPage, int page) {
        isScrollLoad = true;
        if (isFetched) {
            this.maxPage = maxPage;
            lastFetch = page;

        }
    }

    public void reset() {
        lastFetch = 0;
        maxPage = 0;
    }
}