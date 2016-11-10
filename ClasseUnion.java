import java.util.*;

public class ClasseUnion{
	
	// adresse => valeur (adresse du pere)
	// adresse => 1 valeur + 1 liste chainée (adresses pere + chaine des fils)

	private ArrayList<Indice> classe_;
	private int taille_;

	public ClasseUnion(int t){
		taille_ = t;
		classe_ = new ArrayList<Indice>();
		for (int x = 0; x < taille_*taille_; ++x) {
			classe_.add(new Indice());
		}
	}

	public void union(int x1, int y1, int x2, int y2){  			// v et w les classe des deux composantes à unir
		int vRac = classe(x1,y1);
		int wRac = classe(x2,y2);
		if (vRac != wRac) {
			classe_.get(vRac).setPere(wRac);
			classe_.get(wRac).ajouterFils(vRac);
		}
	}

	public int classe(int x, int y){			// x et y les coordonnées de la Cellule dans le tableau
		if (classe_.get(x+y*taille_).getPere() == -1)
			return (x+y*taille_);

		return classe(classe_.get(x+y*taille_).getPere()%taille_,classe_.get(x+y*taille_).getPere()/taille_);
	}

	//Getter
	public int getTaille(){
		return taille_;
	}

	public void afficher(){
		ArrayList<Integer> tmp = classe_.get(0).getTousFils();
		for (int i = 0; i < tmp.size(); ++i) {
			System.out.println("  "+tmp.get(i));
		}
	}

}