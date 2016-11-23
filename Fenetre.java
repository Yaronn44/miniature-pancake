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
class FenetreJeu extends JFrame{

	private Grille grille;
	private JPanel menu;
	private JLabel affScoreJ1, affScoreJ2;
	private JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
	private int joueur, scoreJ1, scoreJ2, nbBase, taille, choix, compt, posTmpX, posTmpY;
	private ArrayList<Integer> listeCoup, evaluer;
	private boolean vJ1, vJ2;

	public FenetreJeu(String titre, int nbB, int t, int c) {

		//------------------------------------------------------------------- Instanciation de la fenêtre principale et de son contenu
		super(titre);
		grille = new Grille(t, nbB);
		menu = new JPanel();
		scoreJ1 = 0;
		scoreJ2 = 0;
		affScoreJ1 = new JLabel("Score joueur 1 : "+ scoreJ1);
		affScoreJ2 = new JLabel("Score joueur 2 : "+ scoreJ2);
		joueur = 1;
		nbBase = nbB;
		taille = t;
		compt = 0;
		posTmpX = 0;
		posTmpY = 0;
		vJ1 = false;
		vJ2 = false;
		choix = c;
		listeCoup = new ArrayList<Integer>();
		evaluer = new ArrayList<Integer>();

		//------------------------------------------------------------------- Paramétrage de la fenêtre principale
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
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
		b5 = new JButton("Distance Case");
		b6 = new JButton("Relie Composante");
		b7 = new JButton("existe Chemin Cases");
		b8 = new JButton("Abandonner");
		b9 = new JButton("relieCaseMin");
		b10 = new JButton("Fils");

		// afficheComposante
        b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		    				grille.afficheComposante((e.getX()-1)/50, (e.getY()-1)/50);
		    				suppr();
		    				if (choix == 1) {
		    					joueDeuxHumain();
		    				}
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
		    				if (choix == 1) {
		    					joueDeuxHumain();
		    				}
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
		    				if (choix == 1) {
		    					joueDeuxHumain();
		    				}
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
		    				if (choix == 1) {
		    					joueDeuxHumain();
		    				}
		        		}
		        });
			}
        });


		// distanceCase
		b5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
	        		public void mousePressed(MouseEvent e){
    					if (compt == 0){
    						posTmpX = (e.getX()-1)/50;
    						posTmpY = (e.getY()-1)/50;
    						++compt;
    					}
    					else{
    						System.out.println(grille.distanceCase(posTmpX, posTmpY, (e.getX()-1)/50, (e.getY()-1)/50));
							suppr();
							if (choix == 1)
								joueDeuxHumain();
							--compt;
    					}
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
		    				System.out.println(grille.relieComposante((e.getX()-1)/50, (e.getY()-1)/50, joueur));
		    				suppr();
		    				if (choix == 1) {
		    					joueDeuxHumain();
		    				}
		        		}
		        });
			}
        });

        // existeCheminCases
        b7.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
	    					if (compt == 0){
	    						posTmpX = (e.getX()-1)/50;
	    						posTmpY = (e.getY()-1)/50;
	    						++compt;
	    					}
	    					else{
	    						System.out.println(grille.existeCheminCases(posTmpX, posTmpY, (e.getX()-1)/50, (e.getY()-1)/50));
								suppr();
								if (choix == 1)
									joueDeuxHumain();
								--compt;
	    					}
		        		}
		        });
			}
        });

        //Abandonner
        b8.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent a){

        		JFrame aband = new JFrame("Tentatived'abandon de la part du joueur n°"+(joueur == 1 ? 1 : 2)+"(c'est lache, très lache)");

				aband.setSize(600,100);
				aband.setLocationRelativeTo(null);
				aband.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
				aband.setVisible(true);

				JButton oui = new JButton("Oui");
				JButton non = new JButton("Non");
				oui.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						dispose();
						aband.dispose();
						FenetreMenu newF = new FenetreMenu();
					}
				});

				non.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent arg0) {aband.dispose();}});

				GridLayout layout = new GridLayout(1, 2,2 ,2);
				aband.setLayout(layout);

				aband.add(oui);
				aband.add(non);
        	}
        });

        //relieCaseMin
        b9.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
	    					if (compt == 0){
	    						posTmpX = (e.getX()-1)/50;
	    						posTmpY = (e.getY()-1)/50;
	    						++compt;
	    					}
	    					else{
	    						int col1 = grille.getVal(posTmpX, posTmpY);
	    						int col2 = grille.getVal((e.getX()-1)/50, (e.getY()-1)/50);
	    						System.out.println(grille.relieCaseMin(posTmpX, posTmpY, (e.getX()-1)/50, (e.getY()-1)/50, col1, col2));
								suppr();
								if (choix == 1)
									joueDeuxHumain();
								--compt;
	    					}
		        		}
		        });
			}
        });

        //getTousFils
        b10.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		        			grille.afficher((e.getX()-1)/50,(e.getY()-1)/50);
		    				suppr();
		    				if (choix == 1) {
		    					joueDeuxHumain();
		    				}
		        		}
		        });
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
		menu.add(b9);
		menu.add(b10);

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


		if (choix == 1) {
			joueDeuxHumain();
		}
	}

	// ------------------------------------------ Méthodes Demandées
	
	public void afficheScore(){

		affScoreJ1.setText("Score joueur 1 : "+ scoreJ1);
		affScoreJ2.setText("Score joueur 2 : "+ scoreJ2);
	}


	public void joueDeuxHumain(){
		grille.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e){		 

    			if (grille.getCell(e.getX(),e.getY()).colorerCase(joueur)){								// colorerCase() s'effectue dans testVal()

    				listeCoup.add((e.getX()-1)/50+((e.getY()-1)/50)*taille);

    				int tmp = grille.relieComposante((e.getX()-1)/50, (e.getY()-1)/50, joueur);

    				for (int i = 0; i < tmp; ++i)
    					grille.union((e.getX()-1)/50, (e.getY()-1)/50, joueur);

 					int scoreTmp = grille.nombreEtoiles((e.getX()-1)/50, (e.getY()-1)/50);

        			if (joueur == 1){

        				if(scoreTmp > 1 && scoreTmp > scoreJ1){
        					scoreJ1 = scoreTmp;
        					afficheScore();
        					if (scoreJ1 > scoreJ2) {
        						vJ1 = true;
        						vJ2 = false;
        					}
        				}

        				++joueur;
        			}
        			else if(joueur == 2){

        				if(scoreTmp > 1 && scoreTmp > scoreJ2){
        					scoreJ2 = scoreTmp;
        					afficheScore();
        					    if (scoreJ2 > scoreJ2) {
        						vJ1 = false;
        						vJ2 = true;
        					}
        				}

        				--joueur;
        			}

        			if (scoreJ1 == nbBase || scoreJ2 == nbBase || (listeCoup.size() == taille*taille - nbBase*2 && (scoreJ1 != 0 || scoreJ2 != 0))) {

        				JFrame fin = new JFrame("Bravo !!");

        				fin.setSize(400,100);
        				fin.setLocationRelativeTo(null);
        				fin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
        				fin.setVisible(true);

        				JButton bouton = new JButton("Bravo le joueur n°"+(vJ1 ? 1 : 2) +" a remporté la partie !");
        				bouton.addActionListener(new ActionListener(){
        					public void actionPerformed(ActionEvent arg0) {
        						dispose();
        						fin.dispose();
        						FenetreMenu newF = new FenetreMenu();
        					}
        				});
        				fin.add(bouton);
        			}
        			else if(listeCoup.size() == taille*taille - nbBase*2){
        				JFrame fin = new JFrame("Hum....");

        				fin.setSize(400,100);
        				fin.setLocationRelativeTo(null);
        				fin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
        				fin.setVisible(true);

        				JButton bouton = new JButton("Et bien il semble que ce soit un match nul !");
        				bouton.addActionListener(new ActionListener(){
        					public void actionPerformed(ActionEvent arg0) {
        						dispose();
        						fin.dispose();
        						FenetreMenu newF = new FenetreMenu();
        					}
        				});
        				fin.add(bouton);
        			}
        		}
        		else{
        			JFrame fenetre = new JFrame("Erreur");

					fenetre.setSize(200,100);
					fenetre.setLocationRelativeTo(null);
					fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   
					fenetre.setVisible(true);
					fenetre.setAlwaysOnTop(true);
					setEnabled(false);

					JButton bouton = new JButton("La case est déjà coloré !");
					bouton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent arg0) {fenetre.dispose(); setEnabled(true);}});
					fenetre.add(bouton);
				}
    		}
   		});
	}
	
	
	public void joueOrdiHumain(){
		grille.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e){		 

			
    			if (grille.getCell(e.getX(),e.getY()).colorerCase(joueur)){								// colorerCase() s'effectue dans testVal()

    				listeCoup.add((e.getX()-1)/50+((e.getY()-1)/50)*taille);

    				int tmp = grille.relieComposante((e.getX()-1)/50, (e.getY()-1)/50, joueur);

    				for (int i = 0; i < tmp; ++i)
    					grille.union((e.getX()-1)/50, (e.getY()-1)/50, joueur);

 					int scoreTmp = grille.nombreEtoiles((e.getX()-1)/50, (e.getY()-1)/50);

        			if (joueur == 1){

        				if(scoreTmp > 1 && scoreTmp > scoreJ1){
        					scoreJ1 = scoreTmp;
        					afficheScore();
        					if (scoreJ1 > scoreJ2) {
        						vJ1 = true;
        						vJ2 = false;
        					}
        				}

        				++joueur;
        			}
        			else if(joueur == 2){

        				if(scoreTmp > 1 && scoreTmp > scoreJ2){
        					scoreJ2 = scoreTmp;
        					afficheScore();
        					    if (scoreJ2 > scoreJ2) {
        						vJ1 = false;
        						vJ2 = true;
        					}
        				}

        				--joueur;
        			}

        			if (scoreJ1 == nbBase || scoreJ2 == nbBase || (listeCoup.size() == taille*taille - nbBase*2 && (scoreJ1 != 0 || scoreJ2 != 0))) {

        				JFrame fin = new JFrame("Bravo !!");

        				fin.setSize(400,100);
        				fin.setLocationRelativeTo(null);
        				fin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
        				fin.setVisible(true);

        				JButton bouton = new JButton("Bravo le joueur n°"+(vJ1 ? 1 : 2) +" a remporté la partie !");
        				bouton.addActionListener(new ActionListener(){
        					public void actionPerformed(ActionEvent arg0) {
        						dispose();
        						fin.dispose();
        						FenetreMenu newF = new FenetreMenu();
        					}
        				});
        				fin.add(bouton);
        			}
        			else if(listeCoup.size() == taille*taille - nbBase*2){
        				JFrame fin = new JFrame("Hum....");

        				fin.setSize(400,100);
        				fin.setLocationRelativeTo(null);
        				fin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
        				fin.setVisible(true);

        				JButton bouton = new JButton("Et bien il semble que ce soit un match nul !");
        				bouton.addActionListener(new ActionListener(){
        					public void actionPerformed(ActionEvent arg0) {
        						dispose();
        						fin.dispose();
        						FenetreMenu newF = new FenetreMenu();
        					}
        				});
        				fin.add(bouton);
        			}
        		}
        		else{
        			JFrame fenetre = new JFrame("Erreur");

					fenetre.setSize(200,100);
					fenetre.setLocationRelativeTo(null);
					fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   
					fenetre.setVisible(true);
					fenetre.setAlwaysOnTop(true);
					setEnabled(false);

					JButton bouton = new JButton("La case est déjà coloré !");
					bouton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent arg0) {fenetre.dispose(); setEnabled(true);}});
					fenetre.add(bouton);
				}
    		}
   		});
	}

	// ------------------------------------------ Méthodes supplémentairess

	public void suppr(){
		MouseListener m[] = grille.getMouseListeners(); 
		if(m.length > 0)
			grille.removeMouseListener(m[0]);
	}

	public int trouverCase1(int j){
		int index = -1;
		int valTmp = taille;
		for(int i = 0; i < taille*taille; ++i){
			if(grille.evaluerCase1(i%taille, i/taille, j) < valTmp{
				valTmp = grille.evaluerCase1(i%taille, i/taille, j);
				index = i;
			}
		}
		return index;	
	}
}
