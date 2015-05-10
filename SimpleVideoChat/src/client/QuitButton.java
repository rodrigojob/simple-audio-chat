package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitButton implements ActionListener {

	SimpleVideoChat svc;

	public QuitButton(SimpleVideoChat svc) {
		this.svc = svc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		svc.stop();
		System.exit(0);
	}

}
