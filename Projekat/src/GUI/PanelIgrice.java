package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

import Logika.Snake;

/**
 * Klasa koja extend-a JPanel i glavni je interfejs za igricu (GUI igre)
 * 
 * @param okvir - potreban u ovom panelu za dispose pri resetu igre
 * @param snake - instanca klase Logika
 * @param tajmer - tajmer igre
 * @param CEKANJE - delay igre, što je veæi igra je sporija
 * @param VELICINA_POLJA - velièina polja u GUI-u
 * @param SIRINA_PROZORA - Stvarna širina prozora (u px)
 * @param VISINA_PROZORA - Stvarna visina prozora (u px)
 * @param bojaGlave - Boja glave zmije
 * @param bojaTijela - Boja tijela zmije
 * @param bojaRepa - Boja repa zmije
 */
public class PanelIgrice extends JPanel implements ActionListener {
	
	private OkvirIgrice okvir;
	private Snake snake;

	private Timer tajmer;
	private int CEKANJE; // što je veæe èekanje to je igra sporija
	private int VELICINA_POLJA = 20;
	private int SIRINA_PROZORA;
	private int VISINA_PROZORA;
	
	private Color bojaGlave;
	private Color bojaTijela;
	private Color bojaRepa;
	
	/**
	 * Konstruktor klase Snake
	 * 
	 * @param okvir - potreban u ovom panelu za dispose pri resetu igre
	 * @param velicinaProzora - Velièina ploèe koja predstavlja prozor
	 * @param prolazakKrozZidove - True ako nema zidova oko ploèe
	 * @param brzinaIgre - Brzina zmije u igri
	 * @param bojaZmije - Tema za boju zmije u igri
	 */
	public PanelIgrice(OkvirIgrice okvir,String velicinaProzora, String prolazakKrozZidove, String brzinaIgre, String bojaZmije) {
		
		this.okvir = okvir;
		snake = new Snake(velicinaProzora, prolazakKrozZidove);
		
		this.postaviVelicinuProzora();
		this.postaviBrzinu(brzinaIgre);
		this.postaviBojuZmije(bojaZmije);
		
		this.setFocusable(true);
		this.setBackground(new Color(17, 17, 17));
		this.addKeyListener(new UnosSaTastature()); // klasa UnosSaTastature implementirana na dnu
		
		snake.pokreniIgru();
		this.postaviTajmer();
		
	}
	
	/**
	 * Funkcija koja setuje širinu i visinu prozora u ovisnosti od širine i visine iz logike
	 * 
	 */
	private void postaviVelicinuProzora() {

		SIRINA_PROZORA = snake.getSIRINA()*VELICINA_POLJA;
		VISINA_PROZORA = snake.getVISINA()*VELICINA_POLJA;
		this.setPreferredSize(new Dimension(SIRINA_PROZORA, VISINA_PROZORA));
		
	}
	
	/**
	 * Funkcija koja setuje brzinu igre u ovinosti od parametra
	 * 
	 * @param brzinaIgre - String na osnovu kojeg se štima CEKANJE
	 */
	private void postaviBrzinu(String brzinaIgre) {
		
		if(brzinaIgre.equals("sporo")) {
			CEKANJE = 150;
		}
		else if(brzinaIgre.equals("brzo")) {
			CEKANJE = 75;
		}
		else if(brzinaIgre.equals("ultra")) {
			CEKANJE = 50;
		}
		else {
			CEKANJE = 100;
		}
		
	}
	
	/**
	 * Funkcija koja setuje temu za boju zmije u ovisnosti od parametra
	 * 
	 * @param brzinaIgre - String na osnovu kojeg se štima bojaGlave, bojaTijela i bojaRepa zmije
	 */
	private void postaviBojuZmije(String bojaZmije) {
		
		if(bojaZmije.equals("plava")) {
			bojaGlave = new Color(0,191,255);
			bojaTijela = new Color(30,144,255);
			bojaRepa = new Color(65,105,225);
		}
		else if(bojaZmije.equals("žuta")) {
			bojaGlave = new Color(255,255,0);
			bojaTijela = new Color(255,215,0);
			bojaRepa = new Color(218,165,32);
		}
		else {
			bojaGlave = Color.green;
			bojaTijela = new Color(0, 128, 0);
			bojaRepa = new Color(34, 139, 34);
		}
		
	}
	
	/**
	 * Funkcija koja pravi instancu klase tajmer i pokreæe ga
	 * 
	 */
	private void postaviTajmer() {
		
		tajmer = new Timer(CEKANJE, this);
		tajmer.start();
	
	}
	
