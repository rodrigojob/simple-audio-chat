import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionListener {
	MainServer ms;

	public ConnectionListener(MainServer ms) {
		this.ms = ms;
	}

	@SuppressWarnings("resource")
	public void run() {
		ServerSocket server = null;
		try {
			server = new ServerSocket(30000);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}

		ExecutorService service = Executors.newFixedThreadPool(5);
		ExecutorService service2 = Executors.newFixedThreadPool(5);

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

			ServerThread runner = new ServerThread(socket, ms);
			service.submit(runner);
			
			ServerUpdate su = new ServerUpdate(ms);
			service2.submit(su);

		}
	}
	
	public static void main(String args[]){
		MainServer ms = new MainServer();
		ConnectionListener cl = new ConnectionListener(ms);
	}
}
