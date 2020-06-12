package com.g12.golddigger.utils;

import java.io.*;
import java.util.Properties;

public class IOManager {
    private final String fileName;
    private final Properties properties;

    public IOManager(String fileName) throws IOException {
        this.fileName = fileName;
        this.properties = new Properties();

        this.load();
    }

    public void load() throws IOException {
        InputStream fileReader = new FileInputStream(this.fileName);
        properties.load(fileReader);
    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }

    public void setAndSave(String property, String value) throws IOException {
        properties.setProperty(property, value);
        this.save();
    }

    private void save() throws IOException {
        OutputStream output = new FileOutputStream(this.fileName);
        properties.store(output, null);
        output.flush();
    }
}




























