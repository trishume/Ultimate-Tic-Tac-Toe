package board;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Board extends JPanel implements ActionListener {
	ActionListener listener;
	int boardSize;
	public Board(int size,ActionListener list){
		super();
		boardSize = size;
		listener = list;
		this.setLayout(new GridLayout(size,size));
		this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		listener.actionPerformed(e);
		//System.out.println("Board Got: " + e.getActionCommand() );
		// TODO Auto-generated method stub
		
	}
}
