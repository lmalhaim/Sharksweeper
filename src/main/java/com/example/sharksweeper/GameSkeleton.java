package com.example.sharksweeper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.*;



//Grid Class
public class GameSkeleton extends JFrame{
	
	//Default Frame Size
	static int WIDTH = 500;
	static final int LENGTH = 500;
	
	
	//Container components
	private JPanel  LPanel; 
	private JButton Easy_LButton, Hard_LButton, Medium_LButton;
	private Color Baby_blue = new Color(220, 233, 245);//Custom Background RGB Color 

	
	public GameSkeleton() {
		
		//Frame set up (Using GridBagLayout)//
		//Background and layout set up
		super("Minesweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(WIDTH,LENGTH));
		setVisible(true);
		
		
		//Using HPanel function to access Home Screen//
		JPanel HPanel = new HPanel();
		getContentPane().add(HPanel);
		
				
		//Adding menu bar to (Change Level, Home Screen, Exit Game)
		JMenuBar MenuBar = new JMenuBar();
		setJMenuBar(MenuBar);
		JMenu Menu = new JMenu("Options");
		MenuBar.add(Menu);
		
		JMenuItem HSMenu = new JMenuItem("Return to Home Screen");
		JMenuItem EMenu = new JMenuItem("Exit Game");
		EndingListener EactionListener = new EndingListener();
		EMenu.addActionListener(EactionListener);
		HSMenu.addActionListener(HactionListener);
		Menu.add(HSMenu);
		Menu.add(EMenu);
		
		
		setVisible(true);
		
		
		//Level Panel//
		//Set up panel size and background
		LPanel = new JPanel();
		LPanel.setPreferredSize(new Dimension(300,300));
		LPanel.setBackground(Baby_blue);
		LPanel.setLayout(new GridBagLayout());
		GridBagConstraints LPGridBag = new GridBagConstraints();
		
		//Setting buttons for choosing level option (Easy, Medium, Hard)
		LPGridBag.gridx = 1;
		LPGridBag.gridy = 0;
		Easy_LButton = new JButton("Easy");
		LPanel.add(Easy_LButton, LPGridBag);
		Easy_LButton.addActionListener(SBactionListener);
		Easy_LButton.setPreferredSize(new Dimension(150,70));
		
		
		LPGridBag.gridx = 1;
		LPGridBag.gridy = 1;
		Medium_LButton = new JButton("Medium");
		LPanel.add(Medium_LButton, LPGridBag);
		Medium_LButton.setPreferredSize(new Dimension(150,70));
		Medium_LButton.addActionListener(MBactionListener);
		
		LPGridBag.gridx = 1;
		LPGridBag.gridy = 2;
		Hard_LButton = new JButton("Hard");
		LPanel.add(Hard_LButton, LPGridBag);
		Hard_LButton.setPreferredSize(new Dimension(150,70));
		Hard_LButton.addActionListener(HBactionListener);
		
		pack();
		
		
	}
	
	
	
	//Create Home panel function
	public class HPanel extends JPanel{
		public HPanel() {
			setSize(new Dimension(WIDTH,LENGTH));
			Color Baby_blue = new Color(220, 233, 245);
			setBackground(Baby_blue);
			setLayout(new GridBagLayout());
			GridBagConstraints HGrid = new GridBagConstraints();
			HGrid.gridx = 0;
			HGrid.gridy = 2;
			JLabel WLabel = new JLabel("Welcome to "+"\n"
					+ "Sharksweeper!");
			WLabel.setFont(new Font("Andale Mono", Font.PLAIN ,20));
			WLabel.setPreferredSize(new Dimension(300,100));
			add(WLabel, HGrid);
			
			//Adding play and select level buttons
			JButton Play_Button = new JButton("PLAY");
			Play_Button.setPreferredSize(new Dimension(120, 70));
			HGrid.gridx = 0;
			HGrid.gridy = 1;
			add(Play_Button, HGrid);
			Play_Button.addActionListener(SBactionListener);
			
			JButton Level_Button = new JButton("SELECT LEVEL");
			HGrid.gridx = 0;
			HGrid.gridy = 3;
			Level_Button.setPreferredSize(new Dimension(120, 70));
			add(Level_Button,HGrid);
			setVisible(true);
			Level_Button.addActionListener(LactionListener);
		}
		
	}
	
	
	//Ending Listeners//
	
	//Accessing Home Panel
	ActionListener HactionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JPanel HPanelB = new HPanel();
			setContentPane(HPanelB);
			invalidate();
			validate();
	    }

	};
	
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
			setSize(new Dimension(552,660));
			//Creating Game panel of desired size, in our case we want our easy game to be of size 10x10
			GamePanel GPanel2 = new GamePanel(10,10);
			setContentPane(GPanel2);
			invalidate();
			validate();
		 }

	};
	
	ActionListener MBactionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//Changing size of frame to fit game panel
			setSize(new Dimension(825,660));
			//Creating Game panel of desired size, in our case we want our medium game to be of size 15x10
			GamePanel GPanel1 = new GamePanel(15,10);
			setContentPane(GPanel1);
			invalidate();
			validate();
		}
	};
	ActionListener HBactionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//Changing size of frame to fit game panel
			setSize(new Dimension(1100,660));
			//Creating Game panel of desired size, in our case we want our hard game to be of size 20x10
			GamePanel GPanel3 = new GamePanel(20,10);
			setContentPane(GPanel3);
			invalidate();
			validate();
		}
	};
	
	
	

	
	

}
