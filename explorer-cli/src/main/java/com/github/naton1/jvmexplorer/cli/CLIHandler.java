package com.example.jvmexplorer.cli;

public class CLIHandler {
    private final JVMManager jvmManager;

    public CLIHandler(JVMManager jvmManager) {
        this.jvmManager = jvmManager;
    }

    public void processCommand(String command) {
        String[] parts = command.split("\\s+");
        if (parts.length == 0) {
            System.out.println("Empty command. Type 'help' for options.");
            return;
        }

        switch (parts[0].toLowerCase()) {
            case "help":
                printHelp();
                break;
            case "list-jvms":
                listJVMs();
                break;
            case "select-jvm":
                if (parts.length > 1) {
                    selectJVM(parts[1]);
                } else {
                    System.out.println("Usage: select-jvm <pid>");
                }
                break;
            case "list-classes":
                listClasses();
                break;
            case "inspect-class":
                if (parts.length > 1) {
                    inspectClass(parts[1]);
                } else {
                    System.out.println("Usage: inspect-class <class-name>");
                }
                break;
            case "edit-field":
                if (parts.length > 3) {
                    editField(parts[1], parts[2], parts[3]);
                } else {
                    System.out.println("Usage: edit-field <class-name> <field-name> <value>");
                }
                break;
            default:
                System.out.println("Unknown command: " + parts[0] + ". Type 'help' for options.");
        }
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  list-jvms                - List all running JVMs");
        System.out.println("  select-jvm <pid>         - Attach to a JVM by PID");
        System.out.println("  list-classes             - List loaded classes in the selected JVM");
        System.out.println("  inspect-class <class>    - Inspect a class's details");
        System.out.println("  edit-field <class> <field> <value> - Edit a field value");
        System.out.println("  exit                     - Quit the CLI");
    }

    private void listJVMs() {
        List<VirtualMachineDescriptor> jvms = jvmManager.listJVMs();
        if (jvms.isEmpty()) {
            System.out.println("No JVMs found.");
            return;
        }
        System.out.println("Running JVMs:");
        for (VirtualMachineDescriptor jvm : jvms) {
            System.out.println("  PID: " + jvm.id() + ", Main: " + jvm.displayName());
        }
    }

    private void selectJVM(String pid) {
        if (jvmManager.attachToJVM(pid)) {
            System.out.println("Attached to JVM with PID: " + pid);
        }
    }

    private void listClasses() {
        if (jvmManager.getCurrentJVM() == null) {
            System.out.println("No JVM selected. Use 'select-jvm <pid>' first.");
            return;
        }
        List<String> classes = jvmManager.listClasses();
        if (classes.isEmpty()) {
            System.out.println("No classes found.");
            return;
        }
        System.out.println("Loaded classes:");
        for (String cls : classes) {
            System.out.println("  " + cls);
        }
    }

    private void inspectClass(String className) {
        if (jvmManager.getCurrentJVM() == null) {
            System.out.println("No JVM selected. Use 'select-jvm <pid>' first.");
            return;
        }
        System.out.println(jvmManager.inspectClass(className));
    }

    private void editField(String className, String fieldName, String value) {
        if (jvmManager.getCurrentJVM() == null) {
            System.out.println("No JVM selected. Use 'select-jvm <pid>' first.");
            return;
        }
        if (jvmManager.editField(className, fieldName, value)) {
            System.out.println("Field updated successfully.");
        } else {
            System.out.println("Failed to update field.");
        }
    }
}