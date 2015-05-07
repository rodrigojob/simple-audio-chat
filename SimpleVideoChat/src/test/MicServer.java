package test;
import java.net.*;
import java.util.*;
import java.io.*;
import javax.sound.sampled.*;

public class MicServer {
	ServerSocket socket;
	TargetDataLine microphone;

	public static void main(String[] args) throws Exception {
		if(args.length < 1) {
			System.out.println("java MicServer <port>");
			return;
		}
		MicServer server = new MicServer(Integer.parseInt(args[0]));
		server.listen();
	}

	public MicServer(int port) throws Exception{
		this.socket = new ServerSocket(port);
	}

	public void listen() throws Exception {
		Socket client = socket.accept();
		OutputStream out = client.getOutputStream();
		AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
		microphone = AudioSystem.getTargetDataLine(format);
		
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		microphone = (TargetDataLine) AudioSystem.getLine(info);
		microphone.open(format);
		microphone.start();
		byte[] buffer = new byte[1024];
		int n = 0;
		while((n = microphone.read(buffer, 0, 1024)) > 0) {
			out.write(buffer, 0, n);
		}
	}
}
