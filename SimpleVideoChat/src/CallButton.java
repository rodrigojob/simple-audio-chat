import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JList;
import javax.swing.JOptionPane;

public class CallButton implements ActionListener {

	private JList<String> list;
	private Socket socket;

	public CallButton(JList<String> list, Socket socket) {
		this.list = list;
		this.socket = socket;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (list.isSelectionEmpty()) {
			JOptionPane.showMessageDialog(null, "Select a user.");
			return;
		}
		String s = "get:" + list.getSelectedValue().toString();
		try {
			socket.getOutputStream().write(s.getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
