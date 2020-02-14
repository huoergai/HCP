package com.huoergai.hcp.lesson41;

/**
 * D&T: 2020/2/10 10:35
 * DES:
 */
public class PineApple implements Fruit {
    @Override
    public void prtColor() {
        System.out.println("a shinny golden pineapple.");
    }

    @Override
    public void eat() {
        System.out.println("so sweet, so delicious!");
    }

    @Override
    public double getPrice() {
        return 2.40;
    }
}
