package main;

public class Connect4Algorithm {
	int[][] board = new int[7][6];
	private static ConnectFourGUI gui;
	public static void main (String[] args){
		gui = new ConnectFourGUI();
	}
	private void ai(){
		//search tree of every possible move, and then every possible move of user
		//heuristic score based on number of consecutive tiles of user
		//if its blank, higher; if its next to black piece, lower
	}
	private void resetGame(){
		gui.resetGameBoard();
	}
}
