

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
			String str;
			do{
				byte [] line = new byte[100];
				System.out.println("Digite algo");
				System.in.read(line);
				o.write(line);
				i.read(line);
				str = new String(line);
				System.out.println(str);					
			}while(!str.trim().equals("bye"));
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
				c.close();
				
			}
			
		
		}
		catch (Exception err){
			System.err.println(err);
		}
	}
}
