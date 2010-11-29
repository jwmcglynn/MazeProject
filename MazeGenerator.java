/**
 * Maze Project
 * CS171
 * 
 * Jeff McGlynn
 * Jesus Quezada
 * 
 * November 29th, 2010
**/

import maze.*;

public class MazeGenerator {
	public MazeGenerator() {
	}

	public Maze generateMaze(int width, int height) {
		IGenerator gen = new ImprovedWilsonGenerator();
		
		Maze.WriteableMaze wrmaze = gen.generate(width, height);
		return wrmaze.getFixedMaze();
	}
}
