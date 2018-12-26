package jlauncher.packets;

import java.util.Optional;

public abstract class LauncherPacket {
    public abstract byte[] serialize();

    public static Optional<LauncherPacket> deserialize(byte[] buff){
        return Optional.empty();
    }

    public abstract PacketType getPacketType();
}
