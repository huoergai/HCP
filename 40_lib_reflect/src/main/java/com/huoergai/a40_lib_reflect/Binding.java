package com.huoergai.a40_lib_reflect;

import android.app.Activity;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * D&T: 2020/2/11 14:07
 * DES:
 */
public class Binding {
    public static void bind(Activity activity) {
        Field[] fields = activity.getClass().getDeclaredFields();
        for (Field field : fields) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null) {
                try {
                    field.setAccessible(true);
                    field.set(activity, activity.findViewById(bindView.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    Log.e("BindView", "BindView error set field value failed!");
                }
            }
        }
    }
}
