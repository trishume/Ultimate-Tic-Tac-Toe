package AI.uttt;

import AI.ttt.TTTAI;
import AI.ttt.basic.BasicTTTAI;
import board.BoardBoard;

public class BasicTttOnlyUTTTAI extends BaseUTTAI {
	TTTAI basicAi;
	@Override
	public int getMove(BoardBoard board, String player) {
		System.out.println("BasicTttOnlyUTTTAI called");
		basicAi = new BasicTTTAI();
		return basicAi.getMove(board.getActiveBoard(), player);
		//return 0;
	}
}
