package com.huoergai.hcp.base;

import android.util.Log;
import android.view.View;

import com.huoergai.hcp.Utils;

public class CubeTransformer extends BaseTransformer {
    private View tmp;

    @Override
    protected boolean isPagingEnabled() {
        return true;
    }

    private static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return (((value - fromLow) / (fromHigh - fromLow)) * (toHigh - toLow)) + toLow;
    }

    @Override
    public void onTransform(View view, float position) {
       /* if (tmp == null) {
            tmp = view;
        } else if (tmp.equals(view)) {
            Log.d("transformer", "position = " + position);
        }*/
        float rotationY = (float) mapValueFromRangeToRange(position, -1.0d, 1.0d, -90.0d, 90.0d);
        view.setCameraDistance(Utils.px2dp(5600));
        if (position > 0.0f) {
            view.setRotationY(rotationY);
            view.setPivotX(0.0f);
            view.setPivotY(view.getHeight() * 0.5f);
        } else if (position < 0.0f) {
            view.setRotationY(rotationY);
            view.setPivotX(view.getWidth());
            view.setPivotY(view.getHeight() * 0.5f);
        } else {
            view.setRotationY(0.0f);
            view.setPivotX(view.getWidth() * 0.5f);
            view.setPivotY(view.getHeight() * 0.5f);
        }
    }
}
