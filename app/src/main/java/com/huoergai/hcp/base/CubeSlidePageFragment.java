package com.huoergai.hcp.base;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.huoergai.hcp.R;

import java.util.Locale;

public class CubeSlidePageFragment extends Fragment {
    private int count;

    public CubeSlidePageFragment(int position) {
        this.count = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cube_slide, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.fragment_cube_tv);
        tv.setText(String.format(Locale.CHINA, "fragment:%d", count));
        if (count % 2 == 0) {
            view.setBackgroundColor(Color.CYAN);
        }
    }
}
