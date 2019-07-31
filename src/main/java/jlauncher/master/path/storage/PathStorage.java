package jlauncher.master.path.storage;

import java.io.File;

public class PathStorage {

    private static PathStorage instance;
    private String configFileExtension = "conf";
    private String configFileBase = "JLauncher";
    private String configFileName = configFileBase + "." + configFileExtension;
    private String defaultDir = System.getProperty("user.dir");
    private String currentDir = new File(defaultDir).toString();
    private File currentPathToFile = new File(currentDir, configFileName);

    public void storeConfigDir(File dir){
        currentPathToFile = dir;
    }

    public File getLogFile(String name){
        new File(currentDir + "//log").mkdir();
        return new File(currentDir + "//log", name + ".log");
    }

    public void changeCurrentPathToFile(File file){
        if(file != null) currentPathToFile = file;
    }

    public File getCurrentPathToFile(){return currentPathToFile;}

    public String getCurrentDir() {
        return currentDir;
    }

    public String getConfigFileBase() {
        return configFileBase;
    }

    public String getConfigFileExtension() {
        return configFileExtension;
    }

    private PathStorage(){

    }

    public static PathStorage getInstance(){
        if(instance == null) instance = new PathStorage();
        return instance;
    }
}
