package Logika;

import java.util.Random;

/**
 * Klasa preko koja èuva svu logiku igre
 * 
 * @param random - Random varijabla za generisanje sluèajnih brojeva
 * @param SIRINA - Broj polja horizontalno
 * @param VISINA - Broj polja vertikalno
 * @param BROJ_POLJA - Ukupni broj polja u igrici
 * @param zidovi - Èuva informaciju o tome da li ima zidova ili ne u ovom mode-u igre
 * @param xKoordinateZmije - Èuva niz x koordinate zmije, gdje je nulti indeks glava, prvi indeks vrat itd.
 * @param yKoordinateZmije - Èuva niz y koordinate zmije, gdje je nulti indeks glava, prvi indeks vrat itd.
 * @param brojDijelovaZmije - Ukupan broj dijelova zmije do sada
 * @param smjerKretanjaZmije - Smjer u kojem se zmija trenutno kreæe
 * @param zmijaSeKrece - Informacija o tome da li je zmija u pokretu
 * @param xKoordinataJabuke1 - x koordinata obiène jabuke
 * @param yKoordinataJabuke1 - y koordinata obiène jabuke
 * @param xKoordinataJabuke2 - x koordinata specijalne jabuke
 * @param yKoordinataJabuke2 - y koordinata specijalne jabuke
 * @param brojPojedenihJabuka - Èuva informaciju o broju pojedenih jabuka do sada
 * @param prepreka1x - Èuva x koordinate prepreke1
 * @param prepreka1y - Èuva y koordinate prepreke1
 * @param prepreka2x - Èuva x koordinate prepreke2
 * @param prepreka2y - Èuva y koordinate prepreke2
 * @param prepreka3x - Èuva x koordinate prepreke3
 * @param prepreka3y - Èuva y koordinate prepreke3
 * @param prepreka4x - Èuva x koordinate prepreke4
 * @param prepreka4y - Èuva y koordinate prepreke4
 * @param obrnuteKomande - True ako su trenutno obrnute komande
 * @param vrijemePoremecaja - Vrijeme preostalo do kraja izvrnutih komandi
 */
public class Snake {
	
	Random random;
	
	private int SIRINA;
	private int VISINA;
	private int BROJ_POLJA;
	private boolean zidovi;
	
	private int xKoordinateZmije[];
	private int yKoordinateZmije[];
	private int brojDijelovaZmije = 5; // jedan dio zmije zauzima taèno jedno polje
	private char smjerKretanjaZmije = 'D'; // 'D' - desno, 'L' - lijevo, 'G' - gore, 'S' - strmu/south/jug
	private boolean zmijaSeKrece = false;

	private int xKoordinataJabuke1;
	private int yKoordinataJabuke1;
	private int xKoordinataJabuke2; // x koordinata specijalne jabuke
	private int yKoordinataJabuke2; // y koordinata specijalne jabuke
	private int brojPojedenihJabuka; // jedna pojedena jabuka je jedan bod
	
	private int prepreka1x[], prepreka1y[];
	private int prepreka2x[], prepreka2y[];
	private int prepreka3x[], prepreka3y[];
	private int prepreka4x[], prepreka4y[];
	private boolean obrnuteKomande = false;
	private int vrijemePoremecaja = 0;
	
	/**
	 * Konstruktor klase Snake
	 * 
	 * @param velicinaProzora - Velièina ploèe koja predstavlja prozor
	 * @param prolazakKrozZidove - True ako nema zidova oko ploèe
	 */
	public Snake(String velicinaProzora, String prolazakKrozZidove) {
		
		random = new Random();
		
		setujVelicinuProzora(velicinaProzora);
		setujProlazakKrozZidove(prolazakKrozZidove);
		
		// setujemo zmiju za ulazak sa sredine lijevog zida
		setujZmijuLijevo();
	
	}
	
