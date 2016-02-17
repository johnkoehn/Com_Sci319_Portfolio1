package ServerClient;

import java.net.*;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import core.Utility;
import ui.BetGUI;

import java.awt.EventQueue;
import java.io.*;

public class Client implements Runnable
{
	private Socket socket = null;
	private Thread thread = null;
	private DataOutputStream streamOut = null;
	private ClientThread client = null;
	private String username;
	private BetGUI frame;

	public Client(String serverName, int serverPort)
	{
		//first open up a JDialog, asking the user for their name
		username = JOptionPane.showInputDialog(new JFrame(), "Enter name: ");
		
		JDialog dialog = new JDialog(new JFrame(), "Establishing connection...");
		dialog.setVisible(true);
		try
		{
			socket = new Socket(serverName, serverPort);
			dialog.setVisible(false);
			dialog.dispose();
			start();
		} catch (UnknownHostException h)
		{
			dialog.setVisible(false);
			dialog.dispose();
			JOptionPane.showMessageDialog(new JFrame(), "Unknown Host " + h.getMessage());
			System.exit(1);
		} catch (IOException e)
		{
			dialog.setVisible(false);
			dialog.dispose();
			JOptionPane.showMessageDialog(new JFrame(), "IO exception: " + e.getMessage());
			System.exit(1);
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

	public synchronized void handleChat(String msg)
	{
		frame.recieveMessage(msg);
	}
	
	public synchronized void handleRoll(String value)
	{
		frame.recieveRoll(Integer.parseInt(value));
		
	}
	
	public synchronized void handleStart(String color, String amt)
	{
		frame.recieveStart(Utility.stringToColor(color), Integer.parseInt(amt));
		
	}
	
	public synchronized void handleBet(String color, String amt)
	{

		frame.recieveBet(Utility.stringToColor(color), Integer.parseInt(amt));
		
	}

	public void start() throws IOException
	{
		frame = new BetGUI(username);
		frame.setVisible(true);
		
		streamOut = new DataOutputStream(socket.getOutputStream());
		if (thread == null)
		{
			client = new ClientThread(this, socket);
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
			if (streamOut != null)
				streamOut.close();
			if (socket != null)
				socket.close();
			
			//notify the user and then finish closing
			JOptionPane.showMessageDialog(new JFrame(), "Server connection down! Closing program");
			frame.dispose();
			System.exit(1);
		} catch (IOException ioe)
		{
			System.out.println("Error closing ...");
		}
	}

	public static void main(String args[])
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{
					Client client = new Client("10.26.42.198", 1222);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
}
