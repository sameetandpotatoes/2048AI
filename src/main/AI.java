package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingWorker;

public class AI {
	private Board board;
	private GUI gui;
	private static final int AI_EASY = 3;
    private static final int AI_HARD = 4;
    private static final int AI_REALLY_HARD = 5;
    private static int aiDepth = AI_EASY;
    public boolean ai_running = false;
    private static Map<String, Double> weights = new HashMap<String, Double>();
	public AI(){
        weights.put("Smooth", 0.1);
        weights.put("Mono",1.0);
        weights.put("Empty",2.7);
        weights.put("Max",1.0);
	}
	public void setGUI(GUI gui){
		this.gui = gui;
		this.board = gui.getBoard();
	}
	public void setBoard(Board b){
		board = b.copyBoard();
	}
	public void makeMove(){
		new SwingWorker<Integer, Integer>() {
	    	  protected Integer doInBackground() {
				Board copyBoard = board.copyBoard();
				State currentState = new State(board, 0, aiDepth);
	    		createGameTree(currentState, aiDepth, board);
	    		board = copyBoard.copyBoard();
	    		
	    		minimax(currentState);
	    		int keyStroke = currentState.getChildren()[currentState.getChildren().length - 1].getLastMove();
	    		boolean movedAlready = false;
	    		board.updateBoard(keyStroke, true, false);
	    		gui.updateText();
	    		gui.updateColors();
	    			if (ai_running){
	    				makeMove();
	    			}
	    		int countEmpty = board.countEmpty();
	    		
	    		if (countEmpty <= 2)
	    			aiDepth = AI_REALLY_HARD;
	    		else if (countEmpty <= 6)
	    			aiDepth = AI_HARD;
	    		else
	    			aiDepth = AI_EASY;
	    		return 0;
	    	  }
	      }.execute();
	}
	public static void createGameTree(State s, int d, Board originalBoard) {
    	if(d == 0){
    		return;
    	}
    	if(s.getChildren().length == 0){
    		s.initializeChildren(originalBoard, d);
    	}
    	for(State st : s.getChildren()){
    		createGameTree(st, d-1, st.getBoard());
    	}
    }

    public void minimax(State s) {
    	if(s.getValue() != 0){
    		return;
    	}
    	if(s.getChildren().length == 0){
    		s.setValue(evaluateBoard(s.getBoard(),s.getScore()));
    		return;
    	}
    	for(State st:s.getChildren()){
    		minimax(st);
    	}
    	Arrays.sort(s.getChildren());
    	s.setValue(s.getChildren()[s.getChildren().length - 1].getValue());
    }
    public static int evaluateBoard(Board board, int stateScore){
    	int countEmpty = board.countEmpty();
    	int[] maxLocations = highestTile(board);
    	double smoothness =(smoothness(board) * weights.get("Smooth"));
    	double mono =monotonocity(board) * weights.get("Mono"); 
    	double empty =Math.log(countEmpty) * weights.get("Empty");
    	double max = maxLocations[2] * weights.get("Max");
    	double score = smoothness + mono + empty + max;
    	double factor = (Math.log(maxLocations[2]) / Math.log(2));
    	if (maxLocations[0] == 0 && (maxLocations[1] == 0 || maxLocations[1] == 3)
    	    	|| (maxLocations[0] == 3 && (maxLocations[1] == 0 || maxLocations[1] == 3))){
    	    score *= factor;
    	}
    	return (int) Math.round(score);
    }
    private static double smoothness(Board b){
    	int[][] board = b.getBoard();
    	double smoothness = 0;
    	for (int r = 0; r < board.length; r++){
    		for (int c = 0; c < board[r].length; c++){
    			if (board[r][c] != 0){
    				double value = Math.log(board[r][c]) / Math.log(2);
	    			if (c != 3){
						//check right
						if (board[r][c+1] != 0 && board[r][c] != 0){
							double targetValue = Math.log(board[r][c+1]) / Math.log(2);
							smoothness -= Math.abs(value - targetValue);
						}
	    			}
					if (r != 3){
						//check down
						if (board[r+1][c] != 0 && board[r][c] != 0){
							double targetValue = Math.log(board[r+1][c] / Math.log(2));
							smoothness -= Math.abs(value - targetValue);
						}
					}
    			}
    		}
    	}
    	return smoothness;
    }
    private static int monotonocity(Board b){
    	int[][] board = b.getBoard();
    	int[] totals = new int[]{0, 0, 0, 0};
    	//Left Right
    	for (int c = 0; c < 4; c++){
    		int current = 0;
			int nextRow = current + 1;
			while (nextRow < 4){
				while (nextRow < 4 && board[nextRow][c] == 0)
					nextRow++;
				if (nextRow == 4)
					nextRow--;
				int currentValue = 0, nextValue = 0;
				if (board[current][c] != 0){
					currentValue = (int) (Math.log(board[current][c]) / Math.log(2));
				}
				if (board[nextRow][c] != 0){
					nextValue = (int) (Math.log(board[nextRow][c]) / Math.log(2));
				}
	  	      	if (currentValue > nextValue) {
	  	      		totals[0] += nextValue - currentValue;
	  	      	} else if (nextValue > currentValue) {
	  	      		totals[1] += currentValue - nextValue;
	  	      	}
	  	      	current = nextRow;
				nextRow++;
			}
    	}
    	//Up Down
    	for (int r = 0; r < 4; r++){
    		int current=0;
			int nextCol = current+1;
			while (nextCol < 4){
				while (nextCol < 4 && board[r][nextCol] == 0)
					nextCol++;
				if (nextCol == 4)
					nextCol--;
				int currentValue = 0, nextValue = 0;
				if (board[r][current] != 0){
					currentValue = (int) (Math.log(board[r][current]) / Math.log(2));
				}
				if (board[r][nextCol] != 0){
					nextValue = (int) (Math.log(board[r][nextCol]) / Math.log(2));
				}
	  	      	if (currentValue > nextValue) {
	  	      		totals[2] += nextValue - currentValue;
	  	      	} else if (nextValue > currentValue) {
	  	      		totals[3] += currentValue - nextValue;
	  	      	}
	  	      	current = nextCol;
	  	      	nextCol++;
			}
    	}
    	  return Math.max(totals[0], totals[1]) + Math.max(totals[2], totals[3]);
    }
    public static int[] highestTile(Board b) {
    	int[][] board = b.getBoard();
        int maxRow = 0;
        int maxCol = 0;
        int maxNum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] > maxNum || board[i][j] == maxNum && isCorner(i, j)){ 
                	maxRow = i;
                	maxCol = j;
                	maxNum = board[i][j];
            	}
            }
        }
    	int[] maxStuff = new int[]{maxRow, maxCol, maxNum};
        return maxStuff;
    }
    private static boolean isCorner(int r, int c){
    	return (r == 0 && (c == 0 || c == 3) || (r == 3) && (c == 0 || c == 3));
    }
}
