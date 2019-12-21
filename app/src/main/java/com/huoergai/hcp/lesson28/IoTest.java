package com.huoergai.hcp.lesson28;

import java.io.File;
import java.io.IOException;

import okio.BufferedSource;
import okio.Okio;

public class IoTest {
    public static void main(String[] args) {
        okio1();
    }

    private static void okio1() {
        File file = new File("./app/test_copy.txt");
        try (BufferedSource bs = Okio.buffer(Okio.source(new File("./app/test.txt")))) {
            System.out.println("read=" + bs.readUtf8());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
