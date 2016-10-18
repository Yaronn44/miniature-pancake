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
public class Fenetre extends JFrame{
	
	/**
	 * Construteur.
	 * @param titre Titre de la fenêtre afficé dans le bandeau
	 * @param panel Contenu de la fenêtre
	 */

	private Grille grille;
	private JPanel menu;
	private JButton b1, b2, b3, b4, b5, b6, b7, b8;
	private boolean joue; 
	private int j1;


	public Fenetre(String titre) {

		//---------------------------------------------------- Instanciation de la fenêtre principale et de son contenu
		super(titre);
		grille = new Grille(Constante.N);
		menu = new JPanel();
		joue = false;
		j1 = 1;

		//---------------------------------------------------- Paramétrage de la fenêtre principale
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(new GridLayout(1,2)); // CHANGER PAR GROUPLAYOUT

		//---------------------------------------------------- Paramétrage du menu
		menu.setLayout(new GridLayout(8,1));
		menu.setVisible(true);
		menu.setPreferredSize(new Dimension(0,grille.getDim()));

		//---------------------------------------------------- Instanciation et paramétrage du contenu du menu
		b1 = new JButton("Colorer une case");
		b2 = new JButton("Afficher composante");
		b3 = new JButton("Existe chemin cases");
		b4 = new JButton("Relier Case Min");
		b5 = new JButton("Nombre d'étoiles");
		b6 = new JButton("Afficher le score");
		b7 = new JButton("Relie composante ?");
		b8 = new JButton("Nouvelle partie");


		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				if (!joue){
					joue = true;
					addMouseListener(new MouseAdapter(){
        				public void mousePressed(MouseEvent e){
		        			grille.getCell(e.getX(),e.getY()).colorerCase(j1);
		        			removeMouseListener(this);
		        			if (j1 == 1) 
		        				++j1;
		        			else
		        				--j1;
		        			joue = false;
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

		add(grille);
		add(menu);
		pack();

		System.out.println(grille.getCell(1,1).getPreferredSize().getHeight());
	}


	public void nombreEtoile(){
		JTextArea texte = new JTextArea("Nombre d'étoile par joueur : ");
	}

	public void afficheScore(){
		JTextArea scoreJ1 = new JTextArea("Le joueur 1 à : " + " points.");
		JTextArea scoreJ2 = new JTextArea("Le joueur 2 à : " + " points.");
	}
}
