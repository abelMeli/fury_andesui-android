package com.mercadolibre.android.andesui.demoapp.utils;

import static android.graphics.Color.parseColor;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mercadolibre.android.andesui.demoapp.R;

public class PageIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {

    private static final int UNSELECTED_SIZE_DP = 8;
    private static final int SELECTED_SIZE_DP = 12;
    private static final String SELECTED_COLOR = "#3483fa";
    private static final String UNSELECTED_COLOR = "#cccccc";
    private ImageView[] mViews;

    /**
     * Default constructor
     *
     * @param context the application's context
     */
    public PageIndicator(@NonNull final Context context) {
        this(context, null);
    }

    /**
     * @param context the application's context
     * @param attrs   the attributes
     */
    public PageIndicator(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context      the applitacion's context
     * @param attrs        the view's attributes
     * @param defStyleAttr the style attributes
     */
    public PageIndicator(@NonNull final Context context, @Nullable final AttributeSet attrs,
                         final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.andesui_widget_page_indicator, this);
    }

    /**
     * Attaches the view pager to the page indicator
     *
     * @param viewPager the viewPager where's being attached to
     */
    public void attach(@NonNull final ViewPager viewPager) {
        viewPager.addOnPageChangeListener(this);

        PagerAdapter pagerAdapter = viewPager.getAdapter();
        if (pagerAdapter == null) {
            return;
        }

        mViews = new ImageView[pagerAdapter.getCount()];
        for (int i = 0; i < pagerAdapter.getCount(); i++) {

            final ImageView view = (ImageView) LayoutInflater.from(getContext())
                    .inflate(R.layout.andesui_widget_page_circle_indicator, this, false);

            addView(view);

            mViews[i] = view;
        }

        setSelected(viewPager.getCurrentItem());
    }

    private void setSelected(final int position) {

        for (int i = 0; i < mViews.length; i++) {
            if (i == position) {
                tintImageView(mViews[i], SELECTED_COLOR);
                resizeView(mViews[i], SELECTED_SIZE_DP);
            } else {
                tintImageView(mViews[i], UNSELECTED_COLOR);
                resizeView(mViews[i], UNSELECTED_SIZE_DP);
            }
        }
    }

    private void tintImageView(ImageView view, String color) {
        view.setColorFilter(parseColor(color), PorterDuff.Mode.SRC_IN);
    }

    private void resizeView(View view, int newSizeInDp) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutParams.height = dpToPx(newSizeInDp, view.getContext());
        layoutParams.width = dpToPx(newSizeInDp, view.getContext());
        view.setLayoutParams(layoutParams);
    }

    private int dpToPx(int dp, Context context) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset,
                               final int positionOffsetPixels) {
        // Nothing to do
    }

    @Override
    public void onPageSelected(final int position) {
        setSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
        // Nothing to do
    }
}