package jlauncher.slave.GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Tray {

    private Boolean stopped = false;

    public Tray(GuiEvents eventsController){
        try {
            if (SystemTray.isSupported()) {
                BufferedImage image = ImageIO.read(getClass().getResource("images/icon.png"));
                PopupMenu popupMenu = new PopupMenu();
                MenuItem itemStartStop = new MenuItem("Stop");
                MenuItem itemRestart = new MenuItem("Restart");
                MenuItem itemExit = new MenuItem("Exit");

                popupMenu.add(itemStartStop);
                popupMenu.add(itemRestart);
                popupMenu.add(itemExit);

                TrayIcon trayIcon = new TrayIcon(image, "LauncherSlave", popupMenu);

                trayIcon.setImageAutoSize(true);

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

                SystemTray.getSystemTray().add(trayIcon);

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
