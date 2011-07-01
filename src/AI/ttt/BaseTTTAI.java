package AI.ttt;

import board.BoardBoard;
import board.ButtonBoard;

public class BaseTTTAI implements TTTAI {
	 public static final int[][] winningCombo = {
		 {0, 1, 2},
		 {3, 4, 5},
		 {6, 7, 8},
		 {0, 3, 6},
		 {1, 4, 7},
		 {2, 5, 8},
		 {0, 4, 8},
		 {2, 4, 6}
	 };
	public static String checkWin(ButtonBoard board){
		return checkWin(board.getArrayBoard());
	}
	public static String checkWin(BoardBoard board){
		return checkWin(board.getArrayBoard());
	}
	public static String checkWin(String[] board){
		String winner = " ";
		for (int comboNum = 0; comboNum < 8; comboNum++){
			 int firstVal = winningCombo[comboNum][0];
			 int secondVal = winningCombo[comboNum][1];
			 int thirdVal = winningCombo[comboNum][2];
			    if ((board[firstVal].equals(board[secondVal])) &&
			    	(board[secondVal].equals(board[thirdVal])) &&
			    	(board[firstVal].equals(" ") == false)){
			    	winner = board[firstVal];
			    	System.out.println("checkwin won with combo: " + comboNum + " by: " + board[firstVal]);
			    } // end if	  
		 } // end for
		return winner;
	}
	@Override
	public int getMove(ButtonBoard board, String Player) {
		/**
		 * base class, meythod must be implemented in subclasses
		 */
		return 0;
	}

	@Override
	public int[] tilescores(ButtonBoard board, String Player) {
		/**
		 * base class, meythod must be implemented in subclasses
		 */
		return null;
	}
	public static String oppositePlayer(String Player){
		String oppositePlayer = "X";
		if(Player.equals("X")){
			oppositePlayer = "O";
		} else if(Player.equals("O")){
			oppositePlayer = "X";
		} else {
			System.out.println("Not valid player for minimax AI");
		}
		return oppositePlayer;
	}

	@Override
	public int[] tilescores(BoardBoard board, String Player) {
		// TODO Auto-generated method stub
		return null;
	}

}
