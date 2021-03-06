package jlauncher.slave.app.storage;

import jlauncher.packets.AppLog;
import jlauncher.utils.Listener;

import java.io.File;
import java.util.List;

public interface ApplicationApi {

    ApplicationApi setCmd(String cmd);

    ApplicationApi setDir(File file);

    File getDir();

    List<String> getCmd();

    boolean start(Listener<AppLog> logListener);

    String getName();

    boolean stop();

    boolean isAlive();

}
