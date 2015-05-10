package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HangUpButton implements ActionListener {

	SimpleAudioChat svc;

	public HangUpButton(SimpleAudioChat svc) {
		this.svc = svc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		svc.stop();
	}

}
