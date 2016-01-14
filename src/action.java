import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.swing.*;
import javax.swing.event.*;

import org.apache.commons.codec.binary.Base32;

class update_save {
	public static void update_list() {
		int size = Window.acc_list.size();
		String tmp = "";
		for (int i = 0; i < size; i++) {
			tmp += Window.acc_list.elementAt(i);
			tmp += "\r\n";
			tmp += Window.tok_list.elementAt(i);
			if (i != size - 1)
				tmp += "\r\n";
		}
		//jar
		//File file = new File(update_save.class.getResource("save.txt").getFile());
		//java
		File file = new File("./src/save.txt");
		file.delete();
		try {
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write(tmp);
			fileWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

class add_action implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		String cont = Window.getAcc();
		String tok = Window.getTok();
		tok = tok.toUpperCase();
		tok = tok.replaceAll("\\s", "");
		Window.acc_list.addElement(cont);
		Window.tok_list.addElement(tok);
		update_save.update_list();
	}
}

class del_action implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		int ind = requireris.get_index();
		if (ind >= 0) {
			Window.acc_list.removeElementAt(ind);
			Window.tok_list.removeElementAt(ind);
			update_save.update_list();
		}
	}
}

class tok_action implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		Window.loading.setVisible(false);
		int ind = requireris.get_index();
		if (ind >= 0) {
			String token = (String) Window.tok_list.elementAt(ind);
			token = token.replaceAll("\\s", "");
			token = token.toUpperCase();
			int code = 0;
			try {
				code = requireris.calculateCode(new Base32().decode(token), requireris.get_cTime());
			} catch (NoSuchAlgorithmException err) {
				System.err.println("Error NoSuchAlgorithmException");
			} catch (InvalidKeyException er) {
				System.err.println("Error InvalidKeyException");
			}
			String otp = Integer.toString(code);
			StringBuilder str_tmp = new StringBuilder();
			str_tmp.append(otp);
			str_tmp.insert(3, " ");
			Window.setEcran(str_tmp.toString());
			new Timer(1, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					Window.loading.setVisible(true);
					System.out.print("HERE");
				}
			}).start();
			System.out.println("stop");
			Window.loading.setVisible(false);
		} else
			System.out.print("Select item in list");
	}
}

class mouse implements MouseListener {
	private int rm = 0;

	mouse(int ca) {
		rm = ca;
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		if (rm == 1)
			Window.setAcc();
		else if (rm == 2)
			Window.setTok();
		else
			System.out.print("Error");
	}
}

class indexing implements ListSelectionListener {
	@SuppressWarnings("unchecked")
	public void valueChanged(ListSelectionEvent e) {
		JList<String> tmp = new JList<String>();
		tmp = ((JList<String>) e.getSource());
		int i = tmp.getSelectedIndex();
		requireris.set_index(i);
	}
}