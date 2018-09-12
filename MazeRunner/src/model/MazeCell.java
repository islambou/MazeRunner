package model;



public class MazeCell
{
    private int x;

    private int y;

    private MazeCellState cellState;

    public enum MazeCellState
    {
        EXIT, ENTRANCE, WALL, EMPTY;
    }

    public MazeCell(int x, int y, MazeCellState cellState)
    {
        this.x = x;
        this.y = y;
        this.cellState = cellState;
    }
   
    public MazeCell(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.cellState = MazeCellState.EMPTY;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public MazeCellState getCellState()
    {
        return cellState;
    }

    public void setCellState(MazeCellState cellState)
    {
        this.cellState = cellState;
    };

    @Override
    public String toString()
    {
        return "{" + x + "," + y + "," + cellState + "}";
    }
}
