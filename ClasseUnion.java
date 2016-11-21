import java.util.*;

class ClasseUnion{
	

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
		int tv = getTousFils(vRac%taille_, vRac/taille_).size();
		int tw = getTousFils(wRac%taille_, wRac/taille_).size();


		if (vRac != wRac) {
			if (tv <= tw) {
				classe_.get(vRac).setPere(wRac);
				classe_.get(wRac).ajouterFils(vRac);
			}
			else{
				classe_.get(wRac).setPere(vRac);
				classe_.get(vRac).ajouterFils(wRac);
			}
		}

	}

	public int classe(int x, int y){
		if (classe_.get(x+y*taille_).getPere() == -1)
			return (x+y*taille_);
		else{
			return classe(classe_.get(x+y*taille_).getPere()%taille_, classe_.get(x+y*taille_).getPere()/taille_);
			/*	
			int a = classe(classe_.get(x+y*taille_).getPere()%taille_, classe_.get(x+y*taille_).getPere()/taille_);
			classe_.get(x+y*taille_).setPere(a);
			return a;
			*/
		}
	}

	//Getter
	
	public int getTaille(){

		return taille_;
	}


	public ArrayList<Integer> getTousFils(int x, int y){
		
		int t = classe_.get(x+y*taille_).getFils().size();

		if (t == 0)
			return (new ArrayList<Integer>());

		else{
			ArrayList<Integer> tmp = classe_.get(x+y*taille_).getFils();
			for (int i = 0; i < t; ++i)
				tmp.addAll(getTousFils(tmp.get(i)%taille_, tmp.get(i)/taille_));

			return tmp;
		}
	}
}