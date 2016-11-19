import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class Cellule extends JPanel {

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


		// Affichage de l'étoile dans les bases
		setPreferredSize(new Dimension(50,50));
		JLabel texte = new JLabel("*");
		texte.setFont(new Font("Serif", Font.BOLD, 20));
		texte.setForeground(Color.BLACK);
		add(texte);
	}


	public int getVal(){
		return case_;
	}

	public boolean isBase(){
		return etoile_;
	}

	public boolean colorerCase(int val){ 			
		if (case_ == 0 && (val == 1 || val == 2 || val == 3)){
			case_ = val;

			if (val == 1) 
				setBackground(Color.blue);
			else if(val == 2)
				setBackground(Color.red);
			else
				setBackground(Color.green);

			return true;
		}
		return false;
	}

	public void colorerTemp(){
		if (getBackground() != Color.green)
			setBackground(Color.green);
		else if(case_ == 1)
			setBackground(Color.blue);
		else
			setBackground(Color.red);
	}

	public boolean setBase(int val){
		if (case_ == 0 && (val ==1 || val == 2)){
			case_ = val;
			etoile_ = true;

			if (val == 1) 
				setBackground(Color.blue);
			else
				setBackground(Color.red);

			JLabel texte = new JLabel("*");
			Font font = new Font("Serif", Font.BOLD, 20);
			texte.setFont(font);
			texte.setForeground(Color.BLACK);
			add(texte);

			return true;
		}
		return false;
	}
}
