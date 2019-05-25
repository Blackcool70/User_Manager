package com.usrmngr.server;

import com.usrmngr.server.cli.CLIUserManagerServerMain;
import com.usrmngr.server.ui.FXUserManagerServerMain;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javafx.application.Application.launch;

public class Main {
    public static final Logger LOGGER = LogManager.getLogger(Main.class.getName());
    // https://logging.apache.org/log4j/2.x/manual/configuration.html
    public static void main(String[] args) {
        //configure global app  logging
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        LOGGER.log(Level.INFO, "Application started on {}", dateFormat.format(new Date()));

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                LOGGER.log(Level.INFO, "Application stopped on {}.", dateFormat.format(new Date()))));

        //runs a gui or cli version depending on inputs
        if (args.length == 1 && "-cli".equalsIgnoreCase(args[0])) {
            new CLIUserManagerServerMain().runApp();
        } else {
            launch(FXUserManagerServerMain.class);
        }
    }
}

