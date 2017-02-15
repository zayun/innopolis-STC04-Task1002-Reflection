package com.innopolis.smoldyrev;

import javax.xml.parsers.ParserConfigurationException;

public class Main {

    public static void main(String[] args) {

        CustomClassLoader ccl = new CustomClassLoader();
        ccl.initFilePath("temp/Animal.jar",
                "https://github.com/zayun/" +
                "innopolis-STC04-Task1002-Reflection/" +
                "blob/master/Animal.jar?raw=true");
        try {
            Object obj = ccl.findClass("Animal").newInstance();

            DataManager.serializeXML(obj, "temp/Animal.xml");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }
}
