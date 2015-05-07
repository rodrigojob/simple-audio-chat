package mainserver;

public class ServerUpdate extends Thread {
	MainServer ms;

	public ServerUpdate(MainServer ms) {
		this.ms = ms;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String s = ms.update();
			ms.usersOnline(s);
		}
	}
}
