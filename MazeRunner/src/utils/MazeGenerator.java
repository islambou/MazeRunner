package utils;

 
import java.util.Random;

import model.*;
 

/**
 * Un générateur de labyrinthe parfait (garanti d'avoir une sortie)
 * testé sur un labyrinthe de 2500 * 2500 cellules
 * 
 * @author Islam Bouderbala
 *
 */

public class MazeGenerator {
	private MazeCell[][] maze;
	private int size=1001;


    
   
    
    
	/**
	 * Génère un labyrinthe avec une taille (size) sur l'objet Maze fourni
	 *
	 * @param  Maze l'objet où le labyrinthe généré sera implémenté
	 * @param  int  la taille du labyrinthe
	 * @return void.
	 */
	
	public void generate (Maze maze, int size) {
		this.size=size;		
		this.maze = maze.getMaze();
		populate_maze(this.size);
		init_maze(this.size);
		generate(0 , this.size-1 , 0 , this.size-1 );

	}

	private void init_maze(int width) {
		//définir les murs de la frontière
		for( int i = 0 ; i < width ; i++) {
			maze[0][i].setCellState(MazeCell.MazeCellState.WALL);
			maze[width-1][i].setCellState(MazeCell.MazeCellState.WALL);
		}
		for( int i = 0 ; i < width ; i++) {
			maze[i][0].setCellState(MazeCell.MazeCellState.WALL);
			maze[i][width-1].setCellState(MazeCell.MazeCellState.WALL);
		}
		//définir l'entrée et la sortie
		maze[1][0].setCellState(MazeCell.MazeCellState.ENTRANCE);
		maze[width-2][width-1].setCellState(MazeCell.MazeCellState.EXIT);
	}
	
	/**
	 * remplir le labyrinthe avec des objets MazeCell (empty)
	 * 
	 */
	private void populate_maze(int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				this.maze[i][j] = new MazeCell(i, j, MazeCell.MazeCellState.EMPTY);
			}
		}
	}
	

   /**
    * appels récursifs indirects pour générer le labyrinthe en divisant une partie de celui-ci
    * soit verticalement ou horizontalement
    * https://en.wikipedia.org/wiki/Maze_generation_algorithm
    */
	private void  generate (int x1 , int x2  , int y1 , int y2 ) {
		//si la largeur ou la hauteur <3 MazeCells ne coupe plus
		
		if ((x2-x1) <=3 || (y2-y1)<=3) return ;
		
		//si la hauteur est plus grande que la largeur coupée horizontalement sinon couper verticalement
		
		if((y2-y1)> (x2-x1))
			cut_h( x1 ,  x2  ,  y1 ,  y2 ) ;
		else 
			cut_v( x1 ,  x2  ,  y1 ,  y2 );
			
		
		
	
		
	}
	

	
	
	/**
	 * couper la zone horizontalement et laisser un chemin
	 * s'assurer que la construction du mur de cette zone ne bloque pas d'autres sorties
	 * 
	 * @param x1  coordonnée gauche de la zone
	 * @param x2  coordonnée droite de la zone
	 * @param y1  coordonnée haut de la zone
	 * @param y2  coordonnée bas de la zone
	 */
	private void cut_h(int x1 , int x2  , int y1 , int y2 ) {
		boolean hole_made=false;
		int i=0;
		Random rn = new Random();
		
		int r= y1+ (rn.nextInt(((y2-y1)/2)-1)+1)*2;
		int g =x1+ rn.nextInt(x2-x1-1)+1;
		
		
		for( i = x1+1 ; i < x2 ; i++) {
			maze[i][r].setCellState(MazeCell.MazeCellState.WALL);
		}
		
		//vérifier, si j'ai bloqué un trou, en faire un autre  au même endroit j'ai bloqué
		if((x2 < size)
				&&(maze[x2][r].getCellState()==MazeCell.MazeCellState.EMPTY)) {
					maze[x2-1][r].setCellState(MazeCell.MazeCellState.EMPTY);	
					hole_made=true;
				}
		if((x1 >0)
				&&(maze[x1][r].getCellState()==MazeCell.MazeCellState.EMPTY)) {
					maze[x1+1][r].setCellState(MazeCell.MazeCellState.EMPTY);	
					hole_made=true;
						}
				
		//sinon chosi un endroit aléatoire (variable g)
				if(!hole_made)
		maze[g][r].setCellState(MazeCell.MazeCellState.EMPTY);
		generate (x1 , x2  , y1 , r  ) ;
		generate (x1 , x2  , r , y2  ) ;
	 
	}  

	/**
	 * couper la zone verticallement et laisser un chemin
	 * le choix de position de la coupure est choisie aléatoirment (par le variable r)
	 * s'assurer que la construction du mur de cette zone ne bloque pas d'autres sorties
	 * 
	 * @param x1  coordonnée gauche de la zone
	 * @param x2  coordonnée droite de la zone
	 * @param y1  coordonnée haut de la zone
	 * @param y2  coordonnée bas de la zone
	 */
	private void cut_v(int x1 , int x2  , int y1 , int y2 ) {
		boolean hole_made=false; //Ai-je fait un trou dans le mur ou pas encore

		Random rn = new Random();
		int r= x1+ (rn.nextInt(((x2-x1)/2)-1)+1)*2;
		int g =y1+ rn.nextInt(y2-y1-1)+1;
		
	
		for( int i = y1+1 ; i < y2 ; i++) {
			maze[r][i].setCellState(MazeCell.MazeCellState.WALL);
		}
		
		//vérifier, si j'ai bloqué un trou, en faire un autre  au même endroit j'ai bloqué
		if((y2 < size)
		&&(maze[r][y2].getCellState()==MazeCell.MazeCellState.EMPTY)) {
			maze[r][y2-1].setCellState(MazeCell.MazeCellState.EMPTY);	
			hole_made=true;
		}
		if((y1 >0)
				&&(maze[r][y1].getCellState()==MazeCell.MazeCellState.EMPTY)) {
					maze[r][y1+1].setCellState(MazeCell.MazeCellState.EMPTY);	
					hole_made=true;
				}
		
		//sinon chosi un endroit aléatoire (variable g)
		if(!hole_made)
		maze[r][g].setCellState(MazeCell.MazeCellState.EMPTY);

		
		generate (x1 , r  , y1 , y2  ) ;
		generate (r , x2  , y1 , y2  ) ;
		
	
	}

	
	


  

    
    
    
   	

/**
 * 
 * @return un labyrinthe généré en tant que matrice de MazeCell
 */
	public MazeCell[][] getMaze() {
		return maze;
	}

 
    
   
    
    

}


 
 
 
 
 
 
 
 
 
