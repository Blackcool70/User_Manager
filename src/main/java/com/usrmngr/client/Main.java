package com.usrmngr.client;

import com.usrmngr.client.CLI.CLIUserManagerMain;
import com.usrmngr.client.GUI.FXUserManagerMain;
import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
        if (args.length == 1 && "-cli".equalsIgnoreCase(args[0])) {
            System.out.print("NO gui Got here");
            new CLIUserManagerMain().runApp();
        } else {
            System.out.print("gui Got here");
            Application.launch(FXUserManagerMain.class);
        }
    }
}
