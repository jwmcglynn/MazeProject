import maze.*;

public class DummyGenerator implements IGenerator {
	public DummyGenerator() {
	}
	
	public Maze generate(int width, int height) {
		Maze tmp = new Maze();
		Maze.WriteableMaze wrmaze = tmp.new WriteableMaze(width, height, false);
		
		wrmaze.setStart(new Pair(0, 0));
		wrmaze.setEnd(new Pair(width - 1, height - 1));
		
		// Link horizontally.
		for (int i = 0; i < width - 1; ++i) {
			wrmaze.addEdge(new Pair(i, 0), new Pair(i + 1, 0));
		}

		// Link vertically.
		for (int j = 0; j < height - 1; ++j) {
			wrmaze.addEdge(new Pair(width - 1, j), new Pair(width - 1, j + 1));
		}
		
		return wrmaze.getFixedMaze();
	}
}
