package com.example.sharksweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*public class HomePanel extends JPanel{
	public HomePanel(){
		//Set up
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(300,300));
		setBackground(Color.WHITE);
		
		//Adding UIC 
		//Adding Weclome Label
		JLabel WLabel = new JLabel("                Welcome to Bombsweeper!");
		WLabel.setPreferredSize(new Dimension(180,100));
		add(WLabel, BorderLayout.PAGE_START);
		
		//Adding play and select level buttons
		JButton Play_Button = new JButton("PLAY");
		Play_Button.setPreferredSize(new Dimension(180, 100));
		add(Play_Button, BorderLayout.CENTER);
		Play_Button.addActionListener(SBactionListener);
		JButton Level_Button = new JButton("SELECT LEVEL");
		Level_Button.setPreferredSize(new Dimension(180, 100));
		add(Level_Button, BorderLayout.PAGE_END);
		setVisible(true);
		Level_Button.addActionListener(LactionListener);
	}
	
	//Accessing Level Panel 
	ActionListener LactionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setContentPane(LPanel);
				invalidate();
				validate();
		    }

		};

		
		
		//Setting level action listeners
		//Standard
		ActionListener SBactionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Changing size of frame to fit game panel
				setSize(new Dimension(402,688));
				//Creating Game panel of desired size, in our case we want our easy game to be of size 10x15
				GamePanel GPanel2 = new GamePanel(10,15);
				setContentPane(GPanel2);
				invalidate();
				validate();
			 }

		};
		

}*/
