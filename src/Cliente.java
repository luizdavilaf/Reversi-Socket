

import java.net.*;
import java.io.*;
public class Cliente {
	private static Socket clienteSocket;
	private InputStream input;
	private OutputStream output;
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
	
	public void receberMsg() {
		mensagemRecebida = null;
		boolean read=false;
		try {			
			while(read==false){
				byte[] line = new byte[1000];				
				input.read(line);				
				mensagemRecebida = new String(line);
				mensagemRecebida = mensagemRecebida.trim();
				if (mensagemRecebida.contains("--end--") ) {
					read = true;
				}else{
					System.out.println(mensagemRecebida);					
				}
			}
		} catch (Exception err) {
			System.err.println(err);
		}
	}

	public void jogar() {
		boolean sent = false;
		try {
			while (sent==false) {
				byte[] line = new byte[1500];
				mensagemRecebida = mensagemRecebida.trim();
				if(mensagemRecebida.contains("jogada")){
					System.out.println("Escolha sua jogada");
					System.in.read(line);
					output.write(line);
				}else{
					System.out.println("Espere o movimento do adversario\n");
				}
				sent=true;
			}

		} catch (Exception err) {
			System.err.println(err);
		}
	}
	










	public static void main(String[] args) throws IOException {
		Cliente cliente = new Cliente();
		boolean endGame = false;
		cliente.connect();
		while(endGame==false){
			cliente.receberMsg();			
			cliente.jogar();
			
		}
	}
}
