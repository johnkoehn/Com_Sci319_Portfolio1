package ServerClient;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ClientThread extends Thread
{
	private Socket socket;
	private Client client;
	private DataInputStream streamIn;

	public ClientThread(Client client, Socket socket)
	{
		this.client = client;
		this.socket = socket;
		
		open();
		start();
	}

	public void open()
	{
		try
		{
			streamIn = new DataInputStream(socket.getInputStream());
		} catch (IOException ioe)
		{
			System.out.println("Error getting input stream: " + ioe);
			client.stop();
		}
	}

	public void close()
	{
		try
		{
			if (streamIn != null)
				streamIn.close();
		} catch (IOException ioe)
		{
			System.out.println("Error closing input stream: " + ioe);
		}
	}

	public void run()
	{
		while (true)
		{
			try
			{
				String msg = streamIn.readUTF();
				Scanner parser = new Scanner(msg);
				parser.useDelimiter("#");
				
				String sub = parser.next();
				if(sub.equals("Chat"))
				{
					client.handleChat(msg.replaceFirst("Chat#", ""));
				}
				else if(sub.equals("Bet"))
				{
					//equals Bet
					client.handleBet(parser.next(), parser.next());
				}
				else if(sub.equals("Roll"))
				{
					client.handleRoll(parser.next());
				}
				else if(sub.equals("Start"))
				{
					client.handleStart(parser.next(), parser.next());
				}
				
				parser.close();
				
			} catch (IOException ioe)
			{
				System.out.println("Listening error: " + ioe.getMessage());
				client.stop();
				System.exit(1);
			}
		}
	}
}