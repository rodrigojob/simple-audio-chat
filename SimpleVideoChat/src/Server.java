import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

	int port;
	boolean connected = false;
	OutputStream os;
	SimpleVideoChat svc;

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
			try {
				System.out.println("New connection from "
						+ socket.getInetAddress().getHostName() + ":"
						+ socket.getPort() + ". Local port: "
						+ socket.getLocalPort());
				InputStream is = socket.getInputStream();
				os = socket.getOutputStream();
				svc.openStream(is, os);

			} catch (IOException e) {
				System.out.println(e);
			}
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
