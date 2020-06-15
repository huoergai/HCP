package com.huoergai.md.themeswitcher;


import androidx.annotation.ArrayRes;
import androidx.annotation.AttrRes;
import androidx.annotation.StyleableRes;

import com.huoergai.md.R;

/**
 * A helper class that facilitates overriding of theme switcher resources in the Catalog app.
 */
public class ThemeSwitcherResourceProvider {

    @StyleableRes
    private static final int[] PRIMARY_THEME_OVERLAY_ATTRS = {
            R.attr.colorPrimary, R.attr.colorPrimaryDark
    };

    @StyleableRes
    private static final int[] SECONDARY_THEME_OVERLAY_ATTRS = {R.attr.colorSecondary};

    @StyleableRes
    public int[] getPrimaryThemeOverlayAttrs() {
        return PRIMARY_THEME_OVERLAY_ATTRS;
    }

    @StyleableRes
    public int[] getSecondaryThemeOverlayAttrs() {
        return SECONDARY_THEME_OVERLAY_ATTRS;
    }

    @AttrRes
    public int getPrimaryColor() {
        return R.attr.colorPrimary;
    }

    @ArrayRes
    public int getPrimaryColors() {
        return R.array.mtrl_primary_palettes;
    }

    @ArrayRes
    public int getSecondaryColors() {
        return R.array.mtrl_secondary_palettes;
    }

    @ArrayRes
    public int getPrimaryColorsContentDescription() {
        return R.array.mtrl_palettes_content_description;
    }

    @ArrayRes
    public int getSecondaryColorsContentDescription() {
        return R.array.mtrl_palettes_content_description;
    }

    public int getShapes() {
        return R.array.mtrl_shape_overlays;
    }

    public int getShapesContentDescription() {
        return R.array.mtrl_shapes_content_description;
    }

    public int getShapeSizes() {
        return R.array.mtrl_shape_size_overlays;
    }

    public int getShapeSizesContentDescription() {
        return R.array.mtrl_shape_size_content_description;
    }
}
