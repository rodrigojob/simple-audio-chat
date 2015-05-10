package mainserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map.Entry;

public class MainServer {
	Socket socket;
	InputStream is;
	OutputStream os;
	private HashMap<String, Socket> connections;

	public MainServer() {
		connections = new HashMap<String, Socket>();
	}

	public synchronized void addConnection(String userName, Socket socket) {
		connections.put(userName, socket);
	}

	public synchronized void removeConnections(String userName) {
		connections.remove(userName);
	}

	public synchronized String getUser(String userName) {
		socket = connections.get(userName);

		String ip = socket.getInetAddress().getHostAddress();

		return ip;
	}

	public synchronized String update() {
		String s = "";
		for (Entry<String, Socket> entry : connections.entrySet()) {
			String userName = entry.getKey();
			s += userName;
			s += ",";
		}
		return s;
	}

	public synchronized void usersOnline(String s) {
		for (Entry<String, Socket> entry : connections.entrySet()) {
			Socket socket = entry.getValue();
			String update = "update:" + s;
			try {
				os = socket.getOutputStream();
				os.write(update.getBytes());
			} catch (SocketException e) {
				removeConnections(entry.getKey());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
