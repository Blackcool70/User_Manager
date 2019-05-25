package com.usrmngr.client;

import com.usrmngr.client.CLI.CLIUserManagerClientMain;
import javafx.application.Application;

public class ClientMain {

    public static void main(String[] args) {
        if (args.length == 1 && "-cli".equalsIgnoreCase(args[0])) {
            System.out.print("NO gui Got here");
            new CLIUserManagerClientMain().runApp();
        } else {
            System.out.print("gui Got here");
            Application.launch(FXUserManagerClientMain.class);
        }
    }
}
