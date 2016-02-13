package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import core.BetColor;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class BetGUI extends JFrame {

	private JPanel contentPane;
	private JTextField chatField;
	private String username = "Anus Potato";
	
	private BetColor betColor = BetColor.NONE;
	private ActionListener colorButtonsListener;
	
	private JButton btnRed;
	private JButton btnGreen;
	private JButton btnBlack;
	
	private JButton btnBet;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{
					BetGUI frame = new BetGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BetGUI() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//chatArea is where user chat takes place
		JTextArea chatArea = new JTextArea();
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
					chatArea.append(message); //TODO, send the text to server/host
					
					chatField.setText("");
					revalidate();
					
				}
					
			}
		});
		
		JLabel lblChat = new JLabel("Chat:");
		lblChat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblChat.setBounds(360, 303, 46, 20);
		contentPane.add(lblChat);
		
		JLabel lblUsernamePoints = new JLabel("Username:     Points");
		lblUsernamePoints.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsernamePoints.setBounds(10, 11, 134, 20);
		contentPane.add(lblUsernamePoints);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(68, 303, 106, 20);
		contentPane.add(formattedTextField);
		
		btnBet = new JButton("Bet");
		btnBet.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBet.setForeground(Color.GRAY);
		btnBet.setBounds(184, 302, 89, 23);
		contentPane.add(btnBet);
		
		JLabel lblPoints = new JLabel("Points:");
		lblPoints.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPoints.setBounds(10, 302, 57, 18);
		contentPane.add(lblPoints);
		
		btnRed = new JButton("");
		btnRed.setBackground(new Color(165, 42, 42));
		btnRed.setForeground(Color.GRAY);
		btnRed.setBounds(10, 69, 89, 23);
		contentPane.add(btnRed);
		
		btnGreen = new JButton("");
		btnGreen.setBounds(109, 69, 89, 23);
		btnGreen.setBackground(Color.GREEN);
		btnGreen.setForeground(Color.GRAY);
		contentPane.add(btnGreen);
		
		btnBlack = new JButton("");
		btnBlack.setBackground(Color.BLACK);
		btnBlack.setForeground(Color.GRAY);
		btnBlack.setBounds(208, 69, 89, 23);
		contentPane.add(btnBlack);
		
		JLabel lblClickAColor = new JLabel("Click a Color To Bet On");
		lblClickAColor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblClickAColor.setBounds(10, 42, 175, 20);
		contentPane.add(lblClickAColor);
		
		JLabel lblRedPointBet = new JLabel("Red Point bet");
		lblRedPointBet.setBounds(10, 103, 89, 20);
		contentPane.add(lblRedPointBet);
		
		JLabel lblNewLabel = new JLabel("Green Points bet");
		lblNewLabel.setBounds(109, 103, 89, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Black Points bet");
		lblNewLabel_1.setBounds(208, 103, 89, 18);
		contentPane.add(lblNewLabel_1);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 264, 164, 14);
		contentPane.add(progressBar);
		
		JLabel lblTimeTillBet = new JLabel("Time Till bet");
		lblTimeTillBet.setBounds(184, 264, 70, 18);
		contentPane.add(lblTimeTillBet);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GREEN);
		panel.setBounds(10, 147, 89, 81);
		contentPane.add(panel);
		
		//create colorButtons Action Listener and add them
		initColorButtonListener();
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
					btnBet.setBackground(new Color(165, 42, 42));
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
}
