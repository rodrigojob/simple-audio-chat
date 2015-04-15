import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.DataLine;

public class SimpleVideoChat {

	static TargetDataLine targetLine;

	public boolean soundCheck() {
		try {
			AudioFormat format = new AudioFormat(Encoding.PCM_SIGNED, 44100,
					16, 2, 4, 44100, false);

			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			if (!AudioSystem.isLineSupported(info)) {
				System.err.println("Sound input error");
			}
			targetLine = (TargetDataLine) AudioSystem.getLine(info);
			targetLine.open();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void openStream(InputStream is, OutputStream os) {
		System.out.println("inputstream and output stream fetched");

		GetMicThread gmt = new GetMicThread(is);
		SendMicThread smt = new SendMicThread(targetLine, os);

		try {
			gmt.start();
			smt.start();
			Thread.sleep(50000);
			System.exit(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		System.out.print("Accept connections on (port): ");
		Scanner scan = new Scanner(System.in);
		int port = scan.nextInt();

		SimpleVideoChat svc = new SimpleVideoChat();
		Server server = new Server(port, svc);
		if (svc.soundCheck()) {
			server.start();
		} else {
			System.out.println("No sound input detected.");
			System.exit(1);
		}
		System.out.print("Connect to (Address): ");
		Scanner scan2 = new Scanner(System.in);
		String address = scan2.next();

		System.out.print("Connect to (port): ");
		Scanner scan3 = new Scanner(System.in);
		int port2 = scan3.nextInt();

		Client client = new Client(port2, address, svc);
	}
}