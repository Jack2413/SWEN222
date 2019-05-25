package SwordAndShell;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Chess;

public class KeyController implements KeyListener {

	GUI gui;

	public KeyController(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Chess selected = gui.gameboard.getSelected();
		if (selected != null&&!selected.moved) {
			String chessName = selected.name + "";
			System.out.println(e.getKeyCode());
			switch (e.getKeyCode()) {
			case 38:
				gui.gameboard.move(chessName, "up");
				return;
			case 37:
				gui.gameboard.move(chessName, "left");
				return;
			case 40:
				gui.gameboard.move(chessName, "down");
				return;
			case 39:
				gui.gameboard.move(chessName, "right");
				return;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println(e.getKeyChar());

	}
}
