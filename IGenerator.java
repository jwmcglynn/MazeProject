/**
 * Maze Project
 * CS171
 * 
 * Jeff McGlynn
 * Jesus Quezada
 * 
 * November 29th, 2010
**/

import maze.Maze;

public interface IGenerator {
	public Maze.WriteableMaze generate(int width, int height);
}
