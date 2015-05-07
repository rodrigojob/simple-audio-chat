package client;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class GUI {

	private JFrame frame;
	private JList<String> jlist;
	private Socket socket;
	private String username;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				String username = JOptionPane
						.showInputDialog("Input username:");

				SimpleVideoChat svc = new SimpleVideoChat();
				// INSERT INCOMMING PORT HERE //
				Server server = new Server(30000, svc);
				if (svc.soundCheck()) {
					server.start();
				} else {
					JOptionPane.showMessageDialog(null,
							"No sound input detected.");
					System.exit(1);
				}

				Socket socket = null;
				try {
					// INSERT MASTER SERVER IP AND PORT HERE //
					socket = new Socket("192.168.0.17", 40000);
					String s = "username:" + username;
					socket.getOutputStream().write(s.getBytes());
				} catch (IOException e) {
					System.out.println(e);
					System.exit(1);
				}
				
				GUI gui = new GUI(socket, username);
				gui.frame.setVisible(true);

				ServerListener sl = new ServerListener(socket, svc, gui);
				sl.start();
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI(Socket socket, String username) {
		this.socket = socket;
		this.username = username;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		jlist = new JList<String>();
		jlist.setBounds(25, 32, 202, 214);
		frame.getContentPane().add(jlist);

		JLabel lblOnlineUsers = new JLabel("Online users:");
		lblOnlineUsers.setBounds(25, 6, 91, 16);
		frame.getContentPane().add(lblOnlineUsers);

		JButton btnCall = new JButton("Call");
		btnCall.setBounds(290, 135, 117, 29);
		frame.getContentPane().add(btnCall);
		btnCall.addActionListener(new CallButton(jlist, socket));

		JButton btnHangUp = new JButton("Hang up");
		btnHangUp.setBounds(290, 176, 117, 29);
		frame.getContentPane().add(btnHangUp);

		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(290, 217, 117, 29);
		frame.getContentPane().add(btnQuit);

		JLabel lblYourUsernameIs = new JLabel("Your username is:");
		lblYourUsernameIs.setBounds(290, 32, 125, 16);
		frame.getContentPane().add(lblYourUsernameIs);

		JLabel lblNewLabel = new JLabel(username);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(269, 60, 160, 16);
		frame.getContentPane().add(lblNewLabel);
	}

	public void updateList(ArrayList<String> list) {
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		for (String s : list) {
			dlm.addElement(s);
		}
		jlist.removeAll();
		jlist.setModel(dlm);
	}

}
