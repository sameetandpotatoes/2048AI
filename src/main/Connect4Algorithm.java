package main;

public class Connect4Algorithm {
	int[][] board = new int[7][6];
	private static ConnectFourGUI gui;
	public static void main (String[] args){
		gui = new ConnectFourGUI();
	}
//	private boolean checkWin(){
//		return false;
//	}
//	private boolean checkHorizontal(){
//		return false;		
//	}
//	private boolean checkVertical(){
//		return false;
//	}
//	private boolean checkDiagonal(){
//		return false;
//	}
//	private boolean checkDiagonalBack(){
//		return false;
//	}
	private void resetGame(){
		gui.resetGameBoard();
	}
}
