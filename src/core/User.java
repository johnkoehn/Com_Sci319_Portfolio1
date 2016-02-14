package core;

public class User
{
	private int points;
	private String username;
	private String password;
	
	public User(int points, String username, String password)
	{
		this.points = points;
		this.username = username;
		this.password = password;
		
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
		return username + "," + password + "," + Integer.toString(points);
	}
	
	public String getPassowrd()
	{
		return password;
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
