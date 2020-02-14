package com.huoergai.hcp.lesson41;

import java.util.ArrayList;
import java.util.List;

/**
 * D&T: 2020/2/10 10:38
 * DES:
 */
public class Test {
    public static void main(String[] args) {
        // 适用于做为形参 可读不可写
        List<? extends Fruit> fruitList = new ArrayList<PineApple>();
        // x fruitList.add(new Orange());
        // x fruitList.add(new PineApple());
        Fruit fruit = fruitList.get(1);

        List<Vegetable> vegetables = new ArrayList<>();
        vegetables.add(new Tomato());
        vegetables.add(new Cucumber());
        Vegetable vegetable = vegetables.get(0);

        getTotalPrice(new ArrayList<Orange>());
        List<Fruit> oranges = new ArrayList<>();
        getTotalPrice(oranges);



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

    /**
     * 水果总价
     *
     * @param fruits 只能是 List<Fruit>
     */
    private static double getTotalPriceX(List<Fruit> fruits) {
        double sum = 0;
        for (Fruit fruit : fruits) {
            sum += fruit.getPrice();
        }
        return sum;
    }
}
