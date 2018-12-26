package jlauncher.packets;

import static jlauncher.packets.PacketType.Stop;

public class StopPacket extends LauncherPacket {

    private String appName;
    private byte packetId = 1;

    public StopPacket(String appName){ this.appName = appName; }

    public String getAppName() { return appName; }

    @Override
    public byte[] serialize() {
        byte[] packetIdBytes = {packetId};
        byte[] appNameBytes = appName.getBytes();
        byte[] bytes = new byte[packetIdBytes.length + appNameBytes.length];
        System.arraycopy(packetIdBytes, 0, bytes, 0, packetIdBytes.length);
        System.arraycopy(appNameBytes, 0, bytes, packetIdBytes.length, appNameBytes.length);
        return bytes;
    }

    @Override
    public PacketType getPacketType() {
        return Stop;
    }
}
