

import java.net.*;
import java.util.Scanner;
import java.io.*;
public class Cliente {

	private static Scanner regScan;
	private static String name;

	public void connect(){
		try{
			Socket c = new Socket("192.168.1.18", 50001);
			InputStream i = c.getInputStream();
			OutputStream o = c.getOutputStream();
			String nome=null;
			byte [] line = new byte[100];
			while (nome == null){
				System.out.println("nome"+nome);
				System.out.println("Digite seu nome");				
				System.in.read(line);
				o.write(line);
				i.read(line);
				nome = new String(nome);
			}
		}
		catch (Exception err){
			System.err.println(err);
		}
	}
	

	










	public static void main(String[] args) {
		Cliente cliente = new Cliente();
		cliente.connect();
	}
}
