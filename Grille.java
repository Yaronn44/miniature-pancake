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
	private ArrayList<Integer> posBaseJ1, posBaseJ2;
	private int xCentreJ1, yCentreJ1, xCentreJ2, yCentreJ2;


	public Grille(int t, int nbBase) {

		
		taille_ = t;   						// dimensions pour les positions
		dim_ = 50*(taille_);				// dimensions de la fenêtre
		tab_ = new Cellule[taille_*taille_];
		classe_ = new ClasseUnion(t);
		posBaseJ1 = new ArrayList<Integer>();
		posBaseJ2 = new ArrayList<Integer>();

		Dimension d = new Dimension(dim_, dim_);

		setBackground(Color.BLACK);
		setPreferredSize(d);
		GridLayout layout = new GridLayout(taille_, taille_,2 ,2);
		setLayout(layout);
		

		for (int x = 0; x < taille_*taille_; ++x) {
			tab_[x] = new Cellule();
			add(tab_[x]);
		}

		int distMin = taille_/nbBase;

		for (int i = 0; i < nbBase; ++i){
			int nb1 = 0;
			int nb2 = 0;
			boolean possible = true;

			do{

				possible = true;

				nb1 = (int)(Math.random() * taille_);
				nb2 = (int)(Math.random() * taille_);

				for (int j = 0; j < posBaseJ1.size(); ++j) {
					if (distanceCase(nb1, nb2, posBaseJ1.get(j)%taille_, posBaseJ1.get(j)/taille_) < distMin) {
						possible = false;
						break;
					}
				}
			}while(tab_[nb1+nb2*taille_].getVal() != 0 || !possible);
				
			tab_[nb1+nb2*taille_].setBase(1);
			posBaseJ1.add(nb1+nb2*taille_);
		}

		for (int i = 0; i < nbBase; ++i){
			int nb1 = 0;
			int nb2 = 0;
			boolean possible = true;

			do{

				possible = true;

				nb1 = (int)(Math.random() * taille_);
				nb2 = (int)(Math.random() * taille_);

				for (int j = 0; j < posBaseJ2.size(); ++j) {
					if (distanceCase(nb1, nb2, posBaseJ2.get(j)%taille_, posBaseJ2.get(j)/taille_) < distMin) {
						possible = false;
						break;
					}
				}
			}while(tab_[nb1+nb2*taille_].getVal() != 0 || !possible);
				
			tab_[nb1+nb2*taille_].setBase(2);
			posBaseJ2.add(nb1+nb2*taille_);
		}
		
		xCentreJ1 = 0;
		yCentreJ1 = 0;
		xCentreJ2 = 0;
		yCentreJ2 = 0;
		
		for (int i = 0; i < nbBase; ++i) {
			xCentreJ1 += posBaseJ1.get(i)%taille_;
			yCentreJ1 += posBaseJ1.get(i)/taille_;

			xCentreJé += posBaseJ2.get(i)%taille_;
			yCentreJé += posBaseJ2.get(i)/taille_;
		}
		
		xCentreJ1 /= nbBase;
		yCentreJ1 /= nbBase;
		xCentreJ2 /= nbBase;
		yCentreJ2 /= nbBase;
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


	public int getComp(int z){
		return classe_.classe(z%taille_, z/taille_);
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


	// ------------------------------------------ Méthodes Demandées

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


	public boolean existeCheminCases(int x, int y, int z, int t){
		return (getComp(x, y) == getComp(z, t));
	}


	public int relieCaseMin(int x, int y, int z, int t, int c1, int c2){

		int xDep = 0;
		int yDep = 0;
		int xArr = 0;
		int yArr = 0;
		int compt = 0;

		if(c1 == c2){

			ArrayList<Integer> caseImp = new ArrayList<Integer>();
			ArrayList<Integer> caseWait = new ArrayList<Integer>();
			ArrayList<Integer> caseAcc = new ArrayList<Integer>();

			caseAcc.add(x+y*taille_);
			caseWait.add(x+y*taille_);
			caseImp.add(x+y*taille_);
			

			for (int v = 0; v < taille_*taille_; ++v) {						

				caseWait.clear();
				caseWait.addAll(caseAcc);
				int tour = caseWait.size();
				caseAcc.clear();

				for (int a = 0; a < tour; ++a) {

					int xTmp = caseWait.get(a)%taille_;
					int yTmp = caseWait.get(a)/taille_;

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

					for (int i = yDep; i <= yArr; ++i) {
						for (int j = xDep; j <= xArr; ++j){
							if (i == 0 && j == 0) 
								continue;

							if (tab_[(xTmp+j)+(yTmp+i)*taille_].getVal() == 0){

								if (distanceCase(xTmp+j, yTmp+i, z, t) == 0)
										return ++compt;

								if (!caseImp.contains(((xTmp+j)+(yTmp+i)*taille_))){
									caseAcc.add(((xTmp+j)+(yTmp+i)*taille_));
									caseImp.add(((xTmp+j)+(yTmp+i)*taille_));
								}
							}
							else if (tab_[(xTmp+j)+(yTmp+i)*taille_].getVal() == c1) {

								ArrayList<Integer> tabComp = new ArrayList<Integer>();

								if (!caseImp.contains((xTmp+j)+(yTmp+i)*taille_)) {

									int pereComp = getComp((xTmp+j),(yTmp+i));
									tabComp.addAll(classe_.getTousFils(pereComp%taille_, pereComp/taille_));
									tabComp.add(pereComp);

									int xDep2 = 0;
									int yDep2 = 0;
									int xArr2 = 0;
									int yArr2 = 0;
									int xTmp2 = 0;
									int yTmp2 = 0;

									for (int f = 0; f < tabComp.size(); ++f) {
										xTmp2 = tabComp.get(f)%taille_;
										yTmp2 = tabComp.get(f)/taille_;
										if (distanceCase(xTmp2, yTmp2, z, t) == -1)
											return compt;
									}

									for (int f = 0; f < tabComp.size(); ++f) {
										xTmp2 = tabComp.get(f)%taille_;
										yTmp2 = tabComp.get(f)/taille_;
										if (distanceCase(xTmp2, yTmp2, z, t) == -1)
											return compt;

										if (xTmp2 == 0){
											if( yTmp2 == 0){
												xDep2 = 0; yDep2 = 0; xArr2 = 1; yArr2 = 1;
											}
											else if(yTmp2 == taille_ - 1){
												xDep2 = 0; yDep2 = -1; xArr2 = 1; yArr2 = 0;
											}
											else {
												xDep2 = 0; yDep2 = -1; xArr2 = 1; yArr2 = 1;
											}
										}
										else if (xTmp2 == taille_ - 1){
											if( yTmp+i == 0){
												xDep2 = -1; yDep2 = 0; xArr2 = 0; yArr2 = 1;
											}
											else if(yTmp2 == taille_ - 1){
												xDep2 = -1; yDep2 = -1; xArr2 = 0; yArr2 = 0;
											}
											else {
												xDep2 = -1; yDep2 = -1; xArr2 = 0; yArr2 = 1;
											}
										}
										else{
											if( yTmp2 == 0){
												xDep2 = -1; yDep2 = 0; xArr2 = 1; yArr2 = 1;
											}
											else if(yTmp2 == taille_-1){
												xDep2 = -1; yDep2 = -1; xArr2 = 1; yArr2 = 0;
											}
											else {
												xDep2 = -1; yDep2 = -1; xArr2 = 1; yArr2 = 1;
											}
										}

										for (int i2 = yDep2; i2 <= yArr2; ++i2) {
											for (int j2 = xDep2; j2 <= xArr2; ++j2){
												if (i2 != 0 || j2 != 0) {

													if (tab_[(xTmp2+j2)+(yTmp2+i2)*taille_].getVal() == 0){

														if(distanceCase(xTmp2+j2, yTmp2+i2, z, t) == 0)
															return ++compt;

														if (!caseImp.contains(((xTmp2+j2)+(yTmp2+i2)*taille_))){
															caseAcc.add(((xTmp2+j2)+(yTmp2+i2)*taille_));
															caseImp.add(((xTmp2+j2)+(yTmp2+i2)*taille_));
														}
													}
													else if (tab_[(xTmp2+j2)+(yTmp2+i2)*taille_].getVal() != c1){

														if (!caseImp.contains(((xTmp+j2)+(yTmp+i2)*taille_)))
															caseImp.add(((xTmp2+j2)+(yTmp2+i2)*taille_));
													}
												}
												else{
													if (!caseImp.contains(((xTmp2+j2)+(yTmp2+i2)*taille_))){
														caseImp.add(((xTmp2+j2)+(yTmp2+i2)*taille_));
													}
												}
											}
										}
									}
								}
							}
							else{
								if (!caseImp.contains(((xTmp+j)+(yTmp+i)*taille_))){
									caseImp.add(((xTmp+j)+(yTmp+i)*taille_));
								}
							}
						}
					}
					if (caseAcc.isEmpty()){
						caseImp.add((xTmp+yTmp*taille_));
					}
				}
				++compt;
			}
		}
		return 0;
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


	public int relieComposante(int x, int y, int c){				// x et y les coordonnées de la Cellule dans le tableau 

		int tmp = -1;
		int compt = 0;
		int xDep = 0;
		int yDep = 0;
		int xArr = 0;
		int yArr = 0;

		if (x == 0){
			if( y == 0){
				xDep = 0; yDep = 0; xArr = 1; yArr = 1;
			}
			else if(y == taille_ - 1){
				xDep = 0; yDep = -1; xArr = 1; yArr = 0;
			}
			else {
				xDep = 0; yDep = -1; xArr = 1; yArr = 1;
			}
		}
		else if (x == taille_ - 1){
			if( y == 0){
				xDep = -1; yDep = 0; xArr = 0; yArr = 1;
			}
			else if(y == taille_ - 1){
				xDep = -1; yDep = -1; xArr = 0; yArr = 0;
			}
			else {
				xDep = -1; yDep = -1; xArr = 0; yArr = 1;
			}
		}
		else{
			if( y == 0){
				xDep = -1; yDep = 0; xArr = 1; yArr = 1;
			}
			else if(y == taille_-1){
				xDep = -1; yDep = -1; xArr = 1; yArr = 0;
			}
			else {
				xDep = -1; yDep = -1; xArr = 1; yArr = 1;
			}
		}

		for (int k = yDep; k <= yArr; ++k) {
			for (int l = xDep; l <= xArr;  ++l) {

				if (k == 0 && l == 0) 		
					continue;

				if (tab_[(x+l)+(y+k)*taille_].getVal() == c && tmp == -1)
					tmp = classe_.classe(x+l, y+k);

				if (tab_[(x+l)+(y+k)*taille_].getVal() == c && !existeCheminCases(x+l, y+k, tmp%taille_, tmp/taille_)) 
					compt++;
			}
		}
		if (tmp != -1)
			compt += 1;

		return compt;
	}

	public int evaluerCase1(int x, int y, int j){
		if(j == 1){
			if(getVal(x, y) != j)
				return taille_;
			else
				return distanceCase(x, y, xCentreJ1, yCentreJ1);
		}
		else if(j == 2){
			if(getVal(x, y) != j)
				return taille_;
			else
				return distanceCase(x, y, xCentreJ2, yCentreJ2);
		}
	}

	// ------------------------------------------ Méthodes supplémentaires

	public void union(int x, int y, int c){

	
		int xDep = 0;
		int yDep = 0;
		int xArr = 0;
		int yArr = 0;

		if (x == 0){
			if( y == 0){
				xDep = 0; yDep = 0; xArr = 1; yArr = 1;
			}
			else if(y == taille_ - 1){
				xDep = 0; yDep = -1; xArr = 1; yArr = 0;
			}
			else {
				xDep = 0; yDep = -1; xArr = 1; yArr = 1;
			}
		}
		else if (x == taille_ - 1){
			if( y == 0){
				xDep = -1; yDep = 0; xArr = 0; yArr = 1;
			}
			else if(y == taille_ - 1){
				xDep = -1; yDep = -1; xArr = 0; yArr = 0;
			}
			else {
				xDep = -1; yDep = -1; xArr = 0; yArr = 1;
			}
		}
		else{
			if( y == 0){
				xDep = -1; yDep = 0; xArr = 1; yArr = 1;
			}
			else if(y == taille_-1){
				xDep = -1; yDep = -1; xArr = 1; yArr = 0;
			}
			else {
				xDep = -1; yDep = -1; xArr = 1; yArr = 1;
			}
		}

		for (int k = yDep; k <= yArr; ++k) {
				for (int l = xDep; l <= xArr;  ++l) {

					if (k == 0 && l == 0) 		
						continue;
					
					if (tab_[(x+l)+(y+k)*taille_].getVal() == c && !existeCheminCases(x+l, y+k, x, y)) {
						classe_.union(x, y, x+l, y+k);
						return;
				}
			}
		}
	}


	public int distanceCase(int x, int y, int z, int t){
		int tmp1 = Math.abs(x-z);
		int tmp2 = Math.abs(y-t);
		return Math.max(tmp1, tmp2) - 1;

	}
	

	// A SUPPRIMER NORMALEMENT (aucune utilité)
	public ArrayList<Integer> triTab(ArrayList<Integer> tab, int z, int t){

		ArrayList<Integer> res = new ArrayList<Integer>();	

		while(!tab.isEmpty()){

			int min = taille_;
			int pos = 0;

			for (int i = 0; i < tab.size(); ++i) {

				if (distanceCase(tab.get(i)%taille_, tab.get(i)/taille_, z, t) < min) {
					min = distanceCase(tab.get(i)%taille_, tab.get(i)/taille_, z, t);
					pos = i;
				}
			}

			res.add(tab.get(pos));
			tab.remove(pos);
		}

		return res;
	}

}
