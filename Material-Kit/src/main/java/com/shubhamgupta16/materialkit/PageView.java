package com.shubhamgupta16.materialkit;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.IdRes;
import androidx.annotation.MenuRes;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

public class PageView extends RelativeLayout {

    public PageView(Context context) {
        this(context, null);
    }

    public PageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public static int TOOLBAR_NAV_CLICK_NONE = 0;
    public static int TOOLBAR_NAV_CLICK_FINISH = 1;
    public static int TOOLBAR_TITLE_CENTER = 1;
    public static int TOOLBAR_TITLE_START = 0;

    private _FlexToolbar flexToolbar;
    private RelativeLayout relativeLayout;
    private final TypedArray a;

    private void _setupLinearLayout() {
        flexToolbar = new _FlexToolbar(getContext());
        flexToolbar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.actionBarHeight)));
        flexToolbar.setId(android.R.id.navigationBarBackground);

        relativeLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        llp.addRule(RelativeLayout.BELOW, flexToolbar.getId());
        relativeLayout.setId(android.R.id.content);
        relativeLayout.setLayoutParams(llp);
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        _setupLinearLayout();

        RelativeLayout layout = new RelativeLayout(getContext());
        layout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.addView(relativeLayout);
        layout.addView(flexToolbar);

        addView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        float oneDP = UtilsKit.dpToPx(1, getContext());

        a = context.obtainStyledAttributes(attrs, R.styleable.PageView, defStyleAttr, defStyleRes);

        setToolbarTitleFont(a.getResourceId(R.styleable.PageView_toolbarTitleFont, 0));
        setToolbarTitleSize(TypedValue.COMPLEX_UNIT_PX, a.getDimension(R.styleable.PageView_toolbarTitleSize, oneDP * 20));
        setToolbarTitle(a.getString(R.styleable.PageView_toolbarTitle));
        if (a.hasValue(R.styleable.PageView_toolbarNavIcon))
            setToolbarNavIcon(a.getResourceId(R.styleable.PageView_toolbarNavIcon, android.R.color.transparent));

        setToolbarNavClick(a.getInt(R.styleable.PageView_toolbarNavClick, TOOLBAR_NAV_CLICK_FINISH));
        setToolbarBackground(a.getResourceId(R.styleable.PageView_toolbarBackground, android.R.color.transparent));
        setToolbarTitlePosition(a.getInt(R.styleable.PageView_toolbarTitlePosition, TOOLBAR_TITLE_START));

        if (a.hasValue(R.styleable.PageView_toolbarHeight))
            setToolbarHeight(a.getDimensionPixelSize(R.styleable.PageView_toolbarHeight, 0));
        if (a.hasValue(R.styleable.PageView_toolbarTitleColor))
            setToolbarTitleColor(a.getColor(R.styleable.PageView_toolbarTitleColor, Color.WHITE));
        if (a.hasValue(R.styleable.PageView_toolbarMenu))
            setMenuItems(a.getResourceId(R.styleable.PageView_toolbarMenu, 0));
        if (a.hasValue(R.styleable.PageView_toolbarNavTint))
            setToolbarNavTint(a.getColor(R.styleable.PageView_toolbarNavTint, Color.WHITE));
        if (a.hasValue(R.styleable.PageView_toolbarMenuTint))
            setToolbarMenuTint(a.getColor(R.styleable.PageView_toolbarMenuTint, Color.WHITE));
        if (a.hasValue(R.styleable.PageView_toolbarMenuBadgeBackgroundColor))
            setMenuBadgeBackgroundColor(a.getColor(R.styleable.PageView_toolbarMenuBadgeBackgroundColor, Color.RED));
        if (a.hasValue(R.styleable.PageView_toolbarMenuBadgeTextColor))
            setMenuBadgeTextColor(a.getColor(R.styleable.PageView_toolbarMenuBadgeTextColor, Color.WHITE));


        int color = a.getColor(R.styleable.PageView_toolbarShadowColor, 0x10000000);
        View view = new View(getContext());
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                a.getDimensionPixelSize(R.styleable.PageView_toolbarShadowHeight, (int) (oneDP * 10)));
        p.addRule(RelativeLayout.BELOW, flexToolbar.getId());
        view.setLayoutParams(p);

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{color, 0x00FFFFFF});
        gd.setCornerRadius(0f);

        view.setBackground(gd);
        layout.addView(view);

    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 0) relativeLayout.addView(child);
        else super.addView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) relativeLayout.addView(child, params);
        else super.addView(child, params);
    }

    public void setDefaultToolbarTitlePosition(int position) {
        if (!a.hasValue(R.styleable.PageView_toolbarTitlePosition))
            setToolbarTitlePosition(position);
    }

    public void setToolbarTitlePosition(int position) {
        if (position == TOOLBAR_TITLE_CENTER) {
            flexToolbar.toolbarTitle.setGravity(Gravity.CENTER);
        } else {
            flexToolbar.toolbarTitle.setGravity(Gravity.LEFT);
        }
        updateToolbarTitlePos();
    }

    public void setDefaultToolbarTitleColor(@ColorInt int color) {
        if (!a.hasValue(R.styleable.PageView_toolbarTitleColor))
            setToolbarTitleColor(color);
    }

    public void setToolbarTitleColor(@ColorInt int color) {
        flexToolbar.toolbarTitle.setTextColor(color);
    }

    public void setDefaultToolbarNavTint(@ColorInt int color) {
        if (!a.hasValue(R.styleable.PageView_toolbarNavTint))
            setToolbarNavTint(color);
    }

    public void setDefaultToolbarMenuTint(@ColorInt int color) {
        if (!a.hasValue(R.styleable.PageView_toolbarMenuTint))
            setToolbarMenuTint(color);
    }
