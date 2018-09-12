package model;

/**
 * M.java
 * Purpose: Creat , Solve and Display a maze.
 *
 * @author Bouderbala Islam
 * @version 1.0
 */

public class Maze
{
    private MazeCell[][] maze;

    private int optimalPathLength = Integer.MAX_VALUE;
    
    private int size;

    public Maze(int N)
    {
        maze = new MazeCell[N][N];
        
        this.size = N;
    }

    public MazeCell[][] getMaze()
    {
        return maze;
    }

    public int getOptimalPathLength()
    {
        return optimalPathLength;
    }

    void setOptimalPathLength(int optimalPathLength)
    {
        this.optimalPathLength = optimalPathLength;
    }


    public int getSize() {
    	return size;
    }


}
