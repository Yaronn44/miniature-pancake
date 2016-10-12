import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * Grille d'un monde gérant l'ensemble des positions libres
 * ou occupées par des choses.
 * 
 * TODO : à compléter.
 */
public class Grille extends JPanel {
	// Dimensions de la grille : 0..N lignes et 0..M colonnes
	private int N;
	private int M;

	// Dimensions de la fenêtre d'affichage en pixels
	private int largeur;
	private int hauteur;

	/**
	 * Constructeur
	 * @param N N+1 lignes indicées de 0 à N
	 * @param M M+1 colonnes indicées de 0 à M
	 * 
	 * TODO : à compléter
	 */
	public Grille(int N, int M) {
		// dimensions pour les positions
		this.N = N;
		this.M = M;

		// dimensions de la fenêtre
		largeur = Constante.Pix*(M+2);
		hauteur = Constante.Pix*(N+2);
		setPreferredSize(new Dimension(largeur,hauteur));
	}

	/**
	 * @return Hauteur de la fenêtre graphique en pixels
	 */
	public int getHauteur() {
		return hauteur;
	}

	/**
	 * @return Largeur de la fenêtre graphique en pixels
	 */
	public int getLargeur() {
		return largeur;
	}

	/**
	 * Méthode publique de dessin de la grille dans la fenêtre graphique
	 */
	public void dessiner() {
		repaint();  // appel de paintComponent redéfinie ci-après
	}

	/**
	 * Dessin effectif de la grille
	 * @param g Composant graphique de dessin
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;

		// fond
		Color couleur = new Color(80,80,80);
		g2d.setColor(couleur);
		g2d.fillRect(0,0,largeur,hauteur);

		// superposition des couleurs
		g2d.setXORMode(couleur);
		
		// la grille et les choses
		for (int x = 0; x <= M; ++x) {
			int px = (x + 1) * Constante.Pix;
			for (int y = 0; y <= N; ++y) {
				int py = (y + 1) * Constante.Pix;
				
				// Affichage pour la position (x,y) sur le pixel (px,py)
				
				// Ici un petit cercle de couleur blanche est affiché
				// pour montrer que la position est libre

				// TODO : afficher les formes des choses là où elles se trouvent

				g2d.setColor(Color.WHITE);
				g2d.fillOval(px,py,3,3);
			}
		}
	}

}
