import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class Cellule extends JPanel {

	private int valeur_;
	private boolean etoile_;


	//!\ Constructeur de Cellule
	public Cellule(){

		//!\ Initialisation des variables
		valeur_ = 0;
		etoile_ = false;

		// Interface graphique de la case
		setBackground(Color.white);
		setPreferredSize(new Dimension(50,50));
	}


	//!\---------------------- Getters
	public int getVal(){
		return valeur_;
	}


	public boolean isBase(){
		return etoile_;
	}	
	//!\---------------------- Fin Getters

	//------------------------------------------------------------------- Méthodes Obligatoires

	//!\brief Méthode n°1 colorerCase
	//!\param val : si = 1 colore la case en bleu, si = 2 colore la case en rouge
	//!\return un booléen afin de savoir si la case à bien été coloré 
	public boolean colorerCase(int val){ 		

		if (valeur_ == 0 && (val == 1 || val == 2)){
			valeur_ = val;

			if (val == 1) 
				setBackground(Color.blue);
			else
				setBackground(Color.red);

			return true;
		}
		return false;
	}


	// ------------------------------------------ Méthodes supplémentaires
	
	//!\ Méthode utile à afficheComposante
	public void colorerTemp(){

		if (getBackground() != Color.green)
			setBackground(Color.green);
		else if(valeur_ == 1)
			setBackground(Color.blue);
		else if(valeur_ == 2)
			setBackground(Color.red);
		else
			setBackground(Color.white);
	}


	//!\ Méthode utile à l'initialisation de la grille de jeu
	public boolean setBase(int val){

		if (valeur_ == 0 && (val ==1 || val == 2)){
			valeur_ = val;
			etoile_ = true;

			if (val == 1) 
				setBackground(Color.blue);
			else
				setBackground(Color.red);

			// Interface graphique de la case transformé en base
			JLabel texte_ = new JLabel("*");
			Font font_ = new Font("Serif", Font.BOLD, 20);
			texte_.setFont(font_);
			texte_.setForeground(Color.BLACK);
			add(texte_);

			return true;
		}
		return false;
	}
}