//    private void setToolbarMenuTint(int color) {
//        flexToolbar.setToolbarMenuTint(color);
//    }

    public void setToolbarNavTint(@ColorInt int color) {
        flexToolbar.nav.setColorFilter(color);
    }

    public void setDefaultToolbarTitleFont(@FontRes int font) {
        if (!a.hasValue(R.styleable.PageView_toolbarTitleFont))
            setToolbarTitleFont(font);
    }

    public void setToolbarTitleFont(@FontRes int font) {
        if (font != 0) {
            new Handler().post(() -> {
                Typeface typeface = ResourcesCompat.getFont(getContext(), font);
                flexToolbar.toolbarTitle.setTypeface(typeface);
            });
        }
    }


    public void setDefaultToolbarTitleSize(int unit, float size) {
        if (!a.hasValue(R.styleable.PageView_toolbarTitleSize))
            setToolbarTitleSize(unit, size);
    }

    public void setToolbarTitleSize(int unit, float size) {
        flexToolbar.toolbarTitle.setTextSize(unit, size);
    }

    public void setDefaultToolbarNavIcon(@DrawableRes int res) {
        if (!a.hasValue(R.styleable.PageView_toolbarNavIcon))
            setToolbarNavIcon(res);
    }

    public void setToolbarNavIcon(@DrawableRes int res) {
        flexToolbar.nav.setImageResource(res);
        if (res == android.R.color.transparent) {
            flexToolbar.nav.setVisibility(GONE);
        } else {
            flexToolbar.nav.setVisibility(VISIBLE);
        }
        updateToolbarTitlePos();
//        if (res == android.R.color.transparent)
//            flexToolbar.nav.setVisibility(GONE);
//        else
//            flexToolbar.nav.setVisibility(VISIBLE);
    }

    public void setDefaultToolbarNavClick(int navClick) {
        if (!a.hasValue(R.styleable.PageView_toolbarNavClick))
            setToolbarNavClick(navClick);
    }

    public void setToolbarNavClick(int navClick) {
        if (navClick == TOOLBAR_NAV_CLICK_FINISH)
            flexToolbar.nav.setOnClickListener(v -> ((Activity) getContext()).finish());
        else flexToolbar.nav.setOnClickListener(null);
    }

    public void setToolbarNavClick(OnClickListener onClickListener) {
        flexToolbar.nav.setOnClickListener(onClickListener);
    }

    public void setDefaultToolbarTitle(String title) {
        if (!a.hasValue(R.styleable.PageView_toolbarTitle))
            setToolbarTitle(title);
    }

    public void setToolbarTitle(String title) {
        flexToolbar.toolbarTitle.setText(title);
    }

    public void setDefaultToolbarHeight(int height) {
        if (!a.hasValue(R.styleable.PageView_toolbarHeight))
            flexToolbar.getLayoutParams().height = height;
    }

    public void setToolbarHeight(int height) {
        flexToolbar.getLayoutParams().height = height;
    }

    public void setDefaultToolbarBackground(@DrawableRes int res) {
        if (!a.hasValue(R.styleable.PageView_toolbarBackground))
            flexToolbar.setBackgroundResource(res);
    }

    public void setToolbarBackground(@DrawableRes int res) {
        flexToolbar.setBackgroundResource(res);
    }

    //    getters
    public String getToolbarTitle() {
        return flexToolbar.toolbarTitle.getText().toString();
    }

    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener onMenuItemClickListener) {
        flexToolbar.onMenuItemClickListener = onMenuItemClickListener;
    }


