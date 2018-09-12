package view;

/**
 * Creates a Maze Visualizer.
 *
 * @return A ResToImage Object.
 */

import java.awt.Color;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import model.Maze;
import model.MazeCell;

public class ResToImage {
	
	private int size=5;
	private MazeCell[][] maze; //le labyrinthe à afficher
	
	private BufferedImage img; // l'image (BMP)
	
	private ArrayList<MazeCell> checkpoints; //La solution
	
	/**
	 * Crée un visualiseur de labyrinth.
	 *
     * @param Maze       le labyrinthe à afficher
     * @param solution   l'ensemble des cellules de labyrinthe de l'entrée à la sortie de type <MazeCell>

	 */
	
	public ResToImage(Maze maze , ArrayList<MazeCell> solution) {
		this.size = maze.getSize();
	
		this.maze = maze.getMaze();
		
		this.checkpoints = solution;
		
		img= new BufferedImage( size, size, BufferedImage.TYPE_INT_RGB );
	}
	
	
	
	
	
	
	/**
	 * imprimer la solution sur l'image
	 */
	
	private void map_path() {
		System.out.println("rendering the image");

		img.setRGB(1, 1, Color.PINK.getRGB() );
	
		for(MazeCell p: checkpoints ) {
				img.setRGB(p.getX(), p.getY(), Color.PINK.getRGB() );
		}
		
	 
		
	
		
		
		System.out.println("Done !");

	}

	/**
	 * imprimer le labyrinthe sur l'image
	 */
    public void map_lab(){
        for (int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
            	if(maze[x][y].getCellState()==MazeCell.MazeCellState.WALL)
                    img.setRGB(x, y, Color.BLACK.getRGB() );
            	else
            		if(maze[x][y].getCellState()==MazeCell.MazeCellState.ENTRANCE)
                        img.setRGB(x, y, Color.RED.getRGB() );
            	else
                	if(maze[x][y].getCellState()==MazeCell.MazeCellState.EXIT)
                            img.setRGB(x, y, Color.GREEN.getRGB() );
                else
                img.setRGB(x, y, Color.WHITE.getRGB() );
            	
            	
            		
            }
        }
        
       
    }
	
	
	
	
	
	/**
	 * enregistre un labyrinthe avec sa solution en tant qu'image bmp
	 * 
	 * @param chemin où l'image doit être enregistrée
	 */
	
	public  void savePNG(final String path ){


		try {
			map_lab();
			map_path();
			RenderedImage rendImage = img;
			ImageIO.write(rendImage, "bmp", new File(path));
			Desktop.getDesktop().open( new File(path));

		} catch ( IOException e) {
			e.printStackTrace();
		}
	}
	

}
