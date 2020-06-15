package com.huoergai.md.themeswitcher;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.huoergai.md.R;

/**
 * Helper class for demos to support theme switcher functionality.
 */
public class ThemeSwitcherHelper {
    private final FragmentManager fragmentManager;
    private final boolean enabled;

    public <F extends Fragment & ThemeSwitcherFragment> ThemeSwitcherHelper(F fragment) {
        fragmentManager = fragment.getFragmentManager();
        enabled =
                fragment.shouldShowDefaultDemoActionBar()
                        && fragment.getActivity() instanceof ThemeSwitcherActivity;

        if (enabled) {
            fragment.setHasOptionsMenu(true);
        }
    }

    public ThemeSwitcherHelper(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.enabled = true;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (enabled) {
            menuInflater.inflate(R.menu.mtrl_theme_switcher_menu, menu);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (enabled) {
            if (menuItem.getItemId() == R.id.theme_switcher) {
                showThemeSwitcher();
                return true;
            }
        }
        return false;
    }

    private void showThemeSwitcher() {
        new ThemeSwitcherDialogFragment().show(fragmentManager, "theme-switcher");
    }

    /**
     * Implement this interface to whitelist an Activity for theme switcher support.
     */
    public interface ThemeSwitcherActivity {
    }

    /**
     * Implement this interface to allow a Fragment to be used with {@link ThemeSwitcherHelper}.
     */
    public interface ThemeSwitcherFragment {

        /**
         * If this is true, then the demo's default action bar comes with theme switcher support.
         */
        boolean shouldShowDefaultDemoActionBar();
    }
}
