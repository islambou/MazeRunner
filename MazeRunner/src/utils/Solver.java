package utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import model.*;


/**
 * Un solveur pour des labyrinthes parfaits (garanti d'avoir une sortie)
 * test� sur un labyrinthe de 2500 * 2500 cellules
 * 
 * @author Islam Bouderbala
 *
 */
public class Solver {
	
	private MazeCell[][] maze; //une copie du labyrinthe d'origine

	private Point position = new Point(1,1);  //Pour garder la trace de la position actuelle

	private Stack<Point> checkpoints = new Stack<Point>();  //pile de marches vers la sortie

	private boolean out; //indique soit je suis hors du labyrinthe ou pas encore

	Long start_time;  // garder une trace du temps n�cessaire pour r�soudre le labyrinthe



/**
 * Cr�e un objet "Solver" qui r�soudre le labyrinthe sp�cifi� fourni
 * @param maze le labyrinthe que nous voulons trouver sa solution
 */
	public Solver( Maze maze) {
		
		// Cloner le labyrinthe pour pouvoir le modifier librement
		
		this.maze = new MazeCell[maze.getSize()][maze.getSize()] ;
		
	 
		for(int i=0 ; i< maze.getSize(); i++) {
			for(int j=0; j< maze.getSize(); j++) {
				MazeCell c = maze.getMaze()[i][j];
				this.maze[i][j] = new MazeCell(c.getX(),c.getY(),c.getCellState());
			}
		}


	}


/**
 * v�rifie si je peux aller � le MazeCell sp�cifi� ou non
 * @param x coordonn�e x de la MazeCell
 * @param y coordonn�e y de la MazeCell
 * @return boolean vrai si je peux y aller, faux sinon
 */
	private boolean can_go(int x , int y) {

		try {
			if(maze[x][y].getCellState()==MazeCell.MazeCellState.EMPTY) return true;		
		}
		catch(Exception e ) {
			return false;
		}
		return false;
	}

	

	/**
	 * D�placez la position actuelle vers le Mazecell
 * @param x x de MazeCell
 * @param y y de MazeCell
	 */
	
	private void go(int x , int y) {

		position.setLocation(x, y);

		if(i_am_out()) return;
		
		//Marquer la cellule que j'ai pass�e sur notre labyrinthe local comme un mur,
		//pour que je ne peux pas y retourner

		maze[x][y].setCellState(MazeCell.MazeCellState.WALL);
		
		//si j'ai plusieurs directions � choisir, marquer cet endroit un point de contr�le
		//de sorte que je peux revenir � si je suis coinc�
		if(is_branch(x,y,0)) {
			checkpoints.push(new Point(x,y));
		} 

      

		

	}

/**
 * la m�thode qui renvoie la liste des MazeCells de l'entr�e � la sortie
 * 
 * @return an ArrayList<Point> of the solution
 */
	public ArrayList<Point> solve() {
        //commencer � calculer le temps
		start_time = System.currentTimeMillis();

        /**
         * Tant que je ne suis pas hors du labyrinthe
         * continuez � avancer avec la priorit�: vers le bas / droite / gauche / haut 
         * https://en.wikipedia.org/wiki/Maze_solving_algorithm
         */
		while (!out) { 	
			if(can_go(position.x , position.y+1)) go(position.x , position.y+1); else
				if(can_go(position.x+1 , position.y)) go(position.x+1 , position.y); else
					if(can_go(position.x-1 , position.y)) go(position.x-1 , position.y); else
						if(can_go(position.x , position.y-1)) go(position.x , position.y-1);

			/**
			 * si je ne peux pas me d�placer, retournez au dernier point de contr�le
			 */
						else 
						{
							//v�rifier si je peux toujours bouger quand je reviens � ce point de contr�le

							if(is_branch(checkpoints.peek().x , checkpoints.peek().y,0)) {
								position.setLocation(checkpoints.peek());    //revenir au dernier point de contr�le                         
							}
							
							//si je ne peux pas bouger, supprimez ce point de contr�le
							//(ce qui signifie supprimer les �tapes vers l'impasse)
							else if(checkpoints.size()>1) {
									
								checkpoints.pop();  
								position.setLocation(checkpoints.peek()); //revenir au avant dernier point de contr�le


							}

							else {
								System.out.println("sorry i can't solve this maze , i'm stuck at "+position);	
								break;
							}

							
						}

		}
		return new ArrayList<Point>(checkpoints);
		
	}


	/**
	 * v�rifier si cette MazeCell a plusieurs destinations
	 * 
	 * @param x coordonn�e x de MazeCell
	 * @param y coordonn�e y de MazeCell
	 * @param n nombre de destination n�cessaire pour dire que cette MazeCell a plusieurs destinations
	 * @return boolean
	 */
	private boolean is_branch(int x , int y , int n) {

		int nbd =0;
		try {
			if(maze[x+1][y].getCellState()==MazeCell.MazeCellState.EMPTY) nbd++;
			if(maze[x-1][y].getCellState()==MazeCell.MazeCellState.EMPTY) nbd++;
			if(maze[x][y+1].getCellState()==MazeCell.MazeCellState.EMPTY) nbd++;
			if(maze[x][y-1].getCellState()==MazeCell.MazeCellState.EMPTY) nbd++;
			if(nbd>n) return true;
		}
		catch(Exception e) {
			//out of bounds exception
			return false;
		}
		return false;
	}


	/**
	 *  retourne si j'ai trouv� la sortie ou non
	 * @return boolean
	 */

	private boolean i_am_out() {
		if(maze[position.x][position.y+1].getCellState()==MazeCell.MazeCellState.EXIT) {
            checkpoints.push(new Point(position));
			System.out.println("JE SUIS LIBRE !!!!  : "+(System.currentTimeMillis()-start_time)+" ms");
			out = true;
			return true;
		}
		return false;
	}



	
	
	
	
	
	
	

}


