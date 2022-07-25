package Konzola;

import java.util.Scanner;

import Logika.Snake;

/**
 * Klasa preko koje se realizuje igrica u konzoli
 * 
 */
public class KonzolaUnosIspis {
	/**
	 * Konstruktor klase KonzolaUnosIspis
	 * 
	 * @param unos - Scanner unosa podataka u konzolu
	 * @param prolazakKrozZidove - varijabla koja cuva podatak da li ima zidova u mode-u
	 * @param SIRINA_TABELE - širina tabele/ploèe igrice koju ispisujem u konzolu
	 * @param VISINA_TABELE - visina tabele/ploèe igrice koju ispisujem u konzolu
	 * @param BROJ_POLJA - broj polja ploèe
	 * @param tabela - ploèa koja èuva informacije o svim poljima igre
	 * @param brojDijelovaZmije - trenutni broj dijelova zmije
	 * @param repI - x koordinata repa zmije
	 * @param repJ - y koordinata repa zmije
	 * @param potez - najnoviji potez igraèa
	 */
	public KonzolaUnosIspis() {
		
		Scanner unos = new Scanner(System.in);
		
		System.out.println("Zmija prolazi kroz zidove (da/ne): ");
		String prolazakKrozZidove = unos.next();
		
		Snake snake = new Snake("konzola", prolazakKrozZidove);
		snake.pokreniIgru();
		
		final int SIRINA_TABELE = snake.getSIRINA();
		final int VISINA_TABELE = snake.getVISINA();
		final int BROJ_POLJA = snake.getBROJ_POLJA();

		System.out.println("Broj kolona: " + SIRINA_TABELE);
		System.out.println("Broj redova: " + VISINA_TABELE);
		System.out.println("Broj polja: " + BROJ_POLJA);
		
		char[][] tabela = new char[VISINA_TABELE][SIRINA_TABELE];

		int prepreka1x[] = snake.getPrepreka1x();
		int prepreka2x[] = snake.getPrepreka2x();
		int prepreka3x[] = snake.getPrepreka3x();
		int prepreka4x[] = snake.getPrepreka4x();

		int prepreka1y[] = snake.getPrepreka1y();
		int prepreka2y[] = snake.getPrepreka2y();
		int prepreka3y[] = snake.getPrepreka3y();
		int prepreka4y[] = snake.getPrepreka4y();
		
		for(int i=0; i<VISINA_TABELE; i++) {
			for(int j=0; j<SIRINA_TABELE; j++) {
				tabela[i][j] = '-';
			}
		}
		
		for(int i=0; i<3; i++) {
			tabela[prepreka1x[i]][prepreka1y[i]] = '*';
			tabela[prepreka2x[i]][prepreka2y[i]] = '*';
			tabela[prepreka3x[i]][prepreka3y[i]] = '*';
			tabela[prepreka4x[i]][prepreka4y[i]] = '*';
		}
		
		String potez;
		
		int brojDijelovaZmije = snake.getBrojDijelovaZmije();
		int repI = snake.getYKoordinateZmije(brojDijelovaZmije-1);
		int repJ = snake.getXKoordinateZmije(brojDijelovaZmije-1);

		tabela[repI][repJ] = 'R';
		for(int i=brojDijelovaZmije-2; i>0; i--) {
			tabela[snake.getYKoordinateZmije(i)][snake.getXKoordinateZmije(i)] = 'T';
		}
		tabela[snake.getYKoordinateZmije(0)][snake.getXKoordinateZmije(0)] = 'G';
		
		while(snake.getZmijaSeKrece()) {

			ispisi(snake, tabela);
			
			System.out.print("Unesite potez: (desno/lijevo) ");
			potez = unos.next();
			snake.setSmjerKretanjaZmije(potez);
			
			snake.pomjeriZmiju();

			if(snake.dosloDoSudara()) {
				break;
			}
			else if(snake.hranaPojedena()) {
				snake.azurirajPodatke();
			}
			
			brojDijelovaZmije = snake.getBrojDijelovaZmije();
			
			tabela[repI][repJ] = '-'; // brišemo stari rep

			repI = snake.getYKoordinateZmije(brojDijelovaZmije-1);
			repJ = snake.getXKoordinateZmije(brojDijelovaZmije-1);
			
			tabela[repI][repJ] = 'R';
			for(int i=brojDijelovaZmije-2; i>0; i--) {
				tabela[snake.getYKoordinateZmije(i)][snake.getXKoordinateZmije(i)] = 'T';
			}
			tabela[snake.getYKoordinateZmije(0)][snake.getXKoordinateZmije(0)] = 'G';
					
		}

		unos.close();
		
		ispisi(snake, tabela);
		
		System.out.println("Igra je gotova!");
		System.out.println("Broj osvojenih bodova: " + snake.getBrojPojedenihJabuka());
		
	}

	/**
	 * Funkcija za ispis trenutne situacije u igri u vidu tabele/ploèe
	 * 
	 * @param xJabuke1 - x koordinata obiène jabuke
	 * @param yJabuke1 - y koordinata obiène jabuke
	 * @param xJabuke2 - x koordinata specijalne jabuke
	 * @param yJabuke2 - y koordinata specijalne jabuke
	 */
	private void ispisi(Snake snake, char[][] tabela) {
		
		int xJabuke1 = snake.getXKoordinataJabuke1();
		int yJabuke1 = snake.getYKoordinataJabuke1();
		int xJabuke2 = snake.getXKoordinataJabuke2();
		int yJabuke2 = snake.getYKoordinataJabuke2();
		
		// Ispis tabele u konzolu
		for(int i=0; i<tabela.length; i++) {
			
			for(int j=0; j<tabela[i].length; j++) {
				
				if(tabela[i][j] != '-') System.out.print(tabela[i][j]);
				else if(yJabuke1 == i && xJabuke1 == j) System.out.print('J');
				else if(yJabuke2 == i && xJabuke2 == j) System.out.print('S');
				else System.out.print(tabela[i][j]);
				System.out.print(" ");
				
			}
			
			System.out.println();
			
		}
		
	}
	
}
