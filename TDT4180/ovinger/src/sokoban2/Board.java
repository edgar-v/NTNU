package sokoban2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
				if (grid[i][j] == '@' || grid[i][j] == '+') {
					playerX = j;
					playerY = i;
				} 
			}
		}
		
	}
	
	public void loadBoardFromFile(String file) {

		String line;
		ArrayList<String> map = new ArrayList<String>();
		try {
		BufferedReader br = new BufferedReader(new FileReader(file));
		while ((line = br.readLine()) != null) {
			map.add(line);
		}
		br.close();
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		}
		sizeX = map.get(0).length();
		sizeY = map.size();
		grid = new char[sizeY][sizeX];
		
		for (int i = 0; i < sizeY; i++) {
			for (int j = 0; j < sizeX; j++) {
				grid[i][j] = map.get(i).charAt(j);
				if (grid[i][j] == '@' || grid[i][j] == '+') {
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
