package core;

public class Utility
{
	public static String colorToString(BetColor color)
	{
		switch(color)
		{
		case RED:
			return "red";
		case GREEN:
			return "green";
		case BLACK:
			return "black";
		case NONE:
			return "none";
		default:
			return "none";
		}
	}
	
	public static BetColor stringToColor(String data)
	{
		if(data.equals("red"))
		{
			return BetColor.RED;
		}
		else if(data.equals("green"))
		{
			return BetColor.GREEN;
		}
		else if(data.equals("black"))
		{
			return BetColor.BLACK;
		}
		
		return BetColor.NONE;
	}

}
