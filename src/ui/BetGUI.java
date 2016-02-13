package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JProgressBar;

public class BetGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
	public BetGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(360, 11, 206, 281);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(413, 303, 153, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
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
		
		JButton btnBet = new JButton("Bet");
		btnBet.setBounds(184, 302, 89, 23);
		contentPane.add(btnBet);
		
		JLabel lblPoints = new JLabel("Points:");
		lblPoints.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPoints.setBounds(10, 302, 57, 18);
		contentPane.add(lblPoints);
		
		JButton btnRed = new JButton("");
		btnRed.setBackground(Color.RED);
		btnRed.setForeground(Color.GRAY);
		btnRed.setBounds(10, 69, 89, 23);
		contentPane.add(btnRed);
		
		JButton btnGreen = new JButton("");
		btnGreen.setBounds(109, 69, 89, 23);
		btnGreen.setBackground(Color.GREEN);
		btnGreen.setForeground(Color.GRAY);
		contentPane.add(btnGreen);
		
		JButton btnBlack = new JButton("");
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
	}
}
