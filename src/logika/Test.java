package logika;

import java.util.LinkedList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		
	Igra tmp = new Igra(Igralec.MODRI);
	tmp.odigraj_potezo(new Poteza(2,2));
	tmp.odigraj_potezo(new Poteza(2,3));
	
	//System.out.println(tmp.obstaja_pot(Igralec.MODRI, tmp2));
	//System.out.println(tmp.stanje());
	System.out.println(tmp.razpolozljive_poteze().size());
	System.out.println(tmp.plosca.matrikaPolj[2][2] != Polje.PRAZNO);
	}
	
	//TODO namesto teh 'naivnih' testov se naredi junit test

}
