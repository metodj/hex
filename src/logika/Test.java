package logika;

import java.util.LinkedList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		
	Igra tmp = new Igra(Igralec.MODRI);
	tmp.odigraj_potezo_advanced(new Poteza(5,2));
	tmp.odigraj_potezo_advanced(new Poteza(5,2));
	System.out.println(tmp.plosca.matrikaPolj[5][2] == Polje.RDECE);
	System.out.println(tmp.plosca.matrikaPolj[2][5] == Polje.PRAZNO);
	System.out.println(tmp.razpolozljive_poteze().size());
	System.out.println(tmp.stPotez);
	
	}
}
