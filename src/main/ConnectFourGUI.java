package main;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class ConnectFourGUI {
private final String CONFIGFILE = "config.txt";
private JLabel[][] slots;
private JFrame Framen;
private JTextField[] playerScore;
private ImageIcon[] playerIcon;
private ImageIcon[] scoreIcon;
private ImageIcon blank;
private JLabel nextPlayerIcon;
private Color scorelines = new Color(128, 64, 0);
//private Color background = new Color(220, 128, 5);
private Color background = new Color(255, 255, 0);
private String logoIcon;
private String blankfile;
private String[] iconFile;
private String[] scoreIconFile;
private JLabel errorMessage;
public final int NUMPLAYER = 2;
public int CURRENTPLAYERTURN = 0;
public final int NUMROW = 6;
public final int NUMCOL = 7;
public int MAXGAME;    //number of games needed to win the series
private final int PieceSize = 80;
private final int GridW = NUMCOL * PieceSize;
private final int GridH = NUMROW * PieceSize;
private final int Scoreboardw = 2 * PieceSize;
private final int ScoreboardH = GridH;
private final int LogoH = 2 * PieceSize;
private final int logow = GridW + Scoreboardw;
private final int FrameW = (int)(logow * 1.07);
private final int FrameH = (int)((LogoH + GridH) * 1.1);
private final Font HeaderFont = new Font ("Serif", Font.PLAIN, 18);
private final Font regularFont = new Font ("Serif", Font.BOLD, 16);
public ConnectFourGUI () {
	initConfig();
	InitilizeImageIcons();
	initSlots();
	createMainFrame();
}

	private void initConfig() {
	/* TO DO:  initialize the following variables with information read from the config file
	*         – MAXGAME
	*         – logoIcon
	*         – iconFile
	*/
	
		iconFile = new String[NUMPLAYER];
		scoreIconFile = new String[NUMPLAYER];
			MAXGAME = 2;
			logoIcon = "asdf";
			iconFile[0] = "Red.png";
			iconFile[1] = "Black.png";
			scoreIconFile[0] = "Red.png";
			scoreIconFile[1] = "Black.png";
			blankfile = "asdf";
	}
	
	// InitilizeImageIcons
	// Initialize playerIcon arrays with graphic files
	private void InitilizeImageIcons() {
		playerIcon = new ImageIcon[NUMPLAYER];
		scoreIcon = new ImageIcon[NUMPLAYER];
		for (int i = 0; i < NUMPLAYER; i++) {
			playerIcon[i] = new ImageIcon(iconFile[i]);
			scoreIcon[i] = new ImageIcon(scoreIconFile[i]);
		}
		blank = new ImageIcon(blankfile);
	}
	
	// initSlots
	// initialize the array of JLabels
	private void initSlots() {
		slots = new JLabel[NUMROW][NUMCOL];
		for (int i = 0; i < NUMROW; i++) {
			for (int j = 0; j < NUMCOL; j++) {
				slots[i][j] = new JLabel ();
				slots[i][j].setPreferredSize(new Dimension(PieceSize, PieceSize));
				slots[i][j].setHorizontalAlignment (SwingConstants.CENTER);
				slots[i][j].setBorder (new LineBorder (scorelines));
				final int r = i;
				final int c = j;
				slots[i][j].addMouseListener(new MouseListener(){
					public void mouseClicked(MouseEvent arg0) {
						// AI CALL HERE
//						if (CURRENTPLAYERTURN == 0){
							if (!setPiece(r, c)){
								errorMessage.setText("Invalid move! Try again.");
							} else{
								errorMessage.setText("");
							}
//						} else{
//							ai();
//						}
					}
					public void mouseEntered(MouseEvent arg0) {
						
					}
					public void mouseExited(MouseEvent arg0) {
					}
					public void mousePressed(MouseEvent arg0) {
					}
					public void mouseReleased(MouseEvent arg0) {	
					}
				});
			}
		}
	}
	private void ai(){
		
	}
	// Create a the grid
	private JPanel CreateGrid() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(GridW, GridH));
		panel.setBackground(background);
		panel.setLayout(new GridLayout(NUMROW, NUMCOL));
		for (int i = 0; i < NUMROW; i++) {
			for (int j = 0; j < NUMCOL; j++) {
				panel.add(slots[i][j]);
			}
		}
		return panel;
	}
	
	// createInfoPanel
	private JPanel createInfoPanel() {
	
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(Scoreboardw, ScoreboardH));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground (scorelines);
		
		// Create a panel for the scoreboard
		JPanel scorePanel = new JPanel();
		scorePanel.setBackground(scorelines);
		
		// Create the label to display "SCOREBOARD" heading
		JLabel scoreLabel = new JLabel ("SCOREBOARD", JLabel.CENTER);
		scoreLabel.setFont(HeaderFont);
		scoreLabel.setForeground(Color.white);
		scoreLabel.setAlignmentX (Component.CENTER_ALIGNMENT);
		//scoreLabel.setForeground(Color.white);
		
		// Create JLabels for players
		JLabel[] playerLabel = new JLabel[NUMPLAYER];
		for (int i = 0; i < NUMPLAYER; i++) {
			playerLabel[i] = new JLabel(scoreIcon[i]);
		}
	
		// Create the array of textfield for players’ score
		playerScore = new JTextField[NUMPLAYER];
		for (int i = 0; i < NUMPLAYER; i++) {
			playerScore[i] = new JTextField();
			playerScore[i].setFont(regularFont);
			playerScore[i].setText("0");
			playerScore[i].setForeground(Color.white);
			playerScore[i].setEditable(false);
			playerScore[i].setHorizontalAlignment (JTextField.CENTER);
			playerScore[i].setPreferredSize (new Dimension (Scoreboardw - PieceSize - 10, 30));
			playerScore[i].setBackground(scorelines);
		}
	
		scorePanel.add(scoreLabel);
		for (int i = 0; i < NUMPLAYER; i++) {
			scorePanel.add(playerLabel[i]);
			scorePanel.add(playerScore[i]);
		}
		
		JPanel errorPanel = new JPanel();
		errorPanel.setBackground(scorelines);
		JLabel errorHeader = new JLabel("NOTIFICATIONS", JLabel.CENTER);
		errorHeader.setFont(HeaderFont);
		errorHeader.setForeground(Color.white);
		errorHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		errorMessage = new JLabel("", JLabel.CENTER);
		errorMessage.setFont(HeaderFont);
		errorMessage.setForeground(Color.white);
		errorMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
		errorPanel.add(errorHeader);
		errorPanel.add(errorMessage);
		
		JPanel nextPanel = new JPanel();
		//nextPanel.setBackground(background);
		nextPanel.setBackground(scorelines);
		
		// Create the label to display "NEXT TURN" heading
		JLabel nextLabel = new JLabel ("NEXT TURN", JLabel.CENTER);
		nextLabel.setFont(HeaderFont);
		nextLabel.setForeground(Color.white);
		nextLabel.setAlignmentX (Component.CENTER_ALIGNMENT);
		//nextLabel.setForeground(Color.white);
		
		// Create the JLabel for the nextPlayer
		nextPlayerIcon = new JLabel();
		nextPlayerIcon.setAlignmentX(JLabel.CENTER_ALIGNMENT);
