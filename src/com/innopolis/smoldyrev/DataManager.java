package com.innopolis.smoldyrev;


import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Field;

import static jdk.nashorn.internal.objects.NativeString.substring;

/**
 * Created by smoldyrev on 10.02.17.
 */
public class DataManager {
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
}
