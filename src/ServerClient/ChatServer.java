package ServerClient;

import java.net.*;

import core.BetColor;

import java.io.*;

public class ChatServer implements Runnable
{
	private ChatServerThread chatClients[] = new ChatServerThread[10];
	private ServerSocket server = null;
	private Thread thread = null;
	private int clientCount = 0;

	public ChatServer(int port)
	{
		try
		{
			System.out.println("Binding to port " + port + ", please wait  ...");
			server = new ServerSocket(port);
			System.out.println("Server started: " + server);
			start();
		} catch (IOException ioe)
		{
			System.out.println("Can not bind to port " + port + ": " + ioe.getMessage());
		}
	}

	public void run()
	{
		while (thread != null)
		{
			try
			{
				System.out.println("Waiting for a client ...");
				addThread(server.accept());
			} catch (IOException ioe)
			{
				System.out.println("Server accept error: " + ioe);
				stop();
			}
		}
	}

	public void start()
	{
		if (thread == null)
		{
			thread = new Thread(this);
			thread.start();
		}
	}

	public void stop()
	{
		if (thread != null)
		{
			thread.stop();
			thread = null;
		}
	}

	private int findClient(int ID)
	{
		for (int i = 0; i < clientCount; i++)
			if (chatClients[i].getID() == ID)
				return i;
		return -1;
	}

	public synchronized void handle(String input)
	{

		for (int i = 0; i < clientCount; i++)
			chatClients[i].sendMessage(input);
	}

	public synchronized void remove(int ID)
	{
		int pos = findClient(ID);
		if (pos >= 0)
		{
			ChatServerThread toTerminate = chatClients[pos];
			System.out.println("Removing client thread " + ID + " at " + pos);
			if (pos < clientCount - 1)
				for (int i = pos + 1; i < clientCount; i++)
					chatClients[i - 1] = chatClients[i];
			clientCount--;
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
		if (clientCount < chatClients.length)
		{
			System.out.println("Client accepted: " + socket);
			chatClients[clientCount] = new ChatServerThread(this, socket);
			try
			{
				chatClients[clientCount].open();
				chatClients[clientCount].start();
				clientCount++;
			} catch (IOException ioe)
			{
				System.out.println("Error opening thread: " + ioe);
			}
		} else
			System.out.println("Client refused: maximum " + chatClients.length + " reached.");
	}

	public static void main(String args[])
	{
		ChatServer server = null;
		server = new ChatServer(1222);
	}
}