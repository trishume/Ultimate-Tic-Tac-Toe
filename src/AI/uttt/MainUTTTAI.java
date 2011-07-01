package AI.uttt;

import java.io.Serializable;

import board.BoardBoard;
import board.ButtonBoard;
import AI.ttt.BaseTTTAI;
import AI.ttt.TTTAI;
import AI.ttt.basic.BasicTTTAI;
import board.TttTile;

public class MainUTTTAI extends BaseUTTAI implements Serializable {
	private static final long serialVersionUID = 1L;
	final int MAIN_SCORE_DEPTH = 1;
	final int CHOOSE_BOARD_DEPTH = 1;
	/** cell score multiplication factors */
	final float TTT_SCORE_FACTOR = (float) 1.0;
	final float BOARD_SCORE_FACTOR = (float) 0.3;
	/** board score constants and multiplication factors */
	final float HIGH_SCORE_FACTOR = (float) 1;
	final float BIG_TTT_SCORE_FACTOR = (float) 2.0;
	final float FIXED_CELL_BONUS = (float) 50.0;
	final float FIXED_CELL_BONUS_2 = (float) 2.0;
	final float OCCUPIED_BONUS = (float) 2.0;
	private TTTAI tttAi = new BasicTTTAI();
	public int cellScore(BoardBoard board, String Player,int bigIndex,int smallIndex, int depth, boolean trace){
		int score = 0;
		if(trace){
		System.out.println("Big index: " + bigIndex);
		System.out.println("Small index: " + smallIndex);
		}
		
		int[] scores = tttAi.tilescores(board.getBoard(bigIndex), Player);
		score += scores[smallIndex] * TTT_SCORE_FACTOR;
		if(trace)
			System.out.println("Cell score before board subtraction: " + score + " before factor: " + scores[smallIndex]);
		if(depth != 0){
			score -= (boardScore(board,BaseTTTAI.oppositePlayer(Player),smallIndex, depth) * BOARD_SCORE_FACTOR);
		}
		//System.out.println("Cell Score: " + score);
		return score;
	}
	public int boardScore(BoardBoard board, String Player,int index, int depth){
		int score = 0;
		int boardDepth = depth - 1;
		if(((TttTile)board.getComponent(index)).fixed){
			score = getFixedCellBonus(board,Player);
		} else {
			if(depth == 0){
				boardDepth = 0;
			}
			score += getBoardHighScore(board, Player, boardDepth,index) * HIGH_SCORE_FACTOR;
			//System.out.println("Board High Score: " + score);
			int[] scores = tttAi.tilescores(board, Player);
			score += scores[index] * BIG_TTT_SCORE_FACTOR;
			int occupied = board.getBoard(index).getOccupied(Player);
			if(occupied > 0){
				//System.out.println("Cell occupied: " + occupied);
				score += OCCUPIED_BONUS;
			}
		}
		System.out.println("Board Score: " + score);
		return score;
	}
	public int getBoardHighScore(BoardBoard board, String player, int depth, int index) {
		int bestScore = 0;
		//System.out.println("getboardhighscore depth: " + depth);
		for (int i = 0; i < 9; i++){
			int curScore = cellScore(board,player,index,i,depth,false);
			if(curScore > bestScore){
				bestScore = curScore;
			}
		}
		return bestScore;
	}
	public int getFixedCellBonus(BoardBoard board,String Player){
		int bonus = (int) FIXED_CELL_BONUS_2;
		if(getNearWins(board,Player) > 0){
			bonus = (int) FIXED_CELL_BONUS;
		}
		return bonus;
	}
	public int getNearWins(BoardBoard board,String Player){
		int numNears = 0;
		for (int i = 0; i < 9; i++){
			TttTile curTile = (TttTile) board.getComponent(i);
			if(((BasicTTTAI)tttAi).getNearWin(curTile.board,Player) && curTile.fixed == false){
				numNears++;
			}
		}
		System.out.println("Near Win count: " + numNears);
		return numNears;
	}
	public int chooseBoard(BoardBoard board, String player){
		System.out.println("Choosing board");
		int bestTile = 4;
		int bestScore = -9999;
		for (int i = 0; i < 9; i++){
			int curScore = boardScore(board,player,i,CHOOSE_BOARD_DEPTH);
			if(curScore > bestScore && !((TttTile)board.getComponent(i)).fixed){
				bestScore = curScore;
				bestTile = i;
			}
		}
		System.out.println("Choose board: " + bestTile);
		return bestTile;
	}
	@Override
	public int getMove(BoardBoard board, String player) {
		int bestTile = 4;
		int bestScore = -9999;
		if(((TttTile)board.getComponent(board.activeBoard)).fixed){
			board.activeBoard = chooseBoard(board,player);
		}
		for (int i = 0; i < 9; i++){
			int curScore = cellScore(board,player,board.activeBoard,i,MAIN_SCORE_DEPTH,true);
			System.out.println("Cell Score calculated in getmove: "+curScore+" at loop: " + i);
			if(curScore > bestScore && board.getActiveBoard().buttons[i].getText().equals(" ")){
				System.out.println("Cell Score better than previous");
				bestScore = curScore;
				bestTile = i;
			}
		}
		System.out.println("Best utttai Score: " + bestScore + " for tile: " + bestTile);
		return bestTile;
	}
}
