package board;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import AI.ttt.BaseTTTAI;
import board.ButtonBoard;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import board.FixedTile;
import board.TttTile;

public class BoardBoard extends Board implements Serializable {
	JPanel[] boards;
	int numBoards;
	String[] boardSpots;
	public String winner;
	public int activeBoard = 0;
	public BoardBoard(int size,String[] spots,ActionListener list,int bsize){
		super(bsize,list);
		boardSpots = spots;
		numBoards = bsize*bsize;
		for( int i = 0; i < numBoards; i++){
			this.add(new TttTile(size,spots,this,i), i );
		}
	}
	public void enableAll(){
		for (int i = 0; i < numBoards; i++){
			((TttTile)this.getComponent(i)).enableButtons();
		}
	}
	public void disableAll(){
		for (int i = 0; i < numBoards; i++){
			((TttTile)this.getComponent(i)).disableButtons();
		}
	}
	public String[] getArrayBoard(){
		String[] array = new String[9];
		for (int i = 0; i < numBoards; i++)
			array[i] = ((TttTile)this.getComponent(i)).winner; // Reset Board
		return array;
	}
	public void enableOne(int index){
		for (int i = 0; i < numBoards; i++){
			TttTile currentBoard = (TttTile)this.getComponent(i);
			if(i == index){
				if(currentBoard.fixed){
					this.enableAll();
					return;
				} else {
					currentBoard.enableButtons(); // Enable Board
				}
			} else{
					currentBoard.disableButtons();
			}
		}
	}
	public void resetBoard(){
		for (int i = 0; i < numBoards; i++){
			TttTile currentBoard = (TttTile)this.getComponent(i);
			currentBoard.resetTile(); // Reset Board
		}
	}
	public void SetColor(Color color){
		for( int i = 0; i < numBoards; i++)
	    	this.getBoard(i).setColor(color);
	}
	public String getWinner(int index){
		return ((TttTile)this.getComponent(index)).winner;
		
	}
	public ButtonBoard getBoard(int index){
		return ((TttTile)this.getComponent(index)).board;
	}
	public ButtonBoard getActiveBoard(){
		return ((TttTile)this.getComponent(activeBoard)).board;
	}
	private void resetUI(){
		this.invalidate();
		this.revalidate();
		SwingUtilities.updateComponentTreeUI(this);
		this.repaint();
	}
	public void winBoard(int index,String state){
		//this.remove(index);
		System.out.println(state + " - " + index);
		TttTile currentBoard = (TttTile)this.getComponent(index);
		currentBoard.winTile(state);
		
	}
	public boolean checkforwinner() {
		winner = BaseTTTAI.checkWin(this);
		if(winner.equals(" ") == false){
			return true;
		}
		return false;
	}
	public void fixEnds(){ // fix the tiles of finished games
		fixWins();
		fixTies();
		
	}
	public void fixTies(){
		for (int i = 0; i < numBoards; i++){
			TttTile currentBoard = (TttTile)this.getComponent(i);
			boolean space = currentBoard.anySpace();
			if(!space && !currentBoard.fixed){
				currentBoard.winTile(" ");
			}
		}
	}
	public void fixWins(){
		for (int i = 0; i < numBoards; i++){
			TttTile currentBoard = (TttTile)this.getComponent(i);
			String winner = currentBoard.checkWin();
			if(!(winner.equals(" ")) && !currentBoard.fixed){
				currentBoard.winTile(winner);
			}
		}
	}
}
