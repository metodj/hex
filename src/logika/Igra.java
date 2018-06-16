package logika;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Igra {
	public Plosca plosca;
	private Igralec naPotezi;
	//public static int stPotez = 0;
	public int stPotez;
	public MatrikaSosednosti matrikaRdeci;
	public MatrikaSosednosti matrikaModri;
	
	
	public Igra(Igralec prvi) {
		this.plosca = new Plosca();
		plosca.inicializacija();
		this.matrikaModri = new MatrikaSosednosti();
		this.matrikaRdeci = new MatrikaSosednosti();
		this.matrikaModri.inicializacija(Igralec.MODRI);
		this.matrikaRdeci.inicializacija(Igralec.RDECI);
		this.naPotezi = prvi; //za moznost izbire kdo zacne
		this.stPotez = 0;
	}
	
	
	/**
	 * nov konstruktor (za metodo copyIgra)
	 * 
	 */
	public Igra(Igra igra) {
		this.plosca = new Plosca();
		for(int x=1; x <= Plosca.N; x++) {
			for(int y=1; y <= Plosca.N; y++) {
				this.plosca.matrikaPolj[y][x] = igra.plosca.matrikaPolj[y][x];
			}
		}
		this.matrikaModri = new MatrikaSosednosti();
		this.matrikaRdeci = new MatrikaSosednosti();
		for (int i = 0; i < Plosca.N*Plosca.N + 2; i++) {
			for (int j = 0; j < Plosca.N*Plosca.N + 2; j++) {
				this.matrikaModri.matrika[i][j] = igra.matrikaModri.matrika[i][j];
				this.matrikaRdeci.matrika[i][j] = igra.matrikaRdeci.matrika[i][j];
			}
		}
		
		this.naPotezi = igra.naPotezi; //za moznost izbire kdo zacne
		this.stPotez = igra.stPotez;
	}
	
	

	/**
	 * rekurzivna funkcija ki pove ali je dano vozlisce povezano s koncnim robom.
	 * ce zmagovalna pot obstaja jo funkcija vrne; v nasprotnem primeru vrne null.
	 */
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
	
	
	
	
	/**
	 * Alternativa obstaja poti kot je pokazal asistent
	 */
	public List<Tuple> obstaja_pot2(Igralec igralec, Tuple zacetni){
		List<Tuple> sosedi = new ArrayList<>();
		List<Tuple> father = new ArrayList<>();
		sosedi.add(zacetni);
		father.add(null);
		if (igralec == Igralec.MODRI) {
			int i = 0;
			while(i < sosedi.size()) {
				Tuple trenutni = sosedi.get(i);
				if (trenutni.getY() == Plosca.N) {
					List<Tuple> pot = new ArrayList<>();
					pot.add(trenutni);
					Tuple oce = father.get(i);
					while (oce != null) {
						int j = sosedi.indexOf(oce);
						pot.add(sosedi.get(j));
						oce = father.get(j);
					}
					Collections.reverse(pot);
					return pot;
				}
				for (Tuple sosed : plosca.sosedi(trenutni.getX(), trenutni.getY())) {
					if(!sosedi.contains(sosed) && plosca.matrikaPolj[sosed.getY()][sosed.getX()] == Polje.MODRO) {
						sosedi.add(sosed);
						father.add(trenutni);
					}
				}
				i ++;
			}
			return null;
		} else {
			int i = 0;
			while(i < sosedi.size()) {
				Tuple trenutni = sosedi.get(i);
				if (trenutni.getX() == Plosca.N) {
					List<Tuple> pot = new ArrayList<>();
					pot.add(trenutni);
					Tuple oce = father.get(i);
					while (oce != null) {
						int j = sosedi.indexOf(oce);
						pot.add(sosedi.get(j));
						oce = father.get(j);
					}
					Collections.reverse(pot);
					return pot;
				}
				for (Tuple sosed : plosca.sosedi(trenutni.getX(), trenutni.getY())) {
					if(!sosedi.contains(sosed) && plosca.matrikaPolj[sosed.getY()][sosed.getX()] == Polje.RDECE) {
						sosedi.add(sosed);
						father.add(trenutni);
					}
				}
				i ++;
			}
			return null;
		}
	}
	
	
		
	
	
		/**
		 * naprej pogledamo, kdo je na potezi. potem pogledamo, ali je v prejsnji potezi zmagal nasprotnik.
		 * to  naredimo tako, da gremo cez vsa robna vozlisca in gledamo ali obstaja pot do nasprotnega roba
		 */
		public Stanje stanje() {
			boolean stikalo_rdec = true;
			boolean stikalo_moder = true;
			if (naPotezi == Igralec.MODRI) {
				for (int y = 1; y <= Plosca.N; y++) {
					if (plosca.matrikaPolj[y][1] == Polje.RDECE && stikalo_rdec) {
						//List<Tuple> tmp2 = new LinkedList<Tuple>();
						//tmp2.add(new Tuple(1,y));
						Tuple tmp2 = new Tuple(1,y);
						stikalo_rdec = false;
						if (obstaja_pot2(naPotezi.nasprotnik(),tmp2) != null) {
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
						//List<Tuple> tmp2 = new LinkedList<Tuple>();
						//tmp2.add(new Tuple(x,1));
						Tuple tmp2 = new Tuple(x,1);
						stikalo_moder = false;
						if (obstaja_pot2(naPotezi.nasprotnik(),tmp2) != null) {
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
		
	
		
		
		/**
		 * Odigramo potezo in dodatno sproti popravljamo matriki sosednosti.
		 */
		public boolean odigraj_potezo_advanced(Poteza p) {
			if (stPotez == 1 && plosca.matrikaPolj[p.getY()][p.getX()] != Polje.PRAZNO) {
				if (naPotezi == Igralec.MODRI) {
					plosca.matrikaPolj[p.getY()][p.getX()] = Polje.PRAZNO;
					plosca.matrikaPolj[p.getX()][p.getY()] = Polje.MODRO;
					this.matrikaModri.popravi_matriko_sosednosti(Polje.MODRO, Igralec.MODRI, p);
					this.matrikaRdeci.popravi_matriko_sosednosti(Polje.MODRO, Igralec.RDECI, p);
				} else {
					plosca.matrikaPolj[p.getY()][p.getX()] = Polje.PRAZNO;
					plosca.matrikaPolj[p.getX()][p.getY()] = Polje.RDECE;
					this.matrikaModri.popravi_matriko_sosednosti(Polje.RDECE, Igralec.MODRI, p);
					this.matrikaRdeci.popravi_matriko_sosednosti(Polje.RDECE, Igralec.RDECI, p);
				}
				naPotezi = naPotezi.nasprotnik();
				stPotez ++;
				return true;
			} else if (plosca.matrikaPolj[p.getY()][p.getX()] == Polje.PRAZNO) {
				if (this.naPotezi == Igralec.MODRI) {
					plosca.matrikaPolj[p.getY()][p.getX()] = Polje.MODRO;
					this.matrikaModri.popravi_matriko_sosednosti(Polje.MODRO, Igralec.MODRI, p);
					this.matrikaRdeci.popravi_matriko_sosednosti(Polje.MODRO, Igralec.RDECI, p);
				} else {
					plosca.matrikaPolj[p.getY()][p.getX()] = Polje.RDECE;
					this.matrikaModri.popravi_matriko_sosednosti(Polje.RDECE, Igralec.MODRI, p);
					this.matrikaRdeci.popravi_matriko_sosednosti(Polje.RDECE, Igralec.RDECI, p);
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
					if (plosca.matrikaPolj[y][x] == Polje.PRAZNO || this.stPotez == 1) {
						rez.add(new Poteza(x, y));
					}
				}
			}
			return rez;
		}
		
		

		
		public boolean prva_poteza_pomozna() {
			if (this.stPotez == 1) {
				for (int x = 1; x <= Plosca.N; x ++) {
					for (int y = 1; y <= Plosca.N; y ++) {
						if (this.plosca.matrikaPolj[y][x] != Polje.PRAZNO) {
							if ((3 <= x) && (x <= Plosca.N - 2) && (3 <= y) && (y <= Plosca.N - 2)) {
								return true;
							}
						}
					}
				}
			}
			return false;
		}
		
		

}
