import maze.*;

import java.util.Iterator;
import java.util.LinkedList;
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
		
		//Randomizes whether two adjacent cells are connected or not.
		Random randBoolGen = new Random();
		
		//Start and finish points of the maze
		wrmaze.setStart(new Pair(0, 0));
		wrmaze.setEnd(new Pair(width - 1, height - 1));
		
		//Place every cell in its own set.
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				arrayOfSets[i][j] = new HashSet<Pair>();
				arrayOfSets[i][j].add(new Pair (i, j));
			}
		}
		
		for (int i = 0; i < height - 1; i++) {
			
			for (int j = 0; j < width - 1; j++) {
				
				//Are the two cells in the same set? If not, then apply random chance.
				if ( randBoolGen.nextBoolean() ) {
					boolean match = false;
					Pair nextPair = new Pair(j + 1, i);
					for (Pair p : arrayOfSets[j][i]) {
						if (p.equals(nextPair)) {
							match = true;
							break;
						}
					}
					if (!match) {
						//Connect cells.
						wrmaze.addEdge( new Pair(j, i), new Pair(j + 1, i) );
						merge( arrayOfSets[j][i], arrayOfSets[j + 1][i], arrayOfSets);
					}
				}
			}
			
			//VERTICAL PATH CARVING RUN THROUGH
			
			//Contains sets that do not have a connection to the next row.
			LinkedList<HashSet<Pair>> notConnectedToNextRow = (LinkedList<HashSet<Pair>>) new LinkedList();
			for (int j = 0; j < width; j++) {
				notConnectedToNextRow.add(arrayOfSets[j][i]);
				//Sets are randomly connected to the next row unless it is size 1, in which
				//case it must be connected.
				if ( arrayOfSets[j][i].size() == 1 || randBoolGen.nextBoolean() ) {
					wrmaze.addEdge( new Pair(j, i), new Pair(j, i + 1) );
					merge( arrayOfSets[j][i + 1], arrayOfSets[j][i], arrayOfSets);
					notConnectedToNextRow.remove(arrayOfSets[j][i]);
					
				}
			}
			//Ensures that every set in the current row has a connection to the next row.
			//Only one connection is made since the opportunity to make multiple connections
			//was already given.
			while (!notConnectedToNextRow.isEmpty()) {
				Iterator<HashSet<Pair>> iterator = notConnectedToNextRow.iterator();
				while (iterator.hasNext()) {
					HashSet<Pair> nextSet = iterator.next();
					for (Pair p : nextSet) {
						if (p.y == i && randBoolGen.nextBoolean()) {
							wrmaze.addEdge( p, new Pair(p.x, i + 1) );
							merge( arrayOfSets[p.x][i + 1], arrayOfSets[p.x][i], arrayOfSets);
							iterator.remove();
							break;
							//Break because only one connection needed.
						}
					}
				}
			}
		}
		
		//Special Procedure for he final row. If adjacent cells are not in the same set,
		//connect them.
		for (int i = 0; i < width - 1; i++) {
			boolean match = false;
			Pair nextPair = new Pair(i + 1, height - 1);
			for (Pair p : arrayOfSets[i][height - 1]) {
				if (p.equals(nextPair)) {
					match = true;
					break;
				}
			}
			if ( !match ) {
				wrmaze.addEdge( new Pair(i, height - 1), new Pair(i + 1, height - 1) );
				merge( arrayOfSets[i + 1][height - 1], arrayOfSets[i][height - 1], arrayOfSets);
			}
		}
		
		return wrmaze.getFixedMaze();
	}
	
	//In order to properly merge two sets, all the pointers that previously pointed to one set
	//must be changed to point to the other set. Otherwise, there will be inconsistency.
	private void merge ( HashSet<Pair> o1, HashSet<Pair> o2, HashSet<Pair>[][] main) {
		//Work with the smaller set.
		if (o1.size() > o2.size()) {
			o1.addAll(o2);
			for (Pair p : o2) {
				main[p.x][p.y] = o1;
			}
		}
		else {
			o2.addAll(o1);
			for (Pair p : o1) {
				main[p.x][p.y] = o2;
			}
		}
		return;
	}
}
