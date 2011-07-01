package AI.ttt;

import board.BoardBoard;
import board.ButtonBoard;

public interface TTTAI {
	int getMove(ButtonBoard board, String Player);
	int[] tilescores(ButtonBoard board, String Player);
	int[] tilescores(BoardBoard board, String Player);
}
