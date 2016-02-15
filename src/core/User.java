package core;

public class User
{
	private int points;
	private String username;
	
	public User(int points, String username)
	{
		this.points = points;
		this.username = username;
		
	}
	
	public void addPoints(int points)
	{
		this.points += points;
	}

	public boolean subtractPoints(int points)
	{
		if((this.points - points) < 0)
		{
			return false;
		}
		
		this.points -= points;
		return true;
	}
	
	public String printUserInfo()
	{
		return username + "," + Integer.toString(points);
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public int getPoints()
	{
		return points;
	}
}
