import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	public Client(int port, String address, SimpleVideoChat svc) {

		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		try {
			socket = new Socket(address, port);
			is = socket.getInputStream();
			os = socket.getOutputStream();
			svc.openStream(is, os);
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
