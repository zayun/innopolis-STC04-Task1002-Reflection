package com.innopolis.smoldyrev;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Human man = new Human();

        man.setName("Egor");
        man.setAge(30);
        man.setSalary(50.5d);
        //man.save("resources/"+man.getName()+".xml");
        man.load("resources/"+man.getName()+".xml");

        try {
            try {
                DataManager.deserializeXML(man,"resources/"+man.getName()+".xml");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            man.getClass().getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        man.getClass().getDeclaredFields();
        man.getClass().getMethods();
    }
}
