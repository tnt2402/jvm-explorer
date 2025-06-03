package com.example.jvmexplorer.cli;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JVMManager jvmManager = new JVMManager();
        CLIHandler cliHandler = new CLIHandler(jvmManager);

        if (args.length > 0) {
            // Non-interactive mode
            cliHandler.processCommand(String.join(" ", args));
        } else {
            // Interactive mode
            Scanner scanner = new Scanner(System.in);
            System.out.println("JVM Explorer CLI - Type 'help' for commands");
            while (true) {
                System.out.print("> ");
                String command = scanner.nextLine().trim();
                if (command.equalsIgnoreCase("exit")) {
                    break;
                }
                cliHandler.processCommand(command);
            }
            scanner.close();
        }
    }
}