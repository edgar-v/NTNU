package sokoban2;

import java.awt.event.KeyEvent;

import acm.graphics.GImage;
import acm.program.GraphicsProgram;

public class GUI extends GraphicsProgram {
	
	int imgXSize = 16;
	int imgYSize = 16;
	Sokoban sokoban = new Sokoban();
	
	public void init() {
		addKeyListeners();
	}
	
	public void run() {
		//sokoban.board.loadBoard(sokoban.currentLevel);
		sokoban.board.loadBoardFromFile("sokobanlevel.txt");
		paintGrid();
	}
	
	private void paintGrid() {
		removeAll();
		for (int i = 0; i < sokoban.board.sizeX; i++) {
			for (int j = 0; j < sokoban.board.sizeY; j++) {
				switch (sokoban.board.grid[j][i]) {
				case '#':
					add(new GImage("sokoban/wall16x16.png"), i * imgXSize, j * imgYSize);
					break;
					
				case ' ':
					add(new GImage("sokoban/blank16x16.png"), i * imgXSize, j * imgYSize);
					break;
					
				case '.':
					add(new GImage("sokoban/target16x16.png"), i * imgXSize, j * imgYSize);
					break;
					
				case '@':
					add(new GImage("sokoban/mover16x16.png"), i * imgXSize, j * imgYSize);
					break;
					
				case '+':
					add(new GImage("sokoban/mover_on_target16x16.png"), i * imgXSize, j * imgYSize);
					break;
					
				case '$':
					add(new GImage("sokoban/movable16x16.png"), i * imgXSize, j * imgYSize);
					break;
					
				case '*':
					add(new GImage("sokoban/movable_on_target16x16.png"), i * imgXSize, j * imgYSize);
					break;
				}
			}
		}
	}
	
	public void keyPressed(KeyEvent event) {
		boolean acceptedMove = false;
		int key;
		if (Character.isLetter(event.getKeyChar())) {
			key = event.getKeyChar();
		} else {
			key = event.getKeyCode();
		}
		switch (key) {
		case KeyEvent.VK_UP:
			sokoban.moves += "u";
			acceptedMove = sokoban.move(0, -1);
			break;
		case KeyEvent.VK_DOWN:
			sokoban.moves += "d";
			acceptedMove = sokoban.move(0, 1);
			break;
		case KeyEvent.VK_LEFT:
			sokoban.moves += "l";
			acceptedMove = sokoban.move(-1, 0);
			break;
		case KeyEvent.VK_RIGHT:
			sokoban.moves += "r";
			acceptedMove = sokoban.move(1, 0);
			break;
		case 'u':
			sokoban.undo();
			acceptedMove = true;
			break;
		case 'p':
			sokoban.printmoves();
			acceptedMove = true;
			break;
		}
		if (acceptedMove) {
			paintGrid();
		} 
		else
		{
			sokoban.moves = sokoban.moves.substring(0, sokoban.moves.length() - 1);
		}
	}
}
