package GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * Klasa koja extend-a JFrame, sadrži 4 JPanel-a, koji su u zasebnim fajlovima
 * 
 * @param velicinaProzora - Velièina ploèe koja predstavlja prozor
 * @param prolazakKrozZidove - True ako nema zidova oko ploèe
 * @param brzinaIgre - Brzina zmije u igri
 * @param bojaZmije - Tema za boju zmije u igri
 * @param panel1 - Panel za izbor velièine prozora
 * @param panel2 - Panel za izbor moguænosti prolaska zmije kroz zidove
 * @param panel3 - Panel za izbor brzine igre
 * @param panel4 - Panel za izbor teme za boju zmije
 */
public class OkvirIzbora extends JFrame implements ActionListener {
	
	private String velicinaProzora = "normalan";
	private String prolazakKrozZidove = "da";
	private String brzinaIgre = "normalno";
	private String bojaZmije = "zelena";

	private PanelIzborVelicine panel1;
	private PanelIzborZidovi panel2;
	private PanelIzborBrzine panel3;
	private PanelIzborBojaZmije panel4;
    
	/**
     * Konstuktor klase OkvirIzbora
     * 
     */
	public OkvirIzbora(){

		this.setTitle("Izbor modova: ");
		
		Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Edin\\OneDrive\\Fakultet\\Semestri\\V semestar\\Razvoj softvera\\Java zadaci\\Projekat\\src\\GUI\\snake.png");    
		this.setIconImage(icon);   
		
		this.setLayout(new GridBagLayout());		
		
		GridBagConstraints c = new GridBagConstraints();
		  
		//panel 1 za izbor velicine
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		panel1 = new PanelIzborVelicine();
		panel1.getRadioDugme1().addActionListener(this);
		panel1.getRadioDugme2().addActionListener(this);
		panel1.getRadioDugme3().addActionListener(this);  
		this.add(panel1, c);

		//panel 2 za izbor zidova
		c.fill = GridBagConstraints.BOTH;  
		c.gridx = 0;
		c.gridy = 1;
		panel2 = new PanelIzborZidovi();
		panel2.getRadioDugme1().addActionListener(this);
		panel2.getRadioDugme2().addActionListener(this);
		this.add(panel2, c);

		//panel 3 za izbor brzine
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		panel3 = new PanelIzborBrzine();
		panel3.getRadioDugme1().addActionListener(this);
		panel3.getRadioDugme2().addActionListener(this);
		panel3.getRadioDugme3().addActionListener(this);
		panel3.getRadioDugme4().addActionListener(this);  
		this.add(panel3, c);
		
		//panel 4 za izbor boje zmije
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 3;
		panel4 = new PanelIzborBojaZmije();
		panel4.getRadioDugme1().addActionListener(this);
		panel4.getRadioDugme2().addActionListener(this);
		panel4.getRadioDugme3().addActionListener(this);
		this.add(panel4, c);
		
		// dugme za pokretanje igrice
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 4;
		JButton potvrdi = new JButton("Pokreni igru");
		
		potvrdi.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				new OkvirIgrice(velicinaProzora, prolazakKrozZidove, brzinaIgre, bojaZmije);
				dispose(); // uništavamo JFrame OkvirIzbora
				
			}
			
		});
		
		this.add(potvrdi, c);

		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	/**
	 * Funkcija koja reaguje na klik na bilo koju od radio dugmadi koji su unutar svaka od 4 JPanel-a ovog JFrame-a
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == panel1.getRadioDugme1()) {
			velicinaProzora = "veliki";
		}
		else if(e.getSource() == panel1.getRadioDugme2()) {
			velicinaProzora = "normalan";
		}
		else if(e.getSource() == panel1.getRadioDugme3()) {
			velicinaProzora = "mali";
		  }
		else if(e.getSource() == panel2.getRadioDugme1()) {
			prolazakKrozZidove = "da";
		}
		else if(e.getSource() == panel2.getRadioDugme2()) {
			prolazakKrozZidove = "ne";
		}
		else if(e.getSource() == panel3.getRadioDugme1()) {
			brzinaIgre = "sporo";
		}
		else if(e.getSource() == panel3.getRadioDugme2()) {
			brzinaIgre = "normalno";
		}
		else if(e.getSource() == panel3.getRadioDugme3()) {
			brzinaIgre = "brzo";
		}
		else if(e.getSource() == panel3.getRadioDugme4()) {
			brzinaIgre = "ultra";
		}
		else if(e.getSource() == panel4.getRadioDugme1()) {
			bojaZmije = "plava";
		}
		else if(e.getSource() == panel4.getRadioDugme2()) {
			bojaZmije = "zelena";
		}
		else if(e.getSource() == panel4.getRadioDugme3()) {
			bojaZmije = "žuta";
		}

	}

}