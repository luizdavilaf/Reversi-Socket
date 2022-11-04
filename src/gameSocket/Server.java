package gameSocket;

import java.net.*;
import java.io.*;
public class Server {

	private ServerSocket ss;
	private int numJogadores;

	public Server(){
		System.out.println("Servidor Reversi");
		numJogadores=0;
		try {
			ss = new ServerSocket(50001);
		} catch (IOException e) {
			System.out.println("Erro no construtor do servidor!!");
		}		
	}

	public void acceptConnections(){
		try {
			System.out.println("Aguardando jogadores...");
			while(numJogadores<2){
				Socket s = ss.accept();
				numJogadores++;
				System.out.println("Jogador #"+ numJogadores + "conectou.");
			}
			System.out.println("Dois jogadores conectados. Iniciando jogo...");
		} catch (Exception e) {
			System.out.println("Erro ao aceitar conexões...");
		}		
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.acceptConnections();

	}

}
