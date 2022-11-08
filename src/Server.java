

import java.net.*;
import java.util.ArrayList;
import java.io.*;
public class Server {

	private ServerSocket ss;
	private int numJogadores;
	private String color;
	private String nome;
	private ArrayList<Player> players = new ArrayList<>();

	public Server(){
		System.out.println("Servidor Reversi");
		numJogadores=0;
		try {
			ss = new ServerSocket(50001);
			
		} catch (IOException e) {
			System.out.println("Erro no construtor do servidor!!");
		}		
	}

	public ArrayList<Player> acceptConnections(){
		try {
			System.out.println("Aguardando jogadores...");
			while(numJogadores<2){
				Socket s = ss.accept();									
				numJogadores++;
				if(numJogadores==1){
					color="white";
				}else{
					color="black";
				}
				byte [] line = new byte[100];
				InputStream i = s.getInputStream();
				OutputStream o = s.getOutputStream();
				o.write(line);
				i.read(line);
				nome = new String(line);
				Player p = new Player(s, color,nome);
				players.add(p);
				System.out.println("Jogador #"+ numJogadores + " conectou.");
			}
			System.out.println("Dois jogadores conectados. Iniciando jogo...");
			
		} catch (Exception e) {
			System.out.println("Erro ao aceitar conexões...");
		}
		return players;		
	}

	public void broadcastacceptConnections() {
		try {
			System.out.println("enviando objeto");
			while(numJogadores<2){
				
			}
			System.out.println("Dois jogadores conectados. Iniciando jogo...");
			
		} catch (Exception e) {
			System.out.println("Erro ao aceitar conexões...");
		}
			
	}

	public static void main(String[] args) throws IOException {
		
		Server server = new Server();
		ArrayList<Player> players = server.acceptConnections();
		System.out.println(players.toString());
		
		Game game = new Game(players, server);
		game.startGame();
		
		
		

	}

	

}
