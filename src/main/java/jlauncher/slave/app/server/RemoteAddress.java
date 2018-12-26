package jlauncher.slave.app.server;

import java.net.InetAddress;

public class RemoteAddress {

    private InetAddress host;
    private Integer port;

    public RemoteAddress(InetAddress host, Integer port){
        this.host = host;
        this.port = port;
    }

    InetAddress getHost(){
        return host;
    }

    Integer getPort(){
        return port;
    }
}
