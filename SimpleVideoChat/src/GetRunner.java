import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class GetRunner extends Thread {

	InputStream is;

	public GetRunner(InputStream is) {
		this.is = is;
	}

	@Override
	public void run() {

		try {
			FileOutputStream fos = new FileOutputStream(new File("test.wav"));
			int length = -1;
			byte[] buffer = new byte[1024];

			while ((length = is.read(buffer)) > -1) {
				fos.write(buffer, 0, length);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
