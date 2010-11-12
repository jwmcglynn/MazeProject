package maze;

import java.util.*;
import java.awt.*;

public class Maze
{
    private Vertex[][] maze;
   
    private Vertex start;
    private Vertex end;

    private int width;
    private int height;

    //don't allow MazeSolver to access arbitrary parts of the maze.
    private Hashtable legalObservations;

    private int numObservations; 

    public Maze()
    {}
   
    Maze(Vertex[][] mz, Vertex st, Vertex nd, int w, int h )
    {
        width = w;
        height = h;
        maze = mz;
        start = st;
        end = nd;
        legalObservations = new Hashtable();
        legalObservations.put(start.getP(),start.getP());
        start.Observe(); 
        legalObservations.put(end.getP(),end.getP());
        end.Observe();

        numObservations = 2;
        
    } 

    public int getNumObservations()
    {
        return numObservations;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    //Observe location p, from the location from
    public LinkedList<Pair> Observe(Pair p)
    {

        //you cannot observe arbitrary squares in the maze, only those next to those immediately reachable from those you have already observed
        if( legalObservations.containsKey(p) )
        {
            
            Vertex vx = maze[p.x][p.y];
    
            if(!vx.wasObserved())
                numObservations++;

            vx.Observe();

            LinkedList<Pair> neighbors = vx.getNeighbors();
            Iterator<Pair> iter = neighbors.iterator();
            while(iter.hasNext())
            {
                Pair q = iter.next();
                legalObservations.put(q,q);
            }

            return vx.getNeighbors();

        }
        else
        {
            System.out.println("You can only observe squares next to those that have been observed!!");
            return null;
        }            
    }

    public Pair getStartLocation()
    {
        return start.getP();
    }

    public Pair getEndLocation()
    {
        return end.getP();
    }

    public void visualize(Graphics g)
    {
        Graphics2D gobj = (Graphics2D)g;

        gobj.scale(2.0,2.0);
        gobj.translate(10.0,20.0);
        //gobj.setBackground(Color.white);

        gobj.setColor(Color.black);

        int D = 4;


        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                boolean isStart = (start.getP().x == i && start.getP().y == j);
                boolean isEnd = (end.getP().x == i && end.getP().y == j);

                LinkedList<Pair> neighbors = maze[i][j].getNeighbors();
                if( !neighbors.contains(new Pair(i-1,j)))
                    gobj.drawLine(D*i,D*j, D*i,D*j+D);
                if( !neighbors.contains(new Pair(i+1,j)))
                    gobj.drawLine(D*i+D,D*j,D*i+D,D*j+D);
                if( !neighbors.contains(new Pair(i,j-1)))
                    gobj.drawLine(D*i,D*j, D*i+D,D*j);
                if( !neighbors.contains(new Pair(i,j+1)))
                    gobj.drawLine(D*i,D*j+D,D*i+D,D*j+D);

                if(isStart)
                {
                    gobj.setColor(Color.green);
                    gobj.fillRect(D*i+1,D*j+1,D-2,D-2);
                    gobj.setColor(Color.black);
                }

                if(isEnd)
                {
                    gobj.setColor(Color.blue);
                    gobj.fillRect(D*i+1,D*j+1,D-2,D-2);
                    gobj.setColor(Color.black);
                }

            }
        }

    }

    public class WriteableMaze
    {
        
        public WriteableMaze(int w, int h, boolean fullyConnected)
        {
            width = w;
            height = h;
            maze = new Vertex[width][height];
            for(int i = 0; i < width; i++)
            {
                for(int j = 0; j < height; j++)
                {
                    LinkedList<Pair> neighbors = new LinkedList<Pair>();

                    if(fullyConnected)
                    {
                        if( i > 0)
                            neighbors.add(new Pair(i-1,j));
                        if(j > 0)
                            neighbors.add(new Pair(i,j-1));
                        if(i < width-1)
                            neighbors.add(new Pair(i+1,j));
                        if(j < height-1)
                            neighbors.add(new Pair(i,j+1));
                    }
            

                    LinkedList<Pair> legalneighbors = new LinkedList<Pair>();
                    if( i > 0)
                        legalneighbors.add(new Pair(i-1,j));
                    if(j > 0)
                        legalneighbors.add(new Pair(i,j-1));
                    if(i < width-1)
                        legalneighbors.add(new Pair(i+1,j));
                    if(j < height-1)
                        legalneighbors.add(new Pair(i,j+1));


                    maze[i][j] = new Vertex(new Pair(i,j),legalneighbors,neighbors);
                }
            }

            legalObservations = new Hashtable();

        }

        public Vertex getVertex(int x, int y)
        {
            return maze[x][y];
        }

        public Vertex[][] getMaze()
        {
            return maze;
        }

        public void setMaze(Vertex[][] mz)
        {
            maze = mz;
        }

        public void setStart(Pair p)
        {
            Vertex st = maze[p.x][p.y];
            start = st;
            legalObservations.put(start,start);
            start.Observe(); 
        }

        public void setEnd(Pair p)
        {
            Vertex nd = maze[p.x][p.y];
            end = nd;
            legalObservations.put(end,end);
            end.Observe();
        }

        public boolean addEdge(Pair p, Pair q)
        {
            Vertex v1 = maze[p.x][p.y];
            Vertex v2 = maze[q.x][q.y];
            boolean b1 = v1.addNeighbor(q);
            boolean b2 = v2.addNeighbor(p);

            return b1 && b2;

        }

        public Maze getFixedMaze()
        {
            Maze mz = new Maze(maze,start,end,width,height);
            return mz;
        }

    }

 
}
