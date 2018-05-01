package logika;



public class Plosca {
	final int N = 11;
	Vozlisce[] tabelaVozlisc;
	
//Plosco predstavimo kot tabelo vozlisc
	public Plosca() {
		this.tabelaVozlisc = new Vozlisce[N*N];
		
	}
	
	public Vozlisce vozlisce(int i){
		return tabelaVozlisc[i];
	}
	
	public boolean povezava (int i, int j){
		return vozlisce(i).sosedi.contains(j);
	}
	
	
	public void dodajPovezavo(int i, int j){
		if (i != j){
			vozlisce(i).sosedi.add(j);
			//vozlisce(j).sosedi.add(i);
		}
	}
	
	//Pretvori double v int
	public static int round(double x) {
		return (int)(x + 0.5);
	}
	
	public void incializacija() {
		int counter = 0;
		for (int y = 0; y <= N-1; y++) {
			for (int x = 0; x <= N-1; x++) {
				Vozlisce tmp = new Vozlisce(x, y);
				tabelaVozlisc[counter] = tmp;
				if (y == 0) {
					tmp.rob.add(Rob.m1);
					dodajPovezavo(counter, counter + 1);
					dodajPovezavo(counter, counter - 1);
					dodajPovezavo(counter, counter + (N-1));
					dodajPovezavo(counter, counter + N);
				} else if (x == 0) {
					tmp.rob.add(Rob.r1);
					dodajPovezavo(counter, counter + 1);
					dodajPovezavo(counter, counter - N);
					dodajPovezavo(counter, counter + N);
					dodajPovezavo(counter, counter - (N-1));
				} else if (x == (N-1)) {
					tmp.rob.add(Rob.r2);
					dodajPovezavo(counter, counter - 1);
					dodajPovezavo(counter, counter - N);
					dodajPovezavo(counter, counter + N);
					dodajPovezavo(counter, counter + (N-1));
				} else if (y == (N-1)) {
					tmp.rob.add(Rob.m2);
					dodajPovezavo(counter, counter + 1);
					dodajPovezavo(counter, counter - 1);
					dodajPovezavo(counter, counter - N);
					dodajPovezavo(counter, counter - (N-1));
				} else {
					dodajPovezavo(counter, counter + 1);
					dodajPovezavo(counter, counter - 1);
					dodajPovezavo(counter, counter - N);
					dodajPovezavo(counter, counter - (N-1));
					dodajPovezavo(counter, counter + N);
					dodajPovezavo(counter, counter + (N-1));
				}
				counter++;
			}
		}
		vozlisce(0).rob.add(Rob.r1);
		vozlisce(10).rob.add(Rob.r2);
		vozlisce(110).rob.add(Rob.m2);
		vozlisce(120).rob.add(Rob.m2);
		vozlisce(0).sosedi.remove(-1);
		vozlisce(0).sosedi.remove(10);
		vozlisce(10).sosedi.remove(11);
		vozlisce(110).sosedi.remove(121);
		vozlisce(120).sosedi.remove(130);
		vozlisce(120).sosedi.remove(131);
	}
	
	//TODO: metoda simetrija, ki vrne simetrièni par za dano vozlišèe (implementacije prek 'podgrup')
	//TODO: rotacija plošèe za 180°. baje pomaga pri complexity (zgleda da bo šlo èez z tabelaVozlisc.reverse())

}
