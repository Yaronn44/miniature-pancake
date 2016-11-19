import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.*;
import java.util.*;


class FenetreMenu extends JFrame{

	private JButton jcj, jco;

	public FenetreMenu(){
		super("Menu");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		jcj = new JButton("Joueur contre Joueur");
		jco = new JButton("Joueur contre Ordi");
		JLabel selection = new JLabel("Veuillez séléctionner le mode de jeu souhaité :");

		jcj.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				dispose();
				Fenetre fenetre = new Fenetre("Un jeu de connexion", 2, 10, 1);
			}
        });

		GroupLayout layout = new GroupLayout(this.getContentPane());
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(selection)
					.addComponent(jcj))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(jco))
		);


		layout.setVerticalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(selection))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(jcj) 
					.addComponent(jco))
		);

		getContentPane().setLayout(layout);
		pack();
	}
	
}