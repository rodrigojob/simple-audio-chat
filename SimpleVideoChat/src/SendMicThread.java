import java.io.OutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class SendMicThread extends Thread {

	private TargetDataLine targetLine;
	private OutputStream os;

	public SendMicThread(TargetDataLine targetLine, OutputStream os) {
		this.targetLine = targetLine;
		this.os = os;
	}

	@Override
	public void run() {
		try {
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
