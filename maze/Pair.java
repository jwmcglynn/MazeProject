package maze;

import java.lang.Math;
public class Pair
{
    public int x;
    public int y;

    public Pair(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
  
    public String toString()
    {
        return "("+x+","+y+")";
    }

    public int hashCode()
    {
        return x % 503 + 1000*(y % 503);
    }

    public boolean equals(Object obj)
    {
        Pair p = (Pair)obj;
        return x == p.x && y == p.y;
    } 
}
