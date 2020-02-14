package com.huoergai.hcp.lesson41;

/**
 * D&T: 2020/2/10 10:44
 * DES:
 */
public class Tomato implements Vegetable {

    @Override
    public void prtName() {
        System.out.println("a tomato.");
    }

    @Override
    public boolean forInstance() {
        return false;
    }
}
