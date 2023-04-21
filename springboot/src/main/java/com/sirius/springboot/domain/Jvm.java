package com.sirius.springboot.domain;

public class Jvm {

    private int id;
    private String name;

    // 1024B * 100 = 100KB
    byte[] a = new byte[1024*100];

    public Jvm(){}

    public Jvm(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}
