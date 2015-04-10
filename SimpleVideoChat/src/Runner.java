import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

public class Runner extends Thread {

	TargetDataLine targetLine;
	OutputStream os;

	public Runner(TargetDataLine targetLine, OutputStream os) {
		this.targetLine = targetLine;
		this.os = os;
	}

	@Override
	public void run() {
		AudioInputStream audioStream = new AudioInputStream(targetLine);

		try {
			AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, os);
			// int length = -1;
			// byte[] buffer = new byte[1024];
			//
			// while ((length = audioStream.read(buffer)) > -1) {
			// os.write(buffer, 0, length);
			//
			// }

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// File audioFile = new File("TESTFIL.wav");
		// try {
		// AudioSystem
		// .write(audioStream, AudioFileFormat.Type.WAVE, audioFile);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}
}
