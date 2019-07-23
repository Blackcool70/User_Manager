package com.usrmngr;

import com.usrmngr.client.ClientMain;

import java.io.File;

import static javafx.application.Application.launch;

public class Main {
    /**
     * Handles the startup of the server's  or client GUI through the JAVAFX framework.
     */
    public static final  String APP_NAME = "User Manager";
    public static final  String PROGRAM_DATA_DIRECTORY = System.getProperty("user.home") + File.separator
            + "." + APP_NAME.replace(" ","_");
    public static final String APP_CONFIG_PATH =  PROGRAM_DATA_DIRECTORY + File.separator + "CLIENT.CONFIG";

    public static void main(String[] args) {
//        LOGGER.log(Level.INFO, "Application started on {}", dateFormat.format(new Date()));
//
//        Thread thread = new Thread(() ->
//                LOGGER.log(Level.INFO, "Application stopped on {}.", dateFormat.format(new Date())));
//        thread.setName("main");
//        Runtime.getRuntime().addShutdownHook(thread);

        //runs a gui or cli version depending on inputs
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

