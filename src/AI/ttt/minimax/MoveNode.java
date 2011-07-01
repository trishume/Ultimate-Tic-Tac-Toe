package AI.ttt.minimax;


import java.util.*;
public class MoveNode
{
	byte board[];
	byte score;
	boolean leaf = false;
	int next = -2;
	MoveNode child[] = new MoveNode[9];
	int numChild = 0;
	int player;
	int winner;
	
	public MoveNode(byte board[],int player)
	{
		//change player that moves with each node so the minimax can guess each players move 
		this.board = board;
		if(player == MinimaxTTTAI.PLAYER_ONE)
			this.player = MinimaxTTTAI.PLAYER_TWO;
		if(player == MinimaxTTTAI.PLAYER_TWO)
			this.player = MinimaxTTTAI.PLAYER_ONE;
		
		//check if winner
		winner = MinimaxTTTAI.checkWin(board);
		
		//if winner then no children available
		if(winner != 0)
			leaf = true;
	}
	public byte[] getBoard()
	{
		return board;
	}
	
	public MoveNode getChild()
	{
		//if children havnt been generated yet then generate children
		if(next == -2 && !leaf)
			generateMoves();
		
		//get next child and increase location in array
		next++;
		
		//if end of children return null to end loop
		if(numChild == 0 || numChild == next || leaf)
			return null;
		return child[next];
	}
	public void generateMoves()
	{
		
		
		//generate children / possable next moves
		// I tried to make this so that you could move it oto other games like checkers without 
		//changing much of the core AI just this and the values along with the board stuff
		byte b[] = (byte[])board;
		next = -1;
		for(int i = 0; i < board.length;i++)
		{
			if(b[i] == 0)
			{
				b[i] = (byte)player;
				child[numChild] = new MoveNode((byte[])b.clone(),player);
				b[i] = 0;
				numChild++;
			}
		}
	}
	public byte value()
	{
		//if you win then you get max points else you get none or min points
		if(winner == AI.PLAYER)
			return Byte.MAX_VALUE;
		else if(winner == 0 || winner == MinimaxTTTAI.TIE)
			return 0;
		else
			return Byte.MIN_VALUE;
	}
	public boolean isLeaf()
	{
		return leaf;
	}
	public void setLeaf(boolean leaf)
	{
		this.leaf = leaf;
	}
	public String toString()
	{
		return board[0] + "" + board[1] + "" + board[2] + "" + board[3] + "" + board[4] + "" + board[5] + "" + board[6] + "" + board[7] + "" + board[8];
	}
}