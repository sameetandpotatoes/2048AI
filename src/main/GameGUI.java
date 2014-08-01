package main;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class GameGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String filename = "scores.dat";
    private static final String frequenciesFile = "frequencies.txt";
    public static int[][] board = new int[4][4];
//    public static int[][] prevBoard = new int[4][4];
    public static int currentScore = 0;
    private static boolean enableOutput = false;
    private static String fontStyle = "Arial";
    private static int highScore = readScore();
    private static boolean ai_running = false;
    private static final int AI_EASY = 3;
    private static final int AI_HARD = 4;
    private static final int AI_REALLY_HARD = 5;
    private static int aiDepth = AI_EASY;
//    public static int expectedScore = 0;
    public static int GOAL = 16384;
    public static Color NUMBER_COLOR = new Color(119, 110, 101);
    public static Color PANEL_BACKGROUND_COLOR = new Color(204, 192, 179);
    public static Color BORDER_COLOR = new Color(187, 173, 160);
    private static Map<String, Double> weights = new HashMap<String, Double>();
    private static Map<String, Integer> frequencies = new HashMap<String, Integer>();
    public GameGUI() {
        initComponents();
        updateText();
        mainGameFrame.setVisible(false);
        updateColors();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
            	updateHigh();
            }
        });
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setTitle("2048");
    }
    public static int getAIDepth(){
    	return aiDepth;
    }
    public static int getCurrentScore(){
    	return currentScore;
    }
    private static int readScore(){
		try {
	        String content = new String(Files.readAllBytes(Paths.get(filename)));
	        if (!content.equals("")){
	        	System.out.println("Read " + Integer.parseInt(content) + " from file.");
	        	return Integer.parseInt(content);
	        }
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("scores.dat file not found");
		} catch(IOException ie){
			throw new IllegalArgumentException("scores.dat file not found");
		}
		return 0;
    }
    public static int[][] getBoard(){
    	return board;
    }
    private void initComponents() {
    	loseLbl = new JLabel();
        mainGameFrame = new JFrame();
        jPanel0_0 = new JPanel();
        jLbl0_0 = new JLabel();
        titleLabel = new JLabel();
        jPanel0_1 = new JPanel();
        jLbl0_1 = new JLabel();
        jPanel0_2 = new JPanel();
        jLbl0_2 = new JLabel();
        jPanel0_3 = new JPanel();
        jLbl0_3 = new JLabel();
        jPanel1_0 = new JPanel();
        jLbl1_0 = new JLabel();
        jPanel1_1 = new JPanel();
        jLbl1_1 = new JLabel();
        jPanel1_2 = new JPanel();
        jLbl1_2 = new JLabel();
        jPanel1_3 = new JPanel();
        jLbl1_3 = new JLabel();
        jPanel2_0 = new JPanel();
        jLbl2_0 = new JLabel();
        jPanel2_1 = new JPanel();
        jLbl2_1 = new JLabel();
        jPanel2_2 = new JPanel();
        jLbl2_2 = new JLabel();
        jPanel2_3 = new JPanel();
        jLbl2_3 = new JLabel();
        jPanel3_0 = new JPanel();
        jLbl3_0 = new JLabel();
        jPanel3_1 = new JPanel();
        jLbl3_1 = new JLabel();
        jPanel3_2 = new JPanel();
        jLbl3_2 = new JLabel();
        jPanel3_3 = new JPanel();
        jLbl3_3 = new JLabel();
        tryAgain = new JButton();
        scorePanel = new JPanel();
        currentScoreLabel = new JLabel();
        scoreHeaderLabel = new JLabel();
        bestPanel = new JPanel();
        highScoreLabel = new JLabel();
        bestHighScoreLbl = new JLabel();
        AIButton = new JButton();

        titleLabel.setFont(new java.awt.Font("Arial", 1, 60)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(119, 110, 101));
        titleLabel.setText("2048");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("2048");
        setBackground(new Color(255, 255, 255));
        setCursor(new Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(null);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                arrowKeyHandler(evt);
            }
        });

        jPanel0_0.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel0_0.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel0_0.setToolTipText("");
        jPanel0_0.setFocusable(false);

        jLbl0_0.setFont(new Font(fontStyle, 1, 31)); // NOI18N
        jLbl0_0.setForeground(NUMBER_COLOR);
        jLbl0_0.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl0_0.setText("" + board[0][0]);

        GroupLayout jPanel0_0Layout = new GroupLayout(jPanel0_0);
        jPanel0_0.setLayout(jPanel0_0Layout);
        jPanel0_0Layout.setHorizontalGroup(
            jPanel0_0Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel0_0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl0_0, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel0_0Layout.setVerticalGroup(
            jPanel0_0Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel0_0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl0_0, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel0_1.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel0_1.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel0_1.setToolTipText("");
        jPanel0_1.setMaximumSize(null);

        jLbl0_1.setFont(new Font(fontStyle, 1, 31)); // NOI18N
        jLbl0_1.setForeground(NUMBER_COLOR);
        jLbl0_1.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl0_1.setText("" + board[0][1]);

        GroupLayout jPanel0_1Layout = new GroupLayout(jPanel0_1);
        jPanel0_1.setLayout(jPanel0_1Layout);
        jPanel0_1Layout.setHorizontalGroup(
            jPanel0_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel0_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl0_1, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel0_1Layout.setVerticalGroup(
            jPanel0_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel0_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl0_1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel0_2.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel0_2.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel0_2.setToolTipText("");
        jPanel0_2.setMaximumSize(null);

        jLbl0_2.setFont(new Font(fontStyle, 1, 31)); // NOI18N
        jLbl0_2.setForeground(NUMBER_COLOR);
        jLbl0_2.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl0_2.setText("" + board[0][2]);

        GroupLayout jPanel0_2Layout = new GroupLayout(jPanel0_2);
        jPanel0_2.setLayout(jPanel0_2Layout);
        jPanel0_2Layout.setHorizontalGroup(
            jPanel0_2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel0_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl0_2, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel0_2Layout.setVerticalGroup(
            jPanel0_2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel0_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl0_2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel0_3.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel0_3.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel0_3.setToolTipText("");
        jPanel0_3.setMaximumSize(null);

        jLbl0_3.setFont(new Font(fontStyle, 1, 31));
        jLbl0_3.setForeground(NUMBER_COLOR);
        jLbl0_3.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl0_3.setText("" + board[0][3]);

        GroupLayout jPanel0_3Layout = new GroupLayout(jPanel0_3);
        jPanel0_3.setLayout(jPanel0_3Layout);
        jPanel0_3Layout.setHorizontalGroup(
            jPanel0_3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel0_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl0_3, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel0_3Layout.setVerticalGroup(
            jPanel0_3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel0_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl0_3, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1_0.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel1_0.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel1_0.setToolTipText("");
        jPanel1_0.setMaximumSize(null);

        jLbl1_0.setFont(new Font(fontStyle, 1, 31));
        jLbl1_0.setForeground(NUMBER_COLOR);
        jLbl1_0.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl1_0.setText("" + board[1][0]);

        GroupLayout jPanel1_0Layout = new GroupLayout(jPanel1_0);
        jPanel1_0.setLayout(jPanel1_0Layout);
        jPanel1_0Layout.setHorizontalGroup(
            jPanel1_0Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl1_0, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1_0Layout.setVerticalGroup(
            jPanel1_0Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl1_0, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1_1.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel1_1.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel1_1.setToolTipText("");
        jPanel1_1.setMaximumSize(null);

        jLbl1_1.setFont(new Font(fontStyle, 1, 31));
        jLbl1_1.setForeground(NUMBER_COLOR);
        jLbl1_1.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl1_1.setText("" + board[1][1]);

        GroupLayout jPanel1_1Layout = new GroupLayout(jPanel1_1);
        jPanel1_1.setLayout(jPanel1_1Layout);
        jPanel1_1Layout.setHorizontalGroup(
            jPanel1_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl1_1, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1_1Layout.setVerticalGroup(
            jPanel1_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl1_1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1_2.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel1_2.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel1_2.setToolTipText("");
        jPanel1_2.setMaximumSize(null);

        jLbl1_2.setFont(new Font(fontStyle, 1, 31)); // NOI18N
        jLbl1_2.setForeground(NUMBER_COLOR);
        jLbl1_2.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl1_2.setText("" + board[1][2]);

        GroupLayout jPanel1_2Layout = new GroupLayout(jPanel1_2);
        jPanel1_2.setLayout(jPanel1_2Layout);
        jPanel1_2Layout.setHorizontalGroup(
            jPanel1_2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl1_2, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1_2Layout.setVerticalGroup(
            jPanel1_2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl1_2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1_3.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel1_3.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel1_3.setToolTipText("");
        jPanel1_3.setMaximumSize(null);

        jLbl1_3.setFont(new Font(fontStyle, 1, 31)); // NOI18N
        jLbl1_3.setForeground(NUMBER_COLOR);
        jLbl1_3.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl1_3.setText("" + board[1][3]);

        GroupLayout jPanel1_3Layout = new GroupLayout(jPanel1_3);
        jPanel1_3.setLayout(jPanel1_3Layout);
        jPanel1_3Layout.setHorizontalGroup(
            jPanel1_3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl1_3, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1_3Layout.setVerticalGroup(
            jPanel1_3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl1_3, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2_0.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel2_0.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel2_0.setToolTipText("");
        jPanel2_0.setMaximumSize(null);

        jLbl2_0.setFont(new Font(fontStyle, 1, 31)); // NOI18N
        jLbl2_0.setForeground(NUMBER_COLOR);
        jLbl2_0.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl2_0.setText("" + board[2][0]);

        GroupLayout jPanel2_0Layout = new GroupLayout(jPanel2_0);
        jPanel2_0.setLayout(jPanel2_0Layout);
        jPanel2_0Layout.setHorizontalGroup(
            jPanel2_0Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2_0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl2_0, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2_0Layout.setVerticalGroup(
            jPanel2_0Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2_0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl2_0, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2_1.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel2_1.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel2_1.setToolTipText("");
        jPanel2_1.setMaximumSize(null);

        jLbl2_1.setFont(new Font(fontStyle, 1, 31)); // NOI18N
        jLbl2_1.setForeground(NUMBER_COLOR);
        jLbl2_1.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl2_1.setText("" + board[2][1]);

        GroupLayout jPanel2_1Layout = new GroupLayout(jPanel2_1);
        jPanel2_1.setLayout(jPanel2_1Layout);
        jPanel2_1Layout.setHorizontalGroup(
            jPanel2_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl2_1, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2_1Layout.setVerticalGroup(
            jPanel2_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl2_1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2_2.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel2_2.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel2_2.setToolTipText("");
        jPanel2_2.setMaximumSize(null);

        jLbl2_2.setFont(new Font(fontStyle, 1, 31)); // NOI18N
        jLbl2_2.setForeground(NUMBER_COLOR);
        jLbl2_2.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl2_2.setText("" + board[2][2]);

        GroupLayout jPanel2_2Layout = new GroupLayout(jPanel2_2);
        jPanel2_2.setLayout(jPanel2_2Layout);
        jPanel2_2Layout.setHorizontalGroup(
            jPanel2_2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl2_2, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2_2Layout.setVerticalGroup(
            jPanel2_2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl2_2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2_3.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel2_3.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel2_3.setToolTipText("");
        jPanel2_3.setMaximumSize(null);

        jLbl2_3.setFont(new Font(fontStyle, 1, 31)); // NOI18N
        jLbl2_3.setForeground(NUMBER_COLOR);
        jLbl2_3.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl2_3.setText("" + board[2][3]);

        GroupLayout jPanel2_3Layout = new GroupLayout(jPanel2_3);
        jPanel2_3.setLayout(jPanel2_3Layout);
        jPanel2_3Layout.setHorizontalGroup(
            jPanel2_3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl2_3, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2_3Layout.setVerticalGroup(
            jPanel2_3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl2_3, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3_0.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel3_0.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel3_0.setToolTipText("");
        jPanel3_0.setMaximumSize(null);

        jLbl3_0.setFont(new Font(fontStyle, 1, 31)); // NOI18N
        jLbl3_0.setForeground(NUMBER_COLOR);
        jLbl3_0.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl3_0.setText("" + board[3][0]);

        GroupLayout jPanel3_0Layout = new GroupLayout(jPanel3_0);
        jPanel3_0.setLayout(jPanel3_0Layout);
        jPanel3_0Layout.setHorizontalGroup(
            jPanel3_0Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3_0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl3_0, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3_0Layout.setVerticalGroup(
            jPanel3_0Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3_0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl3_0, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3_1.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel3_1.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel3_1.setToolTipText("");
        jPanel3_1.setMaximumSize(null);

        jLbl3_1.setFont(new Font(fontStyle, 1, 31)); // NOI18N
        jLbl3_1.setForeground(NUMBER_COLOR);
        jLbl3_1.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl3_1.setText("" + board[3][1]);

        GroupLayout jPanel3_1Layout = new GroupLayout(jPanel3_1);
        jPanel3_1.setLayout(jPanel3_1Layout);
        jPanel3_1Layout.setHorizontalGroup(
            jPanel3_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl3_1, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3_1Layout.setVerticalGroup(
            jPanel3_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl3_1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3_2.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel3_2.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel3_2.setToolTipText("");
        jPanel3_2.setMaximumSize(null);

        jLbl3_2.setFont(new Font(fontStyle, 1, 31)); // NOI18N
        jLbl3_2.setForeground(NUMBER_COLOR);
        jLbl3_2.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl3_2.setText("" + board[3][2]);

        GroupLayout jPanel3_2Layout = new GroupLayout(jPanel3_2);
        jPanel3_2.setLayout(jPanel3_2Layout);
        jPanel3_2Layout.setHorizontalGroup(
            jPanel3_2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl3_2, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3_2Layout.setVerticalGroup(
            jPanel3_2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl3_2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3_3.setBackground(PANEL_BACKGROUND_COLOR);
        jPanel3_3.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        jPanel3_3.setToolTipText("");
        jPanel3_3.setMaximumSize(null);

        jLbl3_3.setFont(new Font(fontStyle, 1, 31)); // NOI18N
        jLbl3_3.setForeground(NUMBER_COLOR);
        jLbl3_3.setHorizontalAlignment(SwingConstants.CENTER);
        jLbl3_3.setText("" + board[3][3]);

        GroupLayout jPanel3_3Layout = new GroupLayout(jPanel3_3);
        jPanel3_3.setLayout(jPanel3_3Layout);
        jPanel3_3Layout.setHorizontalGroup(
            jPanel3_3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl3_3, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3_3Layout.setVerticalGroup(
            jPanel3_3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbl3_3, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scorePanel.setBackground(new Color(197, 173, 160));
        scorePanel.setBorder(new LineBorder(BORDER_COLOR, 5, true));
        scorePanel.setMaximumSize(new Dimension(120, 61));
        scorePanel.setMinimumSize(new Dimension(120, 61));
        scorePanel.setName(""); // NOI18N
        scorePanel.setPreferredSize(new Dimension(120, 61));

        currentScoreLabel.setFont(new Font(fontStyle, 1, 24)); // NOI18N
        currentScoreLabel.setForeground(new Color(255, 255, 255));
        currentScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentScoreLabel.setText("Score: " + currentScore);

        scoreHeaderLabel.setFont(new Font(fontStyle, 1, 14)); // NOI18N
        scoreHeaderLabel.setForeground(new Color(238, 228, 218));
        scoreHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreHeaderLabel.setText("SCORE");
        scoreHeaderLabel.setMaximumSize(new Dimension(50, 17));
        scoreHeaderLabel.setMinimumSize(new Dimension(50, 17));
        scoreHeaderLabel.setPreferredSize(new Dimension(50, 17));

        GroupLayout scorePanelLayout = new GroupLayout(scorePanel);
        scorePanel.setLayout(scorePanelLayout);
        scorePanelLayout.setHorizontalGroup(
            scorePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(currentScoreLabel, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 110, Short.MAX_VALUE)
            .addComponent(scoreHeaderLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        scorePanelLayout.setVerticalGroup(
            scorePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(scorePanelLayout.createSequentialGroup()
                .addComponent(scoreHeaderLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(currentScoreLabel, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
        );

        bestPanel.setBackground(new Color(197, 173, 160));
        bestPanel.setBorder(new LineBorder(BORDER_COLOR, 5, true));
        bestPanel.setMaximumSize(new Dimension(120, 61));
        bestPanel.setMinimumSize(new Dimension(120, 61));
        bestPanel.setPreferredSize(new Dimension(120, 61));

        highScoreLabel.setFont(new Font(fontStyle, 1, 24)); // NOI18N
        highScoreLabel.setForeground(new Color(255, 255, 255));
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        highScoreLabel.setText("Best: " + highScore);

        bestHighScoreLbl.setFont(new Font(fontStyle, 1, 14)); // NOI18N
        bestHighScoreLbl.setForeground(new Color(238, 228, 218));
        bestHighScoreLbl.setHorizontalAlignment(SwingConstants.CENTER);
        bestHighScoreLbl.setText("BEST");
        bestHighScoreLbl.setMaximumSize(new Dimension(37, 17));
        bestHighScoreLbl.setMinimumSize(new Dimension(37, 17));
        bestHighScoreLbl.setName(""); // NOI18N
        bestHighScoreLbl.setPreferredSize(new Dimension(37, 17));

        GroupLayout bestPanelLayout = new GroupLayout(bestPanel);
        bestPanel.setLayout(bestPanelLayout);
        bestPanelLayout.setHorizontalGroup(
            bestPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(highScoreLabel, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(bestHighScoreLbl, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        bestPanelLayout.setVerticalGroup(
            bestPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, bestPanelLayout.createSequentialGroup()
                .addComponent(bestHighScoreLbl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(highScoreLabel, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        loseLbl.setText("Game Over!");
        loseLbl.setVisible(false);
        
        tryAgain.setText("Restart");
        tryAgain.setFocusable(false);
        tryAgain.setRequestFocusEnabled(false);
        tryAgain.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				updateHigh();
				restart();
				updateText();
				updateColors();
				AIButton.setText("Start AI");
			}
        });
        AIButton.setText("Start AI");
        AIButton.setFocusable(false);
        AIButton.setRequestFocusEnabled(false);
        AIButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!ai_running){
					AIButton.setText("Stop AI");
					ai_running = true;
					ai();
				} else{
					AIButton.setText("Start AI");
					ai_running = false;
				}
			}
        });
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scorePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bestPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2_0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel2_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel2_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1_0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel1_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel1_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel0_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3_0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel3_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel3_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel3_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel0_0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel0_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel0_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGap(18, 18, 18)
                        .addComponent(AIButton)
                        .addGap(18, 18, 18)
                        .addComponent(tryAgain)
                        .addGap(18, 18, 18)
                        .addComponent(loseLbl))
                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                	.addComponent(bestPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(scorePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(AIButton)
                    .addGap(18, 18, 18)
                    .addComponent(tryAgain)
                    .addGap(18, 18, 18)
                    .addComponent(loseLbl))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel0_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel0_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel0_0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel0_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1_0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2_0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3_3, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3_0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        pack();
    }

    private void arrowKeyHandler(java.awt.event.KeyEvent evt) {
        int id = evt.getKeyCode();
        handleMoves(id, false);
    }
    private void handleMoves(int id, boolean ai){
         updateBoard(id, true, ai);
         if (currentScore > highScore) {
             highScore = currentScore;
         }
         updateText(); //Score changed, so text needs to be updated
         if (checkWin()) {
             mainGameFrame.setVisible(true);
         }
         if (checkLoss(ai)) {
        	 loseLbl.setVisible(true);
         }
         updateColors(); //If tiles changed numbers, we need to update the colors
         if (ai_running)
        	 ai();
    }
    private static void getInfoFromContent(String content){
        Scanner scanner = new Scanner(content);
        while (scanner.hasNextLine()){
        	String line = scanner.nextLine();
        	String[] keyValuePair = line.split(": ");
        	frequencies.put(keyValuePair[0], Integer.parseInt(keyValuePair[1]));
        }
        scanner.close();
    }
    public static void updateFrequencies(){
    	try {
	        String content = new String(Files.readAllBytes(Paths.get(frequenciesFile)));
	        getInfoFromContent(content);
	        getInfoFromBoard();
	        frequencies.put("Total", frequencies.get("Total")+1);
	        double p512 = Math.min(100.0, ((double) (frequencies.get("512"))/frequencies.get("Total")) * 100.0);
        	double p1024 = Math.min(100.0, ((double) (frequencies.get("1024"))/frequencies.get("Total")) * 100.0);
        	double p2048 = Math.min(100.0, ((double) (frequencies.get("2048"))/frequencies.get("Total")) * 100.0);
        	double p4096 = Math.min(100.0, ((double) (frequencies.get("4096"))/frequencies.get("Total")) * 100.0);
        	if (p512 == 100.0)
        		frequencies.put("512", frequencies.get("Total"));
        	if (p1024 == 100.0)
        		frequencies.put("1024", frequencies.get("Total"));
        	if (p2048 == 100.0)
        		frequencies.put("2048", frequencies.get("Total"));
        	if (p4096 == 100.0)
        		frequencies.put("4096", frequencies.get("Total"));
	        FileWriter fStream = new FileWriter(frequenciesFile, true);
	        BufferedWriter fbw = new BufferedWriter(fStream);
	        Files.write(Paths.get("./"+frequenciesFile), ("").getBytes());
	        fbw.append(("512: " + frequencies.get("512")));
	        fbw.newLine();
	        fbw.append(("1024: " + frequencies.get("1024")));
	        fbw.newLine();
	        fbw.append(("2048: " + frequencies.get("2048")));
	        fbw.newLine();
	        fbw.append(("4096: " + frequencies.get("4096")));
	        fbw.newLine();
	        fbw.append(("Total: " + frequencies.get("Total")));
	        fbw.newLine();
	        fbw.close();
        	System.out.println("512: " + frequencies.get("512") + " times / " + p512 + "%");
        	System.out.println("1024: "+ frequencies.get("1024") + " times / " + p1024 + "%");
        	System.out.println("2048: "+ frequencies.get("2048") + " times / " + p2048 + "%");
        	System.out.println("4096: "+ frequencies.get("4096") + " times / " + p4096 + "%");
        	System.out.println("Total: "+ frequencies.get("Total"));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("frequencies.dat file not found");
		} catch(IOException ie){
			throw new IllegalArgumentException("frequencies.dat file not found");
		}
    }
    public static void printBoard(int[][] board) {
        for (int[] a: board) {
            for (int i : a)
                System.out.print(i + " ");
            System.out.println();
        }
        System.out.println();
    }
    public static void updateBoard(int n, boolean real, boolean ai) {
    	int[][] before = copyBoard(board);
    	if (n == KeyEvent.VK_LEFT) {
            pushLeft();
            //check tiles with x > 0, from left to right to see if they collapse
            joinTiles(real);
            pushLeft();
            if (!ai)
            	addNewTile(before);
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
    }
    public static void addNewTile(int[][] before){
    	if (!Arrays.deepEquals(before, board)) {
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
    public static void setBoard(int[][] originalBoard){
    	board = originalBoard;
    }
    public static void joinTiles(boolean real){
    	for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
            	//If two consecutive numbers found, add them together
                if (board[i][j-1] == board[i][j]) {
                    board[i][j-1] *= 2;
                    //If we are checking if the user could hypothetically move, then 
                    //this will not run
                    if (real) {
                        currentScore += board[i][j-1];
                    }
                    board[i][j] = 0; //temporarily leave a 0 which will go away when we push again
                }
            }
        }
    }
    public static void pushLeft() {
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
    public static void rotateCW() {
        int[][] rotated = new int[4][4];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                rotated[c][3-r] = board[r][c];
            }
        }
        board = rotated;
    }
    public static void rotateCCW() {
        int[][] rotated = new int[4][4];
        for (int r = 0; r < 4; r++) {
        	for (int c = 0; c < 4; c++) {
        		rotated[3-c][r] = board[r][c];
        	}
        }
        board = rotated;
    }
    public static void restart() {
    	if (loseLbl != null)
    		loseLbl.setVisible(false);
    	
            currentScore = 0;
            aiDepth = AI_EASY;
            ai_running = false;
            board = new int[4][4];
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
            weights.put("Smooth",0.1);
            weights.put("Mono",1.0);
            weights.put("Empty",2.7);
            weights.put("Max",1.0);
            resetFrequencies();
    }
    private static void resetFrequencies(){
    	frequencies.clear();
    	frequencies.put("512", 0);
        frequencies.put("1024", 0);
        frequencies.put("2048", 0);
        frequencies.put("4096", 0);
        frequencies.put("Total", 0);
    }
    public static int[][] copyBoard(int[][] board) {
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
    private static void updateText() {
        jLbl0_0.setText(board[0][0] == 0 ? "": "" + board[0][0]);
        jLbl0_1.setText(board[0][1] == 0 ? "": "" + board[0][1]);
        jLbl0_2.setText(board[0][2] == 0 ? "": "" + board[0][2]);
        jLbl0_3.setText(board[0][3] == 0 ? "": "" + board[0][3]);
        jLbl1_0.setText(board[1][0] == 0 ? "": "" + board[1][0]);
        jLbl1_1.setText(board[1][1] == 0 ? "": "" + board[1][1]);
        jLbl1_2.setText(board[1][2] == 0 ? "": "" + board[1][2]);
        jLbl1_3.setText(board[1][3] == 0 ? "": "" + board[1][3]);
        jLbl2_0.setText(board[2][0] == 0 ? "": "" + board[2][0]);
        jLbl2_1.setText(board[2][1] == 0 ? "": "" + board[2][1]);
        jLbl2_2.setText(board[2][2] == 0 ? "": "" + board[2][2]);
        jLbl2_3.setText(board[2][3] == 0 ? "": "" + board[2][3]);
        jLbl3_0.setText(board[3][0] == 0 ? "": "" + board[3][0]);
        jLbl3_1.setText(board[3][1] == 0 ? "": "" + board[3][1]);
        jLbl3_2.setText(board[3][2] == 0 ? "": "" + board[3][2]);
        jLbl3_3.setText(board[3][3] == 0 ? "": "" + board[3][3]);
        currentScoreLabel.setText("" + currentScore);
        highScoreLabel.setText("" + highScore);
    }
    private static void updateColors() {
        JPanel[] numberPanelContainers = {jPanel0_0, jPanel0_1, jPanel0_2, jPanel0_3, jPanel1_0, jPanel1_1, jPanel1_2, jPanel1_3, jPanel2_0, jPanel2_1, jPanel2_2, jPanel2_3, jPanel3_0, jPanel3_1, jPanel3_2, jPanel3_3};
        JLabel[] numbers = {jLbl0_0, jLbl0_1, jLbl0_2, jLbl0_3, jLbl1_0, jLbl1_1, jLbl1_2, jLbl1_3, jLbl2_0, jLbl2_1, jLbl2_2, jLbl2_3, jLbl3_0, jLbl3_1, jLbl3_2, jLbl3_3};
        for (int i = 0; i < numberPanelContainers.length; i++) {
            if (numbers[i].getText().equals("")) {
            	numberPanelContainers[i].setBackground(PANEL_BACKGROUND_COLOR);
            	numbers[i].setForeground(NUMBER_COLOR);
            }
            else if (numbers[i].getText().equals("2")) {
            	numberPanelContainers[i].setBackground(new Color(238, 228, 218));
            	numbers[i].setForeground(NUMBER_COLOR);
            }
            else if (numbers[i].getText().equals("4")) {
            	numberPanelContainers[i].setBackground(new Color(237, 224, 200));
            	numbers[i].setForeground(NUMBER_COLOR);
            }
            else if (numbers[i].getText().equals("8")){
            	numberPanelContainers[i].setBackground(new Color(242, 177, 121));
            	numbers[i].setForeground(new Color(255, 255, 255));
            }
            else if (numbers[i].getText().equals("16")){
            	numberPanelContainers[i].setBackground(new Color(245, 149, 99));
            	numbers[i].setForeground(new Color(255, 255, 255));
            }
            else if (numbers[i].getText().equals("32")){
            	numberPanelContainers[i].setBackground(new Color(246, 124, 95));
            	numbers[i].setForeground(new Color(255, 255, 255));
            }
            else if (numbers[i].getText().equals("64")){
            	numberPanelContainers[i].setBackground(new Color(246, 94, 59));
            	numbers[i].setForeground(new Color(255, 255, 255));
            }
            else if (numbers[i].getText().equals("128")){
            	numberPanelContainers[i].setBackground(new Color(237, 207, 114));
            	numbers[i].setForeground(new Color(255, 255, 255));
            }
            else if (numbers[i].getText().equals("256")){
            	numberPanelContainers[i].setBackground(new Color(237, 204, 97));
            	numbers[i].setForeground(new Color(255, 255, 255));
            }
            else if (numbers[i].getText().equals("512")){
            	numberPanelContainers[i].setBackground(new Color(237, 200, 80));
            	numbers[i].setForeground(new Color(255, 255, 255));
            }
            else if (numbers[i].getText().equals("1024")){
            	numberPanelContainers[i].setBackground(new Color(237, 197, 63));
            	numbers[i].setForeground(new Color(255, 255, 255));
            }
            else if (numbers[i].getText().equals("2048")){
            	numberPanelContainers[i].setBackground(new Color(237, 194, 46));
            	numbers[i].setForeground(new Color(255, 255, 255));
            }
            else {
            	numberPanelContainers[i].setBackground(new Color(0, 0, 0));
            	numbers[i].setForeground(new Color(255, 255, 255));
            }
        }
    }
    public static boolean checkWin() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == GOAL) 
                	return true;
            }
        }
        return false;
    }
    public static boolean checkMove(int keyEvent, boolean ai){
    	int[][] before = copyBoard(board);
    	boolean change = true;
    	updateBoard(keyEvent, false, ai);
    	if (Arrays.deepEquals(before, board))
    		change = false;
    	board = before;
    	return change;
    }
    public static boolean checkLoss(boolean ai) {
    	return !(	checkMove(KeyEvent.VK_LEFT, ai) ||
    				checkMove(KeyEvent.VK_UP, ai) ||
    				checkMove(KeyEvent.VK_RIGHT, ai) ||
    				checkMove(KeyEvent.VK_DOWN, ai));
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
    private int updateHigh() {
        try {
        	if (readScore() > highScore)
        		highScore = readScore();
        	System.out.println("Writing " + highScore + " to file");
            Files.write(Paths.get("./" + filename), Integer.toString(highScore).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
        return highScore;
    }
    private void ai(){
      new SwingWorker<Integer, Integer>() {
    	  protected Integer doInBackground() {
			int[][] copyBoard = copyBoard(board);
			State currentState = new State(board, 0);
    		createGameTree(currentState, aiDepth, copyBoard);
    		board = copyBoard(copyBoard);
    		
    		minimax(currentState);
    		int keyStroke = currentState.getChildren()[currentState.getChildren().length - 1].getLastMove();
    		boolean movedAlready = false;
    		updateBoard(keyStroke, true, true);
    		updateText();
    		updateColors();
    		if (!movedAlready){
    			addNewTile(copyBoard);
    			updateText();
    			updateColors();
    			if (enableOutput){
        			System.out.println(currentState.getChildren()[currentState.getChildren().length - 1].getValue());
        			System.out.println("Moved " + keyStroke);
        		}
    			if (ai_running){
    				ai();
    			}
    		}
    		int countEmpty = countEmpty(board);
    		
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
    private static int countEmpty(int[][] board){
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
    public static void createGameTree(State s, int d, int[][] stateBoard) {
    	if(d == 0){
    		return;
    	}
    	if(s.getChildren().length == 0){
    		s.initializeChildren(stateBoard, d);
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
    	int countEmpty = countEmpty(board);
    	int[] maxLocations = highestTile(board);
    	double smoothness =(smoothness(board) * weights.get("Smooth"));
    	double mono = monotonocity(board) * weights.get("Mono"); 
    	double empty = Math.log(countEmpty) * weights.get("Empty");
    	double max = maxLocations[2] * weights.get("Max");
    	double score = smoothness + mono + empty + max;
    	if (enableOutput)
    		System.out.println((int) Math.round(score));
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
    private static int monotonocity(int[][] board){
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
    public static void main(String args[]) {
        restart();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameGUI().setVisible(true);
            }
        });
    	
//        java.awt.EventQueue.invokeLater(new Runnable() {
//        	public void run(){
//        		GUI gui = new GUI();
//            	AI ai = new AI(gui);
//            	gui.setAI(ai);
//            	gui.setVisible(true);
//        	}
//        });
    }

    private static JButton AIButton;
    private JFrame mainGameFrame;
    private static JLabel jLbl0_0;
    private static JLabel jLbl2_1;
    private static JLabel jLbl2_2;
    private static JLabel jLbl2_3;
    private static JLabel jLbl3_0;
    private static JLabel jLbl3_1;
    private static JLabel jLbl3_2;
    private static JLabel jLbl3_3;
    private static JLabel currentScoreLabel;
    private static JLabel highScoreLabel;
    private static JLabel scoreHeaderLabel;
    private static JLabel jLbl0_1;
    private static JLabel bestHighScoreLbl;
    private static JLabel jLbl0_2;
    private static JLabel jLbl0_3;
    private static JLabel jLbl1_0;
    private static JLabel jLbl1_1;
    private static JLabel jLbl1_2;
    private static JLabel jLbl1_3;
    private static JLabel jLbl2_0;
    private static JPanel jPanel0_3;
    private static JPanel jPanel1_0;
    private static JPanel jPanel1_1;
    private static JPanel jPanel1_2;
    private static JPanel jPanel1_3;
    private static JPanel scorePanel;
    private static JPanel jPanel2_0;
    private static JPanel jPanel2_1;
    private static JPanel jPanel2_2;
    private static JPanel jPanel2_3;
    private static JPanel jPanel3_0;
    private static JPanel jPanel3_1;
    private static JPanel jPanel3_2;
    private static JPanel jPanel3_3;
    private JPanel bestPanel;
    private static JPanel jPanel0_0;
    private static JPanel jPanel0_1;
    private static JPanel jPanel0_2;
    private static JLabel titleLabel;
    private static JButton tryAgain;
    private static JLabel loseLbl;
}
