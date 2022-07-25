package GUI;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;

/**
 * Klasa koja extend-a JPanel, sadrži radio dugmad Sporo/Normalno/Brzo/Ulra brzo, za izbor brzine igre
 * 
 * @param radioDugme1 - dugme "Sporo" za brzinu igre
 * @param radioDugme2 - dugme "Normalno" za brzinu igre
 * @param radioDugme3 - dugme "Brzo" za brzinu igre
 * @param radioDugme4 - dugme "Ultra brzo" za brzinu igre
 */
public class PanelIzborBrzine extends JPanel {
	
	private JRadioButton radioDugme1;
	private JRadioButton radioDugme2;
	private JRadioButton radioDugme3;
	private JRadioButton radioDugme4;
	
    /**
     * Konstuktor klase PanelIzborBrzine
     * 
     * @param ram - okvir oko ovog panela
     */
	public PanelIzborBrzine() {
	
		TitledBorder ram = new TitledBorder("Brzina igre: ");
		setBorder(ram);
		setLayout(new GridBagLayout());
		ubaciRadioDugmad();
	
	}
	
	/**
	 * Funkcija za kreiranje i ubacivanje radio dugmadi u panel
	 * 
	 * @param grupa - grupa koja sadrži sva radio dugmad tj. grupiše ih
	 */
	private void ubaciRadioDugmad() {
		
		radioDugme1 = new JRadioButton("Sporo");
		radioDugme2 = new JRadioButton("Normalno");
		radioDugme3 = new JRadioButton("Brzo");
		radioDugme4 = new JRadioButton("Ultra brzo");

		radioDugme2.setSelected(true);
	    
		ButtonGroup grupa = new ButtonGroup();
		grupa.add(radioDugme1);
		grupa.add(radioDugme2);
		grupa.add(radioDugme3);
		grupa.add(radioDugme4);
		  
		this.add(radioDugme1);
		this.add(radioDugme2);
		this.add(radioDugme3);	
		this.add(radioDugme4);	
		
	}

	/**
	 * Geter funkcija za radioDugme1
	 * 
	 */
    public JRadioButton getRadioDugme1() {
    	return radioDugme1;
    }

	/**
	 * Geter funkcija za radioDugme2
	 * 
	 */
    public JRadioButton getRadioDugme2() {
    	return radioDugme2;
    }

	/**
	 * Geter funkcija za radioDugme3
	 * 
	 */
    public JRadioButton getRadioDugme3() {
    	return radioDugme3;
    }

	/**
	 * Geter funkcija za radioDugme4
	 * 
	 */
    public JRadioButton getRadioDugme4() {
    	return radioDugme4;
    }
    
}