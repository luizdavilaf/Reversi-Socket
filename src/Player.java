

import java.net.Socket;


public class Player {

    private Socket socket;
    private String color;
    private String name;

    public Player(Socket socket, String color, String name){
        this.socket = socket;
        this.color= color;
        this.name = name;
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



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }
    
}
