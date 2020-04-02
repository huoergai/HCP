package com.huoergai.hcp.l23;

import java.util.HashMap;

/**
 * 23 HashMap 源码解析
 */
public class HashMapTest {
    public void test() {
        new HashMap<>();
        // 1. 数据结构： 1.7 -> 数组+链表；1.8 -> 数组+链表/红黑树
        // 2. 默认值： Capacity: 16;loadFactor: 0.75
        // 3. hash 算法： null -> 0; (h=Object.hashCode())^(h>>>16). 为防止因为数组长度限制，
        //    hashCode 的高位没起作用，导致链表数据过于集中；使用其高位与其异或后，对 hash 进行扰乱；
        // 4. 存入数组的位置 index： hashCode&(n-1), n 是数组长度
        // 5. 链表插入方式： 1.7 头插法，1.8 尾插法
        // 6. 第一次 put() 时创建数组
        // 7. onlyIfAbsent: 1.8 后可设置是否覆盖已有的旧值；
        // 8. 扩容：1.7 先扩容再添加；1.8 先添加再扩容
        // 9. 1.8 当数组长度 > 64 且 链表长度 > 8 时： 链表 --> 树
    }
}
