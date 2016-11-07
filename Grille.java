import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * Grille d'un monde gérant l'ensemble des positions libres
 * ou occupées par des choses.
 * 
 * TODO : à compléter.
 */
public class Grille extends JPanel {

	
	private int taille_;					// Dimensions de la grille : 0..taille_ lignes et 0..taille_ colonnes
	private int dim_; 						// Dimensions de la fenêtre d'affichage en pixels
	private Cellule tab_[][];
	private ClasseUnion classe_;


	public Grille(int t, int nbBase) {

		
		taille_ = t;   						// dimensions pour les positions
		dim_ = 50*(taille_);				// dimensions de la fenêtre
		tab_ = new Cellule[taille_][taille_];
		classe_ = new ClasseUnion(t);

		Dimension d = new Dimension(dim_, dim_);

		setBackground(Color.BLACK);
		setPreferredSize(d);
		GridLayout layout = new GridLayout(taille_, taille_,2 ,2);
		setLayout(layout);
		

		for (int y = 0; y < taille_; ++y) {
			for (int x = 0; x < taille_; ++x) {
				tab_[x][y] = new Cellule();
				add(tab_[x][y]);
			}
		}

		for (int i = 0; i < nbBase; ++i){

			int nb1 = (int)(Math.random() * taille_);
			int nb2 = (int)(Math.random() * taille_);
			System.out.println(nb1+"  " + nb2);

			while(nb2 == nb1){
				nb2 = (int)(Math.random() * taille_);
			}

			tab_[nb1][nb2].setBase(1);
		}

		for (int i = 0; i < nbBase; ++i){

			int nb1 = (int)(Math.random() * taille_);
			int nb2 = (int)(Math.random() * taille_);

			while(nb2 == nb1){
				nb2 = (int)(Math.random() * taille_);
			}

			tab_[nb1][nb2].setBase(2);
		}
	}

	// ---------------------------------------- Getteurs

	public int getTaille(){
		return taille_;
	}

	public int getDim() {
		return dim_;
	}

	public Cellule getCell(int x, int y){
		return tab_[(x-1)/50][(y-1)/50];
	}

	public int getVal(int x, int y){
		return tab_[x][y].getVal();
	}

	public boolean isBase(int x, int y){
		return tab_[x][y].isBase();
	}


	public int getComp(int x, int y){
		return classe_.classe(x,y);
	}



	// ------------------------------------------ Méthode

	public void union(int x, int y, int c){

		if (x == 0){
				if( y == 0)
					unionTest(y, x, y, x, y+1, x+1, c);
				else if(y == taille_-1)
					unionTest(y, x, y-1, x, y, x+1, c);
				else 
					unionTest(y, x, y-1, x, y+1, x+1, c);	
		}
		else if (x == taille_ - 1){
			if( y == 0)
				unionTest(y, x, y, x-1, y+1, x, c);
			else if(y == taille_-1)
				unionTest(y, x, y-1, x-1, y, x, c);
			else 
				unionTest(y, x, y-1, x-1, y+1, x, c);
		}
		else{
			if( y == 0)
				unionTest(y, x, y, x-1, y+1, x+1, c);
			else if(y == taille_-1)
				unionTest(y, x, y-1, x-1, y, x+1, c);
			else 
				unionTest(y, x, y-1, x-1, y+1, x+1, c);
		}
	}

	public void unionTest(int y, int x, int y1, int x1, int y2, int x2, int c){

		for (int k = y1; k <= y2; ++k) {
				for (int l = x1; l <= x2;  ++l) {

					if (k == y && l == x) 		
						continue;
					if (tab_[l][k].getVal() == c) {

					classe_.union(getComp(x,y), getComp(l,k));
					return;
				}
			}
		}
	}


	public void afficheComposante(int x, int y){				// x et y les coordonnées de la Cellule dans le tableau 
		if (tab_[x][y].getVal() != 0) {
			for (int l = 0; l < taille_; ++l) {
				for (int k = 0; k < taille_; ++k) {
					if (classe_.classe(k, l) == classe_.classe(x, y)){

					}
						// AFFICHAGE EN SURBRILLANCE DES CASES CONCERNEES
				}
			}
		}
	}
	

	/**
	 * @brief Fait un truc de fonction
	 *
	**/
	public boolean relieComposante(int x, int y, int c){				// x et y les coordonnées de la Cellule dans le tableau 
		if (tab_[x][y].getVal() == 0) {

			if (x == 0){
				if( y == 0)
					return compTest(y, x, y, x, y+1, x+1, c);
				else if(y == taille_-1)
					return compTest(y, x, y-1, x, y, x+1, c);
				else 
					return compTest(y, x, y-1, x, y+1, x+1, c);	
			}
			else if (x == taille_ - 1){
				if( y == 0)
					return compTest(y, x, y, x-1, y+1, x, c);
				else if(y == taille_-1)
					return compTest(y, x, y-1, x-1, y, x, c);
				else 
					return compTest(y, x, y-1, x-1, y+1, x, c);
			}
			else{
				if( y == 0)
					return compTest(y, x, y, x-1, y+1, x+1, c);
				else if(y == taille_-1)
					return compTest(y, x, y-1, x-1, y, x+1, c);
				else 
					return compTest(y, x, y-1, x-1, y+1, x+1, c);
			}
			
		}
		return false;
	}

	public boolean compTest(int y, int x, int y1, int x1, int y2, int x2, int c){

		if(y1 > y2){
			y1 = y1 + y2;
			y2 = y1 - y2;
			y1 = y1 - y2;
		}

		if(x1 > x2){
			x1 = x1 + x2;
			x2 = x1 - x2;
			x1 = x1 - x2;
		}

		Vector tabtmp = new Vector();

		for (int k = y1; k <= y2; ++k) {
			for (int l = x1; l <= x2;  ++l) {

				if (k == y && l == x) 		
					continue;

				if (tab_[l][k].getVal() == c) {

					tabtmp.add(classe_.classe(k, l));

					if (tabtmp.size() == 1)
						continue;
					if (tabtmp.get(1) != tabtmp.get(0)) {
						return true;
					}
				}
			}
		}
		return false;
	}


	public int nombreEtoiles(int x, int y){

		int compt = 0;

		for (int k = 0; k < taille_; ++k) {
			for (int l = 0; l < taille_;  ++l) {
				if(classe_.classe(k,l) == classe_.classe(x,y) && tab_[k][l].isBase())
					++compt;
			}
		}
		return compt;
	}
	
}
