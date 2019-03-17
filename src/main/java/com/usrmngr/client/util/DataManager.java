package com.usrmngr.client.util;

import org.json.JSONArray;

import java.io.File;
import java.util.Scanner;


public class DataManager {
    public static String readFile(String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(filename);

            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();

            while (scanner.hasNextLine()) {
                sb.append(line);
                line = scanner.nextLine();
            }
            sb.append(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String DATA_PATH = "src/main/resources/samples/MOCK_DATA.json";
        String data = DataManager.readFile(DATA_PATH);
    }
}
