package SwordAndShell;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import model.*;
import resources.IconResources;

public class GUI extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GameBoard gameboard;
	PlayerBoard P1board;
	PlayerBoard P2board;
	CemeteryBoard P1Cemetery;
	CemeteryBoard P2Cemetery;
	PlayerBoard P1SelectedBoard;
	PlayerBoard P2SelectedBoard;

	JComponent BoardPanel;
	JComponent Player1;
	JComponent Player2;
	JComponent P1cemetery;
	JComponent P2cemetery;
	JComponent P1SelBoard;
	JComponent P2SelBoard;
	JComponent AnimatPanel;

	JPanel synthetic1;
	JPanel synthetic2;
	JPanel synthetic3;
	JPanel jp;

	CardLayout cardlayout1;
	CardLayout cardlayout2;
	CardLayout cardlayout3;

	JTextArea textOutputArea;
	JScrollPane textArea;
	boolean WhiteTurn = true;

	public GUI() {
		// super("Sword and Shell");
		// editTextArea.setMaximumSize(new Dimension(0, 25));
		// Left.add(record,BorderLayout.CENTER);
		gameboard = new GameBoard();
		P1board = new PlayerBoard(Chess.Type.P1Chess);
		P2board = new PlayerBoard(Chess.Type.P2Chess);
		P1Cemetery = new CemeteryBoard(Chess.Type.EMPTY);
		P2Cemetery = new CemeteryBoard(Chess.Type.EMPTY);
		P1SelectedBoard = new SelectedBoard(Chess.Type.EMPTY);
		P2SelectedBoard = new SelectedBoard(Chess.Type.EMPTY);
		setLayout(new BorderLayout());
		// JSplitPane splitPane1=new JSplitPane();
		// JSplitPane splitPane1=new
		// JSplitPane(JSplitPane.HORIZONTAL_SPLIT,false,new GameBoard(),new
		// GameBoard());

		BoardPanel = new BoardView(gameboard);
		AnimatPanel = new AnimationView(gameboard);
		Player1 = new PlayerView(P1board);
		Player2 = new PlayerView(P2board);
		P1cemetery = new PlayerView(P1Cemetery);
		P2cemetery = new PlayerView(P2Cemetery);
		P1SelBoard = new PlayerView(P1SelectedBoard);
		P2SelBoard = new PlayerView(P2SelectedBoard);

		cardlayout1 = new CardLayout();
		synthetic1 = new JPanel(cardlayout1);
		synthetic1.add(Player1, "1");
		synthetic1.add(P1SelBoard, "2");

		cardlayout2 = new CardLayout();
		synthetic2 = new JPanel(cardlayout2);
		synthetic2.add(Player2, "1");
		synthetic2.add(P2SelBoard, "2");

		synthetic3 = new JPanel(cardlayout2);
		synthetic3.add(BoardPanel);
		synthetic3.add(AnimatPanel);

		Player1.addMouseListener(new MouseController(P1board, this));
		Player2.addMouseListener(new MouseController(P2board, this));
		P1SelBoard.addMouseListener(new MouseController(P1SelectedBoard, this));
		P2SelBoard.addMouseListener(new MouseController(P2SelectedBoard, this));
		BoardPanel.addMouseListener(new MouseController(gameboard, this));
		AnimatPanel.addMouseListener(new MouseController(gameboard, this));
		this.addKeyListener(new KeyController(this));

		JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, synthetic1, P1cemetery);
		JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, synthetic2, P2cemetery);
		JSplitPane splitPane3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, splitPane1, synthetic3);
		JSplitPane splitPane4 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, splitPane3, splitPane2);
		splitPane1.setOneTouchExpandable(true);
		splitPane2.setOneTouchExpandable(true);
		splitPane3.setOneTouchExpandable(true);
		splitPane4.setOneTouchExpandable(true);

		splitPane1.setDividerLocation(1.0);
		splitPane2.setDividerLocation(1.0);
		splitPane3.setDividerLocation(1.0);
		splitPane4.setDividerLocation(1.0);

		// p1.setBackground(Color.gray);
		textOutputArea = new JTextArea(3, 0);
		textOutputArea.append("GameStarted\nWhite's trun:\n");
		textOutputArea.setLineWrap(true);
		textOutputArea.setWrapStyleWord(true);
		textOutputArea.setEditable(false);

		textArea = new JScrollPane(textOutputArea);
		textArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// DefaultCaret caret = (DefaultCaret)textOutputArea.getCaret();
		// caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JButton Meun = new JButton("Meun");
		JButton info = new JButton("Info");
		JButton quit = new JButton("Quit");
		JButton pass = new JButton("Pass");
		JButton undo = new JButton("Undo");
		JButton surrender = new JButton("Surrder");

		Meun.addActionListener((e) -> {
			cardlayout3.next(jp);
		});
		info.addActionListener((e) -> {

			getInfo();
		});
		quit.addActionListener((e) -> {
			System.exit(0);
		});
		pass.addActionListener((e) -> {
			WhiteTurn = !WhiteTurn;
			String str = ((WhiteTurn ? "White" : "Black") + "'s Turn\n");
			gameboard.reset();
			textOutputArea.append(str);
			requestFocusInWindow();
		});
		undo.addActionListener((e) -> {
			

		});
		surrender.addActionListener((e) -> {
			String str = ((WhiteTurn ? "Black" : "White") + "WIN \n");
			JOptionPane.showMessageDialog(this, str);
			cardlayout3.next(jp);

		});
		JToolBar jToolBar = new JToolBar("my tool bar");
		jToolBar.add(Meun);
		jToolBar.add(info);
		jToolBar.add(quit);
		jToolBar.add(pass);
		jToolBar.add(undo);
		jToolBar.add(surrender);
		jToolBar.setLayout(new GridLayout(1, 6));
		// jToolBar.setPreferredSize(new Dimension(100,Chess.size/3));
		jToolBar.setFocusable(false);

		// add(p1,BorderLayout.NORTH);
		add(jToolBar, BorderLayout.NORTH);
		add(splitPane4, BorderLayout.CENTER);
		add(textArea, BorderLayout.SOUTH);

		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// pack();

		setSize(Chess.size * 19, Chess.size * 12);
		setFocusable(true);
		// setResizable(false);
		setVisible(true);

		// SwingUtilities.updateComponentTreeUI(this);
		repaint();
		cardlayout3 = new CardLayout();
		GameMenu menu = new GameMenu(this);
		jp = new JPanel(cardlayout3);
		jp.add(menu, "1");
		jp.add(this, "2");

		JFrame f = new JFrame("Sword and Shell");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.add(jp);
		f.pack();
		f.setResizable(false);
		f.setVisible(true);
		f.setSize(Chess.size * 19, Chess.size * 12);

	}

	private String readtext() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("src/SwordAndShell/Info.txt"));
		String everything;
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
		} finally {
			br.close();
		}
		return everything;
	}

	// public void update(Observable arg0, Object arg1) {repaint();}

	public static void main(String[] s) {
		new GUI();

	}

	public void swapPanel() {
		if (WhiteTurn) {

			P1board.alpha = 0.0f;
			this.P1board.timer.start();
		
			cardlayout1.next(synthetic1);
			P1SelectedBoard.alpha = 0.0f;
			this.P1SelectedBoard.timer.start();
			
		} else {
			P2board.alpha = 0;
			this.P2board.timer.start();
			P2SelectedBoard.alpha = 0;
			this.P2SelectedBoard.timer.start();
			cardlayout2.next(synthetic2);
		}

	}

	public void swapGamePanel() {
		cardlayout2.next(synthetic3);
	}

	public void updatatext(String str) {
		textOutputArea.append(str);
		int height = 15;
		Point p = new Point();
		p.setLocation(0, this.textOutputArea.getLineCount() * height);
		this.textArea.getViewport().setViewPosition(p);

	}

	public void getInfo() {
		String str = "";
		try {
			str = readtext();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, str, "Info", JOptionPane.ERROR_MESSAGE, IconResources.Info.icon);
		textOutputArea.append(str);
		// System.exit(0);

	}
}