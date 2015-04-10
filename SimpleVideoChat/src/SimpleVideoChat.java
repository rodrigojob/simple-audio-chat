import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.FieldPosition;
import java.util.Scanner;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
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

	// public void testRecording() {
	// try {
	// System.out.println("Start recoriding");
	// targetLine.start();
	//
	// Runner runner = new Runner(targetLine);
	// runner.start();
	// Thread.sleep(5000);
	// targetLine.stop();
	// targetLine.close();
	// System.out.println("Thread ended");
	// }
	//
	// catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }

	public void openStream(InputStream is, OutputStream os) {
		System.out.println("inputstream and output stream fetched");

		GetRunner getrunner = new GetRunner(is);
		Runner runner = new Runner(targetLine, os);

		try {
			getrunner.start();
			runner.start();
			Thread.sleep(5000);
			System.exit(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

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

		Client client = new Client(port, address, svc);
	}
}