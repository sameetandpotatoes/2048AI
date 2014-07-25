package main;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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

    /** Constructor: a game State consisting of board b, with player player
     *  to play next; lm is the last Move made on this game --null
     *  if the user does not care what the last move made was. */
    public State(int[][] b, int lastMove) {
        board = b;
        value = 0;
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
//    	System.out.println("INITIALIZE CHILDREN\n");
//    	GameGUI.printBoard(copyBoard);
		GameGUI.board = GameGUI.copyBoard(copyBoard);
    	State[] states = new State[moves.length];
    	for(int i = 0; i < moves.length; i++){
    		GameGUI.updateBoard(moves[i], false);
//    		System.out.println("MOVED");
//    		GameGUI.printBoard(GameGUI.getBoard());
    		states[i] = new State(GameGUI.getBoard(), moves[i]);
//    		System.out.println("ORIGINAL");
//    		GameGUI.printBoard(copyBoard);

    		GameGUI.board = GameGUI.copyBoard(copyBoard);
//    		System.out.println("GAME");
//    		GameGUI.printBoard(GameGUI.board);
    		
//    		GameGUI.updateBoard(moves[i], false);
//    		System.out.println("MOVED BOARD");
//    		GameGUI.printBoard(GameGUI.getBoard());
//    		states[i] = new State(GameGUI.getBoard(), moves[i]);
//    		System.out.println("ORIGINAL BOARD");
//    		GameGUI.printBoard(copyBoard);
//    		GameGUI.setBoard(copyBoard);
//    		System.out.println("GAME BOARD");
//    		GameGUI.printBoard(GameGUI.getBoard());
    	}
    	children = states;
    }
    private Integer[] getPossibleMoves(){
    	ArrayList<Integer> moves = new ArrayList<Integer>();
    	if (GameGUI.checkMove(KeyEvent.VK_LEFT))
    		moves.add(37);
    	if (GameGUI.checkMove(KeyEvent.VK_UP))
    		moves.add(38);
    	if (GameGUI.checkMove(KeyEvent.VK_RIGHT))
    		moves.add(39);
    	if (GameGUI.checkMove(KeyEvent.VK_DOWN))
    		moves.add(40);
    	return moves.toArray(new Integer[moves.size()]);
    }

	@Override
	public int compareTo(State o) {
		return this.getValue() - o.getValue();
	}
}
