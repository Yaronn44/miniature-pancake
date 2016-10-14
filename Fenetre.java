import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.*;

/**
 * Classe dont les instances sont des fenêtres graphiques
 * dérivées de JFrame.
 */
public class Fenetre extends JFrame {
	
	/**
	 * Construteur.
	 * @param titre Titre de la fenêtre afficé dans le bandeau
	 * @param panel Contenu de la fenêtre
	 */
	public Fenetre(String titre) {

		// instanciation de l'instance de JFrame et de son contenu
		super(titre);
		Grille grille = new Grille(Constante.N);
		getContentPane().add(grille);

		// paramétrage de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocation(500,200);

		// affichage
		pack();
		setVisible(true);

		


		JMenuBar bar = new JMenuBar();
		JButton b1 = new JButton("Colorer une case");
		JButton b2 = new JButton("Afficher composante");
		JButton b3 = new JButton("Existe chemin cases");
		JButton b4 = new JButton("Relier Case Min");
		JButton b5 = new JButton("Nombre d'étoiles");
		JButton b6 = new JButton("Afficher le score");
		JButton b7 = new JButton("Relie composante ?");
		JButton b8 = new JButton("Nouvelle partie");

		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				addMouseListener(new MouseAdapter(){
	        		public void mouseClicked(MouseEvent e){
	        			grille.getCell(e.getX(),e.getY()).colorerCase(1);

	        		}
	        	});
        	}
        });

		bar.add(b1);
		bar.add(b2);
		bar.add(b3);
		bar.add(b4);
		bar.add(b5);
		bar.add(b6);
		bar.add(b7);
		bar.add(b8);

		JFrame menu = new JFrame("Menu");
		menu.setSize(1200,62);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setResizable(true);
		menu.setLocation(120, 120);
		menu.setVisible(true);
		menu.setJMenuBar(bar);
	}



	public void nombreEtoile(){
		JTextArea texte = new JTextArea("Nombre d'étoile par joueur : ");
	}

	public void afficheScore(){
		JTextArea scoreJ1 = new JTextArea("Le joueur 1 à : " + " points.");
		JTextArea scoreJ2 = new JTextArea("Le joueur 2 à : " + " points.");
	}


}
