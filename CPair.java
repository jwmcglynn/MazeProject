import maze.*;

public class CPair implements Comparable
{
    private Pair p;
    private double g;

    public CPair(Pair q, double value)
    {
        p = q;
        g = value;
    }

    public double getValue()
    {
        return g;
    }

    public Pair getP()
    {
        return p;
    }


    public boolean equals(Object o)
    {
        CPair cp = (CPair)o;
        return p.equals(cp.getP());
    }

    //lower values get higher priority
    public int compareTo(Object o)
    {
        CPair cp = (CPair)o;
        if( g < cp.getValue())
            return 1;
        else if( g > cp.getValue())
            return -1;
        else
            return 0; 
    }


}
