package com.usrmngr;

import com.usrmngr.client.ClientMain;

import java.io.File;

import static javafx.application.Application.launch;

/**
 * Handles the startup of the server's  or client GUI through the JAVAFX framework.
 */
public class Main {
    public static final  String APP_NAME = "User Manager";
    public static final  String PROGRAM_DATA_DIRECTORY = System.getProperty("user.home") + File.separator
            + "." + APP_NAME.replace(" ","_");
    public static final String APP_CONFIG_PATH =  PROGRAM_DATA_DIRECTORY + File.separator + "CLIENT.CONFIG";
    public static void main(String[] args) {
        if (args.length >= 1 && "-start".equalsIgnoreCase(args[0])) {
            switch (args[1].toLowerCase()){
                case "client":
                    launch(ClientMain.class);
                default:
                    System.out.println("Invalid Switch");
            }
        } else {
            launch(ClientMain.class);
        }
    }
}

//TODO: This is a test comment to fix the pull requests.

