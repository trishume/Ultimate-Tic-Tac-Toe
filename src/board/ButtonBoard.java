package board;

import javax.swing.JButton;
//import java.awt.event.ActionEvent;
//import java.awt.Color;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class ButtonBoard extends Board implements Serializable {
	public JButton[] buttons;
	public int boardId;
	public String winner = " ";
	public ButtonBoard(int size,String[] spots,ActionListener list,int id){
		super(size,list);
		boardId = id;
		buttons = new JButton[spots.length];
		for (int i = 0; i < spots.length; i++){
			buttons[i] = new JButton(" "); // create button
			buttons[i].setActionCommand(id+":"+spots[i]); //Add unique string
			buttons[i].addActionListener(this); // "Listen" for button[] events
			buttons[i].setEnabled(false);
		}
		for( int i = 0; i < spots.length; i++){
			this.add(buttons[i], i );
		}
	}
	public void enableButtons(){
		for (int i = 0; i < buttons.length; i++){
			//this.setVisible(false);
			this.getComponent(i).setEnabled(true); // Enable Board
			//this.getComponent(i).setBackground(Color.getHSBColor((float)Math.random(), (float)Math.random(), (float)Math.random()));
			//this.setVisible(true);
		}
		//System.out.println("buttons enabled: "+ this.getComponent(1).isEnabled() +" on grid " + boardId);		
	}
	public void disableButtons(){
		for (int i = 0; i < buttons.length; i++)
			buttons[i].setEnabled(false); // Reset Board	
	}
	public void setColor(Color color){
		for( int i = 0; i < buttons.length; i++)
	    	this.buttons[i].setBackground(color);
	}
	public void resetBoard(){
		for (int i = 0; i < buttons.length; i++){
			buttons[i].setText(" "); // Reset Board
			winner = " ";
		}
	}
	public int getOccupied(String Player){
		int occupied=0;
		for (int i = 0; i < buttons.length; i++){
			if(buttons[i].getText().equals(Player)){
				occupied++; // occupied button
			}
		}
		return occupied;
	}
	public String[] getArrayBoard(){
		String[] array = new String[9];
		for (int i = 0; i < buttons.length; i++)
			array[i] = buttons[i].getText(); // Reset Board
		return array;
	}
	public String getTest(){
		return "tested board: " + boardId;
	}
	public boolean anySpace(){
		for( int i = 0; i < buttons.length; i++)
			if( this.buttons[i].getText().equals(" ") )
				return true;
		return false;
	}
	public boolean checkforwinner() {
		//Horizontal Rows Top
		if( this.buttons[0].getText().equals(this.buttons[1].getText())  // Winner on top horizontal row
				&& this.buttons[1].getText().equals(this.buttons[2].getText()) && (this.buttons[0].getText().equals(" ") == false) ){
			System.out.println( "horizontal1 is winner");
			winner =  this.buttons[0].getText();
			return true;
		}
		if( this.buttons[3].getText().equals(this.buttons[4].getText())  // Winner on middle horizontal row
				&&this.buttons[4].getText().equals(this.buttons[5].getText()) && (this.buttons[3].getText().equals(" ") == false) ){
			System.out.println( "horizontal2 is winner");
			winner = this.buttons[3].getText();
			return true;
		}
		if(this.buttons[6].getText().equals(this.buttons[7].getText())  // Winner on bottom horizontal row
				&&this.buttons[7].getText().equals(this.buttons[8].getText()) && (this.buttons[6].getText().equals(" ") == false) ){
			System.out.println( "horizontal3");
			winner = this.buttons[6].getText();
			return true;
		}
		//Vertical Rows Left
		if(this.buttons[0].getText().equals(this.buttons[3].getText())  // Winner on left vertical row
				&&this.buttons[3].getText().equals(this.buttons[6].getText()) && (this.buttons[0].getText().equals(" ") == false) ){
			System.out.println( "vertical1 is winner");
			winner = this.buttons[0].getText();
			return true;
		}
		if( this.buttons[1].getText().equals(this.buttons[4].getText())  // Winner on middle vertical row
				&& this.buttons[4].getText().equals(this.buttons[7].getText()) && (this.buttons[1].getText().equals(" ") == false) ){
			System.out.println( "vertical2");
			winner =  this.buttons[1].getText();
			return true;
		}
		if( this.buttons[2].getText().equals(this.buttons[5].getText())  // Winner on right vertical row
				&& this.buttons[5].getText().equals(this.buttons[8].getText()) && (this.buttons[2].getText().equals(" ") == false) ){
			System.out.println( "vertical3");
			winner =  this.buttons[2].getText();
			return true;
		}
		// Corners
		if( this.buttons[0].getText().equals(this.buttons[4].getText())  // Winner on top left corner
				&& this.buttons[4].getText().equals(this.buttons[8].getText()) && (this.buttons[0].getText().equals(" ") == false) ){
			System.out.println( "corner1 is winner");
			winner =  this.buttons[0].getText();
			return true;
		}
		if( this.buttons[2].getText().equals(this.buttons[4].getText())  // Winner on top right corner
				&& this.buttons[4].getText().equals(this.buttons[6].getText()) && (this.buttons[2].getText().equals(" ") == false) ){
			System.out.println( "corner2 is winner");
			winner =  this.buttons[2].getText();
			return true;
		}
		return false;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//String[] components = e.getActionCommand().split(":");
		//System.out.println("Button board Got: " + components[1] );
		super.actionPerformed(e);
		
	}
}
