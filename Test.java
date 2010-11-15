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

        //MazeGenerator mg = new MazeGenerator();
        IGenerator mg = new WilsonGenerator();
        mz = mg.generate(70,50);
        MazeSolver ms = new MazeSolver();
        solution = ms.solveMaze(mz);
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
