package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * IOClass saves data to file and reads data from file
 */

public class IOClass {

    public boolean saveToFile(Object object, String filename) {
        boolean result = true;
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.flush();
            oos.writeObject(object);
            oos.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
            result = false;
        }
        return result;
    }

    public Object readFromFile(String filename){
        Object object1;
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            object1 = ois.readObject();
        }
        catch(Exception ex){
//            ex.printStackTrace();
            return null;
        }
        return object1;
    }
}
