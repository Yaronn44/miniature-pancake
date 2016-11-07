/**
 * Classe applicative montrant un EXEMPLE de création d'une grille
 * et de son affichage dans une fenêtre graphique.
 */

import java.awt.*;
import javax.swing.*;
import java.*;

public class Application {

	/**
	 * Point d'entrée du programme exécutable
	 * @param args Paramètre non utilisé
	 */
	public static void main(String[] args) {
		
		// Création et affichage de la fenêtre graphique
		Fenetre fenetre = new Fenetre("Un jeu de connexion", 2);
		fenetre.repaint();
	}



}
