import java.util.LinkedList;
import java.util.Random;

import maze.*;

/**
 * Wilson's algorithm from http://www.astrolog.org/labyrnth/algrithm.htm
 * 
 * Modified version of Wilson's algorithm that creates cycles and chooses
 * worst possible start/end points.
 * 
**/


public class ImprovedWilsonGenerator implements IGenerator {
	private Random m_randomGen = new Random();
	
	
	
	public ImprovedWilsonGenerator() {
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
	
	public boolean inside(int x, int y, int width, int height) {
		return (x >= 0 && x < width && y >= 0 && y < height);
	}
	
	Pair findFurthestAndAddLoops(Maze.WriteableMaze wrmaze, Vertex[][] maze, int width, int height, Pair start) {
		int depth[][] = new int[width][height];
		
		LinkedList<Pair> stack = new LinkedList<Pair>();
		stack.add(start);
		
		int furthest = 0;
		Pair furthestPair = null;
		
		while (!stack.isEmpty()) {
			Pair pair = stack.removeFirst();
			int d = depth[pair.x][pair.y];
			
			if (d > furthest) {
				furthest = d;
				furthestPair = pair;
			}
			
			for (Pair v : maze[pair.x][pair.y].getNeighbors()) {
				if (depth[v.x][v.y] > 0) continue;
				depth[v.x][v.y] = d + 1;
				stack.addFirst(v);
			}
		}
		
		// Add loops with similar depth.
		for (int x = 1; x < width; ++x) {
			for (int y = 1; y < height; ++y) {
				int distX = Math.abs(depth[x - 1][y] - depth[x][y]);
				int distY = Math.abs(depth[x][y - 1] - depth[x][y]);
				
				if (distX < 7) {
					wrmaze.addEdge(maze[x - 1][y].getP(), maze[x][y].getP());
					depth[x][y] = 10000000;
				} else if (distY < 7) {
					wrmaze.addEdge(maze[x][y - 1].getP(), maze[x][y].getP());
					depth[x][y] = 10000000;
				}
			}
		}
		
		
		return furthestPair;
	}
	/*************************************************************************/
	
	public Maze.WriteableMaze generate(int width, int height) {
		Maze tmp = new Maze();
		Maze.WriteableMaze wrmaze = tmp.new WriteableMaze(width, height, false);
		Vertex[][] maze = wrmaze.getMaze();
		
		boolean connected[][] = new boolean[width][height];
		Pair nextMatrix[][] = new Pair[width][height];
		int nodesLeft = width * height - 1;
		
		// Pick start point.
		Pair startPos = new Pair(m_randomGen.nextInt(width), m_randomGen.nextInt(height));
		connected[startPos.x][startPos.y] = true;
		wrmaze.setStart(startPos);
		
		////
		
		while (nodesLeft > 0) {
			// Pick a random spot and begin walking a path.
			Pair cur = maze[m_randomGen.nextInt(width)][m_randomGen.nextInt(height)].getP();
			if (connected[cur.x][cur.y]) continue;
			Pair start = cur;
			
			do {
				// Pick a random direction.
				int dir = m_randomGen.nextInt(4);
				Pair next = neighbor(cur, dir, maze);
				
				while (next == null) {
					++dir;
					if (dir > 3) dir = 0;
					next = neighbor(cur, dir, maze);
				}
				
				nextMatrix[cur.x][cur.y] = next;
				cur = next;
			} while (!connected[cur.x][cur.y]);
			
			// Retrace our path.
			nextMatrix[cur.x][cur.y] = null; 
			Pair end = cur;
			cur = start;
			
			do {
				Pair next = nextMatrix[cur.x][cur.y];
				
				connected[cur.x][cur.y] = true;
				--nodesLeft;
				
				wrmaze.addEdge(cur, next);
				cur = next;
				
			} while (cur != end);
		}
		
		// Maze is prepared.  Use depth-first search to determine what the farthest path is.
		Pair furthest = findFurthestAndAddLoops(wrmaze, maze, width, height, startPos);
		wrmaze.setEnd(furthest);
		
		return wrmaze;
	}
}