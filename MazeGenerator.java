import java.util.*;
import maze.*;
public class MazeGenerator
{
    
    public MazeGenerator()
    {
    }    


    public Maze generateMaze(int width, int height)
    {
        Maze tmp = new Maze();
        Maze.WriteableMaze wrmaze = tmp.new WriteableMaze(width,height,false);

        Vertex[][] maze = wrmaze.getMaze();

        //your code here



        Maze outmaze = wrmaze.getFixedMaze();

        return outmaze;
         
    }

}
