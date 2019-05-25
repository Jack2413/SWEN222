package controllers;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.IllegalFormatCodePointException;

import gui.View;
import main.Game;

/**
 * This class is used to implement the key listener
 * */
public class KeyController implements KeyListener {

	private View view;

	public KeyController(View view) {
		this.view = view;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==27) {
			this.view.gameStop();
		}else if(e.getKeyCode()!=27&&!view.getGameStop()&&view.getGame().getPlayer().moveAble()) {
			generalAction(e);
		}
	}

	public void generalAction(KeyEvent e) {
		System.out.println(e.getKeyCode());
		try {
			switch (e.getKeyCode()) {
			case 87:
				view.getGame().move("up");
				break;
			case 65:
				view.getGame().move("left");
				break;
			case 83:
				view.getGame().move("down");
				break;
			case 68:
				view.getGame().move("right");
				break;
			case 69:
				view.getGame().tryBomb();
				break;
			case 81:
				view.getGame().tryPickEquipment();
				break;
			case 75:
				view.getGame().save();
			break;
			case 80:
				view.getGame().getPlayer().setHealth(99999);
				view.getGame().changeView();
				break;
			case 79:
				view.getGame().getPlayer().setHealth(100);
				view.getGame().changeView();
				break;
			}
		} catch (main.InvalidMove invalidMove) {
			System.out.println(invalidMove.getMessage());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
