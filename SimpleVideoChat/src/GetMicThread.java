import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class GetMicThread extends Thread {

	private InputStream is;
	private SourceDataLine speakers;

	public GetMicThread(InputStream is) {
		this.is = is;
	}

	@Override
	public void run() {

		try {
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
