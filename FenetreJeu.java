import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.*;
import java.util.*;


class FenetreJeu extends JFrame{

	private Grille grille_;
	private JPanel menu_;
	private JLabel affScoreJ1_, affScoreJ2_, affTour_;
	private JButton b1_, b2_, b3_, b4_, b5_, b6_, b7_;
	private int joueur_, scoreJ1_, scoreJ2_, nbBase_, taille_, choix_, compt_, posTmpX_, posTmpY_;
	private ArrayList<Integer> listeCoup_, evaluer_;
	private boolean vJ1_, vJ2_;

	public FenetreJeu(String titre, int nbB, int t, int c) {

		//------------------------------------------------------------------- Instanciation de la fenêtre et de son contenu
		super(titre);

		grille_ = new Grille(t, nbB);
		menu_ = new JPanel();

		scoreJ1_ = 0;
		scoreJ2_ = 0;

		joueur_ = 1;
		nbBase_ = nbB;
		taille_ = t;

		compt_ = 0;

		posTmpX_ = 0;
		posTmpY_ = 0;

		vJ1_ = false;
		vJ2_ = false;

		choix_ = c;

		listeCoup_ = new ArrayList<Integer>();
		evaluer_ = new ArrayList<Integer>();
		affScoreJ1_ = new JLabel("Score joueur 1 : "+ scoreJ1_);
		affScoreJ2_ = new JLabel("Score joueur 2 : "+ scoreJ2_);
		affTour_ = new JLabel("C'est au tour du joueur : " + joueur_);


		//------------------------------------------------------------------- Paramétrage de la fenêtre principale
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);


		//------------------------------------------------------------------- Paramétrage du menu
		menu_.setLayout(new GridLayout(8,1));
		menu_.setVisible(true);
		menu_.setPreferredSize(new Dimension(100,grille_.getDim()));


		//------------------------------------------------------------------- Instanciation et paramétrage du contenu du menu
		b1_ = new JButton("Afficher Composante");
		b2_ = new JButton("Existe Chemin Case");
		b3_ = new JButton("Relie Case Min");
		b4_ = new JButton("Nombre d'étoiles");
		b5_ = new JButton("Relie Composante");
		b6_ = new JButton("evaluer Case 1");
		b7_ = new JButton("Abandon");

