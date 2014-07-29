package main;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

/** An instance represents the state of a game of Connect Four. */
public class State implements Comparable<State>{
    /** A State array of length 0. */
    public final static State[] length0= {};
    
    /** It is player's turn to play. */

    /** The current Board layout. */
    private int[][] board;

    /** All possible game States that can result from the next player's Move.
     *  The length of the array equals the number of States.
     *  It is an array of length 0 if there are no possible moves
     *  (once it has been set; initially, it is an array of length0) */
    private State[] children= length0;
    private int lastMove = 0;
    /** How desirable this State is. */
    private int value;
    private int actualScore;
    /** Constructor: a game State consisting of board b, with player player
     *  to play next; lm is the last Move made on this game --null
     *  if the user does not care what the last move made was. */
    public State(int[][] b, int lastMove) {
        board = b;
        value = 0;
        actualScore = GameGUI.getCurrentScore();
        this.lastMove = lastMove;
    }
    
    /** Return this State's Board. */
    public int[][] getBoard() {
        return board;
    }

    /** Return this State's children. */
    public State[] getChildren() {
        return children;
    }
    
    /** Set this State's children to c. */
    public void setChildren(State[] c) {
    	children= c;
    }

    /** Return this State's value. */
    public int getValue() {
        return value;
    }

    /** Set this State's value to v. */
    public void setValue(int v) {
        value= v;
    }
    public int getLastMove(){
    	return lastMove;
    }
    
    public int getScore(){
    	return actualScore;
    }
    /** 
     *  Retrieves the possible moves and initializes this State's children.
     *  The result is that this State's children reflect the possible
     *  States that can exist after the next move. Remember, in the
     *  children it is the opposite player's turn. This method
     *  initializes only this State's children; it does not recursively
     *  initialize all descendants. 
     */
    public void initializeChildren(int[][] copyBoard) { 
    	Integer[] moves = getPossibleMoves();
		GameGUI.board = GameGUI.copyBoard(copyBoard);
    	ArrayList<State> states1 = new ArrayList<State>();
    	for(int i = 0; i < moves.length; i++){
    		GameGUI.updateBoard(moves[i], false, true);
//    		System.out.println("MOVED");
//    		GameGUI.printBoard(GameGUI.getBoard());
    		State rohitsState = new State(GameGUI.getBoard(), moves[i]);
//    		System.out.println("CURRENT SCORE: " + GameGUI.getExpectedScore());
//    		GameGUI.printBoard(GameGUI.getBoard());
    		states1.add(rohitsState);
			
    		ArrayList<State> extraStates = this.getPossibleRandomMoves(moves[i], GameGUI.getBoard());
    		for (State extraState : extraStates)
    			states1.add(extraState);

    		GameGUI.board = GameGUI.copyBoard(copyBoard);
//    		System.out.println("GAME");
//    		GameGUI.printBoard(GameGUI.board);
    		GameGUI.expectedScore = GameGUI.currentScore;
    	}

    	children = states1.toArray(new State[states1.size()]);
    }
    private Integer[] getPossibleMoves(){
    	ArrayList<Integer> moves = new ArrayList<Integer>();
    	if (GameGUI.checkMove(KeyEvent.VK_LEFT, true))
    		moves.add(37);
    	if (GameGUI.checkMove(KeyEvent.VK_UP, true))
    		moves.add(38);
    	if (GameGUI.checkMove(KeyEvent.VK_RIGHT, true))
    		moves.add(39);
    	if (GameGUI.checkMove(KeyEvent.VK_DOWN, true))
    		moves.add(40);
    	return moves.toArray(new Integer[moves.size()]);
    } 
    private ArrayList<State> getPossibleRandomMoves(int lastMove, int[][] board){
    	int[][] originalBoard = GameGUI.copyBoard(board);
    	ArrayList<State> posRandomMoves = new ArrayList<State>();
    	for (int r = 0; r < board.length; r++){
    		for (int c = 0; c < board[r].length; c++){
    			if (board[r][c] == 0){
    				board[r][c] = 2;
    				posRandomMoves.add(new State(board, lastMove));
//    				int rand = (int) (Math.random() * 2);
//    				if (rand == 0){
//	    				board = GameGUI.copyBoard(originalBoard);
//	    				board[r][c] = 4;
//	    				posRandomMoves.add(new State(board, lastMove));
//    				}
    				board = GameGUI.copyBoard(originalBoard);
    			}
    		}
    	}
    	return posRandomMoves;
    }

	@Override
	public int compareTo(State o) {
		return (int) (this.getValue() - o.getValue());
	}
}
