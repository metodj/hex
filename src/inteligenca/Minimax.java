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
	private int alpha = Ocena.ZGUBA-1;
	private int beta = Ocena.ZMAGA+1;

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
		OcenjenaPoteza p = minimax(0, alpha, beta, igra);
		assert (p.poteza != null);
		System.out.println("Minimax: " + p);
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
	private OcenjenaPoteza minimax(int k, int alpha, int beta, Igra igra) {
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
		
		if (k >= globina) {
			// dosegli smo najveèjo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(
					null,
					Ocena.oceniPozicijo(jaz, igra));
		}
		// Hranimo najboljšo do sedaj videno potezo in njeno oceno.
		// Tu bi bilo bolje imeti seznam do sedaj videnih najboljših potez, ker je lahko
		// v neki poziciji veè enakovrednih najboljših potez. Te bi lahko zbrali
		// v seznam, potem pa vrnili nakljuèno izbrano izmed najboljših potez, kar bi
		// popestrilo igro raèunalnika.
		
		List<Poteza> najboljsa = new LinkedList<Poteza>();
		
		if (naPotezi == jaz) {
			int ocenaNajboljsih = Ocena.ZGUBA;
			for (Poteza p : igra.razpolozljive_poteze()) {
				// V kopiji igre odigramo potezo p
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.odigraj_potezo_advanced(p);
				// Izraèunamo vrednost pozicije po odigrani potezi p
				int ocenaP = minimax(k+1, alpha, beta, kopijaIgre).vrednost;
				// Èe je p boljša poteza, si jo zabeležimo
				if (najboljsa.isEmpty()// še nimamo kandidata za najboljšo potezo
					|| (ocenaP > ocenaNajboljsih) ) {
					najboljsa = new LinkedList<Poteza>();
					najboljsa.add(p);
					ocenaNajboljsih = ocenaP;
				} else if (ocenaP == ocenaNajboljsih) {
					najboljsa.add(p);
				}
				alpha = Math.max(ocenaNajboljsih, alpha);
				if (beta < alpha) {
					break;
				}
			}
			// Vrnemo najboljšo najdeno potezo in njeno oceno
			assert (!najboljsa.isEmpty());
			
			int random_index = ThreadLocalRandom.current().nextInt(0, najboljsa.size());
			return new OcenjenaPoteza(najboljsa.get(random_index), ocenaNajboljsih);
		} else {
			int ocenaNajboljsih = Ocena.ZMAGA;
			for (Poteza p : igra.razpolozljive_poteze()) {
				// V kopiji igre odigramo potezo p
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.odigraj_potezo_advanced(p);
				// Izraèunamo vrednost pozicije po odigrani potezi p
				int ocenaP = minimax(k+1, alpha, beta, kopijaIgre).vrednost;
				// Èe je p boljša poteza, si jo zabeležimo
				if (najboljsa.isEmpty()// še nimamo kandidata za najboljšo potezo
					|| (ocenaP < ocenaNajboljsih) ) {
					najboljsa = new LinkedList<Poteza>();
					najboljsa.add(p);
					ocenaNajboljsih = ocenaP;
				} else if (ocenaP == ocenaNajboljsih) {
					najboljsa.add(p);
				}
				beta = Math.min(ocenaNajboljsih, beta);
				if (beta < alpha) {
					break;
				}
			}
			// Vrnemo najboljšo najdeno potezo in njeno oceno
			assert (!najboljsa.isEmpty());
			
			int random_index = ThreadLocalRandom.current().nextInt(0, najboljsa.size());
			return new OcenjenaPoteza(najboljsa.get(random_index), ocenaNajboljsih);
		}
		
		
		//brez seznama
		
		/*Poteza najboljsa = null;
		
		if (naPotezi == jaz) {
			int ocenaNajboljsih = Ocena.ZGUBA;
			for (Poteza p : igra.razpolozljive_poteze()) {
				// V kopiji igre odigramo potezo p
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.odigraj_potezo_advanced(p);
				// Izraèunamo vrednost pozicije po odigrani potezi p
				int ocenaP = minimax(k+1, alpha, beta, kopijaIgre).vrednost;
				// Èe je p boljša poteza, si jo zabeležimo
				if (ocenaP >= ocenaNajboljsih) {
					najboljsa = p;
					ocenaNajboljsih = ocenaP;
				}
				alpha = Math.max(ocenaNajboljsih, alpha);
				if (beta <= alpha) {
					break;
				}
			}
			return new OcenjenaPoteza(najboljsa, ocenaNajboljsih);
		} else {
			int ocenaNajboljsih = Ocena.ZMAGA;
			for (Poteza p : igra.razpolozljive_poteze()) {
				// V kopiji igre odigramo potezo p
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.odigraj_potezo_advanced(p);
				// Izraèunamo vrednost pozicije po odigrani potezi p
				int ocenaP = minimax(k+1, alpha, beta, kopijaIgre).vrednost;
				// Èe je p boljša poteza, si jo zabeležimo
				if (ocenaP <= ocenaNajboljsih)  {
					najboljsa = p;
					ocenaNajboljsih = ocenaP;
				} 
				beta = Math.min(ocenaNajboljsih, beta);
				if (beta <= alpha) {
					break;
				}
			}
			return new OcenjenaPoteza(najboljsa, ocenaNajboljsih);
		}*/
	}
	
}
