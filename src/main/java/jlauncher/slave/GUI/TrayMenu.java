package jlauncher.slave.GUI;

import java.awt.*;

class TrayMenu extends PopupMenu {

    private boolean stopped = false;

    TrayMenu(GuiEvents eventsController){

        MenuItem itemStartStop = new MenuItem("Stop");
        MenuItem itemRestart = new MenuItem("Restart");
        MenuItem itemExit = new MenuItem("Exit");

        this.add(itemStartStop);
        this.add(itemRestart);
        this.add(itemExit);

        itemStartStop.addActionListener(e -> {
            if(!stopped){
                stopped = true;
                eventsController.stop();
                itemStartStop.setLabel("Start");
            }else{
                stopped = false;
                eventsController.start();
                itemStartStop.setLabel("Stop");
            }
        });

        itemRestart.addActionListener(e -> eventsController.restart());
        itemExit.addActionListener(e -> {
            eventsController.exit();
            System.exit(0);
        });

    }
}
