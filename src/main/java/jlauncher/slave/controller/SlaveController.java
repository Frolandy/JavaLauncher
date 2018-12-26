package jlauncher.slave.controller;

import jlauncher.packets.StartPacket;
import jlauncher.packets.StopPacket;
import jlauncher.slave.app.controller.AppController;
import jlauncher.slave.app.server.SlaveServer;
import jlauncher.slave.app.storage.AppStorage;

public class SlaveController {

    private Integer defaultPort = 25552;
    private AppController appController = new AppController(new AppStorage());
    private SlaveServer server = new SlaveServer(defaultPort);

    public SlaveController(){
        appController.addListener(data -> server.send(data));

        server.addListener(data -> {
            try {
                switch (data.getPacketType()){
                    case Stop:
                        StopPacket stopPacket = (StopPacket) data;
                        appController.stop(stopPacket.getAppName());
                        break;
                    case Start:
                        StartPacket startPacket = (StartPacket) data;
                        appController.start(startPacket.getAppName(), startPacket.getDir(), startPacket.getCmd(), log -> {
                            server.send(log);
                        });
                        break;
                    default:
                        break;
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

        });
    }


}
