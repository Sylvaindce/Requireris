import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel container = new JPanel();
	private JPanel side = new JPanel();
	public static JLabel loading;
	private static JLabel screen = new JLabel(" - ", JLabel.CENTER);
	private static JTextField account = new JTextField("Gmail address");
	private static JTextField token = new JTextField("Token");
	private JSplitPane panel;
	private JList<String> acc_jlist;
	public static DefaultListModel<String> acc_list = new DefaultListModel<String>();
	private JLabel my_time = new JLabel("", JLabel.CENTER);
	public static DefaultListModel<String> tok_list = new DefaultListModel<String>();

	public Window() {
		this.setSize(700, 700);
		this.setTitle("Requireris");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setContentPane(container);
		init_cont();
		this.setVisible(true);
	}

	public void init_cont() {
		JPanel header = new JPanel();
		header.setPreferredSize(new Dimension(600, 150));
		side.setPreferredSize(new Dimension(300, 500));

		JLabel logo = new JLabel(new ImageIcon(getClass().getResource("logo.png")));
		logo.setPreferredSize(new Dimension(600, 100));

		Font police = new Font("Open Sans", Font.BOLD, 14);
		JLabel gacc = new JLabel();
		gacc.setFont(police);
		gacc.setPreferredSize(new Dimension(500, 40));
		gacc.setText("Google Account");

		JLabel otp = new JLabel("One Time Password", JLabel.CENTER);
		otp.setFont(police);
		otp.setPreferredSize(new Dimension(300, 80));

		account.setPreferredSize(new Dimension(250, 40));
		account.addMouseListener(new mouse(1));
		token.setPreferredSize(new Dimension(250, 40));
		token.addMouseListener(new mouse(2));

		JButton add = new JButton("Add GAccount");
		add.addActionListener(new add_action());
		add.setPreferredSize(new Dimension(120, 30));
		add.setForeground(Color.WHITE);
		add.setBackground(new Color(79, 145, 239));
		add.setOpaque(true);
		add.setBorderPainted(true);
		add.setFont(new Font("Open Sans", Font.BOLD, 12));

		JButton del = new JButton("Del GAccount");
		del.addActionListener(new del_action());
		del.setPreferredSize(new Dimension(120, 30));
		del.setForeground(Color.WHITE);
		del.setBackground(new Color(79, 145, 239));
		del.setOpaque(true);
		del.setBorderPainted(true);
		del.setFont(new Font("Open Sans", Font.BOLD, 12));

		JButton tok = new JButton("Generate OneTimeToken");
		tok.addActionListener(new tok_action());
		tok.setPreferredSize(new Dimension(200, 30));
		tok.setForeground(Color.WHITE);
		tok.setBackground(new Color(79, 145, 239));
		tok.setOpaque(true);
		tok.setBorderPainted(true);
		tok.setFont(new Font("Open Sans", Font.BOLD, 12));

		screen.setFont(new Font("Open Sans", Font.BOLD, 20));
		screen.setForeground(new Color(79, 145, 239));
		screen.setPreferredSize(new Dimension(300, 50));
		init_list();
		my_time.setPreferredSize(new Dimension(300, 300));
		init_time();

		header.add(logo, BorderLayout.CENTER);
		header.add(gacc, BorderLayout.CENTER);

		loading = new JLabel(new ImageIcon(getClass().getResource("ajax-loader.gif")));
		side.add(account, BorderLayout.CENTER);
		side.add(token, BorderLayout.CENTER);
		side.add(add, BorderLayout.CENTER);
		side.add(del, BorderLayout.CENTER);
		side.add(otp, BorderLayout.CENTER);
		side.add(tok, BorderLayout.CENTER);
		side.add(screen, BorderLayout.CENTER);
		side.add(loading, BorderLayout.CENTER);
		side.add(my_time, BorderLayout.CENTER);

		panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, acc_jlist, side);
		container.add(header, BorderLayout.CENTER);
		container.add(panel, BorderLayout.CENTER);
		loading.setVisible(false);
	}

	public void init_list() {
		String text_in_file = "";
		try {
			text_in_file = requireris.readFile();
		} catch (IOException e) {
			System.err.println("Error read");
		}
		String[] parts;
		if (text_in_file.length() > 0) {
			parts = text_in_file.split("\n");
			for (int i = 0; i < parts.length; i = i + 2) {
				acc_list.addElement(parts[i]);
				tok_list.addElement(parts[i + 1]);
			}
		}
		acc_jlist = new JList<String>(acc_list);
		acc_jlist.setPreferredSize(new Dimension(300, 500));
		acc_jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		acc_jlist.setSelectedIndex(0);
		acc_jlist.addListSelectionListener(new indexing());
	}

	public void init_time() {
		new Timer(0, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Date d = new Date();
				SimpleDateFormat s = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss a");
				my_time.setText(s.format(d));
			}
		}).start();
	}

	public static String getAcc() {
		return (account.getText());
	}

	public static String getTok() {
		return (token.getText());
	}

	public static void setAcc() {
		account.setText("");
	}

	public static void setTok() {
		token.setText("");
	}

	public static void setEcran(String str) {
		screen.setText(str);
	}
}
