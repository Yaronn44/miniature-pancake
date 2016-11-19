import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;


/**
 * Grille d'un monde gérant l'ensemble des positions libres
 * ou occupées par des choses.
 * 
 * TODO : à compléter.
 */
class Grille extends JPanel {

	
	private int taille_;					// Dimensions de la grille : 0..taille_ lignes et 0..taille_ colonnes
	private int dim_; 						// Dimensions de la fenêtre d'affichage en pixels
	private Cellule tab_[];
	private ClasseUnion classe_;


	public Grille(int t, int nbBase) {

		
		taille_ = t;   						// dimensions pour les positions
		dim_ = 50*(taille_);				// dimensions de la fenêtre
		tab_ = new Cellule[taille_*taille_];
		classe_ = new ClasseUnion(t);

		Dimension d = new Dimension(dim_, dim_);

		setBackground(Color.BLACK);
		setPreferredSize(d);
		GridLayout layout = new GridLayout(taille_, taille_,2 ,2);
		setLayout(layout);
		

		for (int x = 0; x < taille_*taille_; ++x) {
			tab_[x] = new Cellule();
			add(tab_[x]);
		}


		for (int i = 0; i < nbBase; ++i){

			int nb1 = (int)(Math.random() * taille_);
			int nb2 = (int)(Math.random() * taille_);

			while(tab_[nb1+nb2*taille_].getVal() != 0) {
				nb1 = (int)(Math.random() * taille_);
				nb2 = (int)(Math.random() * taille_);
			}

			tab_[nb1+nb2*taille_].setBase(1);
		}


		for (int i = 0; i < nbBase; ++i){

			int nb1 = (int)(Math.random() * taille_);
			int nb2 = (int)(Math.random() * taille_);

			while(tab_[nb1+nb2*taille_].getVal() != 0) {
				nb1 = (int)(Math.random() * taille_);
				nb2 = (int)(Math.random() * taille_);
			}

			tab_[nb1+nb2*taille_].setBase(2);
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
		return tab_[((x-1)/50)+((y-1)/50)*taille_];
	}

	public int getVal(int x, int y){
		return tab_[x+y*taille_].getVal();
	}

	public boolean isBase(int x, int y){
		return tab_[x + y*taille_].isBase();
	}

	public int getComp(int x, int y){
		return classe_.classe(x,y);
	}

	public void afficher(int x, int y){
		System.out.println("Pere : "+classe_.classe(x,y));

		int t = getComp(x,y);
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.addAll(classe_.getTousFils(t%taille_, t/taille_));
		for (int i = 0; i < tmp.size(); ++i) {
			System.out.println(tmp.get(i));
		}
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
					if (tab_[l+k*taille_].getVal() == c && classe_.classe(l,k) != classe_.classe(x,y)) {
						classe_.union(x,y,l,k); 														
						return;
				}
			}
		}
	}


	public void afficheComposante(int x, int y){						// x et y les coordonnées de la Cellule dans le tableau 
		if (tab_[x+y*taille_].getVal() != 0) {

			java.util.Timer t = new java.util.Timer();

			int rac = getComp(x,y);
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			tmp.addAll(classe_.getTousFils(rac%taille_,rac/taille_));
			tmp.add(rac);

			class MonAction extends TimerTask {

				int nbRep = 6;

			    public void run() {
			    	if(nbRep > 0){
				    	for (int i = 0; i < tmp.size(); ++i) {
				    		tab_[tmp.get(i)].colorerTemp();
				    	}
				     	--nbRep;
				    }
				    else
				    	t.cancel();
		      	}
		    }

		    t.scheduleAtFixedRate(new MonAction(),0, 1000);
		}
	}
	

	/**
	 * @brief Fait un truc de fonction
	 *
	**/
	public int relieComposante(int x, int y, int c){				// x et y les coordonnées de la Cellule dans le tableau 

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


	public int compTest(int y, int x, int y1, int x1, int y2, int x2, int c){

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

		int tmp = -1;
		int compt = 0;

		for (int k = y1; k <= y2; ++k) {
			for (int l = x1; l <= x2;  ++l) {

				if (k == y && l == x) 		
					continue;

				if (tab_[l+k*taille_].getVal() == c && tmp == -1)
					tmp = classe_.classe(l,k);

				if (tab_[l+k*taille_].getVal() == c && classe_.classe(l,k) != tmp) 
					compt++;
			}
		}
		if (tmp != -1)
			compt += 1;

		return compt;
	}


	public int nombreEtoiles(int x, int y){

		int compt = 0;
		int rac = getComp(x,y);
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.addAll(classe_.getTousFils(rac%taille_,rac/taille_));
		tmp.add(rac);

		for (int i = 0; i < tmp.size(); ++i) {
			if (isBase(tmp.get(i)%taille_, tmp.get(i)/taille_))
				compt++;
		}

		return compt;
	}

	public int relieCaseMin(int x, int y, int z, int t){
		if (tab_[x+y*taille_].getVal() == tab_[z+t*taille_].getVal()) {
			int tmp1 = ((x+1+y+1)/2) - ((z+1+t+1)/2);
			int tmp2 = (Math.abs(x+1-y+1)/2) - (Math.abs(z+1-t+1)/2);
			return (Math.abs(tmp1)+Math.abs(tmp2)+Math.abs(tmp1-tmp2))/2 - 1;
		}
		return 0;
	}

	public boolean existeCheminCase(int x, int y, int z, int t){
		ArrayList<Integer> chemin = new ArrayList<Integer>();
		for (int i = -1; i < 1; ++i) {
			for (int j = -1; j < 1; ++j){
				if (tab_[(x+i)+(y+j)*taille_].getVal() == 0){
					if (i == 0 && j == 0) 
						continue;

					chemin.add((x+i)+(y+j)*taille_);
				}
			}
		}
		if (chemin.size() == 0) {
			return false;
		}
		else{
			Collections.sort(chemin, new Comparator<Fruit>() {
			        @Override
			        public int compare(Fruit fruit2, Fruit fruit1){

			            return  tab_[.fruitName.compareTo(fruit2.fruitName);
			        }
			    });
			for (int i = 0; i < 8; ++i) {
				
			}
		}
	}

}