	/**
	 * Funkcija koja stalno ažurira situaciju u ovisnosti od razlièitih atributa klase
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(snake.getZmijaSeKrece()) {
			
			snake.pomjeriZmiju();
			
			if(snake.dosloDoSudara()) {
				tajmer.stop();
			}
			else if(snake.hranaPojedena()) {
				snake.azurirajPodatke();
			}
			
		}
		
		repaint();
		
	}
	
	/**
	 * Funkcija koja poziva istoimenu funkciju nadklase, a potom i funkciju crtaj()
	 * 
	 * @param g - Grafika koja se koristi pri iscrtavanju
	 */
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);		
		crtaj(g);
		
	}

	/**
	 * Funkcija koja crta prepreke, jabuke, zmiju i tekst broja bodova
	 * 
	 * @param g - Grafika koja se koristi pri iscrtavanju
	 */
	private void crtaj(Graphics g) {

		int brojDijelovaZmije = snake.getBrojDijelovaZmije();
		
		if(snake.getZmijaSeKrece()) {
			
			int prepreka1x[] = snake.getPrepreka1x();
			int prepreka1y[] = snake.getPrepreka1y();
			int prepreka2x[] = snake.getPrepreka2x();
			int prepreka2y[] = snake.getPrepreka2y();
			int prepreka3x[] = snake.getPrepreka3x();
			int prepreka3y[] = snake.getPrepreka3y();
			int prepreka4x[] = snake.getPrepreka4x();
			int prepreka4y[] = snake.getPrepreka4y();

			// crtanje prepreka
			g.setColor(Color.white);
			for(int i=0; i<3; i++) {
				g.fillRect(prepreka1x[i]*VELICINA_POLJA, prepreka1y[i]*VELICINA_POLJA, VELICINA_POLJA, VELICINA_POLJA);
				g.fillRect(prepreka2x[i]*VELICINA_POLJA, prepreka2y[i]*VELICINA_POLJA, VELICINA_POLJA, VELICINA_POLJA);
				g.fillRect(prepreka3x[i]*VELICINA_POLJA, prepreka3y[i]*VELICINA_POLJA, VELICINA_POLJA, VELICINA_POLJA);
				g.fillRect(prepreka4x[i]*VELICINA_POLJA, prepreka4y[i]*VELICINA_POLJA, VELICINA_POLJA, VELICINA_POLJA);
			}
			
			// crtanje obiène jabuke
			g.setColor(Color.red);
			g.fillRect(snake.getXKoordinataJabuke1()*VELICINA_POLJA, snake.getYKoordinataJabuke1()*VELICINA_POLJA, VELICINA_POLJA, VELICINA_POLJA);
			
			// crtanje specijalne jabuke
			g.setColor(Color.orange);
			g.fillRect(snake.getXKoordinataJabuke2()*VELICINA_POLJA, snake.getYKoordinataJabuke2()*VELICINA_POLJA, VELICINA_POLJA, VELICINA_POLJA);
			
			// crtanje glave od zmije
			g.setColor(bojaGlave);
			g.fillRect(snake.getXKoordinateZmije(0)*VELICINA_POLJA, snake.getYKoordinateZmije(0)*VELICINA_POLJA, VELICINA_POLJA, VELICINA_POLJA);
			
			// crtanje tijela od zmije
			g.setColor(bojaTijela);
			for(int i=1; i<brojDijelovaZmije-1; i++) {
				g.fillRect(snake.getXKoordinateZmije(i)*VELICINA_POLJA, snake.getYKoordinateZmije(i)*VELICINA_POLJA, VELICINA_POLJA, VELICINA_POLJA);
			}
			
			// crtanje repa od zmije
			g.setColor(bojaRepa);
			g.fillRect(snake.getXKoordinateZmije(brojDijelovaZmije-1)*VELICINA_POLJA, snake.getYKoordinateZmije(brojDijelovaZmije-1)*VELICINA_POLJA, VELICINA_POLJA, VELICINA_POLJA);
			
			// crtanje teksta za broj bodova, te centriranje horizontalno na dno prozora
			g.setColor(Color.gray);
			g.setFont(new Font("ROMAN_BASELINE", Font.CENTER_BASELINE, VELICINA_POLJA));
			FontMetrics velicina = getFontMetrics(g.getFont());
			g.drawString("Broj bodova: " + snake.getBrojPojedenihJabuka(), (SIRINA_PROZORA - velicina.stringWidth("Broj bodova: " + snake.getBrojPojedenihJabuka()))/2, (VISINA_PROZORA - g.getFont().getSize()));
		
		}
		else { // priprema za kraj igre

			// crtanje tijela i repa zmije
			g.setColor(Color.gray);
			for(int i=1; i<brojDijelovaZmije; i++) {
				g.fillRect(snake.getXKoordinateZmije(i)*VELICINA_POLJA, snake.getYKoordinateZmije(i)*VELICINA_POLJA, VELICINA_POLJA, VELICINA_POLJA);
			}
			
			// crtanje glave od zmije
			g.setColor(Color.orange);
			g.fillRect(snake.getXKoordinateZmije(0)*VELICINA_POLJA, snake.getYKoordinateZmije(0)*VELICINA_POLJA, VELICINA_POLJA, VELICINA_POLJA);
			
			this.igraGotova(g);
			
		}
		
	}
	
	/**
	 * Funkcija koja se pozove samo u sluèaju da je zmija prestala sa kretanjem i iscrtava endgame(kraj igre)
	 * 
	 * @param g - Grafika koja se koristi pri iscrtavanju
	 */
	private void igraGotova(Graphics g) {

		int brojPojedenihJabuka = snake.getBrojPojedenihJabuka();
		
		// promjena boje pozadine
		this.setBackground(Color.black);
		
		// Enter za ponovnu igru
		g.setColor(Color.white);
		g.setFont(new Font("Sans", Font.CENTER_BASELINE, (int)(SIRINA_PROZORA/20)));
		FontMetrics velicinaFontaa = getFontMetrics(g.getFont());
		g.drawString("Pritisnite Enter za novu partiju", (SIRINA_PROZORA - velicinaFontaa.stringWidth("Pritisnite Enter za novu partiju"))/2, (int)(VISINA_PROZORA*0.3));
		
		// ureðivanje i ispisivanje teksta za gotovu igru, te centriranje na prozoru (i horizontalno i vertiklano)
		g.setColor(Color.red);
		g.setFont(new Font("ROMAN_BASELINE", Font.BOLD, SIRINA_PROZORA/10));
		FontMetrics velicinaFonta2 = getFontMetrics(g.getFont());
		g.drawString("Igra Gotova", (SIRINA_PROZORA - velicinaFonta2.stringWidth("Igra Gotova"))/2, VISINA_PROZORA/2);
		
		// ureðivanje i ispisivanje teksta za broj osvojenih bodova, te centriranje horizontalno ispod ovog gore teksta
		g.setColor(Color.white);
		g.setFont(new Font("Sans", Font.CENTER_BASELINE, (int)(SIRINA_PROZORA/20)));
		FontMetrics velicinaFonta1 = getFontMetrics(g.getFont());
		g.drawString("Broj osvojenih bodova: " + brojPojedenihJabuka, (SIRINA_PROZORA - velicinaFonta1.stringWidth("Broj osvojenih bodova: " + brojPojedenihJabuka))/2, (int)(VISINA_PROZORA*0.7));
		
	}
	
	/**
	 * Klasa koja extend-a KeyAdapter, te koja je neophodna radi ažuriranja smjera kretanja zmije
	 * 
	 */
	private class UnosSaTastature extends KeyAdapter {
		

		/**
		 * Funkcija koja stalno ažurira situaciju u ovisnosti od tipke koja je pritisnuta na tastaturi
		 * 
		 * @param e - sadrži informacije o tipki koja je pritisnuta
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			
			int smjerKretanjaZmije = snake.getSmjerKretanjaZmije();
			
			// preusmjeravanje zmije stiskanjem neke od strelica na tastaturi
			switch(e.getKeyCode()) {
				
				// ako je potrebno nema smisla obrnut smjer kretanja zmije ako se mijenja smjer strelicama...
				case KeyEvent.VK_UP:
					if(smjerKretanjaZmije != 'S') {
						snake.setSmjerKretanjaZmije('G');
					}
					break;
					
				case KeyEvent.VK_RIGHT:
					if(smjerKretanjaZmije != 'L') {
						snake.setSmjerKretanjaZmije('D');
					}
					break;
					
				case KeyEvent.VK_DOWN:
					if(smjerKretanjaZmije != 'G') {
						snake.setSmjerKretanjaZmije('S');
					}
					break;
					
				case KeyEvent.VK_LEFT:
					if(smjerKretanjaZmije != 'D') {
						snake.setSmjerKretanjaZmije('L');
					}
					break;
					
				case KeyEvent.VK_A:
					snake.setSmjerKretanjaZmije("lijevo");
					break;
					
				case KeyEvent.VK_D:
					snake.setSmjerKretanjaZmije("desno");
					break;
				

				case KeyEvent.VK_ENTER:
					okvir.dispose();
					new OkvirIzbora();
					break;
					
				case KeyEvent.VK_ESCAPE:
					System.exit(1);
	
			}
			
		}
		
	}

}