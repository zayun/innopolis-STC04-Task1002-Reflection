package com.innopolis.smoldyrev;

import javax.xml.parsers.ParserConfigurationException;
import java.io.Serializable;

public class Human implements Serializable{
    private String name;
    private Integer age;
    private double salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Human(String name, Integer age, double salary) {

        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Human() {
    }

    protected void paySalary() {
        System.out.println("i've salary: " + salary);
    }

    public void save(String fileName) {
        DataManager dm = new DataManager();
        try {
            dm.serializeXML(this,fileName);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
