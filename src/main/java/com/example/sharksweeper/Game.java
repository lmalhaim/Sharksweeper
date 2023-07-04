package com.example.sharksweeper;

public class Game implements Runnable {
	private GameSkeleton GS = new GameSkeleton();
	public static void main(String[] args) {
		new Thread(new Game()).start();
	}

	@Override
	public void run() {
		while(true) {
			GS.repaint(); //repaint paint method in game 
		}
		
	}
}
