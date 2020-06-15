package com.huoergai.md.themeswitcher;

import android.app.Activity;
import android.util.SparseIntArray;

import androidx.annotation.IdRes;
import androidx.annotation.StyleRes;

/**
 * Utils for theme themeOverlays.
 */
public class ThemeOverlayUtils {

    private ThemeOverlayUtils() {
    }

    private static final SparseIntArray themeOverlays = new SparseIntArray();

    public static void setThemeOverlay(@IdRes int id, @StyleRes int themeOverlay) {
        themeOverlays.put(id, themeOverlay);
    }

    public static void clearThemeOverlays(Activity activity) {
        themeOverlays.clear();
        activity.recreate();
    }

    public static int getThemeOverlay(@IdRes int id) {
        return themeOverlays.get(id);
    }

    public static void applyThemeOverlays(Activity activity) {
        for (int i = 0; i < themeOverlays.size(); ++i) {
            activity.setTheme(themeOverlays.valueAt(i));
        }
    }
}
