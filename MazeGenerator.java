import maze.*;

public class MazeGenerator {
	public MazeGenerator() {
	}

	public Maze generateMaze(int width, int height) {
		Maze tmp = new Maze();
		Maze.WriteableMaze wrmaze = tmp.new WriteableMaze(width,height,false);

		DummyGenerator gen = new DummyGenerator();
		gen.generate(wrmaze, width, height);
		
		Maze outmaze = wrmaze.getFixedMaze();

		return outmaze;
	}
}
