package com.huoergai.hcp.base;

import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.huoergai.hcp.R;

/**
 * ViewPager2 和 Transformer 的使用
 */
public class CubeActivity extends AppCompatActivity {
    private static final int PAGE_COUNT = 5;
    private ViewPager2 vp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube);
        vp = findViewById(R.id.activity_cube_vp);

        vp.setPageTransformer(new CubeTransformer());
        vp.setAdapter(new CubeSlidePageAdapter(this));

        SwitchCompat switchBtn = findViewById(R.id.activity_cube_switch);
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    vp.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                } else {
                    vp.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (vp.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            vp.setCurrentItem(vp.getCurrentItem() - 1);
        }
    }

    public static class CubeSlidePageAdapter extends FragmentStateAdapter {

        public CubeSlidePageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return new CubeSlidePageFragment(position);
        }

        @Override
        public int getItemCount() {
            return PAGE_COUNT;
        }
    }
}
