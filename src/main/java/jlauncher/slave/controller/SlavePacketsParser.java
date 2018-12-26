package jlauncher.slave.controller;

import jlauncher.packets.StartPacket;
import jlauncher.packets.StopPacket;
import jlauncher.slave.app.controller.AppController;
import jlauncher.slave.app.server.SlaveServer;

class SlavePacketsParser {

    SlavePacketsParser(AppController appController, SlaveServer slaveServer){
        appController.addListener(slaveServer::send);

        slaveServer.addListener(data -> {
            try {
                switch (data.getPacketType()){
                    case Stop:
                        StopPacket stopPacket = (StopPacket) data;
                        appController.stop(stopPacket.getAppName());
                        break;
                    case Start:
                        StartPacket startPacket = (StartPacket) data;
                        appController.start(startPacket.getAppName(), startPacket.getDir(), startPacket.getCmd(), slaveServer::send);
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
