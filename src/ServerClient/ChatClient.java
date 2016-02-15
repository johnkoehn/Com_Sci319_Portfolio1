package ServerClient;

import java.net.*;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import core.Utility;
import ui.BetGUI;

import java.awt.EventQueue;
import java.io.*;

public class ChatClient implements Runnable
{
	private Socket socket = null;
	private Thread thread = null;
	private DataInputStream console = null;
	private DataOutputStream streamOut = null;
	private ChatClientThread client = null;
	private String username;
	private BetGUI frame;

	public ChatClient(String serverName, int serverPort)
	{
		//first open up a JDialog, asking the user for their name
		username = JOptionPane.showInputDialog(new JFrame(), "Enter name: ");
		
		JDialog dialog = new JDialog(new JFrame(), "Establishing connection...");
		dialog.setVisible(true);
		try
		{
			socket = new Socket(serverName, serverPort);
			dialog.setVisible(false);
			start();
		} catch (UnknownHostException h)
		{
			dialog.setVisible(false);
			JOptionPane.showMessageDialog(new JFrame(), "Unknown Host " + h.getMessage());
		} catch (IOException e)
		{
			dialog.setVisible(false);
			JOptionPane.showMessageDialog(new JFrame(), "IO exception: " + e.getMessage());
		}
	}

	public void run()
	{
		while (thread != null)
		{
			try
			{
				//while(!frame.newMessage()){}
				//we got a new message, notify the server and send the message
				streamOut.writeUTF(frame.getMessage());
				streamOut.flush();
			} catch (IOException ioe)
			{
				System.out.println("Sending error: " + ioe.getMessage());
				stop();
			}
		}
	}

	public void handleChat(String msg)
	{
		frame.sendMessage(msg);
	}
	
	public synchronized void handleBet(String color, String amt)
	{
		frame.recieveBet(Utility.stringToColor(color), Integer.parseInt(amt));
		
	}

	public void start() throws IOException
	{
		frame = new BetGUI(username);
		frame.setVisible(true);
		
		console = new DataInputStream(System.in);
		streamOut = new DataOutputStream(socket.getOutputStream());
		if (thread == null)
		{
			client = new ChatClientThread(this, socket);
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
		try
		{
			if (console != null)
				console.close();
			if (streamOut != null)
				streamOut.close();
			if (socket != null)
				socket.close();
		} catch (IOException ioe)
		{
			System.out.println("Error closing ...");
		}
		client.close();
		client.stop();
	}

	public static void main(String args[])
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{
					ChatClient client = new ChatClient("10.26.46.61", 1222);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
}
