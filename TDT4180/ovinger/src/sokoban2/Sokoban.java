package sokoban2;


public class Sokoban {
	
	public Board board = new Board();
	public int currentLevel = 0;
	public String moves = "";
	
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
			moves += "b";
		}
		updatePlayerNextPos(dx, dy);
		updatePlayerCurrPos(dx, dy, false);
		board.playerX += dx;
		board.playerY += dy;
		if (hasWon()) {
			currentLevel++;
			moves = "";
			board.loadBoard(currentLevel);
		}
	}
	
	private void updatePlayerCurrPos(int dx, int dy, boolean box) {
		switch (board.getCell(board.playerX, board.playerY)) {
		
		case '@':
			if (box) {
				board.setCell('$', board.playerX, board.playerY);
			} else {
				board.setCell(' ', board.playerX, board.playerY);
			}
			break;
			
		case '+':
			if (box) {
				board.setCell('*', board.playerX, board.playerY);
			} else {
				board.setCell('.', board.playerX, board.playerY);
				break;
			}
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
	
	private void undoBoxMove(int dx, int dy) {
		switch (board.getCell(board.playerX - dx, board.playerY - dy)) {
		
		case '$':
			board.setCell(' ', board.playerX - dx, board.playerY - dy);
			break;
			
		case '*':
			board.setCell('.', board.playerX - dx, board.playerY - dy);
			break;
		}
	}
	
	public void undo()
	{
		if (moves.length() == 0) {
			return;
		}
		boolean mbox = false;
		int dx = 0, dy = 0;
		if (moves.charAt(moves.length() - 1) == 'b') {
			mbox = true;
			moves = moves.substring(0, moves.length() - 1);
		}
		switch (moves.charAt(moves.length() - 1)) {
		case 'u':
			dy = 1;
			dx = 0;
			break;
		case 'd':
			dy = -1;
			dx = 0;
			break;
		case 'l':
			dy = 0;
			dx = 1;
			break;
		case 'r':
			dy = 0;
			dx = -1;
		}
		
		updatePlayerNextPos(dx, dy);
		if (mbox) {
			updatePlayerCurrPos(dx, dy, true);
			undoBoxMove(dx, dy);
		} else {
			updatePlayerCurrPos(dx, dy, false);
		}
		board.playerX += dx;
		board.playerY += dy;
		moves = moves.substring(0, moves.length() - 1);
		
	}
	
	public void printmoves() {
		if (moves.length() == 0) {
			return;
		} else {
			System.out.println(moves.replace("b", ""));
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
