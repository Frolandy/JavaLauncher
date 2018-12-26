package jlauncher.packets;

import static jlauncher.packets.PacketType.Start;

public class StartPacket extends LauncherPacket {

    private String appName;
    private String dir;
    private String cmd;
    private byte packetId = 0;

    public StartPacket(String appName, String dir, String cmd){
        this.appName = appName;
        this.dir = dir;
        this.cmd = cmd;
    }

    public String getAppName() { return appName; }
    public String getDir() { return dir; }
    public String getCmd() { return cmd; }

    @Override
    public byte[] serialize() {
        byte[] packetIdBytes = {packetId};
        byte[] appNameBytes = appName.getBytes();
        byte[] dirBytes = dir.getBytes();
        byte[] cmdBytes = cmd.getBytes();
        byte[] bytes = new byte[1 + appNameBytes.length + dirBytes.length + cmdBytes.length];
        System.arraycopy(packetIdBytes, 0, bytes, 0, packetIdBytes.length);
        System.arraycopy(appNameBytes, 0, bytes, packetIdBytes.length, appNameBytes.length);
        System.arraycopy(dirBytes, 0, bytes, appNameBytes.length + packetIdBytes.length, dirBytes.length);
        System.arraycopy(cmdBytes, 0, bytes, appNameBytes.length + packetIdBytes.length + dirBytes.length, cmdBytes.length);
        return bytes;
    }

    @Override
    public PacketType getPacketType() { return Start; }
}
