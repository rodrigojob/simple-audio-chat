package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HangUpButton implements ActionListener {

	SimpleVideoChat svc;

	public HangUpButton(SimpleVideoChat svc) {
		this.svc = svc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		svc.stop();
	}

}
