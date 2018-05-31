import java.util.Arrays;

import javax.swing.JFrame;

import gui.GlavnoOkno;
import inteligenca.FordFulkerson;
import logika.Igralec;
import logika.MatrikaSosednosti;
import logika.Plosca;
import logika.Polje;
import logika.Poteza;

public class Hex {

	public static void main(String[] args) {
		JFrame glavno_okno = new GlavnoOkno();
		glavno_okno.pack();
		glavno_okno.setVisible(true);
		
	/*	MatrikaSosednosti tmp = new MatrikaSosednosti();
		tmp.inicializacija(Igralec.RDECI);
		FordFulkerson tmp2 = new FordFulkerson(Plosca.N*Plosca.N + 2);
		System.out.println(tmp2.fordFulkerson(tmp, 0, Plosca.N*Plosca.N + 1));
		tmp.popravi_matriko_sosednosti(Polje.RDECE, Igralec.RDECI, new Poteza(1,1));
		System.out.println(Arrays.deepToString(tmp.matrika));*/
	}

}
