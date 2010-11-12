import maze.*;
import java.util.Random;
import java.util.HashSet;
import java.util.Collection;

public class EllerGenerator implements IGenerator {
	public EllerGenerator() {
	}
	
	public Maze generate(int width, int height) {
		Maze tmp = new Maze();
		Maze.WriteableMaze wrmaze = tmp.new WriteableMaze(width, height, false);
		
		HashSet<Pair>[][] arrayOfSets = (HashSet<Pair>[][]) new HashSet[width][height];
		
		//Generates the boolean used to decide if two cells will be connected.
		Random randBoolGen = new Random();
		
		Vertex[][] maze = wrmaze.getMaze();
		
		wrmaze.setStart(new Pair(0, 0));
		wrmaze.setEnd(new Pair(width - 1, height - 1));
		/*
		// Link horizontally.
		for (int i = 0; i < width - 1; ++i) {
			wrmaze.addEdge(new Pair(i, 0), new Pair(i + 1, 0));
		}

		// Link vertically.
		for (int j = 0; j < height - 1; ++j) {
			wrmaze.addEdge(new Pair(width - 1, j), new Pair(width - 1, j + 1));
		}
		*/
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				arrayOfSets[i][j] = new HashSet<Pair>();
				arrayOfSets[i][j].add(new Pair (i, j));
			}
		}
		
		for (int i = 0; i < height - 1; i++) {
			for (int j = 0; j < width - 1; j++) {
				boolean match = false;
				Pair nextCell = new Pair(j + 1, i);
				//Check to see if nextCell is already in the current set.
				//If the array contains the cell, it will not be connected.
				for (Pair p : arrayOfSets[j][i]) {
					if ( p.equals( nextCell ) ) {
						match = true;
						break;
					}
				}
				//Carve the horizontal path by connecting two cells randomly.
				if ( !match && randBoolGen.nextBoolean() ) {
					wrmaze.addEdge( new Pair(j, i), nextCell );
					union( arrayOfSets[j][i], arrayOfSets[j + 1][i]);
				}
			}
				//Carve the vertical path by randomly connecting cells in 
				//the current row with cells in the next row.
			for (int j = 0; j < width; j++) {
				if (randBoolGen.nextBoolean() || arrayOfSets[j][i].size() == 1) {
					//Randomly connect cells vertically. If any current cell is of
					//size 1, then it must be connected vertically to prevent isolation.
					wrmaze.addEdge( new Pair(j, i), new Pair(j, i + 1) );
					union( arrayOfSets[j][i], arrayOfSets[j][i + 1] );
				}
				else {
					arrayOfSets[j][i].clear();
					arrayOfSets[j][i].add( new Pair(j, i) );
				}
			}
		}
		for (int i = 0; i < width - 1; i++) {
			boolean match = false;
			for (Pair p : arrayOfSets[i][height - 1]) {
				boolean done = false;
				for (Pair q : arrayOfSets[i + 1][height - 1]) {
					if ( p.equals(q) ) {
						match = done = true;
						break;
					}
				}
				if (done) {
					break;
				}
			}
			if (!match) {
				union( arrayOfSets[i][height - 1], arrayOfSets[i + 1][height - 1]);
			}
		}
		return wrmaze.getFixedMaze();
	}
	
	private boolean union ( Collection<Pair> o1, Collection<Pair> o2 ) {
		o1.addAll(o2);
		o2.addAll(o1);
		return true;
	}
}
