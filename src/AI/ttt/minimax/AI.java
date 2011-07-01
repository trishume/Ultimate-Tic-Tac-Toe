package AI.ttt.minimax;


public class AI extends Thread
{
	MoveNode bestNode;
	static byte PLAYER;
	byte max = Byte.MIN_VALUE;
	int threadsStop = 0;
	//This is more complicated than need be
	//I made the checking system multi threaded it, does not significanly increase speed
	//but it does give room for some random starting pos. 
	public AI(byte board[], byte player)
	{
		//set to check if winning used for AI vs AI
		AI.PLAYER = (byte)(player * -1);
		
		MoveNode top = new MoveNode(board,player);
		MoveNode c;
		int i = 0;
		
		//Start at top level and create all children
		while((c = top.getChild()) != null)
		{
			new RunThread(c).start();
			threadsStop++;
		}

		try
		{
			//when all children are done continue running
			while(threadsStop != 0) {}
		}
		catch(Exception e)
		{
		}
	}
	public byte[] getBoard()
	{
		return bestNode.getBoard();
	}	
	public class RunThread extends Thread
	{
		MoveNode top;
		int depth = 0;
		public RunThread(MoveNode in)
		{
			top = in;
		}
		//start minimax on child
		public void run()
		{
			byte val = minimaxAB(top,true,Byte.MIN_VALUE,Byte.MAX_VALUE);
			if(val >= max)
			{
				max = val;
				bestNode = top;
			}
			threadsStop--;
		}
		//mini max method
		public byte minimaxAB(MoveNode m, boolean mini, byte alpha, byte beta)
		{
			if(m.isLeaf())
				return m.value();
			MoveNode child;
			
			if(mini)
			{
				if(depth == MinimaxTTTAI.MAX_DEPTH)
					return beta;
				depth++;
				while((child = m.getChild()) != null && !(alpha > beta))
				{
					byte val = minimaxAB(child, !mini,alpha,beta);
					depth--;
					if(val < beta)
						beta = val;
					
				}
				return beta;
			}
			else
			{
				if(depth > MinimaxTTTAI.MAX_DEPTH)
					return alpha;
				depth++;
				while((child = m.getChild()) != null && !(alpha > beta))
				{
					byte val = minimaxAB(child, !mini,alpha,beta);
					depth--;
					if(val > alpha)
						alpha = val;
				}
				return alpha;
			}
		}
	}
}