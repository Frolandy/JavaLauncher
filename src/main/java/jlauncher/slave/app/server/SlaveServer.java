package jlauncher.slave.app.server;

import jlauncher.packets.LauncherPacket;
import jlauncher.utils.Listener;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SlaveServer {

    private Integer slavePort;
    private DatagramSocket socket;
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private List<Listener<LauncherPacket>> packetListeners = Collections.synchronizedList(new ArrayList<>());
    private byte[] buf = new byte[256];
    private List<RemoteAddress> remoteAddresses = Collections.synchronizedList(new ArrayList<>());

    private Thread receiveThread = new Thread("SlaveServer: ReceiveDataThread"){
        @Override
        public void run(){
            while (!Thread.interrupted()) {
                receiveData();
            }
        }
    };

    public SlaveServer(Integer slavePort){
        this.slavePort = slavePort;
        connect();
        receiveThread.start();
    }

    public void addListener(Listener<LauncherPacket> listener){
        packetListeners.add(listener);
    }

    private void connect(){
        try{
            socket = new DatagramSocket(slavePort);
            if(receiveThread.getState() == Thread.State.WAITING) receiveThread.notify();
        }catch (Exception ex){
            ex.printStackTrace();
            executor.schedule(this::connect, 1, TimeUnit.SECONDS);
        }
    }

    private void receiveData() {
        try {
            if (socket != null) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                remoteAddresses.add(new RemoteAddress(packet.getAddress(), packet.getPort()));
                LauncherPacket.deserialize(packet.getData()).ifPresent(pack -> packetListeners.forEach(listener -> listener.sendData(pack)));
            }
        } catch (Exception ex) {
            reconnect();
        }
    }

    public void send(LauncherPacket msg){
        if(socket != null) {
            remoteAddresses.forEach(adr -> {
                byte[] buffer = msg.serialize();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adr.getHost(), adr.getPort());
                try {
                    socket.send(packet);
                }catch (Exception ex){
                    ex.printStackTrace();
                    reconnect();
                }
            });
        }
    }

    private void reconnect(){
        waitReceive();
        socket.close();
        connect();
    }

    private void waitReceive(){
        try {
            receiveThread.wait();
        }catch (Exception ex){
            ex.printStackTrace();
            executor.schedule(this::waitReceive, 1, TimeUnit.SECONDS);
        }
    }

}
