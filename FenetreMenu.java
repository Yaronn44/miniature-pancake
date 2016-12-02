import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.*;
import java.util.*;
import java.text.NumberFormat;


class FenetreMenu extends JFrame{

	private JButton jcj_, jco_;
	private JLabel texteSelec_, texteNbBase_, texteTailleTab_;
	private JFormattedTextField nbBase_, tailleTab_;

	public FenetreMenu(){
		super("Paramètre de la partie");

		//------------------------------------------------------------------- Paramétrage de la fenêtre 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		//------------------------------------------------------------------- Instanciation de la fenêtre et de son contenu
		jcj_ = new JButton("Joueur contre Joueur");
		jco_ = new JButton("Joueur contre Ordi");

		texteSelec_ = new JLabel("Veuillez séléctionner le mode de jeu souhaité :");
		texteTailleTab_ = new JLabel("Taille de la grille (4 <= taille <= 15) : ");
		texteNbBase_ = new JLabel("Nombre de base (2 <= nombre base <= taille) : ");

		nbBase_ = new JFormattedTextField(NumberFormat.getIntegerInstance());
		tailleTab_ = new JFormattedTextField(NumberFormat.getIntegerInstance());

		//------------------------------------------------------------------- Action du bouton joueur contre joueur
		jcj_.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				if (nbBase_.getValue() != null && tailleTab_.getValue() != null ) {

					int nb_ = Integer.parseInt(nbBase_.getText());
					int taille_ = Integer.parseInt(tailleTab_.getText());

					if ((taille_ >= 4 && taille_ <= 15) && (nb_ >= 2 && nb_ <= taille_)) {
						dispose();
						FenetreJeu fenetre_ = new FenetreJeu("Un jeu de connexion", nb_, taille_, 1);
					}
				}
			}
        });

		//------------------------------------------------------------------- Action du bouton joueur contre ordinateur
		jco_.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				if (nbBase_.getValue() != null && tailleTab_.getValue() != null ) {

					int nb_ = Integer.parseInt(nbBase_.getText());
					int taille_ = Integer.parseInt(tailleTab_.getText());

					if ((taille_ >= 4 && taille_ <= 15) && (nb_ >= 2 && nb_ <= taille_)) {
						dispose();
						FenetreJeu fenetre_ = new FenetreJeu("Un jeu de connexion", nb_, taille_, 2);
					}
				}
			}
        });

		//------------------------------------------------------------------- Agencement des différents composants graphique de la fenêtre
		GroupLayout layout = new GroupLayout(this.getContentPane());
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(texteSelec_)
					.addComponent(texteTailleTab_)
					.addComponent(texteNbBase_))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(jcj_)
					.addComponent(tailleTab_)
					.addComponent(nbBase_))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(jco_))
		);


		layout.setVerticalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(texteSelec_)
					.addComponent(jcj_)
					.addComponent(jco_))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(texteTailleTab_)
					.addComponent(tailleTab_))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(texteNbBase_)
					.addComponent(nbBase_))
		);

		getContentPane().setLayout(layout);
		pack();
	}
	
}