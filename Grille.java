import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;


class Grille extends JPanel {

	
	private int taille_;											//!\ Dimensions de la grille : 0..taille_ lignes et 0..taille_ colonnes
	private int dim_; 												//!\ Dimensions de la fenêtre d'affichage en pixels
	private Cellule tab_[];											
	private ClasseUnion classe_;									
	private ArrayList<Integer> posBaseJ1_, posBaseJ2_;				//!\ Deux ArrayList contenant les positions des bases des deux joueurs
	private int xCentreJ1_, yCentreJ1_, xCentreJ2_, yCentreJ2_;			//!\ Les centre de masse des deux joueurs


	//!\ Constructeur de la grille
	public Grille(int t, int nbBase_) {

		//------------------------------------------------------------------- Instanciation du contenue de la grille
		taille_ = t;
		dim_ = 50*(taille_);
		tab_ = new Cellule[taille_*taille_];
		classe_ = new ClasseUnion(t);
		posBaseJ1_ = new ArrayList<Integer>();
		posBaseJ2_ = new ArrayList<Integer>();
		Dimension d_ = new Dimension(dim_, dim_);


		//-------------------------------------------------------------------  Paramétrage graphique de la grille
		setBackground(Color.BLACK);
		setPreferredSize(d_);


		//-------------------------------------------------------------------  Ajout d'un conteneur d'éléments en grille
		GridLayout layout = new GridLayout(taille_, taille_,2 ,2);
		setLayout(layout);


		//-------------------------------------------------------------------  Ajout des Cellules dans la grille
		for (int x = 0; x < taille_*taille_; ++x) {
			tab_[x] = new Cellule();
			add(tab_[x]);
		}


		//-------------------------------------------------------------------  Calcule de la distance minimum entre chaque base
		int distMin_ = taille_/nbBase_;

		if (distMin_ == taille_/2)
			--distMin_;


		//-------------------------------------------------------------------  Ajout des bases pour le joueur 1
		for (int i = 0; i < nbBase_; ++i){

			int nb1_ = 0;
			int nb2_ = 0;
			boolean possible_ = true;

			// On modifie les coordonnée de la base tant que lesdits coordonnées ne sont pas celles d'une base déjà existante 
			// et que la distance min entre la nouvelle base et toutes les bases déjà existante est bien supérieur ou égale à distMin_
			do{
				possible_ = true;
				nb1_ = (int)(Math.random() * taille_);
				nb2_ = (int)(Math.random() * taille_);

				for (int j = 0; j < posBaseJ1_.size(); ++j) {
					if (distanceCases(nb1_, nb2_, posBaseJ1_.get(j)%taille_, posBaseJ1_.get(j)/taille_) < distMin_) {
						possible_ = false;
						break;
					}
				}
			}while(getVal(nb1_, nb2_) != 0 || !possible_);
				
			// On transforme la Cellule en base si les conditions sont res_pectées et on ajoute ses coordonnées à l'ArrayList de stockage des bases
			tab_[nb1_+nb2_*taille_].setBase(1);
			posBaseJ1_.add(nb1_+nb2_*taille_);
		}

		//-------------------------------------------------------------------  Ajout des bases pour le joueur 2
		for (int i = 0; i < nbBase_; ++i){

			int nb1_ = 0;
			int nb2_ = 0;
			boolean possible_ = true;

			// On modifie les coordonnée de la base tant que lesdits coordonnées ne sont pas ceux d'une base déjà existante 
			// et que la distance min entre la nouvelle base et toutes les bases déjà existante est bien supérieur ou égale à distMin_
			do{

				possible_ = true;

				nb1_ = (int)(Math.random() * taille_);
				nb2_ = (int)(Math.random() * taille_);

				for (int j = 0; j < posBaseJ2_.size(); ++j) {
					if (distanceCases(nb1_, nb2_, posBaseJ2_.get(j)%taille_, posBaseJ2_.get(j)/taille_) < distMin_) {
						possible_ = false;
						break;
					}
				}
			}while(getVal(nb1_, nb2_) != 0 || !possible_);
				
			// On transforme la Cellule en base si les conditions sont res_pectées et on ajoute ses coordonnées à l'ArrayList de stockage des bases
			tab_[nb1_+nb2_*taille_].setBase(2);
			posBaseJ2_.add(nb1_+nb2_*taille_);
		}
		
		//-------------------------------------------------------------------  Calcule du centre de masse des joueurs
		xCentreJ1_ = 0;
		yCentreJ1_ = 0;
		xCentreJ2_ = 0;
		yCentreJ2_ = 0;
		
		// On calcule les centre de masse des deux joueurs
		for (int i = 0; i < nbBase_; ++i) {
			xCentreJ1_ += posBaseJ1_.get(i)%taille_;
			yCentreJ1_ += posBaseJ1_.get(i)/taille_;

			xCentreJ2_ += posBaseJ2_.get(i)%taille_;
			yCentreJ2_ += posBaseJ2_.get(i)/taille_;
		}
		
		xCentreJ1_ /= nbBase_;
		yCentreJ1_ /= nbBase_;
		xCentreJ2_ /= nbBase_;
		yCentreJ2_ /= nbBase_;
	}


