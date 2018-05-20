package inteligenca;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;
import logika.Tuple;

public class Ocena {
	public static final int ZMAGA = (1 << 20); // vrednost zmage, veè kot vsaka druga ocena pozicije
	public static final int ZGUBA = -ZMAGA;
	
	
	
	public static int oceniPozicijo(Igralec jaz, Igra igra) {
		switch (igra.stanje()) {
		case ZMAGA_MODRI:
			return (jaz == Igralec.MODRI ? ZMAGA : ZGUBA);
		case ZMAGA_RDECI:
			return (jaz == Igralec.RDECI ? ZMAGA : ZGUBA);
		case POTEZA_MODRI:
			int ocena_trenutne_plosce_za_modrega = 0;
			for (int x = 1; x <= Plosca.N; x ++) {
				for (int y = 1; y <= Plosca.N; y ++) {
					if (igra.plosca.matrikaPolj[y][x] == Polje.MODRO) {
						for (Tuple b_sosed : Plosca.sosedi_bridge(x,y)) {
							if (igra.plosca.matrikaPolj[b_sosed.getY()][b_sosed.getX()] == Polje.MODRO) {
								if (Math.abs(y - b_sosed.getY()) == 2) {
									ocena_trenutne_plosce_za_modrega += 1000;
								} else {
									ocena_trenutne_plosce_za_modrega += 500;
								}
								Plosca.sosedi(x, y).retainAll(Plosca.sosedi(b_sosed.getX(), b_sosed.getY()));
								int tmp = 0;
								for (Tuple t : Plosca.sosedi(x, y)) {
									if (igra.plosca.matrikaPolj[t.getY()][t.getX()] == Polje.MODRO) {
										ocena_trenutne_plosce_za_modrega += 200;
									} else if (igra.plosca.matrikaPolj[t.getY()][t.getX()] == Polje.RDECE) {
										tmp += 1;
									} 
								}
								if (tmp == 2) {
									ocena_trenutne_plosce_za_modrega -= 1000;
								} 
							}
						}
					}
				}
			}
			return ocena_trenutne_plosce_za_modrega;
		case POTEZA_RDECI:
			int ocena_trenutne_plosce_za_rdecega = 0;
			for (int x = 1; x <= Plosca.N; x ++) {
				for (int y = 1; y <= Plosca.N; y ++) {
					if (igra.plosca.matrikaPolj[y][x] == Polje.RDECE) {
						for (Tuple b_sosed : Plosca.sosedi_bridge(x,y)) {
							if (igra.plosca.matrikaPolj[b_sosed.getY()][b_sosed.getX()] == Polje.RDECE) {
								if (Math.abs(x - b_sosed.getX()) == 2) {
									ocena_trenutne_plosce_za_rdecega += 1000;
								} else {
									ocena_trenutne_plosce_za_rdecega += 500;
								}
								Plosca.sosedi(x, y).retainAll(Plosca.sosedi(b_sosed.getX(), b_sosed.getY()));
								int tmp = 0;
								for (Tuple t : Plosca.sosedi(x, y)) {
									if (igra.plosca.matrikaPolj[t.getY()][t.getX()] == Polje.RDECE) {
										ocena_trenutne_plosce_za_rdecega += 200;
									} else if (igra.plosca.matrikaPolj[t.getY()][t.getX()] == Polje.MODRO) {
										tmp += 1;
									} 
								}
								if (tmp == 2) {
									ocena_trenutne_plosce_za_rdecega -= 1000;
								}
							}
						}
					}
				}
			}
			return ocena_trenutne_plosce_za_rdecega;
		}
		assert false;
		return 42; // Java je blesava
	} 

}
