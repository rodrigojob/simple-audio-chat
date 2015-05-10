package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class GetMicThread extends Thread {

	private Socket socket;
	private SourceDataLine speakers;
	private SimpleVideoChat svc;

	public GetMicThread(Socket socket, SimpleVideoChat svc) {
		this.socket = socket;
		this.svc = svc;
	}

	@Override
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
			DataLine.Info dataLineInfo = new DataLine.Info(
					SourceDataLine.class, format);
			speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			speakers.open(format);
			speakers.start();
			byte[] buffer = new byte[1024];
			int n;

			while ((n = is.read(buffer)) > 0) {
				speakers.write(buffer, 0, n);
				if (svc.terminate()) {
					socket.close();
					return;
				}
			}
		} catch (SocketException e) {
			try {
				socket.close();
				svc.stop();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}
}
