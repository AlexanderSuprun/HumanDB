package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class Controller manages the data
 */

public class Controller {
    private ArrayList<Human> database = new ArrayList<>();
    private final String filename = "Database";

    void addNewRecord() {
        View view = new View();
        if (readFromDatabase() != null) {
            database = readFromDatabase();
            ArrayList<Human> humanList = view.dataInput();

            if (!humanList.isEmpty()) {
                database.addAll(humanList);
            }

            sortArrayList();
            if (!saveToDatabase()) {
                view.error(1);
            }
            view.viewLists();
        } else {
            view.error(2);
        }
    }

    ArrayList<Human> getDatabase() {
        this.database = readFromDatabase();
        return this.database;
    }

    void sortArrayList() {
        database.sort(Comparator.comparing(Human::getName));
    }

    boolean saveToDatabase() {
        IOClass ioClass = new IOClass();
        return ioClass.saveToFile(database, filename);
    }

    ArrayList<Human> readFromDatabase () {
        IOClass ioClass = new IOClass();
        ArrayList<Human> list = null;

        if (ioClass.readFromFile(filename) instanceof List) {
            list = (ArrayList<Human>) ioClass.readFromFile(filename);
        }
        return list;
    }
}
