import java.io.IOException;
import java.net.Socket;

public class Client {

	public Client(int port, String address, SimpleVideoChat svc) {

		Socket socket = null;
		try {
			socket = new Socket(address, port);
			svc.openStream(socket);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	}

}