	//!\---------------------- Getters

	public int getTaille(){
		return taille_;
	}

	public int getDim() {
		return dim_;
	}

	public Cellule getCellSouris(int x, int y){
		return tab_[((x-1)/50)+((y-1)/50)*taille_];
	}

	public Cellule getCell(int z){
		return tab_[z];
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
	//!\---------------------- Fin Getters


	//------------------------------------------------------------------- Méthodes Obligatoires_

	//!\brief Méthode n°2 afficheComposante
	//!\param x, y : les coordonnées de la case dont pour laquelle la composante sera affiché
	public void afficheComposante(int x, int y){

		if (getVal(x, y) != 0) {

			java.util.Timer t = new java.util.Timer();

			int rac_ = getComp(x,y);
			ArrayList<Integer> tmp_ = new ArrayList<Integer>();

			// On ajoute toutes les Cellules de la composante dans un ArrayList temporaire
			tmp_.addAll(classe_.getTousFils(rac_%taille_,rac_/taille_));
			tmp_.add(rac_);

			// Implémentation d'un timer afin de permettre un affichage en clignotement
			class MonAction extends TimerTask {

				int nbRep_ = 6;

			    public void run() {
			    	if(nbRep_ > 0){
				    	for (int i = 0; i < tmp_.size(); ++i) {
				    		tab_[tmp_.get(i)].colorerTemp();
				    	}
				     	--nbRep_;
				    }
				    else
				    	t.cancel();
		      	}
		    }

		    // Appelle de la fonction pour le clignotement
		    t.scheduleAtFixedRate(new MonAction(),0, 1000);
		}
	}


	//!\brief Méthode n°3 existeCheminCase
	//!\param x, y, z, t : x et y les coordonnées de la première case; z et t les coordonnées de la seconde case
	//!\return un booléen si les deux cases appartiennent à la même composante
	public boolean existeCheminCases(int x, int y, int z, int t){
		return (getComp(x, y) == getComp(z, t));
	}


	//!\brief Méthode n°4 relierCasesMin
	//!\param x, y, z, t : x et y les coordonnées de la première case; z et t les coordonnées de la seconde case;
	//!\return compt_ : le nombre minimum de cases blanche à colorié afin de rejoindre les deux cases. Retourne -1 si les deux cases sont d'une couleur différente
	public int relierCasesMin(int x, int y, int z, int t){

		int compt_ = 0;

		// On vérifie si les deux cases ont la même couleur 
		if(getVal(x, y) == getVal(z, t)){

			ArrayList<Integer> caseImp_ = new ArrayList<Integer>();		// ArrayList permettant d'éviter de retourner sur une case déjà étudié
			ArrayList<Integer> caseAct_ = new ArrayList<Integer>();		// ArrayList de stockage de cases disponibles pour le tour en cours
			ArrayList<Integer> caseWait_ = new ArrayList<Integer>();		// ArrayList de stockage des cases disponibles pour le tour suivant

			caseWait_.add(x+y*taille_);
			caseAct_.add(x+y*taille_);
			caseImp_.add(x+y*taille_);
			
			while(caseWait_.size() > 0) {						

				caseAct_.clear();
				caseAct_.addAll(caseWait_);
				caseWait_.clear();

				for (int a = 0; a < caseAct_.size(); ++a) {

					int xtmp_ = caseAct_.get(a)%taille_;
					int ytmp_ = caseAct_.get(a)/taille_;

					if (distanceCases(xtmp_, ytmp_, z, t) == -1)
						return compt_;

					// Pour chaque case on attribu des valeurs différentes pour la double boucle à suivre afin d'éviter
					// que le programme essaye d'effectuer ses test sur des cases extérieurs au tableau (-1 par exemple)
					ArrayList<Integer> valDebArr_ = new ArrayList<Integer>();
					valDebArr_ = xyDebArr(xtmp_, ytmp_);

					for (int i = valDebArr_.get(1); i <= valDebArr_.get(3); ++i) {
						for (int j = valDebArr_.get(0); j <= valDebArr_.get(2); ++j){

							// Si c'est la case actuelle on l'ajoute au tableau des cases impossibles
							if (i == 0 && j == 0){
								if (!caseImp_.contains((xtmp_+ytmp_*taille_))){
									caseImp_.add((xtmp_+ytmp_*taille_));
								}
								continue;
							}
							// Si c'est une case blanche alors...
							else if (getVal(xtmp_+j, ytmp_+i) == 0){

								// Si elle est adjacente à la case d'arriver on retourne le compt_eur+1
								if (distanceCases(xtmp_+j, ytmp_+i, z, t) == 0)
										return ++compt_;

								// Si elle n'appartient pas au cases imposibles on l'ajoute dans le tableau de case pour le tour suivant ainsi qu'au tableau de cases impossible_
								if (!caseImp_.contains(((xtmp_+j)+(ytmp_+i)*taille_))){
									caseWait_.add(((xtmp_+j)+(ytmp_+i)*taille_));
									caseImp_.add(((xtmp_+j)+(ytmp_+i)*taille_));
								}
							}
							// ...sinon si elle est de la même couleur que la case actuelle alors...
							else if (getVal(xtmp_+j, ytmp_+i) == getVal(x, y)) {


								// Si elle n'appartient pas aux cases impossibles alors...
								if (!caseImp_.contains((xtmp_+j)+(ytmp_+i)*taille_)) {

									// On ajoute d'en un tableau temporaires tous les éléments de la composante dont elle fait partie
									int pereComp = getComp((xtmp_+j),(ytmp_+i));
									caseAct_.addAll(classe_.getTousFils(pereComp%taille_, pereComp/taille_));
									caseAct_.add(pereComp);
								}
							}
							//Sinon c'est une case de couleur adverse donc on l'ajoute au tableau des cases impossibles (si elle n'y est pas déjà)
							else{
								if (!caseImp_.contains(((xtmp_+j)+(ytmp_+i)*taille_))){
									caseImp_.add(((xtmp_+j)+(ytmp_+i)*taille_));
								}
							}
						}
					}
			
				}
				// Pour finir on incrémente le compteur à la fin du tour
				++compt_;
			}
		}
		// Sinon les deux cases ne sont pas de la même couleur, ou bien il n'y a pas de passage possible entre les deux cases donc on retourne -1
		return -1;
	}
	

	//!\brief Méthode n°5 nombreEtoiles
	//!\param x, y : les coordonnées de la case pour laquelle on veut connaitre le nombre de bases appartenant à sa composante
	//!\return un entier étant le nombre de base appartenant à la composante
	public int nombreEtoiles(int x, int y){

		int compt_ = 0;
		int rac_ = getComp(x,y);
		ArrayList<Integer> tmp_ = new ArrayList<Integer>();

		tmp_.addAll(classe_.getTousFils(rac_%taille_,rac_/taille_));
		tmp_.add(rac_);

		// Pour tous les éléments de la composante on test si c'est une base et on incrément le compt_eur si c'est le cas
		for (int i = 0; i < tmp_.size(); ++i) {
			if (isBase(tmp_.get(i)%taille_, tmp_.get(i)/taille_))
				compt_++;
		}

		return compt_;
	}


	//!\brief Méthode n°7 relieComposantes
	//!\param x, y, c : x et y les coordonnées de la case à tester; c la couleur de la case à tester
	//!\return un ArrayList d'entier contenant la position des cases concernées
	public ArrayList<Integer> relieComposantes(int x, int y, int c){
		
		ArrayList<Integer> res_ = new ArrayList<Integer>();
		// On attribu des valeurs différentes pour la double boucle à suivre afin d'éviter
		// que le programme essaye d'effectuer ses test sur des cases extérieurs au tableau (-1 par exemple)
		ArrayList<Integer> valXY_ = new ArrayList<Integer>();
		valXY_ = xyDebArr(x, y);

		// Double boucle qui permet de tester toutes les cases adjacentes la case actuelle
		for (int k = valXY_.get(1); k <=  valXY_.get(3); ++k) {
			for (int l =  valXY_.get(0); l <=  valXY_.get(2);  ++l) {

				// Si c'est la case actuelle on la passe (aucun intérêt à la tester)
				if (k == 0 && l == 0) 		
					continue;

				// Si la case adjacente est de la même couleur que la case actuelle et que l'ArrayList temporaire est vide, on ajoute la classe de la case adjacente dans l'ArrayList
				if (getVal(x+l, y+k) == c){

					boolean newComp_ = true;
					// ... pour toutes les valeur de tmp_...
					for (int i = 0; i < res_.size(); ++i) {

						// ... on test si la classe de la case adjacente n'est pas déjà dedans, si ce n'est pas le cas on incrément le compt_eur et on ajoute sa classe dans tmp_
						if (existeCheminCases(x+l, y+k, res_.get(i)%taille_, res_.get(i)/taille_)){
							newComp_ = false;
						}
					}
					if (newComp_) {
						res_.add((x+l)+(y+k)*taille_);
					}
				}
			}
		}

		return res_;

	}

	//!\brief Méthode n°9.1 evaluerCase1
	//!\param x, y, j : x et y les coordonnées de la case à tester; j le joueur pour lequel la fonction est utilisé
	//!\return un entier étant l'index dans la grille de la case à colorié
	public int evaluerCase1(int x, int y, int j){
		if(j == 1){
			if(getVal(x, y) != 0)
				return taille_;
			else
				return distanceCases(x, y, xCentreJ1_, yCentreJ1_);
		}
		else if(j == 2){
			if(getVal(x, y) != 0)
				return taille_;
			else
				return distanceCases(x, y, xCentreJ2_, yCentreJ2_);
		}
		return taille_;
	}

	//------------------------------------------------------------------- Méthodes supplémentaires_

	//!\ Fait l'union entre les cases de coordonnées (x, y) et (z, t)
	public void union(int x, int y, int z, int t){

		classe_.union(x, y, z, t);

	}


	//!\ Retourne la distance entre deux cases (non comprises)
	public int distanceCases(int x, int y, int z, int t){

		int tmp_1 = Math.abs(x-z);
		int tmp_2 = Math.abs(y-t);
		return Math.max(tmp_1, tmp_2) - 1;

	}

	//!\ Permet de récupérer les valeur de x et y pour la recherche autour d'une case
	public ArrayList<Integer> xyDebArr(int x, int y){

		// res_.get(0) = xDeb; res_.get(1) = yDeb; res_.get(2) = xArr; res_.get(3) = yArr
		ArrayList<Integer> res_ = new ArrayList<Integer>();

		if (x == 0){
			if( y == 0){
				res_.add(0); res_.add(0); res_.add(1); res_.add(1);
			}
			else if(y == taille_ - 1){
				res_.add(0); res_.add(-1); res_.add(1); res_.add(0);
			}
			else {
				res_.add(0); res_.add(-1); res_.add(1); res_.add(1);
			}
		}
		else if (x == taille_ - 1){
			if( y == 0){
				res_.add(-1); res_.add(0); res_.add(0); res_.add(1);
			}
			else if(y == taille_ - 1){
				res_.add(-1); res_.add(-1); res_.add(0); res_.add(0);
			}
			else {
				res_.add(-1); res_.add(-1); res_.add(0); res_.add(1);
			}
		}
		else{
			if( y == 0){
				res_.add(-1); res_.add(0); res_.add(1); res_.add(1);
			}
			else if(y == taille_-1){
				res_.add(-1); res_.add(-1); res_.add(1); res_.add(0);
			}
			else {
				res_.add(-1); res_.add(-1); res_.add(1); res_.add(1);
			}
		}
		return res_;
	}
}
