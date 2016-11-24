import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class Cellule extends JPanel {

	private int case_;
	private boolean etoile_;


	//!\ Constructeur de Cellule
	public Cellule(){

		//!\ Initialisation des variables
		case_ = 0;
		etoile_ = false;

		// Interface graphique de la case
		setBackground(Color.white);
		setPreferredSize(new Dimension(50,50));
	}


	//!\---------------------- Getters
	public int getVal(){
		return case_;
	}


	public boolean isBase(){
		return etoile_;
	}	
	//!\---------------------- Fin Getters


	//!\brief Méthode n°1 colorerCase
	//!\param val : si = 1 colore la case en bleu, si = 2 colore la case en rouge
	//!\return un booléen afin de savoir si la case à bien été coloré 
	public boolean colorerCase(int val){ 		

		if (case_ == 0 && (val == 1 || val == 2 || val == 3)){
			case_ = val;

			if (val == 1) 
				setBackground(Color.blue);
			else if(val == 2)
				setBackground(Color.red);
			else
				setBackground(Color.green);

			return true;
		}
		return false;
	}


	//!\ Méthode utile à afficheComposante
	public void colorerTemp(){

		if (getBackground() != Color.green)
			setBackground(Color.green);
		else if(case_ == 1)
			setBackground(Color.blue);
		else if(case_ == 2)
			setBackground(Color.red);
		else
			setBackground(Color.white);
	}


	//!\ Méthode utile à l'initialisation de la grille de jeu
	public boolean setBase(int val){

		if (case_ == 0 && (val ==1 || val == 2)){
			case_ = val;
			etoile_ = true;

			if (val == 1) 
				setBackground(Color.blue);
			else
				setBackground(Color.red);

			// Interface graphique de la case transformé en base
			JLabel texte = new JLabel("*");
			Font font = new Font("Serif", Font.BOLD, 20);
			texte.setFont(font);
			texte.setForeground(Color.BLACK);
			add(texte);

			return true;
		}
		return false;
	}
}
