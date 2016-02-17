package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import core.BetColor;
import core.BetTracker;
import core.User;
import core.Utility;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.util.Random;

import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Color;
import java.awt.Dialog;

import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class BetGUI extends JFrame {

	private JPanel contentPane;
	private JTextArea chatArea;
	private JTextField chatField;
	private BetTracker betTracker;
	
	private BetColor betColor = BetColor.NONE;
	private BetColor gambleColor = BetColor.GREEN;
	private ActionListener colorButtonsListener;
	
	private JButton btnRed;
	private JButton btnGreen;
	private JButton btnBlack;
	
	private JButton btnBet;
	private JFormattedTextField betField;
	
	private JLabel lblUsernamePoints;
	private JLabel lblRedPoints;
	private JLabel lblGreenPoints;
	private JLabel lblBlackPoints;
	private int redPoints;  
	private int greenPoints;
	private int blackPoints;
	
	private JProgressBar progressBar;
	private JLabel lblPoints;
	private JLabel lblTime;
	
	private boolean betting = false;
	
	private int cyclesSinceGreen = 0;
	private JPanel panel;
	private Color red = new Color(165, 42, 42);
	
	//variables for client/server data
	private volatile boolean newOutput = false;
	private String output;
	private volatile boolean recievedRoll = false;
	private volatile boolean recievedStart = false;
	private int roll = 0;
	private volatile boolean firstStart = true;
	private User user;
	
	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{
					BetGUI frame = new BetGUI("Anus Potato");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	
	/**
	 * Subclass for progress bar process
	 *
	 */
    class Task extends SwingWorker<Void, Void> 
    {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() 
        {
        	while(true)
        	{
        		//wait for new round
        		while(!recievedStart) 
        		{			
        			try
					{
						Thread.sleep(5);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			
        		}
        		
        		//new round start the timer
        		int time = 15;
                double progress = 0;
                progressBar.setValue(100);
                while (progress < 150) 
                {
                    //Sleep for up to one second.
                    try 
                    {
                        Thread.sleep(100);
                    } catch (InterruptedException ignore) {}
                   
                    progress += 1;
                    progressBar.setValue((int)(100 - progress*.666));
                    lblTime.setText("" + (int)(time - progress*.1));
                    
                    EventQueue.invokeLater(new Runnable()
					{
						
						@Override
						public void run()
						{
							revalidate();
							
						}
					});
                    
                }
                
                //time is up to place bets
                betting = false;
                recievedStart = false;
                
                //wait for server to send roll value
                while(!recievedRoll)
                {
                }
                gamble();
        	}
        }

    	private void gamble()
    	{
    		
    		//start displaying the colors
    		for(int i=0; i < roll; i++)
    		{
    			if(gambleColor == BetColor.RED && cyclesSinceGreen >= 14)
    			{
    				//next color is green
    				gambleColor = BetColor.GREEN;
    				panel.setBackground(Color.GREEN);
    				cyclesSinceGreen = 0;
    			}
    			else if(gambleColor == BetColor.RED || gambleColor == BetColor.GREEN)
    			{
    				//next color is Black
    				gambleColor = BetColor.BLACK;
    				panel.setBackground(Color.BLACK);
    				cyclesSinceGreen += 1;
    			}
    			else
    			{
    				//next color is red
    				gambleColor = BetColor.RED;
    				panel.setBackground(red);
    				cyclesSinceGreen += 1;
    			}
    			
    			EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						revalidate();
					}
				});
    			
    			
                try 
                {
                    Thread.sleep(100 + i*10);
                } catch (InterruptedException ignore) {}
    		}
    		
    		//final color has been chosen, process the bets
    		processBets();
    		recievedRoll = false;
    	}
        
    	private void processBets()
    	{
    		betTracker.processBets(gambleColor);
    		
    		//clear labels
    		blackPoints = 0;
    		greenPoints = 0;
    		redPoints = 0;
    		lblGreenPoints.setText("0");
    		lblRedPoints.setText("0");
    		lblBlackPoints.setText("0");
    		
    		//update user points 
    		updateUserLabel();
    		
    		EventQueue.invokeLater(new Runnable()
			{
				
				@Override
				public void run()
				{
					revalidate();
					
				}
			});
    		
    		
    	}
    	
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() 
        {
        }
    }
	
	/**
	 * Create the frame.
	 */
	public BetGUI(String username) 
	{
		user = new User(10000, username);
		betTracker = new BetTracker();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//chatArea is where user chat takes place
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setBounds(-2, -1, 206, 281);
		chatArea.setLineWrap(true);
		contentPane.add(chatArea);
		
		//add chat area to a scrollbar
		JScrollPane chatScrollBar = new JScrollPane(chatArea);
		chatScrollBar.setSize(206, 281);
		chatScrollBar.setLocation(360, 11);
		chatScrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(chatScrollBar);
		
		//chatArea.addKeyListener(l);
		
		//Chat Box, when the user hits enter chat is appened to chat area
		chatField = new JTextField();
		chatField.setBounds(413, 303, 153, 20);
		contentPane.add(chatField);
		chatField.setColumns(10);
		
		chatField.addKeyListener(new KeyListener() 
		{
			
			@Override
			public void keyTyped(KeyEvent e) 
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) 
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					//get the entered text and append it to the text area
					String message = username + ": " + chatField.getText() + "\n";
					//chatArea.append(message);
					
					//change this variable to let the server know a new message is ready
					output = "Chat#" + message;
					newOutput = true;
					
					chatField.setText("");
					revalidate();
					
				}
					
			}
		});
		
		JLabel lblChat = new JLabel("Chat:");
		lblChat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblChat.setBounds(360, 303, 46, 20);
		contentPane.add(lblChat);
		
		lblUsernamePoints = new JLabel(user.getUsername() + ": " + user.getPoints());
		lblUsernamePoints.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsernamePoints.setBounds(10, 11, 134, 20);
		contentPane.add(lblUsernamePoints);
		
		//set up a formatter for integers only
	    NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    //value committed on each keystroke instead of loss on focus
	    formatter.setCommitsOnValidEdit(true);
		
		//allow the bet field to only accept integers
		betField = new JFormattedTextField(formatter);		
		betField.setBounds(68, 303, 106, 20);
		contentPane.add(betField);
		
		btnBet = new JButton("Bet");
		btnBet.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBet.setForeground(Color.GRAY);
		btnBet.setBounds(184, 302, 89, 23);
		contentPane.add(btnBet);
		
		lblPoints = new JLabel("Points:");
		lblPoints.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPoints.setBounds(10, 302, 57, 18);
		contentPane.add(lblPoints);
		
		btnRed = new JButton("1:2");
		btnRed.setBackground(red);
		btnRed.setForeground(Color.GRAY);
		btnRed.setBounds(10, 69, 89, 23);
		contentPane.add(btnRed);
		
		btnGreen = new JButton("1:14");
		btnGreen.setBounds(109, 69, 89, 23);
		btnGreen.setBackground(Color.GREEN);
		btnGreen.setForeground(Color.GRAY);
		contentPane.add(btnGreen);
		
		btnBlack = new JButton("1:2");
		btnBlack.setBackground(Color.BLACK);
		btnBlack.setForeground(Color.GRAY);
		btnBlack.setBounds(208, 69, 89, 23);
		contentPane.add(btnBlack);
		
		JLabel lblClickAColor = new JLabel("Click a Color To Bet On");
		lblClickAColor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblClickAColor.setBounds(10, 42, 175, 20);
		contentPane.add(lblClickAColor);
		
		lblRedPoints = new JLabel("" + redPoints);
		lblRedPoints.setBounds(10, 103, 89, 20);
		contentPane.add(lblRedPoints);
		
		lblGreenPoints = new JLabel("" + greenPoints);
		lblGreenPoints.setBounds(109, 103, 89, 20);
		contentPane.add(lblGreenPoints);
		
		lblBlackPoints = new JLabel("" + blackPoints);
		lblBlackPoints.setBounds(208, 103, 89, 18);
		contentPane.add(lblBlackPoints);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(10, 264, 164, 14);
		progressBar.setValue(100);
		contentPane.add(progressBar);
		
		lblTime = new JLabel("15");
		lblTime.setBounds(184, 264, 70, 18);
		contentPane.add(lblTime);
		
		panel = new JPanel();
		panel.setBackground(Color.GREEN);
		panel.setBounds(10, 147, 89, 81);
		contentPane.add(panel);
		
		//create colorButtons Action Listener and add them
		initColorButtonListener();
		addBetActionListener();
		
		Task task = new Task();
		task.execute();
		
		//Initialize a thread to tell the user they are waiting for client to sync with the server
		Thread waiting = new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				JDialog messageDialog = new JDialog(BetGUI.this, "Waiting to Sync");
				messageDialog.setTitle("Waiting to Sync");
				messageDialog.setSize(300, 150);
				messageDialog.setLocationRelativeTo(BetGUI.this);
				messageDialog.add(new JLabel("Waiting for next round"));
				//messageDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
				messageDialog.setVisible(true);
				
				while(firstStart)
				{
					try
					{
						Thread.sleep(1);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				messageDialog.setVisible(false);
				messageDialog.dispose();				
			}
		});
		waiting.start();
	}
	
	private void initColorButtonListener()
	{
		colorButtonsListener = new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JButton object = (JButton) e.getSource();
				
				if(object == btnRed)
				{
					btnBet.setBackground(red);
					betColor = BetColor.RED;
				}
				else if(object == btnGreen)
				{
					btnBet.setBackground(Color.GREEN);
					betColor = BetColor.GREEN;
				}
				else if(object == btnBlack)
				{
					btnBet.setBackground(Color.BLACK);
					betColor = BetColor.BLACK;
				}
				else
				{
					System.out.println("Unexplained Event for colorButtonListener, debug please!");
				}
				revalidate();
			}
		};
		
		btnRed.addActionListener(colorButtonsListener);
		btnGreen.addActionListener(colorButtonsListener);
		btnBlack.addActionListener(colorButtonsListener);
	}
	
	private void addBetActionListener()
	{
		btnBet.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(betting == false)
				{
					JOptionPane.showMessageDialog(new JFrame(), "Can't Enter a bet until next round!");
					return;
				}
				
				//check that the user entered a value
				int points = 0;
				try
				{
					points = Integer.parseInt(betField.getText().replace(",", ""));
				}
				catch (NumberFormatException x)
				{
					JOptionPane.showMessageDialog(new JFrame(), "Enter a number to bet!");
					return;
				}
				//check that a color to bet on is selected
				if(betColor == BetColor.NONE)
				{
					JOptionPane.showMessageDialog(new JFrame(), "Please select a color to bet on!!!");
					return;
				}
				
				//make sure the user has enough point to place the bet
				if((user.getPoints() - points) < 0)
				{
					JOptionPane.showMessageDialog(new JFrame(), "Not enough points available to bet, you only have " + user.getPoints() + " points");
					return;
				}
				
				//bet can be placed, place the bet
				user.subtractPoints(points);
				betTracker.addBet(user, points, betColor);
				
				//prepare the message to be sent over the server
				output = "Bet#" + Utility.colorToString(betColor) + "#" + points; 
				newOutput = true;
				
				
				//update labels
				updateUserLabel();
				//updatePointLabels(points); This now get updated by server/client calls
				
				revalidate();
			}
		});
	}
	
	
	private void updatePointLabels(int points, BetColor color)
	{
		switch(color)
		{
		case RED:
			redPoints += points;
			lblRedPoints.setText("" + redPoints);
			break;
		case GREEN:
			greenPoints += points;
			lblGreenPoints.setText("" + greenPoints);
			break;
		case BLACK:
			blackPoints += points;
			lblBlackPoints.setText("" + blackPoints);
			break;
		case NONE:
			//should never happen
			break;
		}
	}
	
	private void updateUserLabel()
	{
		lblUsernamePoints.setText(user.getUsername() + ": " + user.getPoints());
	}
	
	public void test()
	{
		System.out.println("Hello");
	}
	
	public void recieveMessage(String message)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				chatArea.append(message);
				revalidate();
			}
		});
	}
	
	public synchronized String getMessage()
	{
		while(!newOutput)
		{
			try
			{
				Thread.sleep(5);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		newOutput = false;
		return output;
	}
	
	public void recieveBet(BetColor color, int amt)
	{
		EventQueue.invokeLater(new Runnable()
		{
			
			@Override
			public void run()
			{
				updatePointLabels(amt, color);
				revalidate();
				
			}
		});

	}
	
	public void recieveStart(BetColor color, int cyclesSinceGreen)
	{
		gambleColor = color;
		this.cyclesSinceGreen = cyclesSinceGreen;

		
		if(color == BetColor.RED)
		{
			panel.setBackground(red);
		}
		else if(color == BetColor.GREEN)
		{
			panel.setBackground(Color.GREEN);
		}
		else
		{
			panel.setBackground(Color.BLACK);
		}
		
		EventQueue.invokeLater(new Runnable()
		{
			
			@Override
			public void run()
			{
				revalidate();
				
			}
		});
		
		
		if(firstStart)
		{
			firstStart = false;
		}
			
		
		recievedStart = true;
		betting = true;
	}
	
	public void recieveRoll(int value)
	{
		roll = value;
		recievedRoll = true;
	}
	
	
	
}
