package ServerClient;

import java.net.*;
import java.util.ArrayList;

import core.BetColor;

import java.io.*;

public class Server implements Runnable
{
	private ArrayList<ServerThread> clients = new ArrayList<ServerThread>();
	private ServerSocket server = null;
	private Thread thread = null;

	public Server(int port)
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