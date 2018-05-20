package inteligenca;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;
import logika.Igra;
import logika.Igralec;
import logika.Poteza;

public class Minimax extends SwingWorker<Poteza, Object>{

	/**
	 * Glavno okno, v katerem poteka ta igra
	 */
	private GlavnoOkno master;

	/**
	 * Globina, do katere pregleduje minimax
	 */

	private int globina;

	/**
	 * Ali ra�ualnik igra X ali O?
	 */
	private Igralec jaz; // koga igramo
	
	/**
	 * @param master glavno okno, v katerem vle�emo poteze
	 * @param globina koliko potez naprej gledamo
	 * @param jaz koga igramo
	 */
	public Minimax(GlavnoOkno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
	}
	
	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		OcenjenaPoteza p = minimax(0, igra);
		assert (p.poteza != null);
		return p.poteza;
	}
	
	@Override
	public void done() {
		try {
			Poteza p = this.get();
			if (p != null) { master.odigraj(p); }
		} catch (Exception e) {
		}
	}

	/**
	 * Z metodo minimax poi��i najbolj�o potezo v dani igri.
	 * 
	 * @param k �tevec globine, do kje smo �e preiskali
	 * @param igra
	 * @return najbolj�a poteza (ali null, �e ji ni), skupaj z oceno najbolj�e poteze
	 */	
	private OcenjenaPoteza minimax(int k, Igra igra) {
		Igralec naPotezi = null;
		// Ugotovimo, ali je konec, ali je kdo na potezi?
		switch (igra.stanje()) {
		case POTEZA_MODRI: naPotezi = Igralec.MODRI; break;
		case POTEZA_RDECI: naPotezi = Igralec.RDECI; break;
		// Igre je konec, ne moremo vrniti poteze, vrnemo le vrednost pozicije
		case ZMAGA_RDECI:
			return new OcenjenaPoteza(
					null,
					(jaz == Igralec.RDECI ? Ocena.ZMAGA : Ocena.ZGUBA));
		case ZMAGA_MODRI:
			return new OcenjenaPoteza(
					null,
					(jaz == Igralec.MODRI ? Ocena.ZMAGA : Ocena.ZGUBA));
		}
		assert (naPotezi != null);
		// Nekdo je na potezi, ugotovimo, kaj se spla�a igrati
		
		if (k >= globina) {
			// dosegli smo najve�jo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(
					null,
					Ocena.oceniPozicijo(jaz, igra));
		}
		
		// Hranimo najbolj�o do sedaj videno potezo in njeno oceno.
		// Tu bi bilo bolje imeti seznam do sedaj videnih najbolj�ih potez, ker je lahko
		// v neki poziciji ve� enakovrednih najbolj�ih potez. Te bi lahko zbrali
		// v seznam, potem pa vrnili naklju�no izbrano izmed najbolj�ih potez, kar bi
		// popestrilo igro ra�unalnika.
		
		List<Poteza> najboljsa = new LinkedList<Poteza>();
		int ocenaNajboljsih = 0;
		for (Poteza p : igra.razpolozljive_poteze()) {
			// V kopiji igre odigramo potezo p
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj_potezo_advanced(p);
			// Izra�unamo vrednost pozicije po odigrani potezi p
			int ocenaP = minimax(k+1, kopijaIgre).vrednost;
			// �e je p bolj�a poteza, si jo zabele�imo
			if (najboljsa.size() == 0// �e nimamo kandidata za najbolj�o potezo
				|| (naPotezi == jaz && ocenaP > ocenaNajboljsih) // maksimiziramo
				|| (naPotezi != jaz && ocenaP < ocenaNajboljsih) // minimiziramo
				) {
				najboljsa = new LinkedList<Poteza>();
				najboljsa.add(p);
				ocenaNajboljsih = ocenaP;
			} else if (ocenaP == ocenaNajboljsih) {
				najboljsa.add(p);
			}
		}
		// Vrnemo najbolj�o najdeno potezo in njeno oceno
		assert (najboljsa != null);
		
		int random_index = ThreadLocalRandom.current().nextInt(0, najboljsa.size());
		return new OcenjenaPoteza(najboljsa.get(random_index), ocenaNajboljsih);
	}
	
	//TODO: odpraviti bug (minimax ne dela za lihe globine, npr. 1 in 3)
}