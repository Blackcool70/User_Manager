package com.usrmngr;

import com.usrmngr.client.ClientMain;
import com.usrmngr.server.ServerMain;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javafx.application.Application.launch;

public class Main {

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
                case "server":
                    launch(ServerMain.class);
                    break;
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

