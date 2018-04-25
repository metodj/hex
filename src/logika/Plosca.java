package logika;



public class Plosca {
	final int N = 121;
	Vozlisce[] tabelaVozlisc;
	
//Plosco predstavimo kot tabelo vozlisc
	public Plosca() {
		this.tabelaVozlisc = new Vozlisce[N];
		
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
			vozlisce(j).sosedi.add(i);
		}
	}
	
	public void incializacija() {
		
		int indeks = 0;
		//spodnji trikotnik romba
		for (int i = 1; i <= 11; i++) {
			for (int j = 1; j <= i; j++ ) {
				Vozlisce a = new Vozlisce(i, j); //pozor: tole niso obiène karteziène koordinate
				if (i <= 11 && j == 1) {
					a.rob.add(Rob.r1);
				} else if (i >= 11 && j == 1) {
					a.rob.add(Rob.m2);
				} else if (i == j) {
					a.rob.add(Rob.m1);
				} else if (i + j == 22) {
					a.rob.add(Rob.r2);
				} else {		// imamo notranje vozlišèe, dodamo 6 sosedov
					a.sosedi.add(indeks+1);
					a.sosedi.add(indeks-1);
					a.sosedi.add(indeks+i);
					a.sosedi.add(indeks-i);
					a.sosedi.add(indeks+i+1);
					a.sosedi.add(indeks-i+1);
				}
				this.tabelaVozlisc[indeks] = a;
				indeks ++;
			}
		}
		
		//zgornji trikotnik romba
		for (int i = 12; i <= 21; i++) {
			for (int j = 1; j <= 22 - i; j++ ) {
				Vozlisce a = new Vozlisce(i, j); //pozor: tole niso obiène karteziène koordinate
				if (i >= 11 && j == 1) {
					a.rob.add(Rob.m2);
				} else if (i + j == 22) {
					a.rob.add(Rob.r2);
				} else {		// imamo notranje vozlišèe, dodamo 6 sosedov
					a.sosedi.add(indeks+1);
					a.sosedi.add(indeks-1);
					a.sosedi.add(indeks+22-i);
					a.sosedi.add(indeks-(22-i));
					a.sosedi.add(indeks+22-i+1);
					a.sosedi.add(indeks-(22-i)+1);
				}
				this.tabelaVozlisc[indeks] = a;
				indeks ++;
			}
		}
	
		//TODO: dodaj sosede robnim vozlišèem (tistim, ki imajo 2, 3 ali 4 sosede)
		
	}
	
	//TODO: metoda simetrija, ki vrne simetrièni par za dano vozlišèe (implementacije prek 'podgrup')
	//TODO: rotacija plošèe za 180°. baje pomaga pri complexity (zgleda da bo šlo èez z tabelaVozlisc.reverse())

}
