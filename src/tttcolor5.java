
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
//import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JColorChooser;
//import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
//import javax.swing.event.PopupMenuEvent;
//import javax.swing.event.PopupMenuListener;
//import javax.swing.plaf.ColorChooserUI;

import AI.ttt.BaseTTTAI;
import AI.uttt.BasicTttOnlyUTTTAI;
import AI.uttt.MainUTTTAI;
import AI.uttt.MinimaxTttOnlyUTTTAI;
import AI.uttt.UTTTAI;
import board.*;
public class tttcolor5 implements ActionListener, Serializable {
	// internal game states
	final int IDLE = 0; // Waiting for move or start game
	final int PLAYING = 1; // Game On
	final int ENDING = 2; // unused
	final int MOVING = 3; // unused
	final int RESET = 4; // Current game interrupted
	final String LNF = "javax.swing.plaf.metal.MetalLookAndFeel";
	final int XMOVE = 0; // Last moved X's
	final int OMOVE = 1; // Last Moved was O's
	
	final String INSTRUCTIONS = "Instructions:\n\n" +
			"Ultimate tictactoe is like tictactoe. " +
			"except much better and with actual strategy. \n\n" +
			"Ultimate TicTacToe is made up of a tictactoe board with " +
			"smaller tictactoe boards inside of it.\n\n" +
			"the catch is that when you go in a cell in one of the smaller boards" +
			" your opponent has to go in the cell corresponding to that cell on the big board.\n\n" +
			"EXAMPLE: If you go on the top left cell on the small board the ai has to go on the top left board on the big board.\n\n" +
			"When you win one of the small boards that cell becomes won on the big board. \n\n" +
			"There is another catch, what happens when you make your opponent go on a board that has been won?\n\n" +
			"ANSWER: Your opponent can go on any board they want. This is very bad for you." +
			"To win the game you have to win three boards in a row on the big board.\n\n" +
			"It is recommended that you try out the game first by just clicking around before you play for real.";
	
	boolean firstmoveflag = false;
	int gamestate = IDLE;
	int lastmove = -1;
	String theWinner; // X or O 
	public boolean fAccess = false;
	// Playing area
	JFrame theWindow = new JFrame("TicTacToe"); // Main Window
	/** instructions window*/
	JFrame instrucWindow = new JFrame("Instructions"); // Instructions Window
	JTextArea instrucText = new JTextArea(INSTRUCTIONS);
	JScrollPane instrucPane = new JScrollPane(instrucText);
	JPanel instrucPanel = new JPanel();
	//JPanel theBoard = new JPanel(); // Main display panel that holds the board
	String[] spots = { "0", "1", "2", "3", "4", "5", "6", "7", "8" };
	//ButtonBoard theBoard = new ButtonBoard(3,spots,this,1); // Main display panel that holds the board
	BoardBoard mainBoard = new BoardBoard(3,spots,this,3);
	ButtonBoard theBoard = ((TttTile)mainBoard.getComponent(mainBoard.activeBoard)).board;
	JButton[] buttons = new JButton[spots.length];
	/** Control Panel */
	JPanel controlpanel = new JPanel();
	JButton startbutton = new JButton("Start New Game");
	JButton savebutton = new JButton("Save Game");
	JButton instrucbutton = new JButton("Instructions");
	
	/** Message Area */
	JPanel screen = new JPanel();
	JTextField messagearea = new JTextField(20);
	
	UTTTAI ai = new MainUTTTAI();
	

	public tttcolor5(boolean fullAccess) {
		fAccess = fullAccess;
		this.setnLnf(LNF);
		createPopupMenu();
		if(fAccess){
			File f = new File("tttsavefile");
			if( f.exists()== true){
				int value = JOptionPane.showConfirmDialog(theWindow, "Do you want to restore your Saved Game?");
				switch( value ){
				case JOptionPane.YES_OPTION:
					loadgame();
					break;
				case JOptionPane.NO_OPTION:
					boolean b = f.delete();
					debug("ttt: " + b);
					break;
				case JOptionPane.CANCEL_OPTION:
					break;
				case JOptionPane.CLOSED_OPTION:
				   break;
				default:
					break;
				}
			}
		}
		// Make the board a simple grid 3x3 layout
		theBoard.setLayout(new GridLayout(3, 3));
		// Mark the spots on the board
		// create instance of each button

		// Put board in window
		theWindow.add(mainBoard, BorderLayout.CENTER);
		// Set up instructions
		//instrucText.append(INSTRUCTIONS);
		instrucText.setFont(new Font("Sans-Serif", Font.BOLD, 14));
		instrucText.setLineWrap(true);
		instrucText.setWrapStyleWord(true);
		instrucText.setEditable(false);
		instrucPane.setPreferredSize(new Dimension(500, 300));
		instrucPanel.add(instrucPane);
		instrucWindow.setResizable(false);
		instrucWindow.add(instrucPanel);
		// Make sure i can see the window title
		//instrucWindow.setMinimumSize(new Dimension(100, 100));
		// Make sure it fits
		instrucWindow.pack();
		// Put start button on the control panel
		startbutton.setActionCommand("start");
		startbutton.addActionListener(this);
		instrucbutton.setActionCommand("instructions");
		instrucbutton.addActionListener(this);
		controlpanel.add(startbutton);
		controlpanel.add(instrucbutton);
		if(fAccess){
			/** Put the Save button on cp */
			savebutton.setActionCommand("save");
			savebutton.addActionListener(this);	
			controlpanel.add(savebutton);
		}
		// Put Control panel in window
		theWindow.add(controlpanel, BorderLayout.SOUTH);
		//Put message area uptop
		screen.add(messagearea);
		messagearea.setText(" Good Luck ");
		//Put in Window
		theWindow.add(screen, BorderLayout.NORTH);
		
		// Make sure i can see the window title
		theWindow.setMinimumSize(new Dimension(700, 700));
		// Make sure it fits
		theWindow.pack();
		// Make sure its visible on the screen
		if(fAccess) theWindow.setVisible(true);
		if(fAccess) theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 
		resetUI();
		
	} // end constructor

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new tttcolor5(true);
	}
	private void aiMove(String player){
		int aiMove = ai.getMove(mainBoard, player);
		mainBoard.getActiveBoard().buttons[aiMove].doClick();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		debug(true,"yeeha1");
		if(e.getSource() instanceof JMenuItem ){
			debug(true,"yeeha");
			Color color = Color.red;
			color = JColorChooser.showDialog( theWindow,"Choose a color", color );
		    mainBoard.SetColor(color);
		    return;
		}
		if (e.getActionCommand().equals("save")) {
			if(fAccess) savegame();
			return;
		}
		if (e.getActionCommand().equals("load")) {
			if(fAccess) loadgame();
			return;
		}
		if (e.getActionCommand().equals("start")) {
			startgame();
			return;
		}
		if (e.getActionCommand().equals("instructions")) {
			instrucWindow.setVisible(true);
			return;
		}
		String[] components = e.getActionCommand().split(":");
		mainBoard.activeBoard = Integer.parseInt(components[0]);
		// hit a spot but no game on
		if (gamestate == IDLE) {
			debug("ActionP: IDLE STATE");
			return;
		}
		debug(true, "Main Got: " + e.getActionCommand());
		if (firstmoveflag == true) { // X goes first
			((JButton) e.getSource()).setText("X");
			firstmoveflag = false;
			lastmove = XMOVE;
		} else if (firstmoveflag == false) {// Game underway Not the first move
			if (((JButton) e.getSource()).getText().equals("X")
					|| ((JButton) e.getSource()).getText().equals("O")) {
				Toolkit tk = (Toolkit.getDefaultToolkit());
				tk.beep();
				debug(true, "Occupado");
				return; // Yes?? Then ignore and return
			} // end if occupied
			// No? then draw x or y
			if (lastmove == XMOVE) {
				((JButton) e.getSource()).setText("O");
				lastmove = OMOVE;
				// Did I win?
				isGameOver();
			} else if (lastmove == OMOVE) {
				((JButton) e.getSource()).setText("X");
				lastmove = XMOVE;
				// Did I win?
				isGameOver();
			}
		}
		//String[] components = e.getActionCommand().split(":");
		mainBoard.activeBoard = Integer.parseInt(components[1]);
		mainBoard.enableOne(mainBoard.activeBoard);
		TttTile currentPanel = (TttTile)mainBoard.getComponent(mainBoard.activeBoard);
		if(!currentPanel.fixed){
			theBoard = (ButtonBoard)currentPanel.board;
		}
		if(mainBoard.checkforwinner()){
			mainBoard.disableAll();
			System.out.println("Big Game Won!!!");
			screenmessage("The entire freaking game has been won by: " + mainBoard.winner);
			return;
		}
		if(!firstmoveflag){
			if(lastmove == XMOVE) {
				aiMove("O");
			} else if(lastmove == OMOVE) {
				aiMove("X");
			}
		}
	}

	private void savegame() {
		if(fAccess){
			try {
				
				ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream("tttsavefile"));
				oos.writeObject(this);
				oos.close();
				savebutton.setText("Load Game");
				savebutton.setActionCommand("load");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private void loadgame() {
		if(fAccess){
			debug(true, "load");
			tttcolor5 myttt = null;
			try {
				
				ObjectInputStream ois = new ObjectInputStream( 
						new FileInputStream("tttsavefile"));
				myttt = (tttcolor5) ois.readObject();
				theWindow.setVisible(false);
				theWindow = myttt.theWindow;
				
				theWindow.setVisible(true);
				screenmessage("Enjoy Your Restored Game");
				controlpanel.validate();
				messagearea.validate();	
				theWindow.pack();
				ois.close();
				savebutton.setText("SaveGame");
				savebutton.setActionCommand("save");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}


	private void isGameOver() {
		mainBoard.fixEnds();
	}

	private void screenmessage(String s) {
		messagearea.setText(s);
		
	}
	private void startgame() {
		if (gamestate == PLAYING) { // Go into reset
			gamestate = RESET;
			mainBoard.resetBoard();
		}
		if (gamestate == RESET)
			debug("Restarting");
		else
			debug("Starting");
		messagearea.setText(" Lets Begin ");
		gamestate = PLAYING;
		firstmoveflag = true;
		mainBoard.enableAll();
		ai = new MainUTTTAI();
		//aiMove();
	}
	public void resetUI(){
		mainBoard.revalidate();
		mainBoard.repaint();
		theWindow.pack();
		theWindow.repaint();
		SwingUtilities.updateComponentTreeUI(theWindow);
	}
	void debug(String s) {
		System.out.println(s);
	}

	void debug(boolean b, String s) {
		if (b)
			debug(s);
	}
	private void setnLnf(String givenLnf){
		try {
			UIManager.setLookAndFeel(givenLnf);
			// Very Important line
			SwingUtilities.updateComponentTreeUI(theWindow);
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InstantiationException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IllegalAccessException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
	}

	

	

public void createPopupMenu() {
    JMenuItem menuItem;

    //Create the popup menu.
    JPopupMenu popup = new JPopupMenu();
    menuItem = new JMenuItem("Color Chooser");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    menuItem = new JMenuItem("Another popup menu item");
    menuItem.addActionListener(this);
    popup.add(menuItem);

    //Add listener to the text area so the popup menu can come up.
    MouseListener popupListener = new PopupListener(popup);
    theWindow.addMouseListener(popupListener);
}
}
class PopupListener extends MouseAdapter {
    JPopupMenu popup;

    PopupListener(JPopupMenu popupMenu) {
        popup = popupMenu;
    }

    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }
}
