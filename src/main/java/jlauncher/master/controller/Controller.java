package jlauncher.master.controller;

import com.typesafe.config.ConfigValueFactory;
import javafx.stage.FileChooser;
import jlauncher.config.ConfigReader;
import jlauncher.master.path.storage.PathStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class Controller {

    private ConfigReader configReader = ConfigReader.getInstance();
    private PathStorage pathStorage = PathStorage.getInstance();

    public void start(ArrayList<Command> commands){

    }

    public void start(Command command){

    }

    public void stop(ArrayList<Command> commands){

    }

    public void stop(Command command){

    }

    public void saveCommandConfig(ArrayList<Command> commands){
        File file = pathStorage.getCurrentPathToFile();

        ArrayList<Variable> variables;

        if (file != null){
            variables = configReader.readVariables(file);
        }else variables = new ArrayList<>();

        try{
            System.out.println("Try write file " + file.toString());
            PrintWriter writer = new PrintWriter(file);
            writer.write("Launcher{\n");
            writeCommandConfig(commands, writer);
            writeVariableConfig(variables, writer);
            writer.write("}\n");
            writer.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void saveVariableConfig(ArrayList<Variable> variables){
        File file = pathStorage.getCurrentPathToFile();

        ArrayList<Command> commands;

        if (file != null){
            commands = configReader.readCommands(file);
        }else commands = new ArrayList<>();

        try{
            PrintWriter writer = new PrintWriter(file);
            writer.write("Launcher{\n");
            writeCommandConfig(commands, writer);
            writeVariableConfig(variables, writer);
            writer.write("}\n");
            writer.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private void writeCommandConfig(ArrayList<Command> commands, PrintWriter writer){
        writer.write("  Applications = [\n");
        commands.forEach(command -> {
            String address = ConfigValueFactory.fromAnyRef(command.getComputerAddress()).render();
            String dir = ConfigValueFactory.fromAnyRef(command.getDir()).render();
            String cmd = ConfigValueFactory.fromAnyRef(command.getCommand()).render();
            String appName = ConfigValueFactory.fromAnyRef(command.getAppName()).render();
            String uud = ConfigValueFactory.fromAnyRef(command.getId()).render();
            String enable = ConfigValueFactory.fromAnyRef(command.getEnable()).render();
            String group = ConfigValueFactory.fromAnyRef(command.getGroup()).render();
            String delay = ConfigValueFactory.fromAnyRef(command.getDelay()).render();
            String[] onStopCommands = command.getOnStopCommands();
            writer.write("    {\n");
            writer.write("     remoteAddress = " + address + "\n");
            writer.write("     dir = " + dir + "\n");
            writer.write("     cmd = " + cmd + "\n");
            writer.write("     appName = " + appName + "\n");
            writer.write("     group = " + group + "\n");
            writer.write("     enable = " + enable + "\n");
            writer.write("     id = " + uud + "\n");
            writer.write("     delay = " + delay + "\n");
            writer.write("     onStopCommands=[");

            for(String stopCommand: onStopCommands){
                writer.write(ConfigValueFactory.fromAnyRef(stopCommand).render());
                writer.write(", ");
            }
            writer.write("]\n");
            writer.write("    }\n");
        });
        writer.write("  ]\n");
    }

    private void writeVariableConfig(ArrayList<Variable> variables, PrintWriter writer){
        writer.write("  vars = [\n");
        variables.forEach(variable -> {
            String variableName = ConfigValueFactory.fromAnyRef(variable.getVariableName()).render();
            String value = ConfigValueFactory.fromAnyRef(variable.getValue()).render();
            String id = ConfigValueFactory.fromAnyRef(variable.getId()).render();
            writer.write("    {\n");
            writer.write("     varName = " + variableName + "\n");
            writer.write("     value = " + value + "\n");
            writer.write("     id = " + id + "\n");
            writer.write("    }\n");
        });
        writer.write("  ]\n");
    }
}
