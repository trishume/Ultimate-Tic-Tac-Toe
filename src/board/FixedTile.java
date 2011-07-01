package board;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

public class FixedTile extends JPanel {
	int state = 0;
	JLabel label;
	public FixedTile(String state){
		super(new GridLayout(1,1));
		//System.out.println("FixedTile Created!!!");
		this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		label = new JLabel(state, JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setFont(new Font("Dialog", 1, 40));
		this.add(label,0);
	}
	public void setState(String state){
		label.setText(state);
	}
}
