package GUI;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;

/**
 * Klasa koja extend-a JPanel, sadrži radio dugmad Plava/Zelena/Žuta, za izbor teme za boju zmije
 * 
 * @param radioDugme1 - dugme "Plava" za izbor teme za boju zmije
 * @param radioDugme2 - dugme "Zelena" za izbor teme za boju zmije
 * @param radioDugme3 - dugme "Žuta" za izbor teme za boju zmije
 */
public class PanelIzborBojaZmije extends JPanel {
	
	private JRadioButton radioDugme1;
	private JRadioButton radioDugme2;
	private JRadioButton radioDugme3;
	
    /**
     * Konstuktor klase PanelIzborBojaZmije
     * 
     * @param ram - okvir oko ovog panela
     */
	public PanelIzborBojaZmije() {
		
		TitledBorder ram = new TitledBorder("Boja zmije: ");
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
		
		radioDugme1 = new JRadioButton("Plava");
		radioDugme2 = new JRadioButton("Zelena");
		radioDugme3 = new JRadioButton("Žuta");

		radioDugme2.setSelected(true);
		
		ButtonGroup grupa = new ButtonGroup();
		grupa.add(radioDugme1);
		grupa.add(radioDugme2);
		grupa.add(radioDugme3);
		
		this.add(radioDugme1);
		this.add(radioDugme2);
		this.add(radioDugme3);	
		
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

}