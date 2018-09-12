
import model.*;

import java.util.ArrayList;

import controller.*;
import utils.*;
import view.ResToImage;
public class M {

	public static void main(String[] args) {
		
		int n = 1001; // la taile de l'abyrinth doit �tre sup�rieur � 3
		
		//le chemin de sauvegarde du l'image de r�sultat
		String save_path = System.getProperty("java.io.tmpdir");
		
        //cr�er un objet Maze
		Maze maze = new Maze(n);

		//Cr�er un g�n�rateur pour ce labyrinthe
		MazeGenerator mg = new MazeGenerator();
		mg.generate(maze,n);

		//cr�er un Path Finder contenant un solveur
		MazePathFinderImp pathFinder = new MazePathFinderImp();

        

		try {
			
			//obtenir le chemin de la solution
			ArrayList<MazeCell> path =  (ArrayList<MazeCell>) pathFinder.findOptimalPath(maze);
			
		 
			//enregistrer et afficher le r�sultat en image BMP
			ResToImage renderer = new ResToImage(maze,path);
			
			renderer.savePNG(save_path+"\\nice_maze.bmp");
			
		} catch (StackOverflowError  e) {
			System.out.println("Sorry : Something is wrong");
		}

	}



}
