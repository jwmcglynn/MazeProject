import maze.*;

public class MazeGenerator {
	public MazeGenerator() {
	}

	public Maze generateMaze(int width, int height) {
		long start = System.nanoTime();
		IGenerator gen = new DummyGenerator();
		Maze ret = gen.generate(width, height);
		long end = System.nanoTime();
		
		System.out.println("Took " + (double) (end - start) / 1000000000.0 + " seconds to compute maze.");
		return ret;
	}
}
