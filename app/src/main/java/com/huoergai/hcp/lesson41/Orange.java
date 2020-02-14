package com.huoergai.hcp.lesson41;

/**
 * D&T: 2020/2/10 10:32
 * DES:
 */
public class Orange implements Fruit {
    @Override
    public void prtColor() {
        System.out.println("a orange orange.");
    }

    @Override
    public void eat() {
        System.out.println("hah.., such a juicy orange!");
    }

    @Override
    public double getPrice() {
        return 1.20;
    }
}
