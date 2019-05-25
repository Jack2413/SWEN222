import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends javax.swing.JFrame {
	// private JFrame frame;
	private static final int PLAYER_BOARD_LEN = 3;
	private static final int PLAYER_BOARD_COL = 8;

	// public static final String NUM = "-?[1-24][0-24]*|0";
	public static final String ACTION = "move|create|undo|rotate|pass";
	public static final String ALPHL = "[A-X]";
	public static final String ALPHS = "[a-x]";
	public static final String DEGREE = "[1-4]";
	public static final String DIRECT = "up|right|down|left";

	private boolean WhiteTurn = true;
	private PlayerBoard WhitePlayerBoard;
	private PlayerBoard BlackPlayerBoard;
	
	JTextArea textOutputArea;
	GameBoard gameborad;
	

	private boolean parse(String command) {

		String[] commands = command.split(" ");

		boolean right = commands.length == 3 && commands[0].matches(ACTION)
				&& commands[1].matches(WhiteTurn ? ALPHL : ALPHS);
		//System.out.println(right);
		return commands[0].equals("undo") || commands[0].equals("pass") ? commands.length == 1
				: commands[0].equals("move")&&commands.length == 3 ? commands[2].matches(DIRECT)
						&& commands[1].matches(WhiteTurn ? ALPHL : ALPHS)
						: right ? commands[2].matches(DEGREE) : false;
	}

	private void fail(String command) {
		textOutputArea.append("\nSyntax Error:  " + command + "  <--- \nThe correcte format as follow <create/rotate> <letter> <1/2/3/4> / move <letter> <up/right/down/left> / undo / pass \nPlease try again : )");
		//String s = "syntax error";
		//System.out.println(s);

	}

	private void execute(String command) {
		String str = "\n" + command + "<-- Syntax Correct!";
		String suc = "\n" + command + "<-- Command Executed!";
		
		String[] commands = command.split(" ");
		String unfind = "\nError 404: " + commands[1] + "<--- index unfind!";
		PlayerBoard playerBoard = WhiteTurn?WhitePlayerBoard:BlackPlayerBoard;
	
		switch (commands[0]){
			case "create":
				Chess chess = playerBoard.get(commands[1]);
				playerBoard.remove(commands[1]);
				str+=gameborad.create(chess,commands[2],WhiteTurn)?suc:unfind;
				break;
			case "move":
				str+=gameborad.find(commands[1])!=null?
				gameborad.move(commands[1],commands[2])?
				suc:"\nCommand Error: " + command + "<--- The chess is moving out of the board":unfind;
				break;
			case "pass":
				WhiteTurn=!WhiteTurn;
				str+=suc;
				str += WhiteTurn?"\nWhite Turn:":"\nBlack Turn:";
				break;
			case "undo":
				break;
			case "rotate":
				str+=gameborad.find(commands[1])!=null?
				gameborad.rotate(commands[1],commands[2])?
				suc:"\nError 404: " + commands[1] + "<--- index unfind!":unfind;
				break;
			}
		
		textOutputArea.append(str);
		redraw();
	}

	public void redraw() {
		this.repaint();
	}

	public GUI() {
		super("Board Game");
		
		// Parse parse = new Parse(command);
		// Canvas canvas = new Canvas();
		gameborad = new GameBoard();
		//Controoler controoler = new Controoler();
		// gameborad.setLayout((LayoutManager) new BorderLayout());
		/*
		this.WhitePlayerBoard = new PlayerBoard(Color.LIGHT_GRAY, new Dimension(Chess.size * 4, Chess.size * 10 + Chess.size / 3),
				PLAYER_BOARD_COL, PLAYER_BOARD_LEN, Chess.Type.P1Chess);
		this.BlackPlayerBoard = new PlayerBoard(Color.LIGHT_GRAY, new Dimension(Chess.size * 4, Chess.size * 10 + Chess.size / 3),
				PLAYER_BOARD_COL, PLAYER_BOARD_LEN, Chess.Type.P2Chess);
				*/

		// Canvas left2 = new Canvas(Color.GREEN,new Dimension(300,300));
		textOutputArea = new JTextArea(3, 0);
		//textOutputArea.append("White Start:");
		textOutputArea.setLineWrap(true);
		textOutputArea.setWrapStyleWord(true);
		textOutputArea.setEditable(false);
		
		
		JTextField editTextArea = new JTextField(1);
		editTextArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String command = editTextArea.getText();
				editTextArea.setText("");
				if (parse(command)) execute(command);
				else fail(command);
				// Parse parse = new Parse(command);
				redraw();
			}
		});
		
		
		
		
		// editTextArea.setMaximumSize(new Dimension(0, 25));
		// Left.add(record,BorderLayout.CENTER);

		setLayout(new BorderLayout());
		//add(gameborad, BorderLayout.CENTER);
		// add(new JScrollPane (editTextArea), BorderLayout.AFTER_LAST_LINE);
		//add(WhitePlayerBoard, BorderLayout.WEST);
		// add(left2);
		//add(BlackPlayerBoard, BorderLayout.EAST);
		add(new JScrollPane(textOutputArea), BorderLayout.NORTH);
		add(editTextArea, BorderLayout.SOUTH);


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack();
		setResizable(false);
		setVisible(true);

	}

	public static void main(String[] args) {
		new GUI();
	}

}