		//------------------------------------------------------------------- Bouton pour afficheComposante
        b1_.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille_.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		    				grille_.afficheComposante((e.getX()-1)/50, (e.getY()-1)/50);
		    				suppr();
		    				if (choix_ == 1) 
		    					joueDeuxHumains();
		    				else if(choix_ == 2)
		    					joueOrdiHumain();
		        		}
		        });
			}
        });


       //------------------------------------------------------------------- Bouton pour existeCheminCases
        b2_.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				compt_ = 0;
				grille_.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
	    					if (compt_ == 0){
	    						posTmpX_ = (e.getX()-1)/50;
	    						posTmpY_ = (e.getY()-1)/50;
	    						++compt_;
	    					}
	    					else{
	    						System.out.println("Il existe un chemin : " + grille_.existeCheminCases(posTmpX_, posTmpY_, (e.getX()-1)/50, (e.getY()-1)/50));
								suppr();
								if (choix_ == 1)
									joueDeuxHumains();
								else if(choix_ == 2)
									joueOrdiHumain();
								--compt_;
	    					}
		        		}
		        });
			}
        });

        //------------------------------------------------------------------- Bouton pour relierCasesMin
        b3_.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				compt_ = 0;
				grille_.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
	    					if (compt_ == 0){
	    						posTmpX_ = (e.getX()-1)/50;
	    						posTmpY_ = (e.getY()-1)/50;
	    						++compt_;
	    					}
	    					else{
	    						System.out.println("Nombre de cases minimum à colorier : " + grille_.relierCasesMin(posTmpX_, posTmpY_, (e.getX()-1)/50, (e.getY()-1)/50));
								suppr();
								if (choix_ == 1)
									joueDeuxHumains();
								else if(choix_ == 2)
									joueOrdiHumain();
								--compt_;
	    					}
		        		}
		        });
			}
        });


		//------------------------------------------------------------------- Bouton pour nombreEtoile
		b4_.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille_.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		    				System.out.println("Nombre de base de la composante : " + grille_.nombreEtoiles((e.getX()-1)/50, (e.getY()-1)/50));
		    				suppr();
		    				if (choix_ == 1) 
		    					joueDeuxHumains();
		    				else if(choix_ == 2)
		    					joueOrdiHumain();
		        		}
		        });
			}
        });


		//------------------------------------------------------------------- Bouton pour relieComposante
        b5_.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille_.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){

		        			ArrayList<Integer> tmp = new ArrayList<Integer>();
		        			tmp.addAll(grille_.relieComposantes((e.getX()-1)/50, (e.getY()-1)/50, joueur_));
		        			System.out.println("Nombre de composantes reliables : " + tmp.size());
		        			System.out.print("Composante(s) : ");
		        			for (int i = 0; i < tmp.size(); ++i) {
		        				System.out.print(tmp.get(i) + " ");
		        			}
		        			System.out.println("");
		    				suppr();
		    				if (choix_ == 1) 
		    					joueDeuxHumains();
		    				else if(choix_ == 2)
		    					joueOrdiHumain();
		        		}
		        });
			}
        });

        //------------------------------------------------------------------- Bouton pour evaluerCase1
        b6_.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille_.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		        			System.out.println("Distance entre la case et le centre de masse : " + grille_.evaluerCase1((e.getX()-1)/50, (e.getY()-1)/50, joueur_));
		    				suppr();
		    				if (choix_ == 1) 
		    					joueDeuxHumains();
		    				else if(choix_ == 2)
		    					joueOrdiHumain();
		        		}
		        });
			}
        });

        //------------------------------------------------------------------- Bouton pour abandonner
        b7_.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent a){

        		JFrame aband = new JFrame("Tentatived'abandon de la part du joueur n°"+(joueur_ == 1 ? 1 : 2)+"(c'est lache, très lache)");

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



        //------------------------------------------------------------------- Ajout des boutons au menu
        menu_.add(b1_);
		menu_.add(b2_);
		menu_.add(b3_);
		menu_.add(b4_);
		menu_.add(b5_);
		menu_.add(b6_);
		menu_.add(b7_);


		//------------------------------------------------------------------- Agencement des différents composants graphique de la fenêtre
		GroupLayout layout = new GroupLayout(this.getContentPane());
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(affScoreJ1_)
					.addComponent(affScoreJ2_)
					.addComponent(grille_))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(affTour_)
					.addComponent(menu_))
		);


		layout.setVerticalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(affScoreJ1_))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(affScoreJ2_)
					.addComponent(affTour_))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(grille_) 
					.addComponent(menu_))
		);

		getContentPane().setLayout(layout);
		pack();


		if (choix_ == 1) {
			joueDeuxHumains();
		}
		else if (choix_ == 2) {
			joueOrdiHumain();
		}
	}

	//------------------------------------------------------------------- Méthodes Obligatoires

	//!\brief Méthode n°6 afficheScores
	public void afficheScores(){

		affScoreJ1_.setText("Score joueur 1 : " + scoreJ1_);
		affScoreJ2_.setText("Score joueur 2 : " + scoreJ2_);
	}


	//!\brief Méthode n°8 joueDeuxHumains
	public void joueDeuxHumains(){
		grille_.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e){		 

    			// On vérifie que la case n'est pas déjà colorée, alors...
    			if (grille_.getCellSouris(e.getX(),e.getY()).colorerCase(joueur_)){								

    				// On ajoute la position de la case dans la liste des coups joués 
    				listeCoup_.add((e.getX()-1)/50+((e.getY()-1)/50)*taille_);

    				// On ajoute la position de tous les composantes disponible dans un ArrayList
    				ArrayList<Integer> tmp = new ArrayList<Integer>();
    				tmp.addAll(grille_.relieComposantes((e.getX()-1)/50, (e.getY()-1)/50, joueur_));

    				// Puis on fait l'union entre toutes les composantes
    				for (int i = 0; i < tmp.size(); ++i){
    					grille_.union((e.getX()-1)/50, (e.getY()-1)/50, tmp.get(i)%taille_, tmp.get(i)/taille_);
    				}

    				// On récupère le nombre de base présentes dans la nouvelle composante
 					int scoreTmp = grille_.nombreEtoiles((e.getX()-1)/50, (e.getY()-1)/50);

 					// On vérifie si la nouvelle composante améliore le score du joueur
 					// En fonction du joueur on vérifie si son score dépasse celui de l'adversaire dans ce cas on met à jour le vainqueur
        			if (joueur_ == 1){

        				if(scoreTmp > 1 && scoreTmp > scoreJ1_){
        					scoreJ1_ = scoreTmp;
        					afficheScores();
        					if (scoreJ1_ > scoreJ2_) {
        						vJ1_ = true;
        						vJ2_ = false;
        					}
        				}

        				++joueur_;
        			}
        			else if(joueur_ == 2){

        				if(scoreTmp > 1 && scoreTmp > scoreJ2_){
        					scoreJ2_ = scoreTmp;
        					afficheScores();
        					    if (scoreJ2_ > scoreJ1_) {
        						vJ1_ = false;
        						vJ2_ = true;
        					}
        				}

        				--joueur_;
        			}

        			// On test que les condition 
        			testFinPartie();
        		}
        		// Sinon on affiche un message d'erreur
        		else
        			afficheMessageErreur();

				affTour_.setText("C'est au tour du joueur : " + joueur_);
    		}
   		});
	}
	
	//!\brief Méthode n°10 joueOrdiHumain
	public void joueOrdiHumain(){

		grille_.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e){		 

    			// On vérifie que la case n'est pas déjà colorée, alors...
    			if (grille_.getCellSouris(e.getX(),e.getY()).colorerCase(1)){	

    				// On ajoute la position de la case dans la liste des coups joués 
    				listeCoup_.add((e.getX()-1)/50+((e.getY()-1)/50)*taille_);

					// On ajoute la position de tous les composantes disponible dans un ArrayList
    				ArrayList<Integer> tmp = new ArrayList<Integer>();
    				tmp.addAll(grille_.relieComposantes((e.getX()-1)/50, (e.getY()-1)/50, 1));

					// Puis on fait l'union entre toutes les composantes
    				for (int i = 0; i < tmp.size(); ++i)
    					grille_.union((e.getX()-1)/50, (e.getY()-1)/50, tmp.get(i)%taille_, tmp.get(i)/taille_);

					// On récupère le nombre de base présentes dans la nouvelle composante
 					int scoreTmp = grille_.nombreEtoiles((e.getX()-1)/50, (e.getY()-1)/50);


 					// On vérifie si la nouvelle composante améliore le score du joueur et on vérifie si son score dépasse celui de l'adversaire dans ce cas on met à jour le vainqueur
    				if(scoreTmp > 1 && scoreTmp > scoreJ1_){
    					scoreJ1_ = scoreTmp;

    					afficheScores();

    					if (scoreJ1_ > scoreJ2_) {
    						vJ1_ = true;
    						vJ2_ = false;
    					}
    				}
    				++joueur_;

    				if (!testFinPartie()) {
						// On trouve la meilleurs case à jouer pour l'ordinateur selon trouverCase1 (qui utilise evaluerCase1)
						int coupOrdi = trouverCase1(2);

						// On colorie la case en question
						grille_.getCell(coupOrdi).colorerCase(2);

						// Puis on ajoute les coordonnées de la case dans la liste des cases jouées
		   				listeCoup_.add(coupOrdi);

		   				// On ajoute la position de tous les composantes disponible dans un ArrayList
		   				tmp.clear();
		   				tmp = grille_.relieComposantes(coupOrdi%taille_, coupOrdi/taille_, 2);
		   				
		   				// Puis on fait l'union entre toutes les composantes
		   				for (int i = 0; i < tmp.size(); ++i)
		   					grille_.union(coupOrdi%taille_, coupOrdi/taille_, tmp.get(i)%taille_, tmp.get(i)/taille_);
		   			
		   				// On récupère le nombre de base présentes dans la nouvelle composante
						scoreTmp = grille_.nombreEtoiles(coupOrdi%taille_, coupOrdi/taille_);
						
						// On vérifie si la nouvelle composante améliore le score du joueur
						 // En fonction du joueur on vérifie si son score dépasse celui de l'adversaire dans ce cas on met à jour le vainqueur
		   				if(scoreTmp > 1 && scoreTmp > scoreJ2_){
		   					scoreJ2_ = scoreTmp;

		   					afficheScores();

		   					if (scoreJ2_ > scoreJ1_) {
		   						vJ1_ = false;
		   						vJ2_ = true;
		   					}
		   				}
		   				--joueur_;
		   				// On vérifie les conditions de fin de partie 
		   				testFinPartie();
    				}

    			}
    			// Sinon on affiche un message d'erreur
	    		else
	    			afficheMessageErreur();
        	}
   		});
	}

	//------------------------------------------------------------------- Méthodes supplémentaires

	//!\ Permet de supprimer le MouseListener actuel si il en existe un
	public void suppr(){
		MouseListener m[] = grille_.getMouseListeners(); 
		if(m.length > 0)
			grille_.removeMouseListener(m[0]);
	}


	//!\ Permet à l'ordinateur de trouver la case la plus pertinante à jouer selon evaluerCase1
	public int trouverCase1(int j){

		int index = -1;
		int valTmp = taille_;

		for(int i = 0; i < taille_*taille_; ++i){
			if(grille_.evaluerCase1(i%taille_, i/taille_, j) < valTmp){
				valTmp = grille_.evaluerCase1(i%taille_, i/taille_, j);
				index = i;
			}
		}
		return index;	
	}


	//!\ Vérifie les différentes conditions d'arrêt de la partie et affiche un message en fonction de la situation actuelle 
	public boolean testFinPartie(){

		// Un des joueurs à remplie toutes ses bases ou la grille est remplie et l'un des joueur a marqué des points
		if (scoreJ1_ == nbBase_ || scoreJ2_ == nbBase_ || ((listeCoup_.size() == taille_*taille_ - nbBase_*2) && (scoreJ1_ != 0 || scoreJ2_ != 0))) {

			JFrame fin = new JFrame("Bravo !!");

			fin.setSize(400,100);
			fin.setLocationRelativeTo(null);
			fin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   
			fin.setVisible(true);
			setEnabled(false);

			JButton bouton = new JButton("Bravo le joueur n°"+(vJ1_ ? 1 : 2) +" a remporté la partie !");
			bouton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					dispose();
					fin.dispose();
					FenetreMenu newF = new FenetreMenu();
				}
			});
			fin.add(bouton);
			return true;
		}
		// Les scores des deux joueurs sont à 0 et la grille est remplie
		else if(listeCoup_.size() == taille_*taille_ - nbBase_*2){

			JFrame fin = new JFrame("Hum....");

			fin.setSize(400,100);
			fin.setLocationRelativeTo(null);
			fin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   
			fin.setVisible(true);
			setEnabled(false);

			JButton bouton = new JButton("Et bien il semble que ce soit un match nul !");
			bouton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					dispose();
					fin.dispose();
					FenetreMenu newF = new FenetreMenu();
				}
			});
			fin.add(bouton);
			return true;
		}
		return false;
	}


	//!\ Affichage d'un message d'erreur dans le cas où il y a tentative de coloré une case non blanche
	public void afficheMessageErreur(){
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
