package logika;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Test {

	public static void main(String[] args) {
		Igra tmp = new Igra(Igralec.modri);
		Set<Vozlisce> bla = new HashSet<Vozlisce>();
		for(int i = 0; i <= 9; i ++) {
			tmp.plosca.vozlisce(i).barva = Barva.rdeca;
		}
		for(int i = 11; i <= 21; i ++) {
			tmp.plosca.vozlisce(i).barva = Barva.rdeca;
		}
		System.out.println(tmp.obstaja_pot(Igralec.rdeci,tmp.plosca.vozlisce(0), bla));
		System.out.println(tmp.stanje());
	}

}
