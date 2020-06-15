package com.huoergai.md.themeswitcher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.widget.CompoundButtonCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.huoergai.md.R;

import java.util.Objects;

/**
 * Theme switcher dialog that allows the user to change different theming aspects like colors and
 * shapes. Each group in the dialog has a set of possible style values that are used as theme
 * overlays, overriding attributes in the base theme like shape appearances or color attributes.
 */
public class ThemeSwitcherDialogFragment extends BottomSheetDialogFragment {

    private enum RadioButtonType {
        DEFAULT,
        XML,
    }

    private enum ThemingType {
        COLOR(RadioButtonType.DEFAULT),
        SHAPE_CORNER_FAMILY(RadioButtonType.XML),
        SHAPE_CORNER_SIZE(RadioButtonType.DEFAULT);

        private final RadioButtonType radioButtonType;

        ThemingType(RadioButtonType type) {
            radioButtonType = type;
        }
    }

    // childFragmentInjector
    ThemeSwitcherResourceProvider resourceProvider;
    private RadioGroup primaryColorGroup;
    private RadioGroup secondaryColorGroup;
    private RadioGroup shapeCornerFamilyGroup;
    private RadioGroup shapeCornerSizeGroup;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater layoutInflater,
            @Nullable ViewGroup viewGroup,
            @Nullable Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.mtrl_theme_switcher_dialog, null);
        initializeChooseThemeButtons(view);
        primaryColorGroup = view.findViewById(R.id.primary_colors);
        initializeThemingValues(
                primaryColorGroup,
                resourceProvider.getPrimaryColors(),
                resourceProvider.getPrimaryColorsContentDescription(),
                resourceProvider.getPrimaryThemeOverlayAttrs(),
                R.id.theme_feature_primary_color,
                ThemingType.COLOR);

        secondaryColorGroup = view.findViewById(R.id.secondary_colors);
        initializeThemingValues(
                secondaryColorGroup,
                resourceProvider.getSecondaryColors(),
                resourceProvider.getSecondaryColorsContentDescription(),
                resourceProvider.getSecondaryThemeOverlayAttrs(),
                R.id.theme_feature_secondary_color,
                ThemingType.COLOR);

        shapeCornerFamilyGroup = view.findViewById(R.id.shape_families);
        initializeThemingValues(
                shapeCornerFamilyGroup,
                resourceProvider.getShapes(),
                resourceProvider.getShapesContentDescription(),
                ThemingType.SHAPE_CORNER_FAMILY);

        shapeCornerSizeGroup = view.findViewById(R.id.shape_corner_sizes);
        initializeThemingValues(
                shapeCornerSizeGroup,
                resourceProvider.getShapeSizes(),
                resourceProvider.getShapeSizesContentDescription(),
                ThemingType.SHAPE_CORNER_SIZE);

        View applyButton = view.findViewById(R.id.apply_button);
        applyButton.setOnClickListener(
                v -> {
                    applyThemeOverlays();
                    dismiss();
                });

        View clearButton = view.findViewById(R.id.clear_button);
        clearButton.setOnClickListener(
                v -> {
                    ThemeOverlayUtils.clearThemeOverlays(getActivity());
                    dismiss();
                });

        return view;
    }

    private void initializeChooseThemeButtons(View view) {
        Context context = view.getContext();
        ThemePreferencesManager themePreferencesManager =
                new ThemePreferencesManager(context, resourceProvider);

        MaterialButtonToggleGroup themeToggleGroup = view.findViewById(R.id.theme_toggle_group);
        themeToggleGroup.check(themePreferencesManager.getCurrentThemeId());

        themeToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                themePreferencesManager.saveAndApplyTheme(checkedId);
            }
        });
    }

    private void applyThemeOverlays() {
        int[][] themesMap = new int[][]{
                {R.id.theme_feature_primary_color, getThemeOverlayResId(primaryColorGroup)},
                {R.id.theme_feature_secondary_color, getThemeOverlayResId(secondaryColorGroup)},
                {R.id.theme_feature_corner_family, getThemeOverlayResId(shapeCornerFamilyGroup)},
                {R.id.theme_feature_corner_size, getThemeOverlayResId(shapeCornerSizeGroup)}
        };

        for (int[] ints : themesMap) {
            ThemeOverlayUtils.setThemeOverlay(ints[0], ints[1]);
        }

        Objects.requireNonNull(getActivity()).recreate();
    }

    private int getThemeOverlayResId(RadioGroup radioGroup) {
        if (radioGroup.getCheckedRadioButtonId() == View.NO_ID) {
            return 0;
        }

        ThemeAttributeValues overlayFeature = (ThemeAttributeValues) getDialog()
                .findViewById(radioGroup.getCheckedRadioButtonId())
                .getTag();

        return overlayFeature.themeOverlay;
    }

    private void initializeThemingValues(
            RadioGroup group,
            @ArrayRes int overlays,
            @ArrayRes int contentDescriptions,
            ThemingType themingType) {
        initializeThemingValues(
                group, overlays, contentDescriptions, new int[]{}, R.id.theme_feature_corner_family, themingType);
    }

    private void initializeThemingValues(
            RadioGroup group,
            @ArrayRes int overlays,
            @ArrayRes int contentDescriptions,
            @StyleableRes int[] themeOverlayAttrs,
            @IdRes int overlayId,
            ThemingType themingType) {
        TypedArray themingValues = getResources().obtainTypedArray(overlays);
        TypedArray contentDescriptionArray = getResources().obtainTypedArray(contentDescriptions);
        if (themingValues.length() != contentDescriptionArray.length()) {
            throw new IllegalArgumentException(
                    "Feature array length doesn't match its content description array length.");
        }

        for (int i = 0; i < themingValues.length(); i++) {
            @StyleRes int valueThemeOverlay = themingValues.getResourceId(i, 0);
            ThemeAttributeValues themeAttributeValues = null;
            // Create RadioButtons for themeAttributeValues values
            switch (themingType) {
                case COLOR:
                    themeAttributeValues = new ColorPalette(valueThemeOverlay, themeOverlayAttrs);
                    break;
                case SHAPE_CORNER_FAMILY:
                    themeAttributeValues = new ThemeAttributeValues(valueThemeOverlay);
                    break;
                case SHAPE_CORNER_SIZE:
                    themeAttributeValues =
                            new ThemeAttributeValuesWithContentDescription(
                                    valueThemeOverlay, contentDescriptionArray.getString(i));
                    break;
            }

            // Expect the radio group to have a RadioButton as child for each themeAttributeValues value.
            AppCompatRadioButton button =
                    themingType.radioButtonType == RadioButtonType.XML
                            ? ((AppCompatRadioButton) group.getChildAt(i))
                            : createCompatRadioButton(group, contentDescriptionArray.getString(i));

            button.setTag(themeAttributeValues);
            themeAttributeValues.customizeRadioButton(button);

            int currentThemeOverlay = ThemeOverlayUtils.getThemeOverlay(overlayId);
            if (themeAttributeValues.themeOverlay == currentThemeOverlay) {
                group.check(button.getId());
            }
        }

        themingValues.recycle();
        contentDescriptionArray.recycle();
    }

    @NonNull
    private AppCompatRadioButton createCompatRadioButton(
            RadioGroup group, String contentDescription) {
        AppCompatRadioButton button = new AppCompatRadioButton(getContext());
        button.setContentDescription(contentDescription);
        group.addView(button);
        return button;
    }

    private static class ThemeAttributeValues {
        @StyleRes
        private final int themeOverlay;

        @SuppressLint("ResourceType")
        public ThemeAttributeValues(@StyleRes int themeOverlay) {
            this.themeOverlay = themeOverlay;
        }

        public void customizeRadioButton(AppCompatRadioButton button) {
        }
    }

    private static class ThemeAttributeValuesWithContentDescription extends ThemeAttributeValues {
        private final String contentDescription;

        @SuppressLint("ResourceType")
        public ThemeAttributeValuesWithContentDescription(
                @StyleRes int themeOverlay, String contentDescription) {
            super(themeOverlay);
            this.contentDescription = contentDescription;
        }

        @Override
        public void customizeRadioButton(AppCompatRadioButton button) {
            button.setText(contentDescription);
            LinearLayout.LayoutParams size =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            MarginLayoutParamsCompat.setMarginEnd(
                    size, button.getResources().getDimensionPixelSize(R.dimen.theme_switcher_radio_spacing));
            button.setLayoutParams(size);
        }
    }

    private class ColorPalette extends ThemeAttributeValues {
        @ColorInt
        private final int main;

        @SuppressLint("ResourceType")
        public ColorPalette(@StyleRes int themeOverlay, @StyleableRes int[] themeOverlayAttrs) {
            super(themeOverlay);
            TypedArray a = getContext().obtainStyledAttributes(themeOverlay, themeOverlayAttrs);
            main = a.getColor(0, Color.TRANSPARENT);

            a.recycle();
        }

        @Override
        public void customizeRadioButton(AppCompatRadioButton button) {
            CompoundButtonCompat.setButtonTintList(
                    button, ColorStateList.valueOf(convertToDisplay(main)));
        }

        @ColorInt
        private int convertToDisplay(@ColorInt int color) {
            return color == Color.WHITE ? Color.BLACK : color;
        }
    }

}
