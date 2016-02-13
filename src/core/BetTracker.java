package core;

import java.util.Stack;

public class BetTracker
{
	private Stack<Bet> bets;
	
	public BetTracker()
	{
		bets = new Stack<Bet>();
	}
	
	public void addBet(User user, int points, BetColor coloredBet)
	{
		bets.push(new Bet(user, points, coloredBet));
	}
	
	public void processBets(BetColor winningColor)
	{
		for(int i = 0; i < bets.size(); i++)
		{
			bets.pop().process(winningColor);
		}
	}
}
