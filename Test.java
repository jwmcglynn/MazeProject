import java.awt.*;
import java.util.*;

import javax.swing.*;
import maze.*;

public class Test extends JFrame
{

    private JPanel panel;

    private Maze mz;
    LinkedList<Pair> solution;

    public Test()
    {
        super();

        MazeGenerator mg = new MazeGenerator(MazeGenerator.Type.Wilson);
        mz = mg.generateMaze(70,70);
        //mz = mg.generateMaze(500,500);
        
        MazeSolver breadthSolver = new MazeSolver(MazeSolver.Type.BreadthFirst);
        LinkedList<Pair> optimalSolution = breadthSolver.solveMaze(mz);
        int numMoves = optimalSolution.size();
        System.out.println(MazeSolver.Type.BreadthFirst + ": optimal path " + numMoves + ", numObservations " + mz.getNumObservations());
        
        MazeSolver.Type[] attempts = { MazeSolver.Type.DepthFirst, MazeSolver.Type.BidirectionalDepth, MazeSolver.Type.AStarSimple, MazeSolver.Type.AStar};
        
        for (MazeSolver.Type type : attempts) {
            mz = mg.getLastMaze();

            MazeSolver ms = new MazeSolver(type);
            solution = ms.solveMaze(mz); // make it so that the different solutions are drawn in different colors
            System.out.println(type + ": Score " + (float) mz.getNumObservations() / numMoves + ", numObservations " + mz.getNumObservations());
            if (!verifyAnswer(solution, mz.getStartLocation(), mz.getEndLocation())) {
            	System.out.println("================= MAZE SOLUTION IS NOT VALID ================");
            	System.out.println(solution);
            	System.out.println("=============================================================");
            }
        }
    }
    
    boolean verifyAnswer(LinkedList<Pair> sol, Pair start, Pair end) {
    	Pair cur = sol.removeFirst();
    	if (cur != start) return false;
    	
    	for (Pair p : sol) {
    		boolean xchange = (Math.abs(cur.x - p.x) == 1);
    		boolean ychange = (Math.abs(cur.y - p.y) == 1);
    		if (xchange == ychange) return false;
    		
    		cur = p;
    	}
    	
    	return (cur == end);
    }

    public static void main(String[] args)
    {
        Test frame = new Test();
        
        

        frame.setSize(800,600);
        frame.createWindow();

   
        frame.setVisible(true);

 
    }
    

    private void createWindow()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout() );
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setBackground(Color.white);

        window.add(panel);

    }

    public void paint(Graphics g)
    {
        mz.visualize(g);
        
        Graphics2D gobj = (Graphics2D)g;

        gobj.setColor(Color.RED);

        int D = 4;
       
       Iterator<Pair> i = solution.iterator();

       Pair current = i.next();
       Pair next = i.next();
       gobj.drawLine(D*current.x + D/2, D*current.y + D/2, D*next.x + D/2, D*next.y + D/2);
        while(i.hasNext()) {
            current = next;
            next = i.next();
            gobj.drawLine(D*current.x + D/2, D*current.y + D/2, D*next.x + D/2, D*next.y + D/2);

        }
        current = next;
        next = mz.getEndLocation();
        gobj.drawLine(D*current.x + D/2, D*current.y + D/2, D*next.x + D/2, D*next.y + D/2);

    }
}
