package jlauncher.packets;

import static jlauncher.packets.PacketType.Status;

public class AppStatus extends LauncherPacket {

    private String appName;
    private Boolean isAlive;
    private byte packetId = 2;

    public AppStatus(String appName, Boolean isAlive){
        this.appName = appName;
        this.isAlive = isAlive;
    }

    public String getAppName(){
        return appName;
    }

    public Boolean isAlive(){
        return isAlive;
    }


    @Override
    public byte[] serialize() {
        byte[] packetIdBytes = {packetId};
        byte[] appNameBytes = appName.getBytes();
        byte[] aliveBytes = {(isAlive) ? (byte)1 : 0};
        byte[] bytes = new byte[packetIdBytes.length + appNameBytes.length];
        System.arraycopy(packetIdBytes, 0, bytes, 0, packetIdBytes.length);
        System.arraycopy(appNameBytes, 0, bytes, packetIdBytes.length, appNameBytes.length);
        System.arraycopy(aliveBytes, 0, bytes, packetIdBytes.length + appNameBytes.length, aliveBytes.length);
        return bytes;
    }

    @Override
    public PacketType getPacketType() {
        return Status;
    }
}
