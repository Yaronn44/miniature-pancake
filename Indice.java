import java.util.*;

class Indice{

	private ArrayList<Integer> fils_; 
	private int pere_;

	//!\ Constructeur d'Indice
	public Indice(){
		
		//!\ Initialisation des variables
		fils_ = new ArrayList<Integer>();
		pere_ = -1;
	}

	//!\---------------------- Getters
	public int getPere(){
		return pere_;
	}

	public ArrayList<Integer> getFils(){
		return fils_;
	}
	//!\---------------------- Fin Getters


	//!\ Permet de modifier le père de l'Indice actuel
	public void setPere(int x){
		pere_ = x;
	}


	//!\Permet d'ajouter un fils à l'Indice actuel
	public void ajouterFils(int x){
		fils_.add(x);
	}

	//!\Permet de retirer un fils à l'Indice actuel (jamais utilisé)
	public void retirerFils(int x){
		fils_.remove(x);
	}
}