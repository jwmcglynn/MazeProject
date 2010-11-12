import maze.*;

public class DummyGenerator implements IGenerator {
	public DummyGenerator() {
	}
	
	public void generate(Maze.WriteableMaze wrmaze, int width, int height) {
		Vertex[][] maze = wrmaze.getMaze();
		
		wrmaze.setStart(new Pair(0, 0));
		wrmaze.setEnd(new Pair(width - 1, height - 1));
		
		// Link horizontally.
		for (int i = 0; i < width - 2; ++i) {
			maze[i][0].addNeighbor(new Pair(i + 1, 0));
		}

		// Link horizontally.
		for (int j = 0; j < height - 2; ++j) {
			maze[width - 1][j].addNeighbor(new Pair(width - 1, j + 1));
		}
	}
}
