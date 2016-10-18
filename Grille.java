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
		dim_ = Constante.Pix*(taille_);		// dimensions de la fenêtre

		tab = new Cellule[taille_][taille_];


		//setPreferredSize(new Dimension(dim_,dim_));
		setBackground(Color.BLACK);
		setLayout(new GridLayout(taille_,taille_,2,2));


		for (int y = 0; y < taille_; y++) {
			for (int x = 0; x<taille_; ++x) {
				tab[x][y] = new Cellule();
				add(tab[x][y]);
			}
		}
	}

	public int getDim() {
		return dim_;
	}

	public Cellule getCell(int x, int y){
		return tab[(x-2)/50][(y-34)/50];
	}

	public void dessiner() {
		repaint();  // appel de paintComponent redéfinie ci-après
	}

	public void afficheComposante(){

	}

	
}
