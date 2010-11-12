import maze.*;

public class MazeGenerator {
	public MazeGenerator() {
	}

	public Maze generateMaze(int width, int height) {
		DummyGenerator gen = new DummyGenerator();
		return gen.generate(width, height);
	}
}
