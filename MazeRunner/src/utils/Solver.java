package utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import model.*;


/**
 * Un solveur pour des labyrinthes parfaits (garanti d'avoir une sortie)
 * testé sur un labyrinthe de 2500 * 2500 cellules
 * 
 * @author Islam Bouderbala
 *
 */
public class Solver {
	
	private MazeCell[][] maze; //une copie du labyrinthe d'origine

	private Point position = new Point(1,1);  //Pour garder la trace de la position actuelle

	private Stack<Point> checkpoints = new Stack<Point>();  //pile de marches vers la sortie

	private boolean out; //indique soit je suis hors du labyrinthe ou pas encore

	Long start_time;  // garder une trace du temps nécessaire pour résoudre le labyrinthe



/**
 * Crée un objet "Solver" qui résoudre le labyrinthe spécifié fourni
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
 * vérifie si je peux aller à le MazeCell spécifié ou non
 * @param x coordonnée x de la MazeCell
 * @param y coordonnée y de la MazeCell
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
	 * Déplacez la position actuelle vers le Mazecell
 * @param x x de MazeCell
 * @param y y de MazeCell
	 */
	
	private void go(int x , int y) {

		position.setLocation(x, y);

		if(i_am_out()) return;
		
		//Marquer la cellule que j'ai passée sur notre labyrinthe local comme un mur,
		//pour que je ne peux pas y retourner

		maze[x][y].setCellState(MazeCell.MazeCellState.WALL);
		
		//si j'ai plusieurs directions à choisir, marquer cet endroit un point de contrôle
		//de sorte que je peux revenir à si je suis coincé
		if(is_branch(x,y,0)) {
			checkpoints.push(new Point(x,y));
		} 

      

		

	}

/**
 * la méthode qui renvoie la liste des MazeCells de l'entrée à la sortie
 * 
 * @return an ArrayList<Point> of the solution
 */
	public ArrayList<Point> solve() {
        //commencer à calculer le temps
		start_time = System.currentTimeMillis();

        /**
         * Tant que je ne suis pas hors du labyrinthe
         * continuez à avancer avec la priorité: vers le bas / droite / gauche / haut 
         * https://en.wikipedia.org/wiki/Maze_solving_algorithm
         */
		while (!out) { 	
			if(can_go(position.x , position.y+1)) go(position.x , position.y+1); else
				if(can_go(position.x+1 , position.y)) go(position.x+1 , position.y); else
					if(can_go(position.x-1 , position.y)) go(position.x-1 , position.y); else
						if(can_go(position.x , position.y-1)) go(position.x , position.y-1);

			/**
			 * si je ne peux pas me déplacer, retournez au dernier point de contrôle
			 */
						else 
						{
							//vérifier si je peux toujours bouger quand je reviens à ce point de contrôle

							if(is_branch(checkpoints.peek().x , checkpoints.peek().y,0)) {
								position.setLocation(checkpoints.peek());    //revenir au dernier point de contrôle                         
							}
							
							//si je ne peux pas bouger, supprimez ce point de contrôle
							//(ce qui signifie supprimer les étapes vers l'impasse)
							else if(checkpoints.size()>1) {
									
								checkpoints.pop();  
								position.setLocation(checkpoints.peek()); //revenir au avant dernier point de contrôle


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
	 * vérifier si cette MazeCell a plusieurs destinations
	 * 
	 * @param x coordonnée x de MazeCell
	 * @param y coordonnée y de MazeCell
	 * @param n nombre de destination nécessaire pour dire que cette MazeCell a plusieurs destinations
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
	 *  retourne si j'ai trouvé la sortie ou non
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


