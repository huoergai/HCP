package com.huoergai.hcp.lesson40;

import com.huoergai.hcp.R;

/**
 * D&T: 2020/2/11 15:11
 * DES: 实现自动绑定的模型
 */
public class L40ActivityViewBinding {

    /**
     * 2.使用注解，编译时须自动生成的等价实现,
     * 在运行时，使用反射按规则匹配到自动生成的类，
     * 通过调用其构造函数，运行相应其中的 statement，完成试图绑定。
     */
    public L40ActivityViewBinding(L40Activity activity) {
        activity.tv = activity.findViewById(R.id.l40_tv);
    }

    /**
     * 1.使用反射进行动态调用的最终实现的等价效果
     */
    public static void binding(L40Activity activity) {
        activity.tv = activity.findViewById(R.id.l40_tv);
    }

}
