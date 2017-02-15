package com.innopolis.smoldyrev;

/**
 * Created by smoldyrev on 15.02.17.
 */
public class Animal {

    private String type;
    private String name;
    private Integer age;

    public Animal(String type, String name, Integer age) {
        this.type = type;
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        System.out.println(2222);
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
