package com.datastore.util.io;

import com.datastore.model.StoreValue;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.datastore.util.Constants.PROCESS_NAME;

public class FileUtil {

    public void writeObjectToFile(String filePath, Object obj) throws IllegalAccessException {

        validateClientAccess(filePath);

        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;

        try {
            fileOut = new FileOutputStream(filePath);
            out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);

        } catch (IOException i) {
            System.out.println(i.getMessage());
        } finally {
            try {
                fileOut.close();
                out.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Map<String, StoreValue> readObjectFromFile(String filePath) throws IllegalAccessException {

        validateClientAccess(filePath);

        Map<String, StoreValue> map = new ConcurrentHashMap<>();
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        try {
            fileIn = new FileInputStream(filePath);
            in = new ObjectInputStream(fileIn);
            map = (Map<String, StoreValue>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println(i.getMessage());
        } catch (ClassNotFoundException c) {
            System.out.println(c.getMessage());
        } finally {
            try {
                fileIn.close();
                in.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return map;
    }

    private void validateClientAccess(String filePath) throws IllegalAccessException {

        if (!filePath.contains(PROCESS_NAME))
            throw new IllegalAccessException("File access is restricted");
    }

}
