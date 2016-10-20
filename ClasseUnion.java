public class ClasseUnion{
	
	private int tab[][];
	private int taille_;

	public ClasseUnion(int t){
		taille_ = t;

		int i = 1;
		for (int y = 0; y < taille_; ++y) {
			for (int x = 0; x<taille_; ++x) {
				tab[x][y] = i;
				++i
			}
		}
	}

	public void Union(int v, int w){
		for (int y = 0; y < taille_; ++y) {
			for (int x = 0; x < taille_; ++x) {

				if (tab[x][y] == w)
					tab[x][y] = v;
			}
		}
	}

	public void Classe(Cellule x){
		
	}
}