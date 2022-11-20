

import java.net.*;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.io.*;
public class Cliente {
	private Socket clienteSocket;
	//private InputStream input;
	//private OutputStream output;
	private String mensagemRecebida;	
	//byte[] mensagemRecebida = new byte[100];
	private String mensagemEnviada;
	private BufferedReader input;
	private PrintWriter output;
	Scanner teclado = new Scanner(System.in);

	public void connect(){
		try{
			clienteSocket = new Socket("localhost", 50001);			
			/* input = new DataInputStream(clienteSocket.getInputStream());
			output = new DataOutputStream(clienteSocket.getOutputStream());	 */
			input = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
			output = new PrintWriter(clienteSocket.getOutputStream(), true);					
			String nome=null;
			byte [] line = new byte[100];
			while (nome == null){				
				System.out.println("Digite seu nome");	
				nome = teclado.nextLine();		
				output.println(nome);		
				output.flush();
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
				byte[] line = new byte[5000];									
								
				mensagemRecebida = input.readLine();
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
				byte[] line = new byte[2500];
				mensagemRecebida = input.readLine();
				mensagemRecebida = mensagemRecebida.trim();				
				if(mensagemRecebida.contains("jogada")){					
					System.out.println("\nEscolha sua jogada\n");					
					mensagemEnviada = teclado.nextLine();
					output.print(mensagemEnviada);
					output.flush();
					sent = true;
				}else{					
					System.out.println("\nEspere o movimento do adversario\n");								
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
