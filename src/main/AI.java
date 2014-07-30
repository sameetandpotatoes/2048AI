package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingWorker;

public class AI {
	private int[][] board;
	private GUI gui;
	private static final int AI_EASY = 3;
    private static final int AI_HARD = 4;
    private static int aiDepth = AI_EASY;
    private static Map<String, Double> weights = new HashMap<String, Double>();
	public AI(GUI gui){
		this.board = gui.getBoard();
		this.gui = gui;
        weights.put("Smooth", 0.1);
        weights.put("Mono",1.0);
        weights.put("Empty",2.7);
        weights.put("Max",1.0);
	}
	public void makeMove(){
		board = gui.copyBoard(gui.getBoard());
		new SwingWorker<Integer, Integer>() {
	    	  protected Integer doInBackground() {
				int[][] copyBoard = gui.copyBoard(board);
				State currentState = new State(board, 0);
	    		createGameTree(currentState, aiDepth, copyBoard);
	    		board = gui.copyBoard(copyBoard);
	    		
	    		minimax(currentState);
	    		int keyStroke = currentState.getChildren()[currentState.getChildren().length - 1].getLastMove();
	    		int[] maxTile = highestTile(currentState.getBoard());
	    		boolean movedAlready = false;
	    		if (!isCorner(maxTile[0], maxTile[1])){
//	    			System.out.println("Max tile is not in the corner");
		    		int i = currentState.getChildren().length - 2;
	    			for (i = currentState.getChildren().length - 2; i >= (int) (currentState.getChildren().length/2); i--){
		    			State nextState = currentState.getChildren()[i];
		    			int[] nextHighest = highestTile(nextState.getBoard());
		    			if (isCorner(nextHighest[0], nextHighest[1]) && Math.abs(nextState.getValue() - currentState.getValue()) < 5){
		    				gui.handleMoves(nextState.getLastMove(), false);
		    				movedAlready = true;
		    				break;
		    			}
		    		}
//	    			System.out.println("Out of the loop");
	    		}
	    		if (!movedAlready){
	    			gui.handleMoves(keyStroke, false);	
	    		}
	    		if (gui.getEnableOutput()){
	    			System.out.println(currentState.getChildren()[currentState.getChildren().length - 1].getValue());
	    			System.out.println("Moved " + keyStroke);
	    		}
	    		
	    		int countEmpty = 0;
	    		for (int r = 0; r < board.length; r++){
	        		for (int c = 0; c < board[r].length; c++){
	        			if (board[r][c] == 0){
	        				countEmpty++;
	        			}
	        		}
	        	}
	    		if (countEmpty <= 3)
	    			aiDepth = AI_HARD;
	    		else
	    			aiDepth = AI_EASY;
	    		
	    		gui.setPrevBoard(copyBoard);
	    		
	    		return 0;
	    	  }
	      }.execute();
	}
	public static void createGameTree(State s, int d, int[][] originalBoard) {
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
    public static int evaluateBoard(int[][] board, int stateScore){
    	int countFilled = 0;
    	for (int r = 0; r < board.length; r++){
    		for (int c = 0; c < board[r].length; c++){
    			if (board[r][c] == 0){
    				
    			} else {
    				countFilled++;
    			}
    		}
    	}
    	int[] maxLocations = highestTile(board);
    	double smoothness =(smoothness(board) * weights.get("Smooth"));
    	double mono =monotonocity(board) * weights.get("Mono"); 
    	double empty =Math.log(16 - countFilled) * weights.get("Empty");
    	double max = maxLocations[2] * weights.get("Max");
    	double score = smoothness + mono + empty + max;
    	double factor = (Math.log(maxLocations[2]) / Math.log(2));
    	if (maxLocations[0] == 0 && (maxLocations[1] == 0 || maxLocations[1] == 3)
    	    	|| (maxLocations[0] == 3 && (maxLocations[1] == 0 || maxLocations[1] == 3))){
    	    score *= factor;
    	}
    	return (int) Math.round(score);
    }
    private static double smoothness(int[][] board){
    	double smoothness = 0;
    	for (int r = 0; r < board.length; r++){
    		for (int c = 0; c < board[r].length; c++){
    			if (board[r][c] != 0){
    				double value = Math.log(board[r][c]) / Math.log(2);
	    			if (c != 3){
						//check right
						if (board[r][c+1] != 0 && board[r][c] != 0){
							double targetValue = Math.log(board[r][c+1]) / Math.log(2);
							smoothness += Math.abs(value - targetValue);
						}
	    			}
					if (r != 3){
						//check down
						if (board[r+1][c] != 0 && board[r][c] != 0){
							double targetValue = Math.log(board[r+1][c] / Math.log(2));
							smoothness += Math.abs(value - targetValue);
						}
					}
    			}
    		}
    	}
    	return smoothness;
    }
    private static double monotonocity(int[][] board){
    	double[] totals = new double[]{0, 0, 0, 0};
    	for (int c = 0; c < 4; c++){
    		int current=0;
			int nextRow = current+1;
			while (nextRow < 4){
				while (nextRow < 4 && board[nextRow][c] == 0)
					nextRow++;
				if (nextRow == 4)
					break;
				double currentValue = 0.0, nextValue = 0.0;
				if (board[current][c] != 0){
					currentValue = (Math.log(board[current][c]) / Math.log(2));
				}
				if (board[nextRow][c] != 0){
					nextValue = (Math.log(board[nextRow][c]) / Math.log(2));
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
    	for (int r = 0; r < 4; r++){
    		int current=0;
			int nextCol = current+1;
			while (nextCol < 4){
				while (nextCol < 4 && board[r][nextCol] == 0)
					nextCol++;
				if (nextCol == 4)
					break;
				double currentValue = 0.0, nextValue = 0.0;
				if (board[r][current] != 0){
					currentValue = (Math.log(board[r][current]) / Math.log(2));
				}
				if (board[r][nextCol] != 0){
					nextValue = (Math.log(board[r][nextCol]) / Math.log(2));
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
    public static int[] highestTile(int[][] board) {
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
