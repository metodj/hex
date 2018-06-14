package inteligenca;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;
import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;
import logika.Poteza;

/**
 * Pomožni razred, ki omogoèa implementacijo hevristike za prvo potezo.
 *
 */
public class PrvaPoteza extends SwingWorker<Poteza, Object> {
	
	private GlavnoOkno master;
	private Igralec jaz;


	public PrvaPoteza(GlavnoOkno master, Igralec jaz) {
		super();
		this.master = master;
		this.jaz = jaz;
	}

	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		Thread.sleep(500);
		for (int x = 3; x <= (Plosca.N - 2); x ++) {
			for (int y = 3; y <= (Plosca.N - 2); y ++) {
				if (igra.plosca.matrikaPolj[y][x] != Polje.PRAZNO) {
					Poteza p = new Poteza(x,y);
					return p;
					}
				}
			}
		return null;
		
	}
	
	@Override
	public void done() {
		try {
			Poteza p = this.get();
			if (p != null) { master.odigraj(p); }
		} catch (Exception e) {
		}
	}

}
