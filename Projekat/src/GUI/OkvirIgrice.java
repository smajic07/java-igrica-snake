package GUI;

import javax.swing.JFrame;
import java.awt.*;

/**
 * Klasa koja extend-a JFrame, sadrži klasu PanelIgrice
 * 
 */
public class OkvirIgrice extends JFrame {
    
	/**
     * Konstuktor klase OkvirIgrice
     * 
	 * @param velicinaProzora - Velièina ploèe koja predstavlja prozor
	 * @param prolazakKrozZidove - True ako nema zidova oko ploèe
	 * @param brzinaIgre - Brzina zmije u igri
	 * @param bojaZmije - Tema za boju zmije u igri
     */
	public OkvirIgrice(String velicinaProzora, String prolazakKrozZidove, String brzinaIgre, String bojaZmije) {

		this.setTitle("Snake");
		
		Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Edin\\OneDrive\\Fakultet\\Semestri\\V semestar\\Razvoj softvera\\Java zadaci\\Projekat\\src\\GUI\\snake.png");    
		this.setIconImage(icon);   
		
		this.add(new PanelIgrice(this, velicinaProzora, prolazakKrozZidove, brzinaIgre, bojaZmije));
		
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
}