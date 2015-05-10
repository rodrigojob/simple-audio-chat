package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class SendMicThread extends Thread {

	private TargetDataLine targetLine;
	private Socket socket;
	private SimpleAudioChat svc;

	public SendMicThread(TargetDataLine targetLine, Socket socket,
			SimpleAudioChat svc) {
		this.targetLine = targetLine;
		this.socket = socket;
		this.svc = svc;
	}

	@Override
	public void run() {
		try {
			OutputStream os = socket.getOutputStream();
			AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
			targetLine = AudioSystem.getTargetDataLine(format);

			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			targetLine = (TargetDataLine) AudioSystem.getLine(info);
			targetLine.open(format);
			targetLine.start();
			byte[] buffer = new byte[1024];
			int n = 0;
			while ((n = targetLine.read(buffer, 0, 1024)) > 0) {
				os.write(buffer, 0, n);
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
