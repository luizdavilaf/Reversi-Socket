

import java.net.*;
import java.util.Scanner;
import java.io.*;
public class Cliente {

	private static Scanner regScan;
	private static String name;

	public void connect(){
		try{
			Socket c = new Socket("192.168.0.53", 50001);
			InputStream i = c.getInputStream();
			OutputStream o = c.getOutputStream();
			String nome=null;
			byte [] line = new byte[100];
			do{
				
				System.out.println("Digite seu nome");
				System.out.println("nome"+nome);
				System.in.read(line);
				o.write(line);
				i.read(line);
				nome = new String(nome);

				
									
			}while(nome==null);
		}
		catch (Exception err){
			System.err.println(err);
		}
	}
	

	










	public static void main(String[] args) {
		regScan = new Scanner(System.in);
		System.out.println("Digite seu nome");
		name = regScan.nextLine();


		




		try{
			Socket c = new Socket("192.168.0.53", 50001);
			InputStream i = c.getInputStream();
			OutputStream o = c.getOutputStream();
			String str;
			while(c.isConnected()){
				byte [] line = new byte[1000];				
				i.read(line);
				str = new String(line);
				System.out.println(str);
				
				
			}
			
		
		}
		catch (Exception err){
			System.err.println(err);
		}
	}
}
