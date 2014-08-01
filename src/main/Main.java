package main;

public class Main {
	public static void main(String[] args){
		AI ai = new AI();
		GUI gui = new GUI(ai);
		ai.setGUI(gui);
	}
}
