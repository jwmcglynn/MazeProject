import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.LinkedList;

import maze.Maze;
import maze.Pair;

public class Test extends JFrame
{

    private JPanel panel;

    private Maze mz;
    LinkedList<Pair> solution;

    public Test()
    {
        super();

        MazeSolver[] solvers = { new MazeSolver(MazeSolver.Type.BreadthFirst), new MazeSolver(MazeSolver.Type.DepthFirst), new MazeSolver(MazeSolver.Type.BidirectionalDepth), 
        							   new MazeSolver(MazeSolver.Type.AStarSimple), new MazeSolver(MazeSolver.Type.AStar), new MazeSolver(MazeSolver.Type.AdaptiveAStar)};
        
        float[] scores = new float[solvers.length];
        final int k_numIterations = 100;
        final int k_numOfAttempts = 5;
        
        for(int num = 0; num < k_numOfAttempts; num++) {
	        for (int i = 0; i < k_numIterations; ++i) {
		        MazeGenerator mg = new MazeGenerator(MazeGenerator.Type.Wilson);
		        //mz = mg.generateMaze(70,70);
		        mz = mg.generateMaze(500,500);
		        int numMoves = -1;
		        
		        for (int j = 0; j < solvers.length; ++j) {
		            mz = mg.getLastMaze();
		            
		            MazeSolver ms = solvers[j];
		            solution = ms.solveMaze(mz); // make it so that the different solutions are drawn in different colors
		            if (j == 0) {
		            	// j == 0 is breadth first, its solution will be the optimal one so use it to determine the minimum number of moves.
		            	numMoves = solution.size();
		            }
		            
		            System.out.println("== " + ms + ": Score " + (float) mz.getNumObservations() / numMoves + ", numObservations " + mz.getNumObservations());
		            scores[j] += ((float) (mz.getNumObservations() - numMoves)) / k_numIterations;
		            
		            if (!verifyAnswer(solution, mz.getStartLocation(), mz.getEndLocation())) {
		            	System.out.println("================= MAZE SOLUTION IS NOT VALID ================");
		            	System.out.println(solution);
		            	System.out.println("=============================================================");
		            }
		        }
		        
		        System.out.println();
	        }
	        
	        // output the dimensions of the maze, how many iterations, the weight of adaptive, and the final scores.
	        FileWriter fstream;
			try {
				fstream = new FileWriter("Scores.txt", true);
		        BufferedWriter output = new BufferedWriter(fstream);        
		        
		        output.write("Width: " + mz.getWidth() + " Height: "  + mz.getHeight());
		        output.newLine();
		        output.write("Number of iterations: " + k_numIterations);
		        output.newLine();
		        output.write("Adaptive AStar Weight = 0.25");
		        output.newLine();
		        
		    	output.write("=========================== SCORES ==========================");
		    	output.newLine();
		    	for (int i = 0; i < solvers.length; ++i) {
		    		output.write(solvers[i] + ": " + scores[i]);
		    		output.newLine();
		    	}
		    	output.write("=============================================================");
		    	output.newLine();
		    	output.newLine();
	
		    	output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
	        // Output scores to console.
	    	System.out.println("=========================== SCORES ==========================");
	    	for (int i = 0; i < solvers.length; ++i) {
	    		System.out.println(solvers[i] + ": " + scores[i]);
	    	}
	    	System.out.println("=============================================================");
	    }
    }
    
    boolean verifyAnswer(LinkedList<Pair> sol, Pair start, Pair end) {
    	Pair cur = sol.getFirst();
    	if (cur != start) return false;
    	
    	for (Pair p : sol) {
    		if (p == cur) continue;
    		
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

    	Pair last = null;
    	for (Pair next : solution) {
    		if (last != null) gobj.drawLine(D*last.x + D/2, D*last.y + D/2, D*next.x + D/2, D*next.y + D/2);
    		last = next;
    	}

    }
}
