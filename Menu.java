import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.*;
import javax.swing.JComponent;

public class Menu{

	
	private JMenuBar bar = new JMenuBar();
	private JButton b1 = new JButton("Colorer une case");
	private JButton b2 = new JButton("Afficher composante");
	private JButton b3 = new JButton("Existe chemin cases");
	private JButton b4 = new JButton("Relier Case Min");
	private JButton b5 = new JButton("Nombre d'étoiles");
	private JButton b6 = new JButton("Afficher le score");
	private JButton b7 = new JButton("Relie composante ?");
	private JButton b8 = new JButton("Nouvelle partie");

	
	public Menu(){

		b1.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {System.exit(0);}              
    	});


		bar.add(b1);
		bar.add(b2);
		bar.add(b3);
		bar.add(b4);
		bar.add(b5);
		bar.add(b6);
		bar.add(b7);
		bar.add(b8);


	}

	public JMenuBar getMenu(){
		return bar;
	}
}
