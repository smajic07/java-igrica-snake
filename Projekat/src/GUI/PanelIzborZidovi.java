package GUI;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;

/**
 * Klasa koja extend-a JPanel, sadrži radio dugmad Da/Ne, za odabir da li ima zidova u igri
 * 
 * @param radioDugme1 - dugme "Da" za zidove oko ploèe
 * @param radioDugme2 - dugme "Ne" za zidove oko ploèe
 */
public class PanelIzborZidovi extends JPanel {
	
	private JRadioButton radioDugme1;
	private JRadioButton radioDugme2;
    
	/**
     * Konstuktor klase PanelIzborZidovi
     * 
     * @param ram - okvir oko ovog panela
     */
	public PanelIzborZidovi() {
		
		TitledBorder ram = new TitledBorder("Zmija prolazi kroz zidove: ");
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
		
		radioDugme1 = new JRadioButton("Da");
		radioDugme2 = new JRadioButton("Ne");

		radioDugme1.setSelected(true);
		  
		ButtonGroup grupa = new ButtonGroup();
		grupa.add(radioDugme1);
		grupa.add(radioDugme2);
		  
		this.add(radioDugme1);
		this.add(radioDugme2);
		
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
   	
}