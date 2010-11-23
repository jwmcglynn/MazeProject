import java.util.Random;

import maze.*;

/**
 * Wilson's algorithm from http://www.astrolog.org/labyrnth/algrithm.htm
 * 
 * This is an improved version of the Aldous-Broder algorithm, in that it
 * produces Mazes exactly like that algorithm, with all possible Mazes
 * generated with equal probability, except that Wilson's algorithm runs much
 * faster. It requires storage up to the size of the Maze. Begin by making a
 * random starting cell part of the Maze. Proceed by picking a random cell not
 * already part of the Maze, and doing a random walk until a cell is found
 * which is already part of the Maze. Once the already created part of the Maze
 * is hit, go back to the random cell that was picked, and carve along the path
 * that was taken, adding those cells to the Maze. More specifically, when
 * retracing the path, at each cell carve along the direction that the random
 * walk most recently took when it left that cell. That avoids adding loops
 * along the retraced path, resulting in a single long passage being appended
 * to the Maze. The Maze is done when all cells have been appended to the Maze.
 * This has similar performance issues as Aldous-Broder, where it may take a
 * long time for the first random path to find the starting cell, however once
 * a few paths are in place, the rest of the Maze gets carved quickly. On
 * average this runs five times faster than Aldous-Broder, and takes less than
 * twice as long as the top algorithms. Note this runs twice as fast when
 * implemented as a wall adder, because the whole boundary wall starts as part
 * of the Maze, so the first walls are connected much quicker.
 * 
**/


public class WilsonGenerator implements IGenerator {
	public WilsonGenerator() {
	}
	
	private Pair neighbor(Pair cur, int dir, Vertex[][] maze) {
		int x = cur.x;
		int y = cur.y;
		switch (dir) {
			case 0: --x; break; // Left.
			case 1: ++x; break; // Right.
			case 2: --y; break; // Up.
			case 3: ++y; break; // Down.
			default: return null;
		}

		if (x >= 0 && x < maze.length && y >= 0 && y < maze[x].length) return maze[x][y].getP();
		else return null;
	}
	
	public boolean inside(Pair p, int width, int height) {
		return (p.x >= 0 && p.x < width && p.y >= 0 && p.y < height);
	}
	
	public Maze.WriteableMaze generate(int width, int height) {
		Maze tmp = new Maze();
		Maze.WriteableMaze wrmaze = tmp.new WriteableMaze(width, height, false);
		Vertex[][] maze = wrmaze.getMaze();
		
		// TODO: Better start/end spots.
		wrmaze.setStart(maze[0][0].getP());
		wrmaze.setEnd(maze[width - 1][height - 1].getP());
		
		boolean connected[][] = new boolean[width][height];
		connected[0][0] = true;
		
		int nodesLeft = width * height - 1;
		
		Random randomGen = new Random();
		int dirMatrix[][] = new int[width][height];
		
		while (nodesLeft > 0) {
			// Pick a random spot and begin walking a path.
			Pair cur = maze[randomGen.nextInt(width)][randomGen.nextInt(height)].getP();
			Pair start = cur;
			
			do {
				// Pick a random direction.
				int dir = randomGen.nextInt(4);
				Pair next = neighbor(cur, dir, maze);
				
				while (next == null) {
					++dir;
					if (dir > 3) dir = 0;
					next = neighbor(cur, dir, maze);
				}
				
				dirMatrix[cur.x][cur.y] = dir;
				cur = next;
			} while (!connected[cur.x][cur.y]);
			
			dirMatrix[cur.x][cur.y] = 4;
			
			// Go back and retrace our path.
			cur = start;
			
			 while (!connected[cur.x][cur.y]) {
				int dir = dirMatrix[cur.x][cur.y];
				
				connected[cur.x][cur.y] = true;
				--nodesLeft;
				
				Pair next = neighbor(cur, dir, maze);
				wrmaze.addEdge(cur, next);

				cur = next;
			}
		}
		
		return wrmaze;
	}
}
