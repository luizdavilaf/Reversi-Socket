

import java.net.SocketAddress;

public class Player {

    private SocketAddress remoteIp;
    private String color;

    public Player(SocketAddress remoteIp, String color){
        this.remoteIp = remoteIp;
        this.color= color;
    }
    
    public SocketAddress getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(SocketAddress remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Player [remoteIp=" + remoteIp + ", color=" + color + "]";
    }
    
}
