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


	// ------------------------------------------ Méthode

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

	public int distanceCase(int x, int y, int z, int t){
		int tmp1 = Math.abs(x-z);
		int tmp2 = Math.abs(y-t);
		return Math.max(tmp1, tmp2) - 1;

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
			

			for (int v = 0; v < taille_; ++v) {						//9 étant le nb max de distance entre deux cases

				caseWait.clear();
				caseWait.addAll(caseAcc);
				int tour = caseWait.size();
				caseAcc.clear();

				for (int a = 0; a < tour; ++a) {

					int xTmp = caseWait.get(a)%taille_;
					int yTmp = caseWait.get(a)/taille_;
					System.out.println("tour : " + v + " case : " + caseWait.get(a));

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

								System.out.println("case : " + ((xTmp+j)+(yTmp+i)*taille_) + " blanc");

								if (distanceCase(xTmp+j, yTmp+i, z, t) == 0)
										return ++compt;

								if (!caseImp.contains(((xTmp+j)+(yTmp+i)*taille_))){
									caseAcc.add(((xTmp+j)+(yTmp+i)*taille_));
									caseImp.add(((xTmp+j)+(yTmp+i)*taille_));
								}
							}
							else if (tab_[(xTmp+j)+(yTmp+i)*taille_].getVal() == c1) {

								System.out.println("case : " + ((xTmp+j)+(yTmp+i)*taille_) + " bleu");

								ArrayList<Integer> tabComp = new ArrayList<Integer>();

								if (!caseImp.contains((xTmp+j)+(yTmp+i)*taille_)) {

									int pereComp = getComp((xTmp+j),(yTmp+i));
									tabComp.addAll(classe_.getTousFils(pereComp%taille_, pereComp/taille_));
									tabComp.add(pereComp);
									//caseImp.addAll(classe_.getTousFils(pereComp%taille_, pereComp/taille_));
									//caseImp.add(pereComp);

									int xDep2 = 0;
									int yDep2 = 0;
									int xArr2 = 0;
									int yArr2 = 0;
									int xTmp2 = 0;
									int yTmp2 = 0;

									for (int f = 0; f < tabComp.size(); ++f) {
										xTmp2 = tabComp.get(f)%taille_;
										yTmp2 = tabComp.get(f)/taille_;
										System.out.println("	case : " + tabComp.get(f));

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

														System.out.println("case2 : " + ((xTmp2+j2)+(yTmp2+i2)*taille_) + " blanc");
														if(distanceCase(xTmp2+j2, yTmp2+i2, z, t) == 0)
															return ++compt;

														if (!caseImp.contains(((xTmp+j2)+(yTmp+i2)*taille_))){
															caseAcc.add(((xTmp2+j2)+(yTmp2+i2)*taille_));
															caseImp.add(((xTmp2+j2)+(yTmp2+i2)*taille_));
														}
													}
													else if (tab_[(xTmp2+j2)+(yTmp2+i2)*taille_].getVal() != c1){
														System.out.println("case2 : " + ((xTmp2+j2)+(yTmp2+i2)*taille_) + " rouge");

														if (!caseImp.contains(((xTmp+j2)+(yTmp+i2)*taille_)))
															caseImp.add(((xTmp2+j2)+(yTmp2+i2)*taille_));
													}
												}
												else{
													if (distanceCase(xTmp2, yTmp2, z, t) == -1)
														return compt;

													if (!caseImp.contains(((xTmp+j2)+(yTmp+i2)*taille_))){
														caseImp.add(((xTmp2+j2)+(yTmp2+i2)*taille_));
													}
												}
											}
										}
									}
								}
							}
							else{
								System.out.println("case : " + ((xTmp+j)+(yTmp+i)*taille_) + " rouge");
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
/*
	public int relieCaseMin(int x, int y, int z, int t, int c1, int c2){

		int compt = 0;

		int xTmp = x;
		int yTmp = y;
		int xDep = 0;
		int yDep = 0;
		int xArr = 0;
		int yArr = 0;


		if(c1 == c2){

			ArrayList<Integer> caseImp = new ArrayList<Integer>();
			ArrayList<Integer> caseWait = new ArrayList<Integer>();
			do {
				if ( xTmp == z && yTmp == t)
					return compt;
				else{
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

							if ((tab_[(xTmp+j)+(yTmp+i)*taille_].getVal() == 0 || tab_[(xTmp+j)+(yTmp+i)*taille_].getVal() == c1) && !caseImp.contains(((xTmp+j)+(yTmp+i)*taille_))){

								if (distanceCase(xTmp+j,yTmp+i, z, t) == -1) {
										return ++compt;
								}

								if (!caseWait.contains(((xTmp+j)+(yTmp+i)*taille_))) {
									caseAcc.add(((xTmp+j)+(yTmp+i)*taille_));
								}
								
								if (!caseWait.contains(((xTmp+j)+(yTmp+i)*taille_))){
									caseWait.add((xTmp+j)+(yTmp+i)*taille_);
								}
							}
							else if(!caseImp.contains(((xTmp+j)+(yTmp+i)*taille_))) {
								caseImp.add(((xTmp+j)+(yTmp+i)*taille_));
							}
						}
					}

					if (caseAcc.isEmpty()){

						caseImp.add((xTmp+yTmp*taille_));

						if (caseWait.contains((xTmp+yTmp*taille_))) {
							caseWait.remove(Integer.valueOf(xTmp+yTmp*taille_));
						}

						if (caseWait.isEmpty()) {
							break;
						}
						else{
							xTmp = caseWait.get(caseWait.size()-1)%taille_;
							yTmp = caseWait.get(caseWait.size()-1)/taille_;
						}
					}
					else{

						ArrayList<Integer> tabComp = new ArrayList<Integer>();

						for (int i = 0; i < caseAcc.size(); ++i){
							if (tab_[caseAcc.get(i)].getVal() == c1 && !tabComp.contains(Integer.valueOf(getComp(caseAcc.get(i))))) {
								tabComp.add(getComp(caseAcc.get(i)));
							}
						}

						if (!tabComp.isEmpty()) {
							for (int i = 0; i < tabComp.size(); ++i) {
								if (!caseWait.contains(tabComp.get(i))) {
									caseAcc.addAll(classe_.getTousFils(tabComp.get(i)%taille_, tabComp.get(i)/taille_));							//Cases même comp peuvent se retrouver 2 fois dans tab
									caseAcc.add(getComp(tabComp.get(i)));
								}
								
								if (!caseWait.contains(tabComp.get(i))){
									caseWait.add(tabComp.get(i));
								}

								caseAcc.addAll(classe_.getTousFils(tabComp.get(i)%taille_, tabComp.get(i)/taille_));							//Cases même comp peuvent se retrouver 2 fois dans tab
								caseAcc.add(getComp(tabComp.get(i)));
							}
							
						}

						caseAcc = triTab(caseAcc, z, t);

						if (!existeCheminCases(xTmp, yTmp, z, t) && getVal(xTmp, yTmp) != c1) {
							++compt;
						}
						xTmp = caseAcc.get(0)%taille_;
						yTmp = caseAcc.get(0)/taille_;
						System.out.println(xTmp + "   " + yTmp);
					}
				}
			}while(!caseWait.isEmpty());
		}
		return 0;
	}
*/
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
