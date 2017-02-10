package com.innopolis.smoldyrev;

public class Main {

    public static void main(String[] args) {
        Human man = new Human();

        man.setName("Egor");
        man.setAge(30);
        man.setSalary(50.5d);
        man.save("resources/"+man.getName()+".xml");

        try {
            man.getClass().getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        man.getClass().getDeclaredFields();
        man.getClass().getMethods();
    }
}
