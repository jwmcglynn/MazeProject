/**
 * Maze Project
 * CS171
 * 
 * Jeff McGlynn
 * Jesus Quezada
 * 
 * November 29th, 2010
**/

import java.util.*;
import maze.*;

public class MazeSolver {
	ISolver m_solver;
	
	/**
	 * Constructor called from test code.
	 * 
	 * @param k
	 */
	public MazeSolver(int k) {
		m_solver = new AdaptiveAStarSolver();
	}

    public LinkedList<Pair> solveMaze(Maze maze) {
		return m_solver.solveMaze(maze);
    }
}
