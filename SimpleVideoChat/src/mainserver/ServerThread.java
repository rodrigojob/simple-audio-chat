package mainserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class ServerThread extends Thread {
	Socket socket;
	MainServer ms;

	public ServerThread(Socket socket, MainServer ms) {
		this.socket = socket;
		this.ms = ms;
	}

	public void run() {
		InputStream is;
		OutputStream os;
		try {
			while (true) {
				is = socket.getInputStream();
				os = socket.getOutputStream();

				int length = -1;
				byte[] buffer = new byte[1024];

				while ((length = is.read(buffer)) > -1) {
					byte[] messageArray = Arrays.copyOfRange(buffer, 0, length);
					String message = new String(messageArray, "UTF-8");

					if (message.startsWith("username:")) {
						String s = message.substring(9, length);
						ms.addConnection(s, socket);
					} else if (message.startsWith("get:")) {
						String s = message.substring(4, length);
						String ip = "get:" + ms.getUser(s);
						os.write(ip.getBytes());
					} else if (message.startsWith("quit:")) {
						String s = message.substring(5, length);
						ms.removeConnections(s);
					}
				}
			}

		} catch (IOException e) {
			System.out.println("Connection closed.");
		}

		try {
			socket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
