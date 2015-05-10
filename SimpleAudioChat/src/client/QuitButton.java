package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitButton implements ActionListener {

	SimpleAudioChat svc;

	public QuitButton(SimpleAudioChat svc) {
		this.svc = svc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		svc.stop();
		System.exit(0);
	}

}
