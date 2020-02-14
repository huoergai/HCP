package com.huoergai.hcp.lesson41;

/**
 * D&T: 2020/2/10 10:51
 * DES:
 */
public class Cucumber implements Vegetable {
    @Override
    public void prtName() {
        System.out.println("a cucumber vegetable.");
    }

    @Override
    public boolean forInstance() {
        return true;
    }
}