	/**
	 * Funkcija koja setuje širinu i visinu ploèe u ovisnosti od parametra
	 * 
	 * @param velicinaProzora - String na osnovu kojeg se štimaju varijable SIRINA, VISINA, BROJ_POLJA, xKoordinateZmije i yKoordinateZmije
	 */
	private void setujVelicinuProzora(String velicinaProzora) {
		
		if(velicinaProzora.equals("konzola")) {
			SIRINA = 10;
			VISINA = 10;
		}
		else if(velicinaProzora.equals("mali")) {
			SIRINA = 20;
			VISINA = 18;
		}
		else if(velicinaProzora.equals("veliki")) {
			SIRINA = 40;
			VISINA = 36;
		}
		else {
			SIRINA = 30;
			VISINA = 27;
		}

		BROJ_POLJA = SIRINA*VISINA;
		xKoordinateZmije = new int[BROJ_POLJA];
		yKoordinateZmije = new int[BROJ_POLJA];
		
	}
	
	/**
	 * Funkcija koja setuje širinu i visinu ploèe u ovisnosti od parametra
	 * 
	 * @param prolazakKrozZidove - String na osnovu kojeg se štima varijabla zidovi
	 */
	private void setujProlazakKrozZidove(String prolazakKrozZidove) {
		
		// setovanje da li ima zidova ili ne
		if(prolazakKrozZidove.equals("ne") || prolazakKrozZidove.equals("NE") || prolazakKrozZidove.equals("Ne") || prolazakKrozZidove.equals("nE")) {
			zidovi = true;
		}
		else zidovi = false;
		
	}

	/**
	 * Funkcija koja setuje zmiju na sredinu ploèe lijevo
	 * 
	 */
	private void setujZmijuLijevo() {
		
		for(int i=0; i<brojDijelovaZmije; i++) {
			xKoordinateZmije[i] = brojDijelovaZmije-i-1;
			yKoordinateZmije[i] = (VISINA-1)/2;
		}
		
	}

	/**
	 * Funkcija koja pokreæe igru
	 * 
	 */
	public void pokreniIgru() {
		
		spawnujPostaviPrepreke();

		postaviJabuku1();
		postaviSpecijalnuJabuku();
		
		zmijaSeKrece = true;
		
	}

	/**
	 * Funkcija koja generiše i postavlja prepreke
	 * 
	 * @param prolazakKrozZidove - String na osnovu kojeg se štima varijabla zidovi
	 */
	private void spawnujPostaviPrepreke() {
		
		int polaSirine = (int)(SIRINA/2); 
		int polaVisine = (int)(VISINA/2);
		
		// 4 dijela ploce		
		// gornji lijevi dio
		int gornjiLijeviX = 2 + random.nextInt(polaSirine-4);
		int gornjiLijeviY = 2 + random.nextInt(polaVisine-4);
		
		// gornji desni dio
		int gornjiDesniX = polaSirine + 2 + random.nextInt(polaSirine-4);
		int gornjiDesniY = 2 + random.nextInt(polaVisine-4);

		// donji lijevi dio
		int donjiLijeviX = 2 + random.nextInt(polaSirine-4);
		int donjiLijeviY = polaVisine + 2 + random.nextInt(polaVisine-4);
		
		// donji desni dio
		int donjiDesniX = polaSirine + 2 + random.nextInt(polaSirine-4);
		int donjiDesniY = polaVisine + 2 + random.nextInt(polaVisine-4);
		
		prepreka1x = new int[]{gornjiLijeviX, gornjiLijeviX, gornjiLijeviX+1};
		prepreka1y = new int[]{gornjiLijeviY, gornjiLijeviY+1, gornjiLijeviY};

		prepreka2x = new int[]{gornjiDesniX, gornjiDesniX, gornjiDesniX-1};
		prepreka2y = new int[]{gornjiDesniY, gornjiDesniY+1, gornjiDesniY};

		prepreka3x = new int[]{donjiLijeviX, donjiLijeviX, donjiLijeviX+1};
		prepreka3y = new int[]{donjiLijeviY, donjiLijeviY-1, donjiLijeviY};
		
		prepreka4x = new int[]{donjiDesniX, donjiDesniX, donjiDesniX-1};
		prepreka4y = new int[]{donjiDesniY, donjiDesniY-1, donjiDesniY};
		
	}

	/**
	 * Funkcija koja postavlja obiènu jabuku na ploèu
	 * 
	 */
	private void postaviJabuku1() {
		
		xKoordinataJabuke1 = random.nextInt((int)(SIRINA));
		yKoordinataJabuke1 = random.nextInt((int)(VISINA));
		
		while(true) {
			boolean postaviOpet = false;
			for(int i=0; i<3; i++) {
				if(xKoordinataJabuke1 == prepreka1x[i] && yKoordinataJabuke1 == prepreka1y[i]) {
					postaviOpet = true;
					break;
				}
				else if(xKoordinataJabuke1 == prepreka2x[i] && yKoordinataJabuke1 == prepreka2y[i]) {
					postaviOpet = true;
					break;
				}
				else if(xKoordinataJabuke1 == prepreka3x[i] && yKoordinataJabuke1 == prepreka3y[i]) {
					postaviOpet = true;
					break;
				}
				else if(xKoordinataJabuke1 == prepreka4x[i] && yKoordinataJabuke1 == prepreka4y[i]) {
					postaviOpet = true;
					break;
				}
			}
			if(postaviOpet) {
				xKoordinataJabuke1 = random.nextInt((int)(SIRINA));
				yKoordinataJabuke1 = random.nextInt((int)(VISINA));
			}
			else {
				return;
			}
		}
	}

	/**
	 * Funkcija koja postavlja specijalnu jabuku na ploèu
	 * 
	 */
	private void postaviSpecijalnuJabuku() {
		
		xKoordinataJabuke2 = random.nextInt((int)(SIRINA));
		yKoordinataJabuke2 = random.nextInt((int)(VISINA));
		
		while(true) {
			boolean postaviOpet = false;
			for(int i=0; i<3; i++) {
				if(xKoordinataJabuke2 == prepreka1x[i] && yKoordinataJabuke2 == prepreka1y[i]) {
					postaviOpet = true;
					break;
				}
				else if(xKoordinataJabuke2 == prepreka2x[i] && yKoordinataJabuke2 == prepreka2y[i]) {
					postaviOpet = true;
					break;
				}
				else if(xKoordinataJabuke2 == prepreka3x[i] && yKoordinataJabuke2 == prepreka3y[i]) {
					postaviOpet = true;
					break;
				}
				else if(xKoordinataJabuke2 == prepreka4x[i] && yKoordinataJabuke2 == prepreka4y[i]) {
					postaviOpet = true;
					break;
				}
			}
			if(postaviOpet) {
				xKoordinataJabuke2 = random.nextInt((int)(SIRINA));
				yKoordinataJabuke2 = random.nextInt((int)(VISINA));
			}
			else {
				return;
			}
		}
	
	}
	

	/**
	 * Funkcija koja pomjera zmiju u smjeru kretanja tj. mijenja x i y koordinate zmije
	 * 
	 */
	public void pomjeriZmiju() {
		
		if(vrijemePoremecaja <= 0) {
			obrnuteKomande = false;
		}
		else {
			vrijemePoremecaja--;
		}
		
		// promjena koordinata za tijelo zmije
		for(int i = brojDijelovaZmije-1; i>0; i--) {
			xKoordinateZmije[i] = xKoordinateZmije[i-1];
			yKoordinateZmije[i] = yKoordinateZmije[i-1];
		}
		
		// promjena koordinata za glavu zmije
		if(smjerKretanjaZmije == 'G') {
			if(!zidovi) {
				if(yKoordinateZmije[0] == 0) {
					yKoordinateZmije[0] = VISINA - 1;
				}
				else {
					yKoordinateZmije[0] = yKoordinateZmije[0] - 1;
				}
			}
			else {
				yKoordinateZmije[0] = yKoordinateZmije[0] - 1;
			}
		}
		else if(smjerKretanjaZmije == 'D') {
			if(!zidovi) {
				if(xKoordinateZmije[0] == (SIRINA-1)) {
					xKoordinateZmije[0] = 0;
				}
				else {
					xKoordinateZmije[0] = xKoordinateZmije[0] + 1;
				}
			}
			else {
				xKoordinateZmije[0] = xKoordinateZmije[0] + 1;
			}
		}
		else if(smjerKretanjaZmije == 'S') {
			if(!zidovi) {
				if(yKoordinateZmije[0] == (VISINA-1)) {
					yKoordinateZmije[0] = 0;
				}
				else {
					yKoordinateZmije[0] = yKoordinateZmije[0] + 1;
				}
			}
			else {
				yKoordinateZmije[0] = yKoordinateZmije[0] + 1;
			}
		}
		else if(smjerKretanjaZmije == 'L') {
			if(!zidovi) {
				if(xKoordinateZmije[0] == 0) {
					xKoordinateZmije[0] = SIRINA - 1;
				}
				else {
					xKoordinateZmije[0] = xKoordinateZmije[0] - 1;
				}
			}
			else {
				xKoordinateZmije[0] = xKoordinateZmije[0] - 1;
			}
		}
		
	}

	/**
	 * Funkcija koja provjerava da li je došlo do sudara zmije sa preprekom, sa sobom ili zidom(ako ima zidova)
	 * 
	 * @return vraæa True ako je došlo do sudara, u suprotnom vraæa false
	 */
	public boolean dosloDoSudara() {
		
		if(zidovi) {
			// ovo provjerava da li se glava sudarila sa gornjim zidom
			if(yKoordinateZmije[0] < 0) {
				zmijaSeKrece = false;
			}
			// ovo provjerava da li se glava sudarila sa desnim zidom
			else if(xKoordinateZmije[0] >= SIRINA) {
				zmijaSeKrece = false;
			}
			// ovo provjerava da li se glava sudarila sa donjim zidom
			else if(yKoordinateZmije[0] >= VISINA) {
				zmijaSeKrece = false;
			}
			// ovo provjerava da li se glava sudarila sa lijevim zidom
			else if(xKoordinateZmije[0] < 0) {
				zmijaSeKrece = false;
			}
		}
		
		if(!zmijaSeKrece) return true;
		
		// ovo provjerava da li se glava zmije sudarila sa svojim tijelom
		for(int i = brojDijelovaZmije-1; i>0; i--) {
			if((xKoordinateZmije[0] == xKoordinateZmije[i]) && (yKoordinateZmije[0] == yKoordinateZmije[i])) {
				zmijaSeKrece = false;
				return true;
			}
		}
		
		// ovo provjerava da li se glava zmije sudarila sa nekom preprekom
		for(int i=0; i<3; i++) {
			if(xKoordinateZmije[0] == prepreka1x[i] && yKoordinateZmije[0] == prepreka1y[i]) {
				zmijaSeKrece = false;
				return true;
			} 
			else if(xKoordinateZmije[0] == prepreka2x[i] && yKoordinateZmije[0] == prepreka2y[i]) {
				zmijaSeKrece = false;
				return true;
			}
			else if(xKoordinateZmije[0] == prepreka3x[i] && yKoordinateZmije[0] == prepreka3y[i]) {
				zmijaSeKrece = false;
				return true;
			} 
			else if(xKoordinateZmije[0] == prepreka4x[i] && yKoordinateZmije[0] == prepreka4y[i]) {
				zmijaSeKrece = false;
				return true;
			}
		}
		
		return false;
		
	}

	/**
	 * Funkcija koja provjerava da li je neka od jabuka pojedena
	 * 
	 * @return vraæa True ako je neka jabuka pojedena, u suprotnom vraæa false
	 */
	public boolean hranaPojedena() {
		
		if(((xKoordinateZmije[0] == xKoordinataJabuke1) && (yKoordinateZmije[0] == yKoordinataJabuke1)) ||
		   ((xKoordinateZmije[0] == xKoordinataJabuke2) && (yKoordinateZmije[0] == yKoordinataJabuke2))) {
			return true;
		}
			 
		return false;
		
	}
	