//   *************************** menus *******************************


    public void setMenuBadge(@IdRes int id, int value) {
        setMenuBadge(id, value, false);
    }

    public void setMenuBadge(@IdRes int id, int value, boolean animation) {
        ViewGroup icon = flexToolbar.linearLayout.findViewById(id);
        TextView badge = (TextView) icon.getChildAt(1);
        if (value > 0) {
            badge.setAlpha(1);
            String text;
            if (value < 100)
                text = String.valueOf(value);
            else
                text = "99+";
            if (animation && !badge.getText().toString().equals(text))
                AnimUtils.scaleAnimation(badge, 1, 1.3f, 200, 4, true, new AccelerateDecelerateInterpolator());
            badge.setText(text);
        } else {
//            badge.setAlpha(0);
            if (animation && !badge.getText().toString().isEmpty())
                AnimUtils.scaleAnimation(badge, 1, 0, 300, new DecelerateInterpolator());
            else {
                badge.setAlpha(0);
            }
            badge.setText("");
        }
        /*int pos = getPositionById(id);
        if (pos != -1)
            setBadgeToPosition(pos, value, animate);*/
    }

    public void setMenuItems(@MenuRes int menuRes) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                flexToolbar.linearLayout.removeAllViews();
                PopupMenu p = new PopupMenu(getContext(), null);
                Menu menu = p.getMenu();
                ((Activity) getContext()).getMenuInflater().inflate(menuRes, menu);

                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    Drawable icon = item.getIcon();
                    if (icon != null) {
                        ViewGroup iconLayout = getIcon();
                        iconLayout.setId(item.getItemId());
                        ImageButton iconItem = (ImageButton) iconLayout.getChildAt(0);
                        iconItem.setImageDrawable(icon);
                        iconItem.setOnClickListener(v -> {
                            if (flexToolbar.onMenuItemClickListener != null)
                                flexToolbar.onMenuItemClickListener.onMenuItemClick(item);
                        });
                        if (flexToolbar.menuTint != 9999)
                            iconItem.setColorFilter(flexToolbar.menuTint);
                        flexToolbar.linearLayout.addView(iconLayout);
                    }
                }
                updateToolbarTitlePos();
            }
        });

    }

    private ViewGroup getIcon() {
        RelativeLayout layout = new RelativeLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) UtilsKit.dpToPx(40, getContext()), flexToolbar.actionBarSize);
        layout.setLayoutParams(layoutParams);
        ImageButton nav = new ImageButton(getContext());
        LinearLayout.LayoutParams navParams = new LinearLayout.LayoutParams(flexToolbar.actionBarSize, flexToolbar.actionBarSize);
        navParams.setMarginStart((int) UtilsKit.dpToPx(-15, getContext()));
        nav.setLayoutParams(navParams);
        nav.setBackgroundResource(R.drawable.kit_icon_ripple);
        layout.addView(nav);
        TextView badge = createBadge();
        layout.addView(badge);
        return layout;
    }

    private TextView createBadge() {
        final TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, (int) UtilsKit.dpToPx(16, getContext()));
        tvParams.setMargins((int) UtilsKit.dpToPx(17, getContext()), (int) UtilsKit.dpToPx(8, getContext()), 0, 0);
        tv.setPadding((int) UtilsKit.dpToPx(3, getContext()), 0, (int) UtilsKit.dpToPx(3, getContext()), (int) UtilsKit.dpToPx(1, getContext()));
        tv.setAlpha(0);
        tv.setLayoutParams(tvParams);
        tv.setMinWidth((int) UtilsKit.dpToPx(16, getContext()));
        tv.setGravity(Gravity.CENTER);
