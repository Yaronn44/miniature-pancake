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
			int tmp1 = (z-x);
			int tmp2 = (t-y);
			return (Math.abs(tmp1)+Math.abs(tmp2)+Math.abs(tmp1-tmp2))/2 - 1;
		}
		return 0;
	}

	public boolean existeCheminCase(int x, int y, int z, int t){


		ArrayList<Integer> caseImp = new ArrayList<Integer>();
		ArrayList<Integer> caseWait = new ArrayList<Integer>();
		int xTmp = x;
		int yTmp = y;
		int xDep = 0;
		int yDep = 0;
		int xArr = 0;
		int yArr = 0;
		int xPrec = -1;
		int yPrec = -1;

		do {
			if (xTmp == 0){
				if( yTmp == 0){
					xDep = 0; yDep = 0; xArr = 1; yArr = 1;
				}
				else if(yTmp == taille_ - 1){
					xDep = 0; yDep = -1; xArr = 1; yArr = 0;
				}
				else {
					xDep = 0; yDep = -1; xArr = 1; yArr = 1;
				}
			}
			else if (xTmp == taille_ - 1){
				if( yTmp == 0){
					xDep = -1; yDep = 0; xArr = 0; yArr = 1;
				}
				else if(yTmp == taille_ - 1){
					xDep = -1; yDep = -1; xArr = 0; yArr = 0;
				}
				else {
					xDep = -1; yDep = -1; xArr = 0; yArr = 1;
				}
			}
			else{
				if( yTmp == 0){
					xDep = -1; yDep = 0; xArr = 1; yArr = 1;
				}
				else if(yTmp == taille_-1){
					xDep = -1; yDep = -1; xArr = 1; yArr = 0;
				}
				else {
					xDep = -1; yDep = -1; xArr = 1; yArr = 1;
				}
			}
			
			ArrayList<Integer> caseAcc = new ArrayList<Integer>();

			for (int i = yDep; i <= yArr; ++i) {
				for (int j = xDep; j <= xArr; ++j){
					if (i == 0 && j == 0) 
						continue;

					System.out.println("num boucle : " + ((xTmp+j)+(yTmp+i)*taille_));
					if (tab_[(xTmp+j)+(yTmp+i)*taille_].getVal() == 0 && !caseImp.contains(((xTmp+j)+(yTmp+i)*taille_))){

						if (relieCaseMin(xTmp+j,yTmp+i, z, t) == 0) {
							return true;
						}

						if (!caseWait.contains(((xTmp+j)+(yTmp+i)*taille_))) {
							caseAcc.add(((xTmp+j)+(yTmp+i)*taille_));
						}
						

						if (!caseWait.contains(((xTmp+j)+(yTmp+i)*taille_))){
							caseWait.add((xTmp+j)+(yTmp+i)*taille_);
						}
					}
					else if(tab_[(xTmp+j)+(yTmp+i)*taille_].getVal() != 0) {
						caseImp.add(((xTmp+j)+(yTmp+i)*taille_));
					}
				}
			}

			if (caseAcc.size() == 0){

				caseImp.add((xTmp+yTmp*taille_));

				for (int i = 0; i < caseWait.size(); ++i) {
					System.out.println("case : " + caseWait.get(i));
				}
				if (caseWait.contains((xTmp+yTmp*taille_))) {
					System.out.println(" caseWait : " + (xTmp+yTmp*taille_));
					caseWait.remove(Integer.valueOf(xTmp+yTmp*taille_));
				}
				if (caseWait.size() == 0) {
					break;
				}
				else{
					xTmp = caseWait.get(caseWait.size()-1)%taille_;
					yTmp = caseWait.get(caseWait.size()-1)/taille_;
				}
			}
			else{
				if (caseWait.size() == 0) {
					break;
				}
				else{
					caseAcc = triTab(caseAcc, z, t);
					for (int i = 0; i < caseAcc.size(); ++i) {
						System.out.println("test : " + caseAcc.get(i));
					}
					xTmp = caseAcc.get(0)%taille_;
					yTmp = caseAcc.get(0)/taille_;
					System.out.println("xTmp : " + xTmp);
					System.out.println("yTmp : " + yTmp);

				}
			}

		}while(caseWait.size() > 0);

		return false;
	}

	public ArrayList<Integer> triTab(ArrayList<Integer> tab, int z, int t){
		ArrayList<Integer> res = new ArrayList<Integer>();
		while(tab.size() > 0){
			int min = taille_;
			int pos = 0;
			for (int i = 0; i < tab.size(); ++i) {
				if (relieCaseMin(tab.get(i)%taille_, tab.get(i)/taille_, z, t) < min) {
					min = relieCaseMin(tab.get(i)%taille_, tab.get(i)/taille_, z, t);
					pos = i;
				}
			}
			res.add(tab.get(pos));
			tab.remove(pos);
		}
		return res;
	}

}