	/**
	 * Funkcija koja ažurira odgovarajuæe varijable klase Logika
	 * 
	 */
	public void azurirajPodatke() {

		if((xKoordinateZmije[0] == xKoordinataJabuke1) && (yKoordinateZmije[0] == yKoordinataJabuke1)) {
			postaviJabuku1();
		}
		else{ /// jedan od 3 specijalna sluèaja...
			int slucaj = random.nextInt(3);
			if(brojDijelovaZmije < 6 && slucaj == 2) {
				slucaj = random.nextInt(2);
			}
			if(slucaj == 0) { // povecaj zmiju za 2 clanka
				brojDijelovaZmije++;
				xKoordinateZmije[brojDijelovaZmije-1] = xKoordinateZmije[brojDijelovaZmije-2];
				yKoordinateZmije[brojDijelovaZmije-1] = yKoordinateZmije[brojDijelovaZmije-2];
			} 
			else if (slucaj == 1) { // obrni komande
				obrnuteKomande = true;
				vrijemePoremecaja = 50;
			}
			else { // smanji zmiju za jedan clanak
				brojDijelovaZmije--;
			}
			postaviSpecijalnuJabuku();
		}
		
		brojDijelovaZmije++;
		xKoordinateZmije[brojDijelovaZmije-1] = xKoordinateZmije[brojDijelovaZmije-2];
		yKoordinateZmije[brojDijelovaZmije-1] = yKoordinateZmije[brojDijelovaZmije-2];
		
		brojPojedenihJabuka++;
		
	}
	
	/**
	 * Geter funkcija za varijablu SIRINA
	 * 
	 * @return SIRINA
	 */
	public int getSIRINA() {
		return SIRINA;
	}
	
	/**
	 * Geter funkcija za varijablu VISINA
	 * 
	 * @return VISINA
	 */
	public int getVISINA() {
		return VISINA;
	}
	
	/**
	 * Geter funkcija za varijablu BROJ_POLJA
	 * 
	 * @return BROJ_POLJA
	 */
	public int getBROJ_POLJA() {
		return BROJ_POLJA;
	}
	
	/**
	 * Geter funkcija za i-tu x koordinatu zmije
	 * 
	 * @return xKoordinateZmije[i]
	 */
	public int getXKoordinateZmije(int i) {
		return xKoordinateZmije[i];
	}
	
	/**
	 * Geter funkcija za i-tu y koordinatu zmije
	 * 
	 * @return yKoordinateZmije[i]
	 */
	public int getYKoordinateZmije(int i) {
		return yKoordinateZmije[i];
	}
	
	/**
	 * Geter funkcija za x koordinatu obiène jabuke
	 * 
	 * @return xKoordinataJabuke1
	 */
	public int getXKoordinataJabuke1() {
		return xKoordinataJabuke1;
	}
	
	/**
	 * Geter funkcija za y koordinatu obiène jabuke
	 * 
	 * @return yKoordinataJabuke1
	 */
	public int getYKoordinataJabuke1() {
		return yKoordinataJabuke1;
	}
	
	/**
	 * Geter funkcija za x koordinatu specijalne jabuke
	 * 
	 * @return xKoordinataJabuke2
	 */
	public int getXKoordinataJabuke2() {
		return xKoordinataJabuke2;
	}
	
	/**
	 * Geter funkcija za y koordinatu specijalne jabuke
	 * 
	 * @return yKoordinataJabuke2
	 */
	public int getYKoordinataJabuke2() {
		return yKoordinataJabuke2;
	}
	
	/**
	 * Geter funkcija za trenutni broj dijelova zmije
	 * 
	 * @return brojDijelovaZmije
	 */
	public int getBrojDijelovaZmije() {
		return brojDijelovaZmije;
	}
	
	/**
	 * Geter funkcija za broj pojedenih jabuka do trenutka poziva funkcije
	 * 
	 * @return brojPojedenihJabuka
	 */
	public int getBrojPojedenihJabuka() {
		return brojPojedenihJabuka;
	}
	
	/**
	 * Geter funkcija za smjer kretanja zmije
	 * 
	 * @return smjerKretanjaZmije
	 */
	public char getSmjerKretanjaZmije() {
		return smjerKretanjaZmije;
	}
	
