package logika;

import java.util.HashMap;
import java.util.Map;

public class Plosca {
	Map<Object, Vozlisce> slovarVozlisc;
	
//Plosco predstavimo kot graf vozlisc
	public Plosca() {
		super();
		this.slovarVozlisc = new HashMap<Object, Vozlisce>();
	}
	public Vozlisce vozlisce(Object ime){
		return slovarVozlisc.get(ime);
	}
	
	public boolean povezava (Vozlisce tocka1, Vozlisce tocka2){
		return tocka1.sosedi.contains(tocka2);
	}
	
	public void dodajVozlisce(Vozlisce tocka){
		if (!slovarVozlisc.containsKey(tocka.ime)){
			slovarVozlisc.put(tocka.ime, tocka);
		}
	}
	
	public void dodajPovezavo(Vozlisce a, Vozlisce b){
		if ((a != b) && (!povezava(a, b))){
			a.sosedi.add(b);
			b.sosedi.add(a);
		}
	}
	//Morda manjka informacija o simetriji za prvo potezo in optimizacijo

}
