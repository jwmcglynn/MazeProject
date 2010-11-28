import maze.*;
import maze.Maze.WriteableMaze;

public class MazeGenerator {
	private Maze.WriteableMaze m_maze;
	
	public enum Type {
		Dummy
		, Wilson
		, ImprovedWilson
		, Eller
		, Eller2
	}
	
	private Type m_genType;
	
	public MazeGenerator(Type genType) {
		m_genType = genType;
	}

	public Maze generateMaze(int width, int height) {
		long start = System.nanoTime();
		IGenerator gen = null;
		
		switch (m_genType) {
			case Dummy:
				gen = new DummyGenerator();
				break;
			case Wilson:
				gen = new WilsonGenerator();
				break;
			case ImprovedWilson:
				gen = new ImprovedWilsonGenerator();
				break;
			case Eller:
				gen = new EllerGenerator();
				break;
			case Eller2:
				gen = new EllerGenerator2();
				break;
		}
		
		m_maze = gen.generate(width, height);
		long end = System.nanoTime();
		
		System.out.println("Took " + (double) (end - start) / 1000000000.0 + " seconds to create maze.");
		return getLastMaze();
	}
	
	// Temp for testing.
	public Maze getLastMaze() {
		Vertex[][] verts = m_maze.getMaze();
		Maze fixedMaze = m_maze.getFixedMaze();
		
		Vertex[][] newVerts = new Vertex[fixedMaze.getWidth()][fixedMaze.getHeight()];
		for (int y = 0; y < fixedMaze.getWidth(); ++y) {
			for (int x = 0; x < fixedMaze.getHeight(); ++x) {
				newVerts[y][x] = new Vertex(verts[y][x].getP(), verts[y][x].getLegalNeighbors(), null);
				newVerts[y][x].setNeighbors(verts[y][x].getNeighbors());
			}
		}
		
		Maze tmp = new Maze();
		Maze.WriteableMaze wrmaze = tmp.new WriteableMaze(fixedMaze.getWidth(), fixedMaze.getHeight(), false);
		wrmaze.setMaze(newVerts);
		Pair oldStart = fixedMaze.getStartLocation();
		wrmaze.setStart(newVerts[oldStart.x][oldStart.y].getP());
		Pair oldEnd = fixedMaze.getEndLocation();
		wrmaze.setEnd(newVerts[oldEnd.x][oldEnd.y].getP());
		return wrmaze.getFixedMaze();
	}
}
