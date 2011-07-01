package AI.ttt.minimax;

import board.ButtonBoard;
import AI.ttt.BaseTTTAI;
import AI.ttt.minimax.AI;

public class MinimaxTTTAI extends BaseTTTAI {
	public static final byte PLAYER_ONE = 1;
	public static final byte PLAYER_TWO = -1;
	public static final int MAX_DEPTH = 3;
	public static final byte TIE = 100;
	byte[] b = new byte[9];
	@Override
	public int getMove(ButtonBoard board, String Player) {
		System.out.println("minimax ttt ai called");
		b = this.getByteBoard(board);
		System.out.println("minimax: " + b.toString());
		byte player = PLAYER_TWO;
		if(Player.equals("X")){
			player = PLAYER_ONE;
		} else if(Player.equals("O")){
			player = PLAYER_TWO;
		} else {
			System.out.println("Not valid player for minimax AI");
		}
		System.out.println("Starting AI Timer");
		long t = System.currentTimeMillis();
		AI ai = new AI((byte[])b.clone(),(byte)(player * -1));
		System.out.println("Total AI Time: " + (System.currentTimeMillis() - t));
		byte temp[] = ai.getBoard();
		int move = 0;
		for(int i = 0; i < temp.length;i++)
		{
			if(temp[i] != b[i])
			{
				move = i;
				break;
			}
		}
		return move;
	}
	public byte[] getByteBoard(ButtonBoard board){
		byte[] bBoard = new byte[9];
		for (int i = 0; i < board.buttons.length; i++){
			if(board.buttons[i].getText().equals("X")){
				bBoard[i] = PLAYER_ONE;
			} else if(board.buttons[i].getText().equals("O")){
				bBoard[i] = PLAYER_TWO;
			}
			else if(board.buttons[i].getText().equals(" ")){
				bBoard[i] = 0;
			}
		}
		return bBoard;
	}
	
	public static byte checkWin(byte b[])
	{
		byte top[] = new byte[3];
		byte mid[] = new byte[3];
		byte bot[] = new byte[3];
		int numLeft = 0;
		for(int i = 0; i < 9;i++)
		{
			if(i < 3)
			top[i] = b[i];
			else if(i < 6)
			mid[(i)%3] = b[i];
			else
			bot[(i)%3] = b[i];
			if(b[i] == 0)
				numLeft++;
		}
		if(top[0] == top[1] && top[1] == top[2] && top[0] != 0)
			return top[0];
		if(mid[0] == mid[1] && mid[1] == mid[2] && mid[0] != 0)
			return mid[0];
		if(bot[0] == bot[1] && bot[1] == bot[2] && bot[0] != 0)
			return bot[0];
			
		if(top[0] == mid[1] && mid[1] == bot[2] && bot[2] != 0)
			return bot[2];
		if(top[2] == mid[1] && mid[1] == bot[0] && bot[0] != 0)
			return bot[0];
		
		if(top[0] == mid[0] && mid[0] == bot[0] && mid[0] != 0)
			return mid[0];
		if(top[1] == mid[1] && mid[1] == bot[1] && mid[1] != 0)
			return mid[1];
		if(top[2] == mid[2] && mid[2] == bot[2] && mid[2] != 0)
			return mid[2];
		if(numLeft == 0)
			return MinimaxTTTAI.TIE;
		return 0;
	}
}
