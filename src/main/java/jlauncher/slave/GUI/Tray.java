package jlauncher.slave.GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Tray {

    private SystemTray tray;
    private TrayIcon trayIcon;

    public Tray(GuiEvents eventsController){
        try {
            if (SystemTray.isSupported()) {
                BufferedImage image = ImageIO.read(getClass().getResource("images/icon.png"));
                trayIcon = new TrayIcon(image, "LauncherSlave", new TrayMenu(eventsController));
                trayIcon.setImageAutoSize(true);
                tray = SystemTray.getSystemTray();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void show(){
        if(tray != null && trayIcon != null) try{
            tray.add(trayIcon);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
