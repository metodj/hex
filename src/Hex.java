import javax.swing.JFrame;

import gui.GlavnoOkno;
import logika.Plosca;

public class Hex {

	public static void main(String[] args) {
		JFrame glavno_okno = new GlavnoOkno();
		glavno_okno.pack();
		glavno_okno.setVisible(true);
		//System.out.println(Plosca.sosedi_bridge(11,11));
	}

}
