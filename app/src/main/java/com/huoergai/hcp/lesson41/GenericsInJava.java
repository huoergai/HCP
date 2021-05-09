package com.huoergai.hcp.lesson41;

import java.util.ArrayList;
import java.util.List;

/**
 * D&T: 2020/2/10 10:38
 * DES: Java 泛型：<? extends Xxx> <? super Xxx>
 * <p>
 * 1. <? extends Xxx> Producer-Extends: 只能向外提供数据，适用于做为形参，可读不可写。
 * 2.『? extends』叫上界通配符，使 Java 泛型具有协变性。
 * 3. <? super Xxx> Consumer-Super: 没有实际使用场景，可用于添加数据。
 * 4.『? super』叫下界通配符，使 Java 泛型具有「逆变性」。
 */
public class GenericsInJava {

    private void demo() {
        // 1.可以使用，不可以修改。
        List<? extends SweetOrange> fruitList = new ArrayList<>();
        // x fruitList.add(new Orange());
        // x fruitList.add(new SweetOrange());
        // x fruitList.add(new SugarSweetOrange());
        Fruit fruit = fruitList.get(1);

        // 2.可以修改，不可以使用(除非从 Object 强转)。
        List<? super SweetOrange> oranges = new ArrayList<>();
        // x oranges.add(new Orange());
        oranges.add(new SweetOrange());
        oranges.add(new SugarSweetOrange());
        Object object = oranges.get(0);
        Fruit orangeFruit = (Fruit) oranges.get(0);
        // x SweetOrange sweetOrange = oranges.get(0);

        getTotalPrice(fruitList);
        // x getTotalPrice(oranges);
    }

    /**
     * 水果总价
     *
     * @param fruits 可以是   List<Fruit>
     *               List<Orange>
     *               ArrayList<Orange>
     *               但是不能向 fruits 中添加元素
     */
    private static double getTotalPrice(List<? extends Fruit> fruits) {
        double sum = 0;
        for (Fruit fruit : fruits) {
            sum += fruit.getPrice();
        }
        return sum;
    }
}
