package com.example.sharksweeper;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;


public class GamePanel extends JPanel {
	
	//Constants//
	//Grid size of game
	private final int ROWS;
	private final int COLS;
	
	//Grid variables
	private final int spacing = 2; //Spacing between game cells
	private final int SqrSize = 55;//Cell size
	
	//Variables//
	//Game variables
	private int[][] mines;//For setting mines 
	private boolean[][] revealed; ;//For checking if revealed
	private boolean[][] sealed;//Sealed cells
	private boolean Bseal = false;//If Button seal is clicked then true
	private int[][] neighbors;//Counting neighbors	
	private boolean status = true;//Game status: Win (True), Lose (False);
	
	//Mouse Variables
	private int MMouseX = -100;
	private int MMouseY = -100;
	
	//Images to be used in game panel 
	private BufferedImage SharkBomb_img;//BufferedImages are define in Paint Component in a Try/Catch method 
	private BufferedImage Seal_img;
	private Icon SharkEnd_img = new ImageIcon(getClass().getResource("/SHARKS.png"));
	private Icon SealEnd_img = new ImageIcon(getClass().getResource("/MYSeal.png"));
	
	//JComponents
	private JButton SButton, QButton;//Seal and Question Mark Buttons
	private JLabel On_Off;//On/Off Label
	
	
	public GamePanel(int Rows, int Cols) {
		
		ROWS = Rows;
		COLS = Cols;
		QButton = new JButton("?");
		QButton.setBorder(new RoundButton(2,"Button"));
		QButton.setForeground(Color.WHITE);
		QButton.setPreferredSize(new Dimension(20,20));
		QClicked QClick = new QClicked();
		QButton.addMouseListener(QClick);
		add(QButton);
		
		
		Icon image = new ImageIcon(getClass().getResource("/seal.png"));
		SButton = new JButton(image);
		SButton.setBorder(new RoundButton(30, "img"));
		SButton.setPreferredSize(new Dimension(80,60));
		MouseClicked Click = new MouseClicked();
		SButton.addMouseListener(Click);
		add(SButton);
		On_Off =  new JLabel("OFF");
		add(On_Off);
		//Since our row and col values are only obtained in constructor private arrays (2D) defined here
		Random rand = new Random();
		revealed = new boolean[ROWS][COLS];
		neighbors = new int[ROWS][COLS];
		mines = new int[ROWS][COLS];
		sealed = new boolean[ROWS][COLS];
		
		//add mines to grid
		for (int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				if(rand.nextInt(100) < 10) { //10% of cells will consist of mines
					mines[i][j] = 1;

				}
				else {
					mines[i][j] = 0;
				}
			}
		}
		
		//counting how many mines surround each cell//
		for (int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				//Setting our mines neighbor value = -1
				if (mines[i][j] == 1) {
					neighbors[i][j] = -1;
				}
	
				//we want to count how many mines surround non-mine cells
				if (mines[i][j] != 1) {	
					int neighbor = 0;//number of neighboring mines
					//Using CellToNeighbor declared below to get number of neighboring cells that surrounding 
					CellToNeighbor Array = new CellToNeighbor(i,j);
					int x = Array.getX(), y = Array.getY(), xmax = Array.getXMax(), ymax = Array.getYMAX();//if no special case; cell is in the middle area of grid
					
					for(int n = x; n < xmax; n++) {
						for (int m = y; m < ymax; m++) {
							if(n == 0 && m == 0) {
							}
							else {
								if (mines[i+n][j+m] == 1) { //We increment if 
									neighbor++;			
								}
							}
						}
					}
					neighbors[i][j] = neighbor;
				}
			}
		}
		
		IfMoved Move = new IfMoved();
		addMouseMotionListener(Move);
		IfClicked clicked = new IfClicked();
		addMouseListener(clicked);
	}
	
	public void paintComponent(Graphics g) {
		try {
			SharkBomb_img = ImageIO.read(getClass().getResourceAsStream("/Shark_Cell.png"));
			Seal_img = ImageIO.read(getClass().getResourceAsStream("/FISHYY.png"));

		}catch(IOException e){
			e.printStackTrace();
		}
		Color Baby_blue = new Color(220, 233, 245);
		g.setColor(Baby_blue);
		g.fillRect(1, 0, 1280, 800);
		Color Light_PINK = new Color(143, 162, 176);
		Color Dark_PINK = new Color(118, 137, 152);
		for (int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				if (i%2 == 0) {
					if (j%2 == 0) {
						g.setColor(Light_PINK);
					}
					else {
						g.setColor(Dark_PINK);
					}
				}
				else {
					if (j%2 == 0) {
						g.setColor(Dark_PINK);
					}
					else {
						g.setColor(Light_PINK);
					}
				}
				Color Blue_white = new Color(240, 248, 255);
				if (revealed[i][j] == true) {
					if (mines[i][j] == 1) {
						g.setColor(Color.RED);
					}
					if (neighbors[i][j] != 0) {
						g.setColor(Blue_white);
					}
					if (neighbors[i][j] == 0) {
						g.setColor(Blue_white);
					}
				}
				if (MMouseX >= spacing+i*SqrSize && MMouseX < i*SqrSize+SqrSize-spacing && MMouseY >= spacing+j*SqrSize+SqrSize && MMouseY < j*SqrSize+SqrSize+SqrSize-spacing) {
					g.setColor(Color.DARK_GRAY);
				} 
				g.fillRect( spacing+i*SqrSize, spacing+j*SqrSize+SqrSize+10,SqrSize-2*spacing, SqrSize-2*spacing);
				
				if (revealed[i][j]==true) {
					if (mines[i][j] == 1) {
						g.drawImage(SharkBomb_img, i*SqrSize, j*SqrSize+SqrSize+17, null);
					}
					if (neighbors[i][j] > 0) {
						if (neighbors[i][j]%2 == 0) {
							g.setColor(Color.RED);
						}
						else{
							g.setColor(Color.CYAN);
						}
						g.setFont(new Font("Times New Romans", Font.BOLD, 20));
						g.drawString(String.valueOf(neighbors[i][j]), i*SqrSize+20, j*SqrSize+SqrSize+35+10);
					}
					
				}
				if(sealed[i][j] == true){
					//g.setColor(Color.RED);
					g.drawImage(Seal_img, i*SqrSize+10, j*SqrSize+SqrSize+20, null);
				}
			}
		
		}
		
	}

	
	//CellToNeighbor function lets us find our starting and ending indices for neighboring cells depending on position//
	public class CellToNeighbor {
		int NROWS , NCOLS;
		int x, y, xmax, ymax;//if no special case; cell is in the middle area of grid
		
		public CellToNeighbor(int i, int j) {
			x = -1;
			y = -1;
			xmax = 2;
			ymax = 2;
			NROWS = ROWS-1;
			NCOLS = COLS-1;
			//Changing x, y, xmax, ymax values depending on where our cell stands in grid
			//Upper Row 
			if (i == 0) { 
				x = 0;
				xmax = 2;
				if (j == 0) {// Upper Left Corner
					y =  0;
					ymax = 2;
				}
				else if (j == NCOLS) {//Upper Right Corner
					y = -1;
					ymax = 1;
				}
				else {//Middle Columns
					y = -1;
					ymax = 2;
				}
			}
			//Bottom Row
			else if(i == NROWS) {
				x = -1;
				xmax = 1;
				if (j == 0) {//Bottom Left Corner
					y = 0;
					ymax = 2;
				}
				else if(j == NCOLS) {//Bottom Right Corner
					y = -1;
					ymax = 1;
				}
				else {//Middle Columns
					y = -1;
					ymax = 1;
				}
			}
			//Middle Rows
			else {
				if (j == 0) {//First Column
					x = -1;
					xmax = 2;
					y = 0;
					ymax = 2;
				}
				else if(j == NCOLS){//Last Column
					x = -1;
					xmax = 2;
					y = -1;
					ymax = 1;
				}
				
			}

		}
		
		public int getX() {
			return x;
		}
		public int getXMax() {
			return xmax;
		}
		public int getY() {
			return y;
		}
		public int getYMAX() {
			return ymax;
		}
		
	}
	
	//SETTERS AND GETTERS//
	//Return X position of mouse in cell
	public int getCellX() {
		for (int i = 0; i < ROWS; i++) {
			for(int j = 0; j< COLS; j++) {
				if (MMouseX >= spacing+i*SqrSize && MMouseX < i*SqrSize+SqrSize-spacing && MMouseY >= spacing+j*SqrSize+SqrSize && MMouseY < j*SqrSize+SqrSize+SqrSize-spacing) {
					return i;
				}
			}
			
		}
		return -1;
	}
	//Return Y position of mouse in cell
	public int getCellY() {
		for (int i = 0; i < ROWS; i++) {
			for(int j = 0; j< COLS; j++) {
				if (MMouseX >= spacing+i*SqrSize && MMouseX < i*SqrSize+SqrSize-spacing && MMouseY >= spacing+j*SqrSize+SqrSize && MMouseY < j*SqrSize+SqrSize+SqrSize-spacing) {
					return j;
				}
			}
			
		}
		return -1;
	}
	
	//Get mines and updated revealed cells values 
	public int[] getMRCount() {
		int rcount = 0,mcount = 0;
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				if (revealed[i][j]==true) {
					rcount++;
				}
				if (mines[i][j] == 1) {
					mcount++;
				}
			}
		}
		int [] returnS = {rcount, mcount};
		return returnS;
	}
	
	
	//VOID FUNCITONS//
	//Display GameOver screen depending on status
	public void setGamestatus(boolean istatus) {
		status = istatus;
		
		//Check whether we lost or won//
		//If lost
		if (status == false) 
		{
			//Add Lose pop up window to RootPane of our GamePanel
			int inputL = JOptionPane.showOptionDialog(getRootPane(), "YOU LOST!\nPlay Again?","GameOver", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, SharkEnd_img, null , null);
			
			//If user click on OK button they choose to play again therefore reset game 
			if(inputL == JOptionPane.OK_OPTION)
			{
				resetAll();
			}
			
		}
		//else won
		else
		{
			//Add Win pop up window to RootPane of our GamePanel
			int inputW = JOptionPane.showOptionDialog(getRootPane(), "WE WON!\nPlay Again?","GameOver", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, SealEnd_img, null , null);
			
			//If user click on OK button they choose to play again therefore reset game 
			if(inputW == JOptionPane.OK_OPTION)
			{
				resetAll();
			}
			
		}
	}
		
		
	//Reseting our values to execute a new layout 
	public void resetAll() {
		
		//Redefine necessary variables// 
		Random rand = new Random();
		status = true;
		Bseal = false; 
	
		
		for (int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				//Add new set of mines
				if(rand.nextInt(100) < 15) { 
					mines[i][j] = 1;

				}
				else {
					mines[i][j] = 0;
				}
				//Redefine Arrays of cells 
				sealed[i][j]= false;//unseal previous cells
				neighbors[i][j]= -1;
				revealed[i][j]=false;//unreveal previous cells
			}
		}
		
		//Count neighbors for new matrix// 
		for (int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				if (mines[i][j] != 1) {	
					int neighbor = 0;
					CellToNeighbor Array = new CellToNeighbor(i,j);
					int x = Array.getX(), y = Array.getY(), xmax = Array.getXMax(), ymax = Array.getYMAX();//if no special case; cell is in the middle area of grid
					
					for(int n = x; n < xmax; n++) {
						for (int m = y; m < ymax; m++) {
							if(n == 0 && m == 0) {
							}
							else {
								if (mines[i+n][j+m] == 1) { 
									neighbor++;			
								}
							}
						}
					}
					neighbors[i][j] = neighbor;
				}
			}
			
		}
	}
	
	
	//MOUSE LISTENERS//
	//Return X and Y values for when mouse moves inside window
	public class IfMoved implements MouseMotionListener{
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			MMouseX = e.getX();
			MMouseY = e.getY();
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	
	//Reveal and Seal and end game depending on conditions of cell user clicks on 
	public class IfClicked implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			//If one of our cells is clicked on
			if (getCellX() > -1 && getCellY() > -1 && revealed[getCellX()][getCellY()] == false) {
				
				//Check whether user wants to seal or reveal a cell/
				//if Seal Button is turned on and the clicked on cell is not revealed
				if (Bseal == true) {
					
					//Check Whether user wants to seal or un-seal the cell//
					//if the cell is not sealed 
					if (sealed[getCellX()][getCellY()] == false) {
						//Make it sealed
						sealed[getCellX()][getCellY()] = true;
					}
					//else cell is already sealed
					else {
						//Make it unsealed 
						sealed[getCellX()][getCellY()] = false;
					}
				}
				//else above condition is not met which is Bsealed == false and revelad == false or Bsealed == false and revelad == true or Bsealed == true and revelad == true
				else {
					
					//if 
					if (Bseal == false && sealed[getCellX()][getCellY()] == false) {
						revealed[getCellX()][getCellY()] = true;
						//If user clicks on empty cell reveal 4 surrounding non mine cells
						if (neighbors[getCellX()][getCellY()] == 0  ) {
							int i = getCellX(), j = getCellY();
							CellToNeighbor arr = new CellToNeighbor(getCellX(),getCellY());
							int x = arr.getX(), y = arr.getY(), xmax = arr.getXMax(), ymax = arr.getYMAX();
										
							for(int n = x; n < xmax; n++) {
								for (int m = y; m < ymax; m++) {
									//If surrounding cell is not a mine
									if (mines[i+n][j+m] != 1 && !revealed[i+n][j+m] && !sealed[i+n][j+m]) { 
										revealed[i+n][j+m] = true;
										/*CellToNeighbor arr1 = new CellToNeighbor(i+n, j+m);
										for(int l = arr1.getX(); i < arr1.getXMax(); i++) {
											for(int q = arr1.getY(); j < arr1.getYMAX(); j++) {
												if (mines[i+n+l][j+m+q] != 1 && !revealed[i+n+l][j+m+q] && !sealed[i+n+l][j+m+q]) { 
													revealed[i+n+l][j+m+q] = true;
												}
											}
										}*/
									}
									
												
								}
							}
						}
						//else if user clicks on a mine 
						else if (mines[getCellX()][getCellY()] == 1){
							//We want to reveal all cells before game ends
							for (int i = 0; i < ROWS; i++) {
								for(int j = 0; j < COLS; j++) {
									sealed[i][j] = false;
									revealed[i][j] = true;
								}
							}
							setGamestatus(false);
							//We will set our status to false and create pop-up GameOver window in GameSkeleton
						}
						
					}
				}
				//Check whether it is the last cell to be revealed so we know if we have finished game or not// 
				//Call get Mine and reveal cell count
				int[] MRCount = getMRCount();
				
				//if revealed >= total - mines 
				if ((MRCount[0] >= (ROWS*COLS) - MRCount[1]) && status == true) {
					
					//since all of our safe cells were revealed we now reveal mines to user
					for(int i = 0; i < ROWS; i++) {
						for(int j = 0; j < COLS; j++) {
							if (mines[i][j] == 1) {
								sealed[i][j] = false;
								revealed[i][j]=true;
							}
						}
					}
					//Set status to true (win) to display win message and reset settings 
					setGamestatus(true);
				}
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	//Changes On_Off label depending on whether seal button is clicked on or not
	 public class MouseClicked implements MouseListener{
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Bseal == false){
					Bseal = true; 
					On_Off.setText("ON");
				}
				else {
					Bseal = false;
					On_Off.setText("OFF");
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
	    	
	    }
	
	 //Show information message when Question-Mark Button is clicked on 
	 public class QClicked implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(getRootPane(), "Hello!\n I am Mr. Seal here to help you defeat the shark\n-Click me once then click on desired cell to seal or unseal \n-Click me twice then click on desired cell to reveal \n-The ON/OFF label on my left will tell you my sealing status","INFORMATION",  JOptionPane.QUESTION_MESSAGE);

			
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		 
	 }

}
