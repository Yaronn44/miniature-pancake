public class ClasseUnion{
	
	private int classe_[][];
	private int taille_;

	public ClasseUnion(int t){
		taille_ = t;
		classe_ = new int[t][t];

		int i = 1;
		for (int y = 0; y < taille_; ++y) {
			for (int x = 0; x<taille_; ++x) {
				classe_[x][y] = i;
				++i;
			}
		}
	}

	public void union(int v, int w){  			// v et w les classe des deux composantes à unir
		for (int y = 0; y < taille_; ++y) {
			for (int x = 0; x < taille_; ++x) {

				if (classe_[x][y] == v)
					classe_[x][y] = w;
			}
		}
	}

	public int classe(int x, int y){			// x et y les coordonnées de la Cellule dans le tableau
		return classe_[x][y];
	}

	//Getteurs
	public int getTaille(){
		return taille_;
	}
}