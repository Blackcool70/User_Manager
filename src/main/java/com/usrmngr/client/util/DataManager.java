package com.usrmngr.client.util;

import java.io.File;
import java.util.Scanner;

public  class DataManager {
    public static String readFile(String filename) {
        String result = "";
        try {
            File file = new File(filename);

            Scanner scanner = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            String line = scanner.nextLine();

            while (scanner.hasNextLine()) {
                sb.append(line);
                line = scanner.next();
            }
            sb.append(line);
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args) {

    }
}
