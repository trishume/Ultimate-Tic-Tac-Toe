package board;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import AI.ttt.BaseTTTAI;
import board.ButtonBoard;
import board.FixedTile;

public class TttTile extends JPanel  implements ActionListener {
	public ButtonBoard board;
	public FixedTile tile;
	public boolean fixed = false;
	public String winner = " ";
	CardLayout cl;
	ActionListener listener;
	public TttTile(int size,String[] spots,ActionListener list,int id){
		super(new CardLayout());
		listener = list;
		board = new ButtonBoard(size,spots,list,id);
		tile = new FixedTile("!");
		this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		cl = (CardLayout)(this.getLayout());
		this.add(board, "board");
		this.add(tile, "tile");
		//cl.show(this, "board");
	}
	public void enableButtons(){
			board.enableButtons();
			
	}
	public boolean anySpace(){
		return board.anySpace();
	}
	public String checkWin(){
		return BaseTTTAI.checkWin(board);
	}
	public void disableButtons(){
		if(!fixed){
			board.disableButtons();
		}
	}
	public void winTile(String state){
		tile.setState(state);
		winner = state;
		cl.show(this,"tile");
		fixed = true;
	}
	public void resetTile(){
		cl.show(this,"board");
		fixed = false;
		winner = " ";
		board.resetBoard();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		listener.actionPerformed(e);
		System.out.println("Tile Got: " + e.getActionCommand() );
		
	}
}
