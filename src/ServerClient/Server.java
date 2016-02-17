package ServerClient;

import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import core.BetColor;
import core.Utility;

import java.io.*;

public class Server implements Runnable
{
	private ArrayList<ServerThread> clients = new ArrayList<ServerThread>();
	private ServerSocket serverSocket = null;
	private Thread mainThread = null;
	
	//for doing bet timing
	private Thread timerThread = null;
	private BetColor currentColor = BetColor.GREEN;
	private boolean canBet = true;
	private int time = 10;
	private Random random = new Random();
	private int cyclesSinceGreen = 0;
	
	//server needs to keep track of points being bet so it can tell new clients
	private int redPoints = 0;
	private int greenPoints = 0;
	private int blackPoints = 0;

	public Server(int port)
	{
		try
		{
			System.out.println("Binding to port " + port + ", please wait  ...");
			serverSocket = new ServerSocket(port);
			System.out.println("Server started: " + serverSocket);
			start();
		} catch (IOException ioe)
		{
			System.out.println("Can not bind to port " + port + ": " + ioe.getMessage());
		}
	}

	public void run()
	{
		while (mainThread != null)
		{
			try
			{
				System.out.println("Waiting for a client ...");
				addThread(serverSocket.accept());
			} catch (IOException ioe)
			{
				System.out.println("Server accept error: " + ioe);
				stop();
			}
		}
	}

	public void start()
	{
		if (mainThread == null)
		{
			mainThread = new Thread(this);
			mainThread.start();
		}
		if(timerThread == null)
		{
			timerThread = new Thread(new Runnable()
			{
				
				@Override
				public void run()
				{
					while(true)
					{
						canBet = true;
						time = 15;
						try
						{
							while(time > 0)
							{
								Thread.sleep(1000);
								time -= 1;
							}
							
							//no new servers can join till true
							canBet = false;
							
							//select a new color, send a message, then wait for a bit, then send a message to clients telling them to start the bets again
							int value = random.nextInt(44 - 30 + 1) + 30;
							handle("Roll#"+value);
							shiftColor(value);
							
							//set bet values to zero
							redPoints = 0;
							greenPoints = 0;
							blackPoints = 0;
							
							//calculate amount of time to wait
							int waitTime = 1200;
							for(int i = 0; i < value; i++)
							{
								waitTime += 100 + i*10;
							}
							Thread.sleep(waitTime);
							handle("Start#" + Utility.colorToString(currentColor) + "#" + cyclesSinceGreen);
							
						} catch (InterruptedException e)
						{
							System.out.println("Thread sleep issue: " + e.getMessage());
						}
						
					}
					
				}
			});
			timerThread.start();
		}
	}

	private void shiftColor(int value)
	{
		//start displaying the colors
		for(int i=0; i < value; i++)
		{
			if(currentColor == BetColor.RED && cyclesSinceGreen >= 14)
			{
				//next color is green
				currentColor = BetColor.GREEN;
				cyclesSinceGreen = 0;
			}
			else if(currentColor == BetColor.RED || currentColor == BetColor.GREEN)
			{
				//next color is Black
				currentColor = BetColor.BLACK;
				cyclesSinceGreen += 1;
			}
			else
			{
				//next color is red
				currentColor = BetColor.RED;
				cyclesSinceGreen += 1;
			}
		}
	}
	
	public void stop()
	{
		if (mainThread != null)
		{
			mainThread.stop();
			mainThread = null;
		}
		
		if(timerThread != null)
		{
			timerThread.stop();
			timerThread = null;
		}
	}

	private int findClient(int ID)
	{
		int pos = 0;
		for (ServerThread thread : clients)
		{
			if (thread.getID() == ID)
				return pos;
			pos += 1;
		}

		return -1;
	}

	public synchronized void handle(String input)
	{

		for (ServerThread thread : clients)
			thread.sendMessage(input);
		
		//new input, check if it was a bet and add it to the points
		Scanner parser = new Scanner(input);
		parser.useDelimiter("#");
		String sub = parser.next();
		if(sub.equals("Bet"))
		{
			BetColor color = Utility.stringToColor(parser.next());
			int amount = Integer.parseInt(parser.next());
			handleBet(color, amount);
		}
		parser.close();
	}
	
	private void handleBet(BetColor color, int amount)
	{
		switch(color)
		{
		case RED:
			redPoints += amount;
			break;
		case GREEN:
			greenPoints += amount;
			break;
		case BLACK:
			blackPoints += amount;
			break;
		}
	}

	public synchronized void remove(int ID)
	{
		int pos = findClient(ID);
		if (pos >= 0)
		{
			//get the serverthread, remove it from the array and then terminate it
			ServerThread toTerminate = clients.get(pos);
			System.out.println("Removing client thread " + ID);
			clients.remove(pos);
			
			try
			{
				toTerminate.close();
			} catch (IOException ioe)
			{
				System.out.println("Error closing thread: " + ioe);
			}
			toTerminate.stop();
		}
	}

	private void addThread(Socket socket)
	{
		System.out.println("New Client at " + socket);
		ServerThread newClient = new ServerThread(this, socket);
		clients.add(newClient);
		
		try
		{
			newClient.open();
			newClient.start();
		} catch (IOException ioe)
		{
			System.out.println("Error opening thread: " + ioe);
		}
	}

	public static void main(String args[])
	{
		Server server = null;
		server = new Server(1222);
	}
}