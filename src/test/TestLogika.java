package test;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;
import logika.Poteza;
import logika.Stanje;
import logika.Tuple;

public class TestLogika extends TestCase {
	
	public void testIgra() {
		Igra igra = new Igra(Igralec.MODRI);
		// Na zaèetku imamo N * N potez
		assertEquals(igra.razpolozljive_poteze().size(), Plosca.N * Plosca.N);
		//Sedaj odigramo dve potezi. Na potezi je ponovno modri igralec, razpolozljivih potez je N*N-2
		igra.odigraj_potezo(new Poteza(10,10));
		igra.odigraj_potezo(new Poteza(9,2));
		assertEquals(igra.razpolozljive_poteze().size(), Plosca.N * Plosca.N - 2);
		assertEquals(igra.stanje(), Stanje.POTEZA_MODRI);
		//Sedaj odigramo neveljano potezo. Na potezi se vedno modri, stevilo raz. potez se ne spremeni
		igra.odigraj_potezo(new Poteza(9,2));
		assertEquals(igra.razpolozljive_poteze().size(), Plosca.N * Plosca.N - 2);
		assertEquals(igra.stanje(), Stanje.POTEZA_MODRI);
		//Naredimo zmagovalno pot za modrega. Preverimo, ce je stanje igre ZMAGA_MODRI
		for (int i = 0; i <= (Plosca.N-1); i++) {
			igra.plosca.matrikaPolj[i][1] = Polje.MODRO;
		}
		igra.odigraj_potezo(new Poteza(1,11));
		assertEquals(igra.stanje(), Stanje.ZMAGA_MODRI);
		
	
	}

}
