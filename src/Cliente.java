

import java.net.*;
import java.io.*;
public class Cliente {
	private Socket clienteSocket;
	private DataInputStream input;
	private DataOutputStream output;
	private String mensagemRecebida;
	//byte[] mensagemRecebida = new byte[100];
	private String mensagemEnviada;

	public void connect(){
		try{
			clienteSocket = new Socket("localhost", 50001);
			input = new DataInputStream(clienteSocket.getInputStream());
			output = new DataOutputStream(clienteSocket.getOutputStream());
			String nome=null;
			byte [] line = new byte[100];
			while (nome == null){				
				System.out.println("Digite seu nome");				
				System.in.read(line);
				output.write(line);
				input.read(line);
				nome = new String(line);
			}
		}
		catch (Exception err){
			System.err.println(err);
		}
	}
	
	public void jogar() {
		try {
			
			while(true){
				byte[] line = new byte[1000];
				input.read(line);
				mensagemRecebida = new String(line);
				System.out.println(mensagemRecebida);
				System.in.read(line);				
				output.write(line);
				output.flush();

			}
			
			
		} catch (Exception err) {
			System.err.println(err);
		}
	}
	










	public static void main(String[] args) {
		Cliente cliente = new Cliente();
		cliente.connect();
		cliente.jogar();
	}
}
