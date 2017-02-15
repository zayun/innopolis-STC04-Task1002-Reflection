package com.innopolis.smoldyrev;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CustomClassLoader extends ClassLoader {

    private String jarFile = "";
    private String urlFile = "";

    public void initFilePath(String jarFile, String urlFile) {
        this.jarFile = jarFile;
        this.urlFile = urlFile;
        loadFile();
    }

    public CustomClassLoader() {
        super(CustomClassLoader.class.getClassLoader());
    }

    public Class loadClass(String className) throws ClassNotFoundException {
        return findClass(className);
    }

    private void loadFile() {
        try {
            URL connection = new URL(urlFile);
            HttpURLConnection urlconn;
            urlconn = (HttpURLConnection) connection.openConnection();
            urlconn.setRequestMethod("GET");
            urlconn.connect();
            InputStream in = urlconn.getInputStream();

            OutputStream writer = new FileOutputStream(jarFile);
            byte buffer[] = new byte[512];

            while (in.read(buffer) > 0) {
                writer.write(buffer);
            }
            writer.flush();
            writer.close();
            in.close();
            System.out.println(urlFile+" was downloaded");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Class findClass(String className) {

        byte classByte[];
        Class result = null;

        try {
            return findSystemClass(className);
        } catch (Exception e) {
            e.getMessage();
        }

        try {

            JarFile jar = new JarFile(jarFile);
            JarEntry entry = jar.getJarEntry(className + ".class");

            InputStream is = jar.getInputStream(entry);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue = is.read();
            while (-1 != nextValue) {
                byteStream.write(nextValue);
                nextValue = is.read();
            }

            classByte = byteStream.toByteArray();
            result = defineClass(className, classByte, 0, classByte.length, null);

            if (result!=null) System.out.println("Class "+className+" founded");
            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