//        tv.measure(0, 0);
        tv.setText("");
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setBackgroundResource(R.drawable.kit_badge_round);
        tv.getBackground().setTint(flexToolbar.badgeBackgroundColor);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        tv.setTextColor(flexToolbar.badgeTextColor);
        tv.setMaxLines(1);
        return tv;
    }

    int getMenuItemsCount() {
        return flexToolbar.linearLayout.getChildCount();
    }

    void updateToolbarTitlePos() {
        if (flexToolbar.toolbarTitle.getGravity() == Gravity.CENTER)
            if (getMenuItemsCount() == 0) {
                if (flexToolbar.nav.getVisibility() == GONE)
                    flexToolbar.toolbarTitle.setPadding(0, 0, 0, 0);
                else
                    flexToolbar.toolbarTitle.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.actionBarHeight), 0);
            } else {
                int pad = (int) (getMenuItemsCount() * UtilsKit.dpToPx(40, getContext()));
                Log.d("tagtag", pad + " pad");
                if (flexToolbar.nav.getVisibility() == GONE)
                    flexToolbar.toolbarTitle.setPadding(pad, 0, 0, 0);
                else
                    flexToolbar.toolbarTitle.setPadding(pad - getResources().getDimensionPixelSize(R.dimen.actionBarHeight), 0, 0, 0);
            }
        else {
            if (flexToolbar.nav.getVisibility() == GONE)
                flexToolbar.toolbarTitle.setPadding((int) UtilsKit.dpToPx(15, getContext()), 0, 0, 0);
            else
                flexToolbar.toolbarTitle.setPadding(0, 0, 0, 0);
        }

    }

    public void setToolbarMenuTint(int color) {
        flexToolbar.menuTint = color;
        for (int i = 0; i < flexToolbar.linearLayout.getChildCount(); i++) {
            ((ImageButton) ((ViewGroup) flexToolbar.linearLayout.getChildAt(i)).getChildAt(0)).setColorFilter(color);
        }
    }

    public void setMenuBadgeBackgroundColor(@ColorInt int color) {
        setBadgeColors(color, flexToolbar.badgeTextColor);
    }

    public void setMenuBadgeTextColor(@ColorInt int color) {
        setBadgeColors(flexToolbar.badgeBackgroundColor, color);
    }

    void setBadgeColors(@ColorInt int bgColor, @ColorInt int textColor) {
        flexToolbar.badgeBackgroundColor = bgColor;
        flexToolbar.badgeTextColor = textColor;
        for (int i = 0; i < flexToolbar.linearLayout.getChildCount(); i++) {
            TextView badge = (TextView) ((ViewGroup) flexToolbar.linearLayout.getChildAt(i)).getChildAt(1);
            badge.setTextColor(textColor);
            badge.getBackground().setTint(bgColor);
        }

    }
}
