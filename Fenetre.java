import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.*;
import java.util.*;


/**
 * Classe dont les instances sont des fenêtres graphiques
 * dérivées de JFrame.
 */
class Fenetre extends JFrame{

	private Grille grille;
	private JPanel menu;
	private JLabel affScoreJ1, affScoreJ2;
	private JButton b1, b2, b3, b4, b5, b6, b7, b8;
	private int j1, scoreJ1, scoreJ2, nbBase;
	private ArrayList<Integer> listeCoup;

	public Fenetre(String titre, int nbB) {

		//------------------------------------------------------------------- Instanciation de la fenêtre principale et de son contenu
		super(titre);
		grille = new Grille(Constante.N, nbB);
		menu = new JPanel();
		scoreJ1 = 0;
		scoreJ2 = 0;
		affScoreJ1 = new JLabel("Score joueur 1 : "+ scoreJ1);
		affScoreJ2 = new JLabel("Score joueur 2 : "+ scoreJ2);
		j1 = 1;
		nbBase = nbB;
		listeCoup = new ArrayList<Integer>();

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
		b1 = new JButton("Afficher Composante");
		b2 = new JButton("Connaitre Composante");
		b3 = new JButton("Connaitre Val");
		b4 = new JButton("Nombre d'étoiles");
		b5 = new JButton("");
		b6 = new JButton("Relie Composante");
		b7 = new JButton("Get Coordonnée");
		b8 = new JButton("Nouvelle partie");

		jouer();

		// afficheComposante
        b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		    				grille.afficheComposante((e.getX()-1)/50, (e.getY()-1)/50);
		    				suppr();
		    				jouer();
		        		}
		        });
			}
        });

        // CONNAITRE COMP
        b2.addActionListener(new ActionListener(){
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

        // CONNAITRE VAL
        b3.addActionListener(new ActionListener(){
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

		// nombreEtoile
		b4.addActionListener(new ActionListener(){
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

		// RELIE COMPOSANTE
        b6.addActionListener(new ActionListener(){
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
		menu.add(b5);
		menu.add(b6);
		menu.add(b7);
		menu.add(b8);

		//------------------------------------------------------------------- Affichage graphique de la fenêtre 

		GroupLayout layout = new GroupLayout(this.getContentPane());
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(affScoreJ1)
					.addComponent(affScoreJ2)
					.addComponent(grille))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(menu))
		);


		layout.setVerticalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(affScoreJ1))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(affScoreJ2))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(grille) 
					.addComponent(menu))
		);

		getContentPane().setLayout(layout);
		pack();
	}


	public void afficheScore(){

		affScoreJ1.setText("Score joueur 1 : "+ scoreJ1);
		affScoreJ2.setText("Score joueur 2 : "+ scoreJ2);
	}


	public void jouer(){
		grille.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e){		 

    			if (grille.getCell(e.getX(),e.getY()).testVal(j1)){								// colorerCase() s'effectue dans testVal()

    				listeCoup.add((e.getX()-1)/50+((e.getY()-1)/50)*Constante.N);

    				int tmp = grille.relieComposante((e.getX()-1)/50, (e.getY()-1)/50, j1);

    				for (int i = 0; i < tmp; ++i)
    					grille.union((e.getX()-1)/50, (e.getY()-1)/50, j1);

 					int scoreTmp = grille.nombreEtoiles((e.getX()-1)/50, (e.getY()-1)/50);

        			if (j1 == 1){

        				if(scoreTmp > 1 && scoreTmp > scoreJ1){
        					scoreJ1 = scoreTmp;
        					afficheScore();
        				}

        				++j1;
        			}
        			else if(j1 == 2){

        				if(scoreTmp > 1 && scoreTmp > scoreJ2){
        					scoreJ2 = scoreTmp;
        					afficheScore();
        				}

        				--j1;
        			}

        			if (scoreJ1 == nbBase || scoreJ2 == nbBase) {

        				JFrame fin = new JFrame("Bravo !!");

        				fin.setSize(400,100);
        				fin.setLocationRelativeTo(null);
        				fin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
        				fin.setVisible(true);

        				JButton bouton = new JButton("Bravo le joueur n°"+(j1 == 1 ? 2 : 1) +" a remporté la partie !");
        				bouton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent arg0) {fin.dispose();}});
        				fin.add(bouton);
        			}
        			else if(listeCoup.size() == Constante.N*Constante.N - nbBase*2){

        				JFrame fin = new JFrame("Hum....");

        				fin.setSize(400,100);
        				fin.setLocationRelativeTo(null);
        				fin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
        				fin.setVisible(true);

        				JButton bouton = new JButton("Et bien il semble que ce soit un match nul !");
        				bouton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent arg0) {fin.dispose();}});
        				fin.add(bouton);
        			}
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
