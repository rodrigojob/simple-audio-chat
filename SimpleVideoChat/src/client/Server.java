package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

	private int port;
	private SimpleVideoChat svc;

	public Server(int port, SimpleVideoChat svc) {
		this.port = port;
		this.svc = svc;
	}

	@SuppressWarnings("resource")
	public void run() {
		ServerSocket server = null;
		if (port == 0) {
			System.out.println("Fill in port.");
			System.exit(1);
		}
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		while (true) {
			Socket socket = null;
			try {
				socket = server.accept();
			} catch (IOException e) {
				System.out.println(e);
				System.exit(1);
			}
			System.out.println("New connection from "
					+ socket.getInetAddress().getHostName() + ":"
					+ socket.getPort() + ". Local port: "
					+ socket.getLocalPort());
			// int i = JOptionPane.showConfirmDialog(null,
			// "Answer incomming call?", "Incomming call",
			// JOptionPane.YES_NO_OPTION);
			// if (i == JOptionPane.YES_OPTION) {
			svc.openStream(socket);
			// } else {
			// try {
			// socket.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// }
		}
	}
}
