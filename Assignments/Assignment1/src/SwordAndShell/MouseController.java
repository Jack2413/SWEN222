package SwordAndShell;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


import model.Chess;
import model.GameBoard;

public class MouseController implements MouseMotionListener, MouseListener {

	GameBoard board;
	GUI gui;
	Chess selected;

	public MouseController(GameBoard board, GUI gui) {
		this.board = board;
		this.gui = gui;

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		selected = gui.gameboard.getSelected();
		 

		if (board.selecte(x, y, gui.WhiteTurn)) {
			if (!(e.getSource() instanceof AnimationView)) {
				if (!board.getSelected().moved) {
					this.selected = board.getSelected();
					String action = selected.check(x, y);
					if (!action.equals("rotate")&&board==gui.gameboard) {
						board.move(selected.name + "", action);
					}

				}
			} else if (!board.getSelected().moved) {
				board.getSelected().rotate("2");
				System.out.println("rotate");

			}
		} else if (e.getSource() instanceof AnimationView) {

			gui.swapGamePanel();
			this.selected.moved = true;
			board.reaction(selected.name+"");
		}
	
		update();
	}

	private void update() {
		gui.P1Cemetery.addDeaths(gui.gameboard.getdeathlist1());
		gui.P2Cemetery.addDeaths(gui.gameboard.getdeathlist2());
		gui.gameboard.checkWin();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		String str = "";
		if (e.getClickCount() == 2 && board.selecte(x, y, gui.WhiteTurn)) {

			if (board == gui.P1SelectedBoard) {
				if (gui.gameboard.create(selected, gui.WhiteTurn)) {
					gui.gameboard.reaction(selected.name+"");
					//gui.P1Cemetery.addDeaths(gui.gameboard.getdeathlist());
					gui.P1board.remove(selected.name + "");
					str = "P1Create : " + selected.name + "\n";

					// this.selected=null;
				}
				gui.swapPanel();
			} else if (board == gui.P2SelectedBoard) {
				if (gui.gameboard.create(selected, gui.WhiteTurn)) {
					gui.gameboard.reaction(selected.name+"");
					//gui.P2Cemetery.addDeaths(gui.gameboard.getdeathlist());
					gui.P2board.remove(selected.name + "");
					str = "P2Create : " + selected.name + "\n";

					// this.selected=null;
				}
				gui.swapPanel();
			} else if (board == gui.P1board && gui.WhiteTurn) {

				this.selected = board.getSelected();
				gui.P1SelectedBoard.add(selected);
				str = "P1 in creation panel: " + selected.name + "\n";

				gui.swapPanel();

			} else if (board == gui.P2board && !gui.WhiteTurn) {

				this.selected = board.getSelected();
				gui.P2SelectedBoard.add(selected);
				str = "P2 in creation panel: " + selected.name + "\n";

				gui.swapPanel();
			} else if (!(e.getSource() instanceof AnimationView)) {
				str = "Into rotate mode";
				gui.swapGamePanel();
			}
			gui.updatatext(str);
		}
		update();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
