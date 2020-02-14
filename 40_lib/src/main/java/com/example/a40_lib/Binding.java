package com.example.a40_lib;

import android.app.Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * D&T: 2020/2/11 16:50
 * DES:
 */
public class Binding {

    /*
    public static void bind(Activity activity) {
        try {
            Class bindingClass = Class.forName(activity.getClass().getCanonicalName() + "Binding");
            Constructor constructor = bindingClass.getDeclaredConstructor(activity.getClass());
            constructor.newInstance(activity);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    */

    public static void bind(Activity activity) {
        // 等价调用
        // new L40ActivityBinding((L40Activity) activity);
        try {
            Class<?> bindingClass = Class.forName(activity.getClass().getCanonicalName() + "ViewBinding");
            Constructor<?> bindingClassConstructor = bindingClass.getDeclaredConstructor(activity.getClass());
            bindingClassConstructor.newInstance(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
