import java.awt.*;
import java.util.*;
import javax.swing.*;
import maze.*;

public class Test extends JFrame
{

    private JPanel panel;

    private Maze mz;

    public Test()
    {
        super();

        MazeGenerator mg = new MazeGenerator();
        mz = mg.generateMaze(70,50);
        MazeSolver ms = new MazeSolver();
        LinkedList<Pair> solution = ms.solveMaze(mz);
        System.out.println("numObservations: " + mz.getNumObservations());


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
    }

}
