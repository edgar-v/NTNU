package sokoban1;


public class Sokoban {
	
	public Board board = new Board();
	public int currentLevel = 0;
	
	public boolean move(int dx, int dy) {
		if (canMove(dx, dy)) {
			doMove(dx, dy);
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean canMove(int dx, int dy) {
		switch (board.getCell(board.playerX + dx, board.playerY + dy)) {
		case '#':
			return false;
		
		case ' ':
			return true;
		
		case '.':
			return true;
		
		case '$':
			return canBoxMove(dx, dy);
			
		case '*':
			return canBoxMove(dx, dy);
			
		default:
			return false;
		}
	}
	
	private boolean canBoxMove(int dx, int dy) {
		switch (board.getCell(board.playerX + (dx * 2), board.playerY + (dy * 2))) {
		
		case '#':
			return false;
			
		case ' ':
			return true;
			
		case '.':
			return true;
			
		case '$':
			return false;
			
		case '*':
			return false;
			
		default:
			return false;
		}
	}
	
	private void doMove(int dx, int dy) {
		if (board.isBox(board.playerX + dx, board.playerY + dy)) {
			updateBoxPosition(dx, dy);
		}
		updatePlayerNextPos(dx, dy);
		updatePlayerCurrPos(dx, dy);
		board.playerX += dx;
		board.playerY += dy;
		if (hasWon()) {
			currentLevel++;
			board.loadBoard(currentLevel);
		}
	}
	
	private void updatePlayerCurrPos(int dx, int dy) {
		switch (board.getCell(board.playerX, board.playerY)) {
		
		case '@':
			board.setCell(' ', board.playerX, board.playerY);
			break;
			
		case '+':
			board.setCell('.', board.playerX, board.playerY);
			break;
		}
	}
	
	private void updatePlayerNextPos(int dx, int dy) {
		switch (board.getCell(board.playerX + dx, board.playerY + dy)) {
		
		case ' ':
			board.setCell('@', board.playerX + dx, board.playerY + dy);
			break;
			
		case '.':
			board.setCell('+', board.playerX + dx, board.playerY + dy);
			break;
			
		case '$':
			board.setCell('@', board.playerX + dx, board.playerY + dy);
			break;
			
		case '*':
			board.setCell('+', board.playerX + dx, board.playerY + dy);
			break;
		}
	}
	
	private void updateBoxPosition(int dx, int dy) {
		switch (board.getCell(board.playerX + (dx * 2), board.playerY + (dy * 2))) {
		
		case ' ':
			board.setCell('$', board.playerX + (dx * 2), board.playerY + (dy * 2));
			break;
			
		case '.':
			board.setCell('*', board.playerX + (dx * 2), board.playerY + (dy * 2));
			break;
		}
	}
	
	public boolean hasWon() {
		for (int i = 0; i < board.sizeX; i++) {
			for (int j = 0; j < board.sizeY; j++) {
				if (board.grid[j][i] == '.' || board.grid[j][i] == '+') {
					return false;
				}
			}
		}
		return true;
	}
}
