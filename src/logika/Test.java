package logika;

import java.util.LinkedList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		
	Igra tmp = new Igra(Igralec.RDECI);
	for (int i = 0; i <= 12; i++) {
		tmp.plosca.matrikaPolj[i][1] = Polje.MODRO;
	}
	List<Tuple> tmp2 = new LinkedList<Tuple>();
	tmp2.add(new Tuple(0,0));
	System.out.println(tmp.obstaja_pot(Igralec.MODRI, tmp2));
	System.out.println(tmp.stanje());
	//System.out.println(new Tuple(0,0) == new Tuple(0,0));
	}
	
	//TODO namesto teh 'naivnih' testov se naredi junit test

}
