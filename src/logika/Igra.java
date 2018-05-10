package logika;

import java.util.LinkedList;
import java.util.List;

public class Igra {
	public Plosca plosca;
	private Igralec naPotezi;
	//public static int stPotez = 0;
	public int stPotez;
	
	
	public Igra(Igralec prvi) {
		this.plosca = new Plosca();
		plosca.inicializacija();
		this.naPotezi = prvi; //za moznost izbire kdo zacne
		this.stPotez = 0;
	}
	
	
	//rekurzivna funkcija ki pove ali je dano vozlisce povezano s koncnim robom.
	// ce zmagovalna pot obstaja jo funkcija vrne; v nasprotnem primeru vrne null.
	public List<Tuple> obstaja_pot(Igralec igralec, List<Tuple> potDoSedaj) {
		Tuple zadnji = potDoSedaj.get(potDoSedaj.size() - 1);
		if (igralec == Igralec.MODRI) {
			if (zadnji.getY() == (Plosca.N)) {
				return potDoSedaj;
			} else {
				for (Tuple sosed : plosca.sosedi(zadnji.getX(), zadnji.getY())) {
					if (plosca.matrikaPolj[sosed.getY()][sosed.getX()] == Polje.MODRO && !potDoSedaj.contains(sosed)) {
						potDoSedaj.add(sosed);
						List<Tuple> rezultat = obstaja_pot(igralec, potDoSedaj);
						if (rezultat != null) {
							return rezultat;
						} else {
							potDoSedaj.remove(potDoSedaj.size() - 1);
						}
					}
				}
			}
		} else {
			if (zadnji.getX() == (Plosca.N)) {
				return potDoSedaj;
			} else {
				for (Tuple sosed : plosca.sosedi(zadnji.getX(), zadnji.getY())) {
					if (plosca.matrikaPolj[sosed.getY()][sosed.getX()] == Polje.RDECE && !potDoSedaj.contains(sosed)) {
						potDoSedaj.add(sosed);
						List<Tuple> rezultat = obstaja_pot(igralec, potDoSedaj);
						if (rezultat != null) {
							return rezultat;
						} else {
							potDoSedaj.remove(potDoSedaj.size() - 1);
						}
					}
				}
			}
			
		}
		return null;
	}
	
	//naprej pogledamo, kdo je na potezi. potem pogledamo, ali je v prejsnji potezi zmagal nasprotnik.
	//to  naredimo tako, da gremo cez vsa robna vozlisca in gledamo ali obstaja pot do nasprotnega roba
		/*public Stanje stanje() {
			if (naPotezi == Igralec.MODRI) {
				for (int y = 1; y <= Plosca.N; y++) {
					if (plosca.matrikaPolj[y][1] == Polje.RDECE ) {
						List<Tuple> tmp2 = new LinkedList<Tuple>();
						tmp2.add(new Tuple(1,y));
						if (obstaja_pot(naPotezi.nasprotnik(),tmp2) != null) {
							return Stanje.ZMAGA_RDECI;
						}
					}
				}
				return Stanje.POTEZA_MODRI;
			} else {
				for (int x = 1; x <= Plosca.N; x++) {
					if (plosca.matrikaPolj[1][x] == Polje.MODRO ) {
						List<Tuple> tmp2 = new LinkedList<Tuple>();
						tmp2.add(new Tuple(x,1));
						if (obstaja_pot(naPotezi.nasprotnik(),tmp2) != null) {
							return Stanje.ZMAGA_MODRI;
						}
					}
				}
				return Stanje.POTEZA_RDECI;
			}
		}*/
	
