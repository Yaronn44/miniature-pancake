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
public class Fenetre extends JFrame{

	private Grille grille;
	private JPanel menu;
	private JButton b1, b2, b3, b4, b5, b6, b7, b8;
	private int j1;

	public Fenetre(String titre, int nbBase) {

		//------------------------------------------------------------------- Instanciation de la fenêtre principale et de son contenu
		super(titre);
		grille = new Grille(Constante.N, nbBase);
		menu = new JPanel();
		j1 = 1;

		//------------------------------------------------------------------- Paramétrage de la fenêtre principale
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);


		//------------------------------------------------------------------- Paramétrage du menu
		menu.setLayout(new GridLayout(8,1));
		menu.setVisible(true);
		menu.setPreferredSize(new Dimension(100,grille.getDim()));

		//------------------------------------------------------------------- Instanciation et paramétrage du contenu du menu
		b1 = new JButton("nb étoiles");
		b2 = new JButton("Relie Composante");
		b3 = new JButton("Connaitre Composante");
		b4 = new JButton("Connaitre Val");
		b7 = new JButton("Get Coordonnée");
		b8 = new JButton("Nouvelle partie");

		jouer();

		
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		    				System.out.println(grille.nombreEtoiles((e.getX()-1)/50, (e.getY()-1)/50));
		    				suppr();
		    				jouer();
		        		}
		        });
			}
        });


        b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		    				System.out.println(grille.relieComposante((e.getX()-1)/50, (e.getY()-1)/50, j1));
		    				suppr();
		    				jouer();
		        		}
		        });
			}
        });


        b3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		    				System.out.println(grille.getComp((e.getX()-1)/50, (e.getY()-1)/50));
		    				suppr();
		    				jouer();
		        		}
		        });
			}
        });

        b4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		    				System.out.println(grille.getVal((e.getX()-1)/50, (e.getY()-1)/50));
		    				suppr();
		    				jouer();
		        		}
		        });
			}
        });

        b7.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		        			System.out.println(e.getX()+"  "+e.getY());
		    				suppr();
		    				jouer();
		        		}
		        });
			}
        });

        b8.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent a){
        		dispose();
        		Fenetre f = new Fenetre(titre, nbBase);
        	}
        });

        // Ajout des boutons au menu
        menu.add(b1);
		menu.add(b2);
		menu.add(b3);
		menu.add(b4);
		menu.add(b7);
		menu.add(b8);

		//------------------------------------------------------------------- Affichage graphique de la fenêtre 

		GroupLayout layout = new GroupLayout(this.getContentPane());
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(grille))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(menu))
		);


		layout.setVerticalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(grille) 
					.addComponent(menu))
		);

		getContentPane().setLayout(layout);
		pack();
	}

	public void afficheScore(){
		JTextArea scoreJ1 = new JTextArea("Le joueur 1 à : " + " points.");
		JTextArea scoreJ2 = new JTextArea("Le joueur 2 à : " + " points.");
	}

	public void jouer(){
		grille.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e){		     
    			boolean test = grille.getCell(e.getX(),e.getY()).testVal(j1);
    			if (test) {
    				grille.union((e.getX()-1)/50, (e.getY()-1)/50, j1);
        			if (j1 == 1) 
        				++j1;
        			else if(j1 == 2)
        				--j1;
        		}
    		}
   		});
	}

	public void suppr(){
		MouseListener m[] = grille.getMouseListeners(); 
		if(m.length > 0)
			grille.removeMouseListener(m[0]);
	}
}
