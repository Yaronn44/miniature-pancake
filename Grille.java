import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * Grille d'un monde gérant l'ensemble des positions libres
 * ou occupées par des choses.
 * 
 * TODO : à compléter.
 */
public class Grille extends JPanel {

	
	private int taille_;					// Dimensions de la grille : 0..taille_ lignes et 0..taille_ colonnes
	private int dim_; 						// Dimensions de la fenêtre d'affichage en pixels
	private Cellule tab[][];


	public Grille(int t) {

		
		taille_ = t;   						// dimensions pour les positions
		dim_ = 50*(taille_);				// dimensions de la fenêtre

		tab = new Cellule[taille_][taille_];
		Dimension d = new Dimension(dim_, dim_);

		setBackground(Color.BLACK);
		setPreferredSize(d);
		GridLayout layout = new GridLayout(taille_, taille_,2 ,2);
		setLayout(layout);
		

		for (int y = 0; y < taille_; ++y) {
			for (int x = 0; x<taille_; ++x) {
				tab[x][y] = new Cellule();
				add(tab[x][y]);
			}
		}

		tab[0][0].setBase(1);
		tab[9][9].setBase(2);
	}


	public int getDim() {
		return dim_;
	}


	public Cellule getCell(int x, int y){
		return tab[(x-1)/50][(y-1)/50];
	}


	public void dessiner() {
		repaint();  // appel de paintComponent redéfinie ci-après
	}


	public void afficheComposante(){

	}

	
}
