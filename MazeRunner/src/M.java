
import model.*;

import java.util.ArrayList;

import controller.*;
import utils.*;
import view.ResToImage;
public class M {

	public static void main(String[] args) {
		
		int n = 1001; // la taile de l'abyrinth doit être supérieur à 3
		
		//le chemin de sauvegarde du l'image de résultat
		String save_path = System.getProperty("java.io.tmpdir");
		
        //créer un objet Maze
		Maze maze = new Maze(n);

		//Créer un générateur pour ce labyrinthe
		MazeGenerator mg = new MazeGenerator();
		mg.generate(maze,n);

		//créer un Path Finder contenant un solveur
		MazePathFinderImp pathFinder = new MazePathFinderImp();

        

		try {
			
			//obtenir le chemin de la solution
			ArrayList<MazeCell> path =  (ArrayList<MazeCell>) pathFinder.findOptimalPath(maze);
			
		 
			//enregistrer et afficher le résultat en image BMP
			ResToImage renderer = new ResToImage(maze,path);
			
			renderer.savePNG(save_path+"\\nice_maze.bmp");
			
		} catch (StackOverflowError  e) {
			System.out.println("Sorry : Something is wrong");
		}

	}



}
