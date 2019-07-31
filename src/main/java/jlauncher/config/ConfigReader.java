package jlauncher.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import jlauncher.master.controller.Command;
import jlauncher.master.controller.CommandBuilder;
import jlauncher.master.controller.Variable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigReader {
    private static ConfigReader instance;

    private ConfigReader(){}

    public static ConfigReader getInstance(){
        if(instance == null) instance = new ConfigReader();
        return instance;
    }

    public ArrayList<Command> readCommands(File file){
        ArrayList<Command> commands = new ArrayList<>();
        try{
            List<? extends Config> configs = getConfig(file, "Applications");
            configs.forEach(config -> {
                String address = config.getString("remoteAddress");
                String appName = config.getString("appName");
                String cmd = config.getString("cmd");
                String dir = config.getString("dir");
                String group = config.getString("group");
                boolean enable = config.getBoolean("enable");
                String id = config.getString("id");
                String delay = config.getString("delay");
                List<String> onStopCommandList = config.getStringList("onStopCommands");
                String[] onStopCommand = new String[onStopCommandList.size()];
                onStopCommandList.toArray(onStopCommand);
                CommandBuilder commandBuilder = new CommandBuilder();
                commandBuilder.setAddress(address);
                commandBuilder.setAppName(appName);
                commandBuilder.setCommand(cmd);
                commandBuilder.setDir(dir);
                commandBuilder.setEnable(enable);
                commandBuilder.setId(id);
                commandBuilder.setGroup(group);
                commandBuilder.setDelay(delay);
                commandBuilder.setOnStopCommand(onStopCommand);
                commands.add(new Command(commandBuilder));
            });
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return commands;
    }

    public ArrayList<Variable> readVariables(File file){
        ArrayList<Variable> variables = new ArrayList<>();
        try {
            List<? extends Config> configs = getConfig(file, "Variables");
            configs.forEach(config -> {
                String varName = config.getString("varName");
                String value = config.getString("value");
                String id = config.getString("id");
                variables.add(new Variable(varName, value, id));
            });
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return variables;
    }

    private List<? extends Config> getConfig(File file, String name){
        Config config = ConfigFactory.parseFile(file).resolve();
        return config.getConfig("Launcher").getConfigList(name);
    }
}
