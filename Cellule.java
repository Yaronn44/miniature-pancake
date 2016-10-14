import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Cellule extends JPanel {

	private int case_;
	private boolean etoile_;

	public Cellule(){

		case_ = 0;
		etoile_ = false;
		setBackground(Color.white);
		setPreferredSize(new Dimension(50,50));
	}

	public Cellule(int val, boolean base){

		case_ = val;
		etoile_ = base;
		if (case_ == 1)
			setBackground(Color.blue);
		else if(case_ == 2)
			setBackground(Color.red);

		JLabel texte = new JLabel();
		texte.setText("*");
		add(texte);
	}


	public int getVal(){
		return case_;
	}

	public boolean isBase(){
		return etoile_;
	}

	public boolean setVal(int val){
		if (case_ == 0 && (val ==1 || val == 2)){
			case_ = val;

			if (val == 1) 
				setBackground(Color.blue);
			else
				setBackground(Color.red);

			return true;
		}
		return false;
	}

	public boolean setBase(int val){
		if (case_ == 0 && (val ==1 || val == 2)){
			case_ = val;
			etoile_ = true;

			if (val == 1) 
				setBackground(Color.blue);
			else
				setBackground(Color.red);

			return true;
		}
		return false;
	}

	public void colorerCase(int val){
		if (!setVal(val)){

			JFrame fenetre = new JFrame("Erreur");

			fenetre.setSize(200,200);
			fenetre.setLocationRelativeTo(null);
			fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
			fenetre.setVisible(true);

			JTextArea texte = new JTextArea("La case est déjà coloré !");
			fenetre.getContentPane().add(texte);

			JButton bouton = new JButton("OK");
			bouton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent arg0) {fenetre.dispose();}});
			fenetre.add(bouton);

		}
	}

}
