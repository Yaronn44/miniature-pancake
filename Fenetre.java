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
	public Fenetre(String titre, JPanel panel) {

		// instanciation de l'instance de JFrame et de son contenu
		super(titre);
		getContentPane().add(panel);

		Menu menu_ = new Menu();
		setJMenuBar(menu_.getMenu());

		// paramétrage de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(true);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(100,100));

		// affichage
		pack();
		setVisible(true);

		addMouseListener( new MouseListener() {
    		public void mouseClicked(MouseEvent arg0) {}
 
 
    		public void mouseEntered(MouseEvent arg0) {}
 
 
    		public void mouseExited(MouseEvent arg0) {}
 
 
    		public void mousePressed(MouseEvent arg0) {}
 
 
    		public void mouseReleased(MouseEvent arg0) {}
		});
	}

	public void nombreEtoile(){
		JTextArea texte = new JTextArea("Nombre d'étoile par joueur : ");
	}

	public void afficheScore(){
		JTextArea scoreJ1 = new JTextArea("Le joueur 1 à : " + " points.");
		JTextArea scoreJ2 = new JTextArea("Le joueur 2 à : " + " points.");
		add
	}
}
