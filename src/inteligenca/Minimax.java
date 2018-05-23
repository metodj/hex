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
	 * Ali raèualnik igra X ali O?
	 */
	private Igralec jaz; // koga igramo
	
	/**
	 * @param master glavno okno, v katerem vleèemo poteze
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
	 * Z metodo minimax poišèi najboljšo potezo v dani igri.
	 * 
	 * @param k števec globine, do kje smo že preiskali
	 * @param igra
	 * @return najboljša poteza (ali null, èe ji ni), skupaj z oceno najboljše poteze
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
		// Nekdo je na potezi, ugotovimo, kaj se splaèa igrati
		
		if (globina % 2 == 0) {
			if (k >= globina) {
				// dosegli smo najveèjo dovoljeno globino, zato
				// ne vrnemo poteze, ampak samo oceno pozicije
				return new OcenjenaPoteza(
						null,
						Ocena.oceniPozicijo(jaz, igra));
			}
		} else {
			if (k >= globina) {
				// dosegli smo najveèjo dovoljeno globino, zato
				// ne vrnemo poteze, ampak samo oceno pozicije
				return new OcenjenaPoteza(
						null,
						-Ocena.oceniPozicijo(jaz, igra));
			}
		}
		// Hranimo najboljšo do sedaj videno potezo in njeno oceno.
		// Tu bi bilo bolje imeti seznam do sedaj videnih najboljših potez, ker je lahko
		// v neki poziciji veè enakovrednih najboljših potez. Te bi lahko zbrali
		// v seznam, potem pa vrnili nakljuèno izbrano izmed najboljših potez, kar bi
		// popestrilo igro raèunalnika.
		
		List<Poteza> najboljsa = new LinkedList<Poteza>();
		int ocenaNajboljsih = 0;
		for (Poteza p : igra.razpolozljive_poteze()) {
			// V kopiji igre odigramo potezo p
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj_potezo_advanced(p);
			// Izraèunamo vrednost pozicije po odigrani potezi p
			int ocenaP = minimax(k+1, kopijaIgre).vrednost;
			// Èe je p boljša poteza, si jo zabeležimo
			if (najboljsa.isEmpty()// še nimamo kandidata za najboljšo potezo
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
		// Vrnemo najboljšo najdeno potezo in njeno oceno
		assert (!najboljsa.isEmpty());
		
		int random_index = ThreadLocalRandom.current().nextInt(0, najboljsa.size());
		return new OcenjenaPoteza(najboljsa.get(random_index), ocenaNajboljsih);
	}
	
	//TODO: odpraviti bug (minimax ne dela za lihe globine, npr. 1 in 3)
	//UPDATE: po dodatnem preverjanje parnosti parametra globina v metodi minimax se zdi,
	// da dela bolje pri globini 3 kot prej, hkrati pa vseeno dela slabše kot pri globini 2. Èudno!
}