	/**
	 * Geter funkcija za varijablu zmijaSeKrece
	 * 
	 * @return zmijaSeKrece
	 */
	public boolean getZmijaSeKrece() {
		return zmijaSeKrece;
	}	
	
	/**
	 * Geter funkcija za niz prepreka1x
	 * 
	 * @return prepreka1x
	 */
	public int[] getPrepreka1x(){
		return prepreka1x;
	}
	
	/**
	 * Geter funkcija za niz prepreka2x
	 * 
	 * @return prepreka2x
	 */
	public int[] getPrepreka2x(){
		return prepreka2x;
	}
	
	/**
	 * Geter funkcija za niz prepreka3x
	 * 
	 * @return prepreka3x
	 */
	public int[] getPrepreka3x(){
		return prepreka3x;
	}
	
	/**
	 * Geter funkcija za niz prepreka4x
	 * 
	 * @return prepreka4x
	 */
	public int[] getPrepreka4x(){
		return prepreka4x;
	}
	
	/**
	 * Geter funkcija za niz prepreka1y
	 * 
	 * @return prepreka1y
	 */
	public int[] getPrepreka1y(){
		return prepreka1y;
	}
	
	/**
	 * Geter funkcija za niz prepreka2y
	 * 
	 * @return prepreka2y
	 */
	public int[] getPrepreka2y(){
		return prepreka2y;
	}
	
	/**
	 * Geter funkcija za niz prepreka3y
	 * 
	 * @return prepreka3y
	 */
	public int[] getPrepreka3y(){
		return prepreka3y;
	}
	
	/**
	 * Geter funkcija za niz prepreka4y
	 * 
	 * @return prepreka4y
	 */
	public int[] getPrepreka4y(){
		return prepreka4y;
	}
	
	/**
	 * Geter funkcija za varijablu obrnuteKomande
	 * 
	 * @return obrnuteKomande
	 */
	public boolean getObrnuteKomande(){
		return obrnuteKomande;
	}
	
	/**
	 * Seter funkcija za smjerKretanjaZmije na osnovu znaka smjer
	 * 
	 * @param smjer - novi smjer kretanja zmije
	 */
	public void setSmjerKretanjaZmije(char smjer) {
		smjerKretanjaZmije = smjer;
	}
	
	/**
	 * Seter funkcija za varijablu obrnuteKomande na osnovu Stringa smjer
	 * 
	 * @param smjer - String na osnovu kojeg se setuje novi smjer kretanja zmije
	 */
	public void setSmjerKretanjaZmije(String smjer) {
		
		String lokalniSmjer = smjer;
		System.out.println("Lokalni smjer: " + lokalniSmjer);
		System.out.println("Pravi smjer: " + this.smjerKretanjaZmije);
		
		if(obrnuteKomande) {
			if(lokalniSmjer == "lijevo") {
				lokalniSmjer = "desno";
			}
			else if(lokalniSmjer == "desno"){ // nije else jer lokalniSmjer može biti bilo šta što igraè ukuca
				lokalniSmjer = "lijevo";
			}
		}
		if(lokalniSmjer.equals("lijevo")) {
			
			if(smjerKretanjaZmije == 'G') {
				setSmjerKretanjaZmije('L');
			}
			else if(smjerKretanjaZmije == 'D') {
				setSmjerKretanjaZmije('G');
			}
			else if(smjerKretanjaZmije == 'S') {
				setSmjerKretanjaZmije('D');
			}
			else if(smjerKretanjaZmije == 'L') {
				setSmjerKretanjaZmije('S');
			}
			
		}
		else if(lokalniSmjer.equals("desno")) {
			
			if(smjerKretanjaZmije == 'G') {
				setSmjerKretanjaZmije('D');
			}
			else if(smjerKretanjaZmije == 'D') {
				setSmjerKretanjaZmije('S');
			}
			else if(smjerKretanjaZmije == 'S') {
				setSmjerKretanjaZmije('L');
			}
			else if(smjerKretanjaZmije == 'L') {
				setSmjerKretanjaZmije('G');
			}
			
		}
		
	}

}
