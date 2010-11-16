import maze.*;
import java.util.Random;

/**
 * Using Eller's algorithm for creating maze, as implemented here:
 * http://ericw.ca/hg/maze/file/41294bc988fa/maze.js
 * 
 * LICENSE:
 ***
The MIT License

Copyright (c) 2010 Eric Woroshow

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 ***
 */
public class EllerGenerator2 implements IGenerator {
	public EllerGenerator2() {
	}
	
	public Maze generate(int width, int height) {
		Maze tmp = new Maze();
		Maze.WriteableMaze wrmaze = tmp.new WriteableMaze(width, height, false);
		
		//Generates the boolean used to decide if two cells will be connected.
		Random randBoolGen = new Random();
		
		wrmaze.setStart(new Pair(0, 0));
		wrmaze.setEnd(new Pair(width - 1, height - 1));
		
		int left[] = new int[width];
		int right[] = new int[width];
		
		// At the top each cell is connected only to itself.
		for (int x = 0; x < width; ++x) {
			left[x] = x;
			right[x] = x;
		}
		
		// Generate each row of the maze excluding the last
		for (int y = 0; y < height - 1; ++y) {
			for (int x = 0; x < width; ++x) {
				// Connect to the right?
				if (x < width - 1 && x + 1 != right[x] && randBoolGen.nextBoolean()) {
					right[left[x + 1]] = right[x];
					left[right[x]] = left[x + 1];
					right[x] = x + 1;
					left[x + 1] = x;
					
					wrmaze.addEdge(new Pair(x, y), new Pair(x + 1, y));
				}
				
				// Link with pair below?
				if (x != right[x] && randBoolGen.nextBoolean()) {
					right[left[x]] = right[x];
					left[right[x]] = left[x];
					right[x] = x;
					left[x] = x;
				} else {
					wrmaze.addEdge(new Pair(x, y), new Pair(x, y + 1));
				}
			}
		}
		
		// Handle last row.
		for (int x = 0; x < width; ++x) {
			if (x != width - 1 && x + 1 != right[x] && (x == right[x] || randBoolGen.nextBoolean())) {
				right[left[x + 1]] = right[x];
				left[right[x]] = left[x + 1];
				right[x] = x + 1;
				left[x + 1] = x;
				
				wrmaze.addEdge(new Pair(x, height - 1), new Pair(x + 1, height - 1));
			}
			
			right[left[x]] = right[x];
			left[right[x]] = left[x];
			right[x] = x;
			left[x] = x;
		}
		
		return wrmaze.getFixedMaze();
	}
}
