package ServerClient;

import java.net.*;
import java.io.*;

public class ChatClientThread extends Thread
{
	private Socket socket;
	private ChatClient client;
	private DataInputStream streamIn;

	public ChatClientThread(ChatClient client, Socket socket)
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
				client.handle(streamIn.readUTF());
				System.out.println("poop");
			} catch (IOException ioe)
			{
				System.out.println("Listening error: " + ioe.getMessage());
				client.stop();
			}
		}
	}
}