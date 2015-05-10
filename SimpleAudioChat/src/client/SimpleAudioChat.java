package client;

import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.DataLine;

public class SimpleAudioChat {

	static TargetDataLine targetLine;
	private boolean terminate = false;
	private Client client;

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

	public void openStream(Socket socket) {
		System.out.println("inputstream and output stream fetched");

		GetMicThread gmt = new GetMicThread(socket, this);
		SendMicThread smt = new SendMicThread(targetLine, socket, this);

		gmt.start();
		smt.start();
	}

	public void start(int port, String address) {
		terminate = false;
		client = new Client(port, address, this);
		client.run();
	}

	public void stop() {
		terminate = true;
	}

	public boolean terminate() {
		return terminate;
	}

	public void setTerminate(boolean b) {
		terminate = b;
	}
}