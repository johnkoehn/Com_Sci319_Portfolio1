package ServerClient;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ChatClientThread extends Thread
{
	private Socket socket = null;
	private ChatClient client = null;
	private DataInputStream streamIn = null;

	public ChatClientThread(ChatClient _client, Socket _socket)
	{
		client = _client;
		socket = _socket;
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
				Boolean flag = false;
				String color = null;
				String amt = null;
				Scanner parser = new Scanner(msg);
				parser.useDelimiter("#");
				while(parser.hasNext())
				{
					String sub = parser.next();
					if(sub.equals("Chat"))
					{
						msg = parser.next();
						flag = true;
					}
					else
					{
						//equals Bet
						color = parser.next();
						amt = parser.next();
					}
				}
				
				if(flag)
				{
					client.handleChat(msg);
				}
				else
				{
					client.handleBet(color, amt);
				}
			} catch (IOException ioe)
			{
				System.out.println("Listening error: " + ioe.getMessage());
				client.stop();
			}
		}
	}
}