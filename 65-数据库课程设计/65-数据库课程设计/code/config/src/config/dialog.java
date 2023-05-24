package config;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class dialog extends JDialog{
	JTextField warn=new JTextField();
	JButton jb1Button=new JButton("确认");
	JButton jb2Button=new JButton("取消");
	Font font=new Font("楷体", Font.PLAIN, 28);
	Font jFont=new Font("华文行楷",Font.BOLD,22);
	
	public dialog(String content,char c) {
		super(new JFrame(),"注意",true);
		this.setDefaultCloseOperation(this.HIDE_ON_CLOSE);
		this.setSize(420,210);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		
		warn.setText(content);
		warn.setHorizontalAlignment(JTextField.CENTER);
		warn.setBounds(15,10,380,80);
		warn.setEditable(false);
		warn.setBorder(null);
		warn.setFont(font);
		warn.setOpaque(false);
		warn.setForeground(Color.black);
		this.add(warn);

		if(c=='s')
		{
			jb1Button.setSize(80,50);jb1Button.setFont(jFont);
			jb1Button.setBounds((this.getWidth()-jb1Button.getWidth()-5)/2,(this.getHeight()-58-jb1Button.getHeight()),jb1Button.getWidth(),jb1Button.getHeight());
			this.add(jb1Button);
		}
		else
		{
			jb1Button.setSize(80,50);jb2Button.setSize(80,50);
			jb1Button.setFont(jFont);jb2Button.setFont(jFont);
			jb1Button.setBounds((this.getWidth()-jb1Button.getWidth()-5)/3,(this.getHeight()-58-jb1Button.getHeight()),jb1Button.getWidth(),jb1Button.getHeight());
			jb2Button.setBounds((this.getWidth()-jb2Button.getWidth()-5)*2/3,(this.getHeight()-58-jb2Button.getHeight()),jb2Button.getWidth(),jb2Button.getHeight());
			this.add(jb1Button);this.add(jb2Button);
		}
		
	}
}
