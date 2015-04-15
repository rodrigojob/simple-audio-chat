import java.net.*;
import java.util.*;
import java.io.*;
import javax.sound.sampled.*;

public class MicClient {
	private Socket socket;
	private SourceDataLine speakers;

	public static void main(String[] args) throws Exception {
		if(args.length < 2) {
			System.out.println("java MicClient <host> <port>");
			return;
		}
		MicClient client = new MicClient(args[0], Integer.parseInt(args[1]));
		client.receive();
	}


	public MicClient(String host, int port) throws Exception {
		this.socket = new Socket(host, port);
	}

	public void receive() throws Exception {
        int bytesRead = 0;
		InputStream in = socket.getInputStream();
		AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
        speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        speakers.open(format);
        speakers.start();
        byte[] buffer = new byte[1024];
        int n;

        while((n = in.read(buffer)) > 0) {
        	speakers.write(buffer, 0, n);
        }
	}
}