		//mal izboljsana metoda kot zgornja (zakomentirana). Uporablja stikala in zato ne gremo cekirat vseh v prvi vrsti.
		public Stanje stanje() {
			boolean stikalo_rdec = true;
			boolean stikalo_moder = true;
			if (naPotezi == Igralec.MODRI) {
				for (int y = 1; y <= Plosca.N; y++) {
					if (plosca.matrikaPolj[y][1] == Polje.RDECE && stikalo_rdec) {
						List<Tuple> tmp2 = new LinkedList<Tuple>();
						tmp2.add(new Tuple(1,y));
						stikalo_rdec = false;
						if (obstaja_pot(naPotezi.nasprotnik(),tmp2) != null) {
							return Stanje.ZMAGA_RDECI;
						}
					} else if (plosca.matrikaPolj[y][1] == Polje.RDECE && !stikalo_rdec) {
						continue;
					} else if (plosca.matrikaPolj[y][1] == Polje.MODRO | plosca.matrikaPolj[y][1] == Polje.PRAZNO) {
						stikalo_rdec = true;
					}
				}
				return Stanje.POTEZA_MODRI;
			} else {
				for (int x = 1; x <= Plosca.N; x++) {
					if (plosca.matrikaPolj[1][x] == Polje.MODRO && stikalo_moder) {
						List<Tuple> tmp2 = new LinkedList<Tuple>();
						tmp2.add(new Tuple(x,1));
						stikalo_moder = false;
						if (obstaja_pot(naPotezi.nasprotnik(),tmp2) != null) {
							return Stanje.ZMAGA_MODRI;
						}
					} else if (plosca.matrikaPolj[1][x] == Polje.MODRO && !stikalo_moder) {
						continue;
					} else if (plosca.matrikaPolj[1][x] == Polje.RDECE | plosca.matrikaPolj[1][x] == Polje.PRAZNO) {
						stikalo_moder = true;
					}
				}
				return Stanje.POTEZA_RDECI;
			}
		}
		
	
		//ta je brez prve poteze
		public boolean odigraj_potezo(Poteza p) {
			if (this.razpolozljive_poteze().contains(p)) {
				if (this.naPotezi == Igralec.MODRI) {
					plosca.matrikaPolj[p.getY()][p.getX()] = Polje.MODRO;
				} else {
					plosca.matrikaPolj[p.getY()][p.getX()] = Polje.RDECE;
				}
				stPotez ++;
				naPotezi = naPotezi.nasprotnik();
				return true;
			}
			return false;
		}
		
		//dodana moznost prve poteze
		public boolean odigraj_potezo_advanced(Poteza p) {
			if (stPotez == 1 && plosca.matrikaPolj[p.getY()][p.getX()] != Polje.PRAZNO) {
				if (naPotezi == Igralec.MODRI) {
					plosca.matrikaPolj[p.getY()][p.getX()] = Polje.PRAZNO;
					plosca.matrikaPolj[p.getX()][p.getY()] = Polje.MODRO;
				} else {
					plosca.matrikaPolj[p.getY()][p.getX()] = Polje.PRAZNO;
					plosca.matrikaPolj[p.getX()][p.getY()] = Polje.RDECE;
				}
				naPotezi = naPotezi.nasprotnik();
				stPotez ++;
				return true;
			} else if (this.razpolozljive_poteze().contains(p)) {
				if (this.naPotezi == Igralec.MODRI) {
					plosca.matrikaPolj[p.getY()][p.getX()] = Polje.MODRO;
				} else {
					plosca.matrikaPolj[p.getY()][p.getX()] = Polje.RDECE;
				}
				naPotezi = naPotezi.nasprotnik();
				stPotez ++;
				return true;
			}
			return false;
			
		}
		
		
		
		
		public List<Poteza> razpolozljive_poteze() {
			List<Poteza> rez = new LinkedList<Poteza>();
			for (int y = 1; y <= Plosca.N; y++) {
				for (int x = 1; x <= Plosca.N; x ++) {
					if (plosca.matrikaPolj[y][x] == Polje.PRAZNO) {
						rez.add(new Poteza(x, y));
					}
				}
			}
			return rez;
		}
		
		


}
