package logika;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Igra {
	Plosca plosca;
	private Igralec naPotezi;
	private List<Vozlisce> razpolozljivePoteze; // seznam se moznih potez
	
	
	
	public Igra(Igralec prvi) {
		this.plosca = new Plosca();
		plosca.incializacija();
		this.naPotezi = prvi; //za moznost izbire kdo zacne
		this.razpolozljivePoteze = new LinkedList<Vozlisce>();
		for (int countor = 0; countor <= 120; countor ++) {
			razpolozljivePoteze.add(plosca.vozlisce(countor));
		}
		
	}
	
	//rekurzivna funkcija ki pove ali je dano vozlisce povezano s koncnim robom
	public Pot obstaja_pot(Igralec igrac, Vozlisce voz, Set<Vozlisce> obiskano) {
		Pot tmp = new Pot();
		tmp.pot = obiskano;
		tmp.pot.add(voz);
		if (igrac == Igralec.modri && voz.barva == Barva.modra) {
			if (voz.rob.contains(Rob.m2)) {
				tmp.koncna = true;
				return tmp;
			}
			for (int sosed : voz.sosedi) {//mogoce se pogoj za optimizacijo
				if (plosca.vozlisce(sosed).barva == Barva.modra && !tmp.pot.contains(plosca.vozlisce(sosed))) {
					tmp.pot.add(plosca.vozlisce(sosed));
					return obstaja_pot(igrac,plosca.vozlisce(sosed),tmp.pot);
				}
			}
		} else if (igrac == Igralec.rdeci && voz.barva == Barva.rdeca) {
			if (voz.rob.contains(Rob.r2)) {
				tmp.koncna = true;
				return tmp;
			}
			for (int sosed : voz.sosedi) {//mogoce se pogoj za optimizacijo
				if (plosca.vozlisce(sosed).barva == Barva.rdeca && !tmp.pot.contains(plosca.vozlisce(sosed))) {
					tmp.pot.add(plosca.vozlisce(sosed));
					return obstaja_pot(igrac,plosca.vozlisce(sosed),tmp.pot);
				}
			}
		} return tmp;
	}
	
	
	
	//naprej pogledamo, kdo je na potezi. potem pogledamo, ali je v prejsnji potezi zmagal nasprotnik
	public Stanje stanje() {
		if (naPotezi == Igralec.modri) {
			for (int i = 0; i <= 10; i++) {
				if (obstaja_pot(naPotezi.nasprotnik(), plosca.vozlisce(i*11), new HashSet<Vozlisce>()).koncna) {
					return Stanje.zmaga_rdeci;
				}
			}
			return Stanje.poteza_modri;
		} else {
			for (int i = 0; i <= 10; i++) {
				if (obstaja_pot(naPotezi.nasprotnik(), plosca.vozlisce(i), new HashSet<Vozlisce>()).koncna) {
					return Stanje.zmaga_modri;
				}
			}
			return Stanje.poteza_rdeci;
		}
	}
	
	
	
	//preverimo legitimnost poteze, kliknemo lahko le na prazno polje
	public boolean odigraj(Poteza p) {
		if (p.izbran.barva == Barva.prazen) {
			if (naPotezi == Igralec.modri) {
				p.izbran.barva = Barva.modra;
			} else {
				p.izbran.barva = Barva.rdeca;
			}
			naPotezi = naPotezi.nasprotnik();
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	

}
