package com.shubhamgupta16.materialkit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MenuRes;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;


class FlexToolbar extends LinearLayout {
    public FlexToolbar(Context context) {
        this(context, null);
    }

    public FlexToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    final TextView toolbarTitle;
    final ImageButton nav;
    final LinearLayout linearLayout;
    int actionBarSize;
    int menuTint = -1;
    Toolbar.OnMenuItemClickListener onMenuItemClickListener;

    public FlexToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        actionBarSize = getContext().getResources().getDimensionPixelSize(R.dimen.actionBarHeight);

//        float oneDP = UtilsKit.dpToPx(1, getContext());


//        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, actionBarSize);
//        params.addRule(CENTER_VERTICAL);
//
//        linearLayout = new LinearLayout(getContext());
//        linearLayout.setLayoutParams(params);
//        addView(linearLayout);
        setGravity(Gravity.CENTER);


        nav = new ImageButton(getContext());
        LinearLayout.LayoutParams navParams = new LinearLayout.LayoutParams(actionBarSize, actionBarSize);
        nav.setLayoutParams(navParams);
        nav.setBackgroundResource(R.drawable.icon_ripple);
        nav.setVisibility(GONE);
        addView(nav);

        toolbarTitle = new TextView(getContext());
        toolbarTitle.setLines(1);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.weight = 1;
        toolbarTitle.setLayoutParams(titleParams);
        toolbarTitle.setTypeface(Typeface.DEFAULT_BOLD);
        addView(toolbarTitle);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, actionBarSize);

        linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        addView(linearLayout);
    }

    private ImageButton getIcon() {
        ImageButton nav = new ImageButton(getContext());
        LinearLayout.LayoutParams navParams = new LinearLayout.LayoutParams(actionBarSize, actionBarSize);
        nav.setLayoutParams(navParams);
        nav.setBackgroundResource(R.drawable.icon_ripple);
        return nav;
    }

    public void setMenuItems(@MenuRes int menuRes) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                PopupMenu p = new PopupMenu(getContext(), null);
                Menu menu = p.getMenu();
                ((Activity) getContext()).getMenuInflater().inflate(menuRes, menu);

                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    Drawable icon = item.getIcon();
                    if (icon != null) {
                        ImageButton iconItem = getIcon();
                        iconItem.setImageDrawable(icon);
                        iconItem.setId(iconItem.getId());
                        iconItem.setOnClickListener(v -> {
                            if (onMenuItemClickListener != null)
                                onMenuItemClickListener.onMenuItemClick(item);
                        });
                        if (menuTint != -1)
                            iconItem.setColorFilter(menuTint);
                        linearLayout.addView(iconItem);
                    }
                }
                if (hasMenuItems())
                    toolbarTitle.setPadding(0, 0, 0, 0);
                else
                    toolbarTitle.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.actionBarHeight), 0);
            }
        });

    }

    boolean hasMenuItems() {
        return linearLayout.getChildCount() > 0;
    }

    public void setToolbarMenuTint(int color) {
        menuTint = color;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ((ImageButton) linearLayout.getChildAt(i)).setColorFilter(color);
            Toast.makeText(getContext(), "Atleast here", Toast.LENGTH_SHORT).show();
        }
    }
}
