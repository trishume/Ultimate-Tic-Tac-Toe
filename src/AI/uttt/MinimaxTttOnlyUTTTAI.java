package AI.uttt;

import AI.ttt.minimax.MinimaxTTTAI;
import board.BoardBoard;

public class MinimaxTttOnlyUTTTAI extends BaseUTTAI {
	MinimaxTTTAI minimaxAi;
	@Override
	public int getMove(BoardBoard board, String player) {
		System.out.println("MinimaxTttOnlyUTTTAI called");
		minimaxAi = new MinimaxTTTAI();
		return minimaxAi.getMove(board.getActiveBoard(), player);
		//return 0;
	}
}
