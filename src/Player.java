

import java.net.Socket;


public class Player {

    private Socket socket;
    private String color;

    public Player(Socket socket, String color){
        this.socket = socket;
        this.color= color;
    }
    


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Player [remoteIp=" + socket + ", color=" + color + "]";
    }



    public Socket getSocket() {
        return socket;
    }



    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
}
