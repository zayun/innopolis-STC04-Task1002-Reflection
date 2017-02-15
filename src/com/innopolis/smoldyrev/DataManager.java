package com.innopolis.smoldyrev;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.lang.reflect.Field;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document; // обратите внимание !


/**
 * Created by smoldyrev on 10.02.17.
 */
public class DataManager {

    public static Object obj2;

    public static void serializeXML(Object obj, String fileName) throws ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation impl = builder.getDOMImplementation();
        Document doc = impl.createDocument(null,
                null,
                null);

        String fullClassName = obj.getClass().getTypeName();
        String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf('.')+1,fullClassName.length());
        Element e1 = doc.createElement(simpleClassName);
        doc.appendChild(e1);
        Field[] declaredFields = obj.getClass().getDeclaredFields();

        for (Field field :
                declaredFields) {
            try {
                field.setAccessible(true);;
                Element e2 = doc.createElement("field");
                e2.setAttribute("type",field.getType().getSimpleName());
                e2.setAttribute("name",field.getName());
                e2.setAttribute("value",field.get(obj).toString());
                e1.appendChild(e2);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        try {
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(fileName));
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void deserializeXML(Object obj, String fileName) throws IOException, SAXException, ParserConfigurationException, IllegalAccessException, InstantiationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); //создали фабрику строителей, сложный и грамосткий процесс (по реже выполняйте это действие)
        // f.setValidating(false); // не делать проверку валидации
        DocumentBuilder db = dbf.newDocumentBuilder(); // создали конкретного строителя документа
        Document doc = db.parse(new File(fileName)); // стооитель построил документ
        //Document - тоже является нодом, и импленментирует методы

        obj2 = obj.getClass().newInstance();
        visit(doc, 0);
    }

    public static void visit(Node node, int level) {
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node childNode = list.item(i); // текущий нод
            process(childNode, level + 1); // обработка
            visit(childNode, level + 1); // рекурсия
        }
    }

    public static void process(Node node, int level) {

        if (node instanceof Element){
            Element element = (Element) node;

            NodeList nodeList = element.getElementsByTagName("field");
            int length = nodeList.getLength();
            for (int i=0;i<length;++i) {
                Element el = (Element) nodeList.item(i);


                    System.out.println(el.getAttribute("value"));
                    System.out.println(el.getAttribute("name"));
                    //Field field = obj2.getClass().getField(el.getAttribute("name"));
                    //field.set(obj2,el.getAttribute("value"));

            }
        }
        System.out.println();
    }
}
