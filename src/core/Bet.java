package core;

public class Bet
{
	private User user;
	private BetColor color;
	private int points;
	
	public Bet(User user, BetColor color, int points)
	{
		this.user = user;
		this.color = color;
		this.points = points;
	}
	
	public void process(BetColor winningColor)
	{
		if(color == winningColor)
		{
			//user won the bet, give him the allocated points
			switch(winningColor)
			{
			case RED:
				user.addPoints(points * 2);
				break;
			case BLACK:
				user.addPoints(points * 2);
				break;
			case GREEN:
				user.addPoints(points * 14);
				break;
			case NONE:
				System.out.println("A user should never bet none, this is a bug!");
			}
		}
	}
}