//		nextPlayerIcon.setIcon(scoreIcon[0]);
		setNextPlayer();
		nextPanel.add(nextLabel);
		nextPanel.add(nextPlayerIcon);
		
		panel.add(scorePanel);
		panel.add(errorPanel);
		panel.add(nextPanel);
		
		return panel;
	}
	
	// createMainFrame
	private void createMainFrame() {
	
		// Create the frame
		Framen = new JFrame ("Connect Four");
		JPanel panel = (JPanel)Framen.getContentPane();
		panel.setLayout (new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		// Create the panel for the logo
		JPanel logoPane = new JPanel();
		logoPane.setPreferredSize(new Dimension (logow, LogoH));
		JLabel logo = new JLabel();
		logo.setIcon(new ImageIcon(logoIcon));
		logoPane.add(logo);
		JLabel welcome = new JLabel("Connect 4");
		welcome.setFont(new Font ("Serif", Font.BOLD, 30));
		logoPane.add(welcome);
		// Create the bottom Panel which contains the play panel and info Panel
		JPanel bottomPane = new JPanel();
		bottomPane.setLayout(new BoxLayout(bottomPane,BoxLayout.X_AXIS));
		bottomPane.setPreferredSize(new Dimension(GridW + Scoreboardw, GridH));
		bottomPane.add(CreateGrid());
		bottomPane.add(createInfoPanel());
		
		// Add the logo and bottom panel to the main frame
		panel.add(logoPane);
		panel.add(bottomPane);
		
		Framen.setContentPane(panel);
		Framen.setSize(FrameW, FrameH);
		Framen.setResizable(false);
		Framen.setVisible(true);
	}
	
	//  Returns the column number of where the given JLabel is on
	
	public int getColumn(JLabel label) {
		int result = -1;
		for (int i = 0; i < NUMROW && result == -1; i++) {
			for (int j = 0; j < NUMCOL && result == -1; j++) {
				if (slots[i][j] == label) {
					result = j;
				}
			}
		}
		return result;
	}
	

	// Display the specified player icon on the specified slot
	
	public boolean setPiece(int row, int col) {
		for (int j = slots.length - 1; j >= 0; j--){
			if (slots[j][col].getIcon() == null){
				slots[j][col].setIcon(playerIcon[CURRENTPLAYERTURN]);
				slots[j][col].paint(slots[j][col].getGraphics());
				if (checkWin()){
					showWinnerMessage(CURRENTPLAYERTURN);
				} else if (checkTie()){
					showTieGameMessage();
				}
				CURRENTPLAYERTURN = 1 - (CURRENTPLAYERTURN);
				setNextPlayer();
				return true;
			}
		} 
		return false;
	}
	
	// Display the score on the textfield of the corresponding player
	
	public void setPlayerScore(int player, int score) {
		playerScore[player].setText(score+"");
	}
	
	// Display the appropriate player icon under"Next Turn"
	
	public void setNextPlayer() {
		nextPlayerIcon.setIcon(playerIcon[CURRENTPLAYERTURN]);
	}
	
	// Reset the game board (clear all the pieces on the game board)
	
	public void resetGameBoard() {
		for (int i = 0; i < NUMROW; i++) {
			for (int j = 0; j < NUMCOL; j++) {
				slots[i][j].setIcon(null);
			}
		}
	}
	public boolean checkTie(){
		for (int r = 0; r < slots.length; r++){
			for (int c = 0; c < slots[r].length; c++){
				if (slots[r][c].getIcon() == null){
					return false;
				}
			}
		}
		return true;
	}
	public boolean checkWin(){
		return (checkHorizontal() || checkVertical() || checkDiagonal() || checkDiagonalBack());
	}
	public boolean checkHorizontal(){
		for (int r = 0; r < slots.length; r++){
			for (int c = 0; c < slots[r].length - 3; c++){
				if (slots[r][c].getIcon() == playerIcon[CURRENTPLAYERTURN] &&
					slots[r][c+1].getIcon() == playerIcon[CURRENTPLAYERTURN] &&
					slots[r][c+2].getIcon() == playerIcon[CURRENTPLAYERTURN] &&
					slots[r][c+3].getIcon() == playerIcon[CURRENTPLAYERTURN]){
					return true;
				}
			}
		}
		return false;
	}
	public boolean checkVertical(){
		for (int r = 0; r < slots.length - 3; r++){
			for (int c = 0; c < slots[r].length; c++){
				if (slots[r][c].getIcon() == playerIcon[CURRENTPLAYERTURN] &&
						slots[r+1][c].getIcon() == playerIcon[CURRENTPLAYERTURN] &&
						slots[r+2][c].getIcon() == playerIcon[CURRENTPLAYERTURN] &&
						slots[r+3][c].getIcon() == playerIcon[CURRENTPLAYERTURN]){
						return true;
				}
			}
		}
		return false;
	}
	public boolean checkDiagonal(){
		for (int r = 0; r < slots.length - 3; r++){
			for (int c = 0; c < slots[r].length - 3; c++){
				if (slots[r][c].getIcon() == playerIcon[CURRENTPLAYERTURN] &&
					slots[r+1][c+1].getIcon() == playerIcon[CURRENTPLAYERTURN] &&
					slots[r+2][c+2].getIcon() == playerIcon[CURRENTPLAYERTURN] &&
					slots[r+3][c+3].getIcon() == playerIcon[CURRENTPLAYERTURN]){
						return true;
				}
			}
		}
		return false;
	}
	public boolean checkDiagonalBack(){
		for (int r = 0; r < slots.length - 3; r++){
			for (int c = slots[r].length - 1; c >= 3; c--){
				if (slots[r][c].getIcon() == playerIcon[CURRENTPLAYERTURN] &&
					slots[r+1][c-1].getIcon() == playerIcon[CURRENTPLAYERTURN] &&
					slots[r+2][c-2].getIcon() == playerIcon[CURRENTPLAYERTURN] &&
					slots[r+3][c-3].getIcon() == playerIcon[CURRENTPLAYERTURN]){
						return true;
				}
			}
		}
		return false;
	}
	// Display a pop up window displaying the message about a tie game
	
	public void showTieGameMessage(){
		JOptionPane.showMessageDialog(null, "Stalemate", "Tie Game", JOptionPane.PLAIN_MESSAGE, null);
		this.resetGameBoard();
	}
	
	// Display a window showing the winner of this game
	public void showWinnerMessage(int player){
		JOptionPane.showMessageDialog(null, " has won this game!", "Winner!", JOptionPane.PLAIN_MESSAGE, playerIcon[player]);
		int newScore = Integer.parseInt(playerScore[player].getText()) + 1;
		playerScore[player].setText(Integer.toString(newScore));
		if (newScore == MAXGAME){
			MatchWinner(player);
		}
		this.resetGameBoard();
	}
	
	// Display a window showing the winner of this match

	public void MatchWinner(int player){
		JOptionPane.showMessageDialog(null, " has won the match with " + MAXGAME + " wins", "Overall Winner!", JOptionPane.PLAIN_MESSAGE, playerIcon[player]);
		System.exit(0);
	}
}