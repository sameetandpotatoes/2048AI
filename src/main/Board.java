package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board {
	private int[][] board;
	private GUI gui;
	private int score;
	private AI ai;
	private static final int GOAL = 2048;
	public Board(int[][] board, GUI gui, AI ai){
		this.board = board;
		this.score = 0;
		this.gui = gui;
		this.ai = ai;
	}
    public Board handleMoves(int id, boolean sourceAI){
        updateBoard(id, true, sourceAI);
        if (score > gui.getHighScore()) {
            gui.updateScore(score);
        }
        gui.updateText(); //Score changed, so text needs to be updated
        if (checkLoss(sourceAI)) {
        	gui.handleLoss();
        }
        gui.updateColors(); //If tiles changed numbers, we need to update the colors
        if (ai.ai_running)
       	 	this.ai.makeMove();
        return this;
   }
    public int getScore(){
    	return score;
    }
   public int[][] getBoard(){
	   return board;
   }
	public Board copyBoard() {
		int[][] boardCopy = new int[4][4];
		for (int r = 0; r < board.length; r++){
			for (int c = 0 ; c < board[r].length; c++){
				boardCopy[r][c] = board[r][c];
			}
		}
       return new Board(boardCopy, gui, ai);
    }
	public boolean checkWin() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == GOAL) 
                	return true;
            }
        }
        return false;
    }
	public boolean checkLoss(boolean ai) {
    	return !(	checkMove(KeyEvent.VK_LEFT, ai) ||
    				checkMove(KeyEvent.VK_UP, ai) ||
    				checkMove(KeyEvent.VK_RIGHT, ai) ||
    				checkMove(KeyEvent.VK_DOWN, ai));
    }
    public boolean checkMove(int keyEvent, boolean ai){
    	Board before = copyBoard();
    	boolean change = true;
    	updateBoard(keyEvent, false, ai);
    	if (Arrays.deepEquals(before.getBoard(), getBoard()))
    		change = false;
    	board = before.copyBoard().getBoard();
    	return change;
    }
    public void printBoard() {
        for (int[] a: board) {
            for (int i : a)
                System.out.print(i + " ");
            System.out.println();
        }
        System.out.println();
    }
    public void updateBoard(int n, boolean real, boolean ai) {
    	Board before = copyBoard();
    	if (n == KeyEvent.VK_LEFT) {
            pushLeft();
            //check tiles with x > 0, from left to right to see if they collapse
            joinTiles(real);
            pushLeft();
            if (!ai){
            	addNewTile(before);
//            	addNewTile();
            }
        }
        else if (n == KeyEvent.VK_UP) {
            rotateCCW();
            updateBoard(KeyEvent.VK_LEFT, real, ai);
            //Reverse effects
            rotateCW();
        }
        else if (n == KeyEvent.VK_RIGHT) {
            rotateCCW();
            rotateCCW();
            updateBoard(KeyEvent.VK_LEFT, real, ai);
            //Reverse effects
            rotateCW();
            rotateCW();
        	
        }
        else if (n == KeyEvent.VK_DOWN) {
            rotateCW();
            updateBoard(KeyEvent.VK_LEFT, real, ai);
            //Reverse effects
            rotateCCW();
        }
        this.ai.setBoard(this);
        this.gui.setBoard(this);
    }
    public void addRandomTiles(){
    	int[] pos1 = {(int) (Math.random() * 3), (int) (Math.random() * 3)};
        int value1 = ((int) (Math.random() * 2) + 1) * 2;
        int[] pos2 = {(int) (Math.random() * 3), (int) (Math.random() * 3)};
        while (pos1[0] == pos2[0] && pos1[1] == pos2[1]) {
                int[] temp = {(int) (Math.random() * 3), (int) (Math.random() * 3)};
                pos2 = temp;
        }
        int value2 = ((int) (Math.random() * 2) + 1) * 2;
        board[pos1[0]][pos1[1]] = value1;
        board[pos2[0]][pos2[1]] = value2;
    }
    public void addNewTile(Board before){
    	if (!Arrays.deepEquals(before.getBoard(), board)) {
            ArrayList<int[]> a = new ArrayList<int[]>();
            //Gather all Empty Tiles left on the board
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (board[i][j] == 0) {
                        int[] coords = {i, j};
                        a.add(coords);
                    }
                }
            }
            //Shuffle arraylist around
            Collections.shuffle(a);
            int random = (int) (Math.random() * 11);
            if (random < 1) {
            	board[a.get(0)[0]][a.get(0)[1]] = 4;
            } else{
            	//Higher chance of getting a 2
            	board[a.get(0)[0]][a.get(0)[1]] = 2;
            }
        }
    }
    public void setBoard(int[][] originalBoard){
    	for (int r = 0; r < originalBoard.length; r++){
    		for (int c = 0; c < originalBoard[r].length; c++){
    			board[r][c] = originalBoard[r][c];
    		}
    	}
    }
    public void joinTiles(boolean real){
    	for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
            	//If two consecutive numbers found, add them together
                if (board[i][j-1] == board[i][j]) {
                    board[i][j-1] *= 2;
                    if (real) {
                        score += board[i][j-1];
                    }
                    board[i][j] = 0; //temporarily leave a 0 which will go away when we push again
                }
            }
        }
    }
    public void pushLeft() {
        //move tiles with values as far left as possible
        for (int i = 0; i < 4; i++) {
            int[] row = new int[4];
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0) {
                    int curr = 0;
                    while(row[curr] != 0) {
                        curr++;
                    }
                    //Found, empty cell so moving it over
                    row[curr] = board[i][j];
                }
            }
            board[i] = row;
        }
    }
    public void rotateCW() {
        int[][] rotated = new int[4][4];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                rotated[c][3-r] = board[r][c];
            }
        }
        board = rotated;
    }
    public void rotateCCW() {
        int[][] rotated = new int[4][4];
        for (int r = 0; r < 4; r++) {
        	for (int c = 0; c < 4; c++) {
        		rotated[3-c][r] = board[r][c];
        	}
        }
        board = rotated;
    }
    public Map<String, Integer> getInfoFromBoard(){
    	Map<String, Integer> frequencies = new HashMap<String, Integer>();
    	for (int r = 0; r < board.length; r++){
    		for (int c = 0; c < board[r].length; c++){
    			if (board[r][c] != 0){
    				switch(board[r][c]){
    					case 512:
    						frequencies.put("512", frequencies.get("512")+1);
    						break;
    					case 1024:
    						frequencies.put("1024", frequencies.get("1024")+1);
    						frequencies.put("512", 	frequencies.get("512")+2);
    						break;
    					case 2048:
    						frequencies.put("2048", frequencies.get("2048")+1);
    						frequencies.put("1024", frequencies.get("1024")+2);
    						frequencies.put("512", 	frequencies.get("512")+4);
    						break;
    					case 4096:
    						frequencies.put("4096", frequencies.get("4096")+1);
    						frequencies.put("2048", frequencies.get("2048")+2);
    						frequencies.put("1024", frequencies.get("1024")+4);
    						frequencies.put("512",  frequencies.get("512")+8);
    						break;
    					default:
    						break;
    				}
    			}
    		}
    	}
    	return frequencies;
    }
    public int countEmpty(){
    	int countEmpty = 0;
		for (int r = 0; r < board.length; r++){
    		for (int c = 0; c < board[r].length; c++){
    			if (board[r][c] == 0){
    				countEmpty++;
    			}
    		}
    	}
		return countEmpty;
    }
}
