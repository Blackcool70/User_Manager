package com.usrmngr.server;

import com.usrmngr.server.CLI.CLIUserManagerServerMain;
import javafx.application.Application;

public class ServerMain {

    public static void main(String[] args) {
        if (args.length == 1 && "-cli".equalsIgnoreCase(args[0])) {
            new CLIUserManagerServerMain().runApp();
        } else {
            Application.launch(FXUserManagerServerMain.class);
        }
    }
}

