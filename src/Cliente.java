

import java.net.*;
import java.io.*;
public class Cliente {
	public static void main(String[] args) {
		try{
			Socket c = new Socket("192.168.0.53", 50001);
			InputStream i = c.getInputStream();
			OutputStream o = c.getOutputStream();
			String str;
			while(c.isConnected()){
				byte [] line = new byte[100];				
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
