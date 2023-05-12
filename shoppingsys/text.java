package config;

import java.awt.Color;
import java.awt.Font;
import java.io.ObjectInputStream.GetField;

import javax.swing.JTextField;

public class text extends JTextField{
	public text() {}
	public text(String s) {
		super(s);
		this.setEditable(false);
		this.setOpaque(false);
		this.setForeground(Color.black);
		Font font=new Font("楷体", Font.PLAIN, 18);
		this.setFont(font);
		this.setHorizontalAlignment(JTextField.CENTER);
	}
}
