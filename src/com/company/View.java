package com.company;

import java.util.*;

/**
 * Class View validates entered data and displays data
 */

public class View {
    private Controller controller = new Controller();

    /**
     * Checks entered data
     * @return new record
     */
    ArrayList<Human> dataInput() {
        ArrayList<Human> humanList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean isNewRecordCreated = true;
        String input;
        String[] inputSplit;
        System.out.println("Введите данные для добавления новой записи \n" +
                "Нажмите клавишу 'Enter' для вывода записей из базы данных \n" +
                "(Имя Фамилия Возраст Пол)");

        while (true) {
            input = scanner.nextLine().trim();
            inputSplit = input.split("\\s+");
            if (input.isEmpty()) {
                isNewRecordCreated = false;
                break;
            } else if (inputSplit.length < 4) {
                System.out.println("Введены не все значения!");
            } else if (!isNumeric(inputSplit[2]) || Integer.parseInt(inputSplit[2]) < 0) {
                System.out.println("Возраст должен быть положительным числом!");
            } else if (!inputSplit[3].equals("M") && !inputSplit[3].equals("F")) {
                System.out.println("Неверно введено значение параметра пол!" +
                        "\nПараметр пол может принимать только значения F или M");
            } else {
                break;
            }
        }

        if (isNewRecordCreated) {
            humanList.add(new Human(
                    inputSplit[0],
                    inputSplit[1],
                    Integer.parseInt(inputSplit[2]),
                    inputSplit[3].charAt(0)));
        }
        return humanList;
    }

    void viewLists () {
        int count = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (count < 4) {
                System.out.println("\nНажмите 'Enter' для вывода следующего списка");
            }
            if (count < 4 && scanner.nextLine().isEmpty()) {
                switch (count) {
                    case 0 -> displayAllRecords();
                    case 1 -> displaySurnameWithoutE();
                    case 2 -> displayHaveMoreThanTwoA();
                    case 3 -> displayNameSurnameStartWithSameLetter();
                }
                count++;
            } else {
                break;
            }
        }
        averageAge();
        maxOccurringChar();
    }

    void displayAllRecords() {
        ArrayList<Human> database = controller.getDatabase();
        System.out.println("\nВсе введенные записи:\n");
        database.forEach(human -> System.out.println(human.toString()));
    }

    /**
     * Displays records if surnames does not include 'E'
     */
    void displaySurnameWithoutE() {
        ArrayList<Human> database = controller.getDatabase();
        System.out.println("\nВ фамилии у которых нет буквы 'E' и возраст которых не больше 35:\n");
        for (Human human : database) {
            if (!human.getSurname().toUpperCase().contains("Е") && human.getAge() <= 35) {
                System.out.println(human.toString());
            }
        }
    }

    /**
     * Displays records if they have more than two 'A' in name and surname
     */
    void displayHaveMoreThanTwoA() {
        ArrayList<Human> database = controller.getDatabase();
        String nameAndSurname;
        int count = 0;
        System.out.println("\nИмя и фамилия которых содержит больше двух букв 'A', пол - мужской:\n");
        for (Human human : database) {
            if (human.getGender() == 'M') {
                nameAndSurname = human.getName().concat(human.getSurname()).toUpperCase();

                for (int i = 0; i < nameAndSurname.length(); i++) {
                    if (nameAndSurname.charAt(i) == 'А') {
                        count++;
                    }
                }

                if (count > 2) {
                    System.out.println(human.toString());
                }
            }
            count = 0;
        }
    }

    /**
     * Displays records if they start with the save letter
     */
    void displayNameSurnameStartWithSameLetter() {
        ArrayList<Human> database = controller.getDatabase();
        System.out.println("\nИмя и фамилия которых начинаются с одинаковой буквы:\n");
        for (Human human : database) {
            if (human.getName().charAt(0) == human.getSurname().charAt(0)) {
                System.out.println(human.toString());
            }
        }
    }

    /**
     * Displays average age for men and women
     */
    void averageAge() {
        int sumAgeF = 0, sumAgeM = 0, quantityF = 0, quantityM = 0;
        ArrayList<Human> database = controller.getDatabase();
        for (Human human : database) {
            if (human.getGender() == 'M') {
                sumAgeM += human.getAge();
                quantityM++;
            } else {
                sumAgeF += human.getAge();
                quantityF++;
            }
        }
        if (quantityF != 0 & quantityM != 0) {
            System.out.println("\nСредний возраст:");
            System.out.println("Для мужчин: " + (sumAgeM / quantityM));
            System.out.println("Для женщин: " + (sumAgeF / quantityF));
        }
    }

    /**
     * Displays maximum occurring char in name and surname for men and women
     */
    void maxOccurringChar() {
        ArrayList<Human> database = controller.getDatabase();
        Map<Character, Integer> mapF = new HashMap<>();
        Map<Character, Integer> mapM = new HashMap<>();
        int maxOccurringF = 0, maxOccurringM = 0;
        String str;

        for (Human human : database) {
            str = human.getName().concat(human.getSurname()).toUpperCase();

            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);

                if (human.getGender() == 'F') {
                    if (mapF.containsKey(ch)) {
                        mapF.put(ch, mapF.get(ch) + 1);
                    } else {
                        mapF.put(ch, 1);
                    }

                    if (mapF.get(ch) > maxOccurringF) {
                        maxOccurringF = mapF.get(ch);
                    }
                } else if (human.getGender() == 'M') {
                    if (mapM.containsKey(ch)) {
                        mapM.put(ch, mapM.get(ch) + 1);
                    } else {
                        mapM.put(ch, 1);
                    }

                    if (mapM.get(ch) > maxOccurringM) {
                        maxOccurringM = mapM.get(ch);
                    }
                }
            }
        }

        System.out.println("\nНаиболее часто встречающаяся буква в имени и фамилии:");
        System.out.println("Для мужчин: ");
        for (Map.Entry<Character, Integer> entry : mapM.entrySet()) {
            if (entry.getValue() == maxOccurringM) {
                System.out.print("" + entry.getKey() + ", ");
            }
        }

        System.out.println("\nДля женщин: ");
        for (Map.Entry<Character, Integer> entry : mapF.entrySet()) {
            if (entry.getValue() == maxOccurringF) {
                System.out.print("" + entry.getKey() + ", ");
            }
        }
    }

    void error(int errorCode) {
        System.out.println("Ошибка:");

        switch (errorCode) {
            case 1 -> System.out.println("Данные не были сохранены");
            case 2 -> System.out.println("Невозможно считать данные из файла");
        }
    }

    private boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
            return true;
        } catch (NullPointerException | NumberFormatException ex) {
            return false;
        }
    }
}
