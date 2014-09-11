package sokoban1;

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
		sokoban.board.loadBoard(sokoban.currentLevel);
		paintGrid();
		
	}
	
	private void paintGrid() {
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
		switch (event.getKeyCode()) {
		case KeyEvent.VK_UP:
			acceptedMove = sokoban.move(0, -1);
			break;
		case KeyEvent.VK_DOWN:
			acceptedMove = sokoban.move(0, 1);
			break;
		case KeyEvent.VK_LEFT:
			acceptedMove = sokoban.move(-1, 0);
			break;
		case KeyEvent.VK_RIGHT:
			acceptedMove = sokoban.move(1, 0);
			break;
		}
		if (acceptedMove) {
			paintGrid();
		}
	}
}
