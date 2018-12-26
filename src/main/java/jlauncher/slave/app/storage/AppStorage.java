package jlauncher.slave.app.storage;

import java.util.*;

public class AppStorage {

    private Map<String, ApplicationApi> applications = Collections.synchronizedMap(new HashMap<>());

    public ApplicationApi add(String name){
        if(applications.containsKey(name)) {
            applications.get(name).stop();
            applications.remove(name);
            return add(name);
        }
        else {
            ApplicationApi app = new App(name);
            applications.put(name, app);
            return app;
        }
    }

    public Boolean remove(String name){
        if(applications.containsKey(name)) {
            Boolean stopped = applications.get(name).stop();
            if(stopped) applications.remove(name);
            return stopped;
        }else return false;
    }

    public Optional<ApplicationApi> get(String name){
        return Optional.ofNullable(applications.get(name));
    }

    public Collection<ApplicationApi> get(){
        return applications.values();
    }

}
