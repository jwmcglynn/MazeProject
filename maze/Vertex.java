package maze;

import java.util.*;

public class Vertex
{
    //private boolean[] neighbors;
    private LinkedList<Pair> neighbors;
    private LinkedList<Pair> legalNeighbors;
    private Pair p;

    private boolean wasObserved = false;

    public Vertex(Pair q,LinkedList<Pair> lnbs,LinkedList<Pair> nbs)
    {
        //Booleans indicating whether or not you can traverse in the N,E,S, and W directions, in that order
        if( nbs != null)
            neighbors = nbs;
        else
            neighbors = new LinkedList<Pair>();
        legalNeighbors = lnbs;
        p = q;
    }

    public LinkedList<Pair> getLegalNeighbors()
    {
        return (LinkedList<Pair>) legalNeighbors.clone();
    }

    public LinkedList<Pair> getNeighbors()
    {
        return (LinkedList<Pair>) neighbors.clone();
    }

    public void setNeighbors(LinkedList<Pair> nlist)
    {
        Iterator<Pair> iter = nlist.iterator();

        while(iter.hasNext())
        {
            Pair next = iter.next();
            if( !legalNeighbors.contains(next))
            {
                System.err.println("Attempt to add non adjacent squares as neighbors, neighbors will not be set!!!");
                return;
            }
        }
        

        neighbors = nlist;
    }

    public boolean addNeighbor(Pair q)
    {
        
        if( !legalNeighbors.contains(q))
        {
            System.err.println("Attempt to add non adjacent squares [ "+p.toString()+" and "+q.toString()+" ] as neighbors, neighbor will not be set!!!");
            return false;
        }
        else 
            neighbors.add(q);

        return true;

    }

    public void remNeighbor(Pair q)
    {
        neighbors.remove(q);
    }

    public int getX()
    {
        return p.x;
    }

    public int getY()
    {
        return p.y;
    }

    public Pair getP()
    {
        return p;
    }

    public boolean wasObserved()
    {
        return wasObserved;
    }

    public void Observe()
    {
        wasObserved = true;
    }

}
