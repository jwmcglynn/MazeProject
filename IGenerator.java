import maze.Maze;

public interface IGenerator {
	public void generate(Maze.WriteableMaze wrmaze, int width, int height);
}
