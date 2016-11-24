import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.*;
import java.util.*;
import java.text.NumberFormat;


class FenetreMenu extends JFrame{

	private JButton jcj, jco;
	private JLabel texteSelec, texteNbBase, texteTailleTab;
	private JFormattedTextField nbBase;
	private JFormattedTextField tailleTab;

	public FenetreMenu(){
		super("Paramètre de la partie");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);

		jcj = new JButton("Joueur contre Joueur");
		jco = new JButton("Joueur contre Ordi");

		texteSelec = new JLabel("Veuillez séléctionner le mode de jeu souhaité :");
		texteTailleTab = new JLabel("Taille de la grille (4 <= taille <= 15) : ");
		texteNbBase = new JLabel("Nombre de base (2 <= nombre base <= taille) : ");

		nbBase = new JFormattedTextField(NumberFormat.getIntegerInstance());
		tailleTab = new JFormattedTextField(NumberFormat.getIntegerInstance());

		jcj.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				if (nbBase.getValue() != null && tailleTab.getValue() != null ) {

					int nb = Integer.parseInt(nbBase.getText());
					int taille = Integer.parseInt(tailleTab.getText());

					if ((taille >= 4 && taille <= 15) && (nb >= 2 && nb <= taille)) {
						dispose();
						FenetreJeu fenetre = new FenetreJeu("Un jeu de connexion", nb, taille, 1);
					}
				}
			}
        });

		jco.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				if (nbBase.getValue() != null && tailleTab.getValue() != null ) {

					int nb = Integer.parseInt(nbBase.getText());
					int taille = Integer.parseInt(tailleTab.getText());

					if ((taille >= 4 && taille <= 15) && (nb >= 2 && nb <= taille)) {
						dispose();
						FenetreJeu fenetre = new FenetreJeu("Un jeu de connexion", nb, taille, 2);
					}
				}
			}
        });

		GroupLayout layout = new GroupLayout(this.getContentPane());
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(texteSelec)
					.addComponent(texteTailleTab)
					.addComponent(texteNbBase))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(jcj)
					.addComponent(tailleTab)
					.addComponent(nbBase))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(jco))
		);


		layout.setVerticalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(texteSelec)
					.addComponent(jcj)
					.addComponent(jco))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(texteTailleTab)
					.addComponent(tailleTab))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(texteNbBase)
					.addComponent(nbBase))
		);

		getContentPane().setLayout(layout);
		pack();
	}
	
}