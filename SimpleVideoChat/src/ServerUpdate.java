import java.util.concurrent.TimeUnit;

public class ServerUpdate extends Thread {
	MainServer ms;

	public ServerUpdate(MainServer ms) {
		this.ms = ms;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(5000);
				System.out.println("Hej");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String s = ms.update();
			ms.usersOnline(s);
		}
	}
}
