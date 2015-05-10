package client;

import java.io.IOException;
import java.net.Socket;

public class Client {

	int port;
	String address;
	SimpleAudioChat svc;

	public Client(int port, String address, SimpleAudioChat svc) {
		this.port = port;
		this.address = address;
		this.svc = svc;
	}

	public void run() {
		Socket socket = null;
		try {
			socket = new Socket(address, port);
			svc.openStream(socket);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	}

}
