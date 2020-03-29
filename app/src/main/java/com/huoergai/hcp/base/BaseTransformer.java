package com.huoergai.hcp.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public abstract class BaseTransformer implements ViewPager2.PageTransformer {
    private static final float DAMPING_RATIO_NO_BOUNCY = 1f;

    /**
     * when the position offset of a fragment is less than -1 or greater than 1:
     *
     * @return true: set the visibility of the fragment to {@link View#GONE}; false: set the fragment to {@link View#VISIBLE}
     */
    protected boolean hideOffscreenPages() {
        return true;
    }

    /**
     * Indicates if the default animations of the view pager should be used.
     */
    protected boolean isPagingEnabled() {
        return false;
    }

    protected void onPreTransform(View view, float position) {
        float alpha = 0.0f;
        float width = view.getWidth();

        view.setRotationX(0.0f);
        view.setRotationY(0.0f);
        view.setRotation(0.0f);

        view.setScaleX(DAMPING_RATIO_NO_BOUNCY);
        view.setScaleY(DAMPING_RATIO_NO_BOUNCY);

        view.setPivotX(0.0f);
        view.setPivotY(0.0f);

        view.setTranslationX(isPagingEnabled() ? 0.0f : ((-width) * position));
        view.setTranslationY(0.0f);

        if (hideOffscreenPages()) {
            if (position > -1.0f && position < DAMPING_RATIO_NO_BOUNCY) {
                alpha = DAMPING_RATIO_NO_BOUNCY;
            }
            view.setAlpha(alpha);
            view.setEnabled(false);
            return;
        }

        view.setAlpha(DAMPING_RATIO_NO_BOUNCY);
        view.setEnabled(true);
    }

    /**
     * called each {@link #transformPage(View, float)}
     *
     * @param view     the view
     * @param position the position
     */
    protected abstract void onTransform(View view, float position);

    /**
     * called each {@link #transformPage(View, float)} call after {@link #onTransform(View, float)} is finished.
     *
     * @param view     the view
     * @param position the position
     */
    protected void onPostTransform(View view, float position) {

    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        onPreTransform(page, position);
        onTransform(page, position);
        onPostTransform(page, position);
    }
}
