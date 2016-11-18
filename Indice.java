import java.util.*;

class Indice{

	private ArrayList<Integer> fils; 
	private int pere;

	public Indice(){
		fils = new ArrayList<Integer>();
		pere = -1;
	}

	public int getPere(){
		return pere;
	}

	public ArrayList<Integer> getFils(){
		return fils;
	}

	public void setPere(int x){
		pere = x;
	}

	public void ajouterFils(int x){
		fils.add(x);
	}

	public void retirerFils(int x){
		fils.remove(x);
	}
}