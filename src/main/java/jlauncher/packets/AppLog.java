package jlauncher.packets;

import static jlauncher.packets.PacketType.Log;

public class AppLog extends LauncherPacket {

    private String[] out;
    private String appName;
    private byte packetId = 3;

    public AppLog(String[] data, String appName){
        this.appName = appName;
        out = data;
    }

    public String[] getData(){
        return out;
    }

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public PacketType getPacketType() {
        return Log;
    }
}
