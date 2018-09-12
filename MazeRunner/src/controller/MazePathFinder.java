package controller;


import java.util.List;
import model.*;
 
public interface MazePathFinder
{
    List<MazeCell> findOptimalPath(Maze maze);
}
