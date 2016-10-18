import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.*;


//------------------------------------------------------------------------ SALE
class ClassA{ 
   public static boolean varGA; 
} 
//------------------------------------------------------------------------ SALE

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

		//---------------------------------------------------- Instanciation de la fenêtre principale et de son contenu
		super(titre);
		Grille grille = new Grille(Constante.N);
		getContentPane().add(grille);

		//---------------------------------------------------- Paramétrage de la fenêtre principale
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocation(500,200);
		setVisible(true);
		pack();

		
		//---------------------------------------------------- Instanciation du menu
		JFrame menu = new JFrame("Menu");

		//---------------------------------------------------- Paramétrage du menu
		menu.setLayout(new GridLayout(8,1));
		menu.setSize(300,400);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setResizable(true);
		menu.setLocation(120, 120);
		menu.setVisible(true);

		//---------------------------------------------------- Instanciation du contenu du menu
		JButton b1 = new JButton("Colorer une case");
		JButton b2 = new JButton("Afficher composante");
		JButton b3 = new JButton("Existe chemin cases");
		JButton b4 = new JButton("Relier Case Min");
		JButton b5 = new JButton("Nombre d'étoiles");
		JButton b6 = new JButton("Afficher le score");
		JButton b7 = new JButton("Relie composante ?");
		JButton b8 = new JButton("Nouvelle partie");

		boolean joue = false;

		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				if (!joue) {
					//joue = true;
					addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		        			grille.getCell(e.getX(),e.getY()).colorerCase(1);
		        			System.out.println(e.getX()+"   "+e.getY());
		        			removeMouseListener(this);
		        		}
		        	});
		        }
			}
        });

        b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				addMouseListener(new MouseAdapter(){
	        		public void mousePressed(MouseEvent e){

	        			grille.getCell(e.getX(),e.getY()).colorerCase(1);
	        			System.out.println(e.getY());
	        			removeMouseListener(this);
	        		}
	        	});
			}
        });

        b8.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent a){
        		dispose();
        		menu.dispose();
        		Fenetre f = new Fenetre(titre);
        	}
        });

		menu.add(b1);
		menu.add(b2);
		menu.add(b3);
		menu.add(b4);
		menu.add(b5);
		menu.add(b6);
		menu.add(b7);
		menu.add(b8);
	}



	public void nombreEtoile(){
		JTextArea texte = new JTextArea("Nombre d'étoile par joueur : ");
	}

	public void afficheScore(){
		JTextArea scoreJ1 = new JTextArea("Le joueur 1 à : " + " points.");
		JTextArea scoreJ2 = new JTextArea("Le joueur 2 à : " + " points.");
	}


}
