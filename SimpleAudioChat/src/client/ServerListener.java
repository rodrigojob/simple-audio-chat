package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerListener extends Thread {

	private Socket socket;
	private SimpleAudioChat svc;
	private GUI gui;

	public ServerListener(Socket s, SimpleAudioChat svc, GUI gui) {
		this.socket = s;
		this.svc = svc;
		this.gui = gui;
	}

	@Override
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			while (true) {
				int length = -1;
				byte[] buffer = new byte[1024];
				String s = "";

				while ((length = is.read(buffer)) > -1) {
					byte[] messageArray = Arrays.copyOfRange(buffer, 0, length);
					s = new String(messageArray, "UTF-8");

					if (s.startsWith("get:")) {
						String address = s.substring(4);
						System.out.println(address);
						svc.start(30000, address);
					} else if (s.startsWith("update:")) {
						String listString = s.substring(7);
						ArrayList<String> list = new ArrayList<String>(
								Arrays.asList(listString.split(",")));
						gui.updateList(list);
					} else if (s.startsWith("asdf")) {
						socket.close();
						return;
					} else {

					}
					s = "";
				}

			}
		} catch (Exception e) {
			System.out.println("Connection closed.");
		}
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}