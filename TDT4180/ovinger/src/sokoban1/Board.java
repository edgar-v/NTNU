package sokoban1;

public class Board {
	public char[][] grid;
	
	
	int playerX;
	int playerY;
	int sizeX;
	int sizeY;
	
	void setCell(char c, int x, int y) {
		grid[y][x] = c;
	}
	
	Character getCell(int x, int y) {
		return grid[y][x];
	}
	
	public boolean isTarget(int x, int y) {
		if (getCell(x, y) == '.' || getCell(x, y) == '+' || getCell(x, y) == '*') {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isBox(int x, int y) {
		if (getCell(x, y) == '$' || getCell(x, y) == '*') {
			return true;
		} else {
			return false;
		}
	}
	
	public void loadBoard(int level) {
		sizeX = boards[level][0].length();
		sizeY = boards[level].length;
		grid = new char[sizeY][sizeX];
		
		for (int i = 0; i < sizeY; i++) {
			for (int j = 0; j < sizeX; j++) {
				grid[i][j] = boards[level][i].charAt(j);
				if (grid[i][j] == '@') {
					playerX = j;
					playerY = i;
				}
			}
		}
		
	}
	
	String[][] boards = { {"#####",
						  "#@$.#",
						  "#####" },
						  
						 { "###  ", 
						   "#.###",
						   "#*$ #",
						   "#  @#",
						   "#####" },
						   
						 { "#######",
						   "#.@ # #",
						   "#$* $ #",
						   "#   $ #",
						   "# ..  #",
						   "#  *  #",
						   "#######" },
						   
						 { "*###########*",
						   "#           #",
						   "#  ... ...  #",
						   "#  *$$ $.$  #",
						   "# $*+$ $*$$ #",
					       "#  *$$ $.$  #",
					       "#  ...  .   #",
						   "#           #",
						   "*###########*" }
						};
}
