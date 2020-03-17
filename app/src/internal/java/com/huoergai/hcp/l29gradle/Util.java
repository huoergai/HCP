package com.huoergai.hcp.l29gradle;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;

public class Util {
    public static void addBadge(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        TextView badge = new TextView(activity);
        badge.setText("internal");
        decorView.addView(badge);
    }
}
