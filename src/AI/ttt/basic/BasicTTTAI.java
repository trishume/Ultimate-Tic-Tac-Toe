package AI.ttt.basic;

import java.io.Serializable;

import AI.ttt.BaseTTTAI;
import board.BoardBoard;
import board.ButtonBoard;

public class BasicTTTAI extends BaseTTTAI implements Serializable {
 final int[] baseRank = {3, 2, 3, 2, 4, 2, 3, 2, 3};
 final int WIN_POINTS = 20;
 final int LINE_POINTS = 1;
 final int RANDOM_POINTS = 1;
 String[] aBoard;
 //ButtonBoard bBoard;
 @Override
 public int[] tilescores(ButtonBoard board, String Player) {
	 int[][] returnResult = tilescores(board.getArrayBoard(), Player, true);
	 return returnResult[0];
 }
 public int[] tilescores(BoardBoard board, String Player) {
	 int[][] returnResult = tilescores(board.getArrayBoard(), Player, false);
	 return returnResult[0];
 }
 public boolean getNearWin(ButtonBoard board, String Player){
	 int[][] returnResult = tilescores(board.getArrayBoard(), Player, false);
	 boolean result = false;
	 if(returnResult[1][0] == 1) result = true;
	 return result;
 }
 public int[][] tilescores(String[] board, String Player,boolean baseRankAdd) {
	 //bBoard = board;
	 aBoard = board;
	 //printBoard(aBoard);
	 int[] rank = new int[9];
	 if(baseRankAdd){
		 rank = baseRank;
	 }
	 boolean winCombo = false;
	 //begin ranking code
	 for (int comboNum = 0; comboNum < 8; comboNum++){
		 int firstVal = winningCombo[comboNum][0];
		 int secondVal = winningCombo[comboNum][1];
		 int thirdVal = winningCombo[comboNum][2];
		    if ((aBoard[firstVal].equals(aBoard[secondVal]))){ 
		    	if(aBoard[firstVal].equals(" ") == false){
			    	//System.out.println("ttt ai 1 in " + comboNum);
			    	if(aBoard[firstVal].equals(Player)){
			    		rank[thirdVal] += WIN_POINTS;
			    		winCombo = true;
			    	} else {
			    		rank[thirdVal] += RANDOM_POINTS;
			    	}
		    	}
		    } // end if

		    if(aBoard[firstVal].equals(aBoard[thirdVal])){ 
		    	if(aBoard[firstVal].equals(" ") == false){
			    	//System.out.println("ttt ai 2 in " + comboNum + " debugging crap- aboard[firstval]:"+aBoard[firstVal] + " aboard[thirdval]:"+aBoard[thirdVal]);
			    	if(aBoard[firstVal].equals(Player)){
			    		rank[secondVal] += WIN_POINTS;
			    		winCombo = true;
			    	} else {
			    		rank[secondVal] += RANDOM_POINTS;
			    	}
		    	}
		    } // end if

		    if(aBoard[secondVal].equals(aBoard[thirdVal])){
		        if(aBoard[secondVal].equals(" ") == false){
			    	//System.out.println("ttt ai 3 in " + comboNum);
			    	if(aBoard[secondVal].equals(Player)){
			    		rank[firstVal] += WIN_POINTS;
			    		winCombo = true;
			    	} else {
			    		rank[firstVal] += RANDOM_POINTS;
			    	}
		        }
		    } // end if
		    /** single cell checking */
		    if(!winCombo){
			    if ((aBoard[firstVal].equals(Player))){ 
				    	rank[secondVal] += LINE_POINTS;
				    	rank[thirdVal] += LINE_POINTS;
			    } // end if
	
			    if(aBoard[secondVal].equals(Player)){ 
				    	rank[firstVal] += LINE_POINTS;
				    	rank[thirdVal] += LINE_POINTS;
			    } // end if
	
			    if(aBoard[thirdVal].equals(Player)){
				    	rank[firstVal] += LINE_POINTS;
				    	rank[secondVal] += LINE_POINTS;
			    } // end if
		    }
	 } // end for

		  	

	 //lower ranking of any cell currently containing anything but blank
	 for (int cellNum = 0; cellNum < 9; cellNum++){
		 if (aBoard[cellNum].equals(" ") == false){
			 rank[cellNum] = 0;
		 } // end if
	 }
	 if(!baseRankAdd){
		 //printRank(rank);
	 }
	 //printRank(rank);
	 int winInt = 0;
	 if(winCombo) winInt = 1;
	 int[][] returnVar = {rank,{winInt}};
	 return returnVar;
 }
 @Override
 public int getMove(ButtonBoard board, String Player) {
	 int[] rank = this.tilescores(board, Player);
	 int highestRank = 0;
	 int bestCell = 4;
	 for (int cellNum = 0; cellNum < 9; cellNum++){
		 if (rank[cellNum] > highestRank){
			 bestCell = cellNum;
			 highestRank = rank[cellNum];
		 } // end if
	 }
	 return bestCell;
 }
 public void printRank(int[] rank){
	 //outputs the current rank structure for debugging purposes
	 System.out.println(rank[0] + ", " + rank[1] + ", " + rank[2]);
	 System.out.println(rank[3] + ", " + rank[4] + ", " + rank[5]);
	 System.out.println(rank[6] + ", " + rank[7] + ", " + rank[8]);
	 System.out.println();
 } // end traceRank
 public void printBoard(String[] rank){
	 //outputs the current rank structure for debugging purposes
	 System.out.println(rank[0] + ", " + rank[1] + ", " + rank[2]);
	 System.out.println(rank[3] + ", " + rank[4] + ", " + rank[5]);
	 System.out.println(rank[6] + ", " + rank[7] + ", " + rank[8]);
	 System.out.println();
 } // end traceRank
}