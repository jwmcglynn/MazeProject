import maze.Maze;

public interface IGenerator {
	public Maze.WriteableMaze generate(int width, int height);
}
