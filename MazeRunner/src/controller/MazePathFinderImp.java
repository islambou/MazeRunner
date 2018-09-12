package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.*;
import utils.*;

public class MazePathFinderImp implements MazePathFinder  {

	@Override
	public List<MazeCell> findOptimalPath(Maze maze) {
		
		Solver solver = new Solver(maze);
		
		List<MazeCell> res = new ArrayList<MazeCell>();
		
		for(Point p:solver.solve()) {
			res.add(new MazeCell(p.x, p.y));
		}
		return res;
 
	}
	
	
 

}
