package gui;

import javax.swing.SwingWorker;

import inteligenca.Minimax;
import inteligenca.NakljucnaInteligenca;
import logika.Igralec;
import logika.Poteza;

/**
 * @author andrej
 *
 * Objekt, ki igra raèunalnikove poteze. Odvisen je od "inteligence",
 * ki je zenkrat fiksirana na nakljuèno izbiro poteze.
 */
public class Racunalnik extends Strateg {
	private GlavnoOkno master;
	private Igralec jaz;
	private SwingWorker<Poteza,Object> mislec;
	
	/**
	 * Ustvari nov objekt, ki vleèe raèunalnikove poteze.
	 * 
	 * @param master okno, v katerem raèunalnik vleèe poteze
	 */
	public Racunalnik(GlavnoOkno master, Igralec jaz) {
		this.master = master;
		this.jaz = jaz;
	}
	
	@Override
	public void na_potezi() {
		// Zaènemo razmišljati...
		
		//mislec = new NakljucnaInteligenca(master);
		mislec = new Minimax(master, 2, jaz);
		mislec.execute();
	}

	@Override
	public void prekini() {
		if (mislec != null) {
			mislec.cancel(true);
		}
	}

	@Override
	public void klik(int i, int j) {
		// Klike ignoriramo
	}

}
