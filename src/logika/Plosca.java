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
			//vozlisce(j).sosedi.add(i);
		}
	}
	
	//Pretvori double v int
	public static int round(double x) {
		return (int)(x + 0.5);
	}
	
	public void incializacija() {
		
		int indeks = 0;
		//spodnji trikotnik romba
		for (int i = 1; i <= 11; i++) {
			for (int j = 1; j <= i; j++ ) {
				Vozlisce a = new Vozlisce(i, j); //pozor: tole niso obiène karteziène koordinate
				this.tabelaVozlisc[indeks] = a;
				if (i <= 11 && j == 1) {
					a.rob.add(Rob.r1);
					dodajPovezavo(indeks, indeks +1);
					dodajPovezavo(indeks, indeks + i);
					dodajPovezavo(indeks, indeks -i + 1);
					dodajPovezavo(indeks, (indeks +1 + i) * (1 - round(Math.pow(0, 11 -i ))));
				} if (i >= 11 && j == 1) {
					a.rob.add(Rob.m2);
				}  if (i == j) {
					a.rob.add(Rob.m1);
					dodajPovezavo(indeks, (indeks +i) * (1 - round(Math.pow(0, 11 -i ))));
					dodajPovezavo(indeks, Math.max(indeks - 1,0));
					dodajPovezavo(indeks, (indeks +1+i) * (1 - round(Math.pow(0, 11 -i ))));
					dodajPovezavo(indeks, Math.max(indeks - i,0));
				} if (i + j == 22) {
					a.rob.add(Rob.r2);
				} if(j > 1 && j < i) {		// imamo notranje vozlišèe, dodamo 6 sosedov
					dodajPovezavo(indeks, indeks+1);
					dodajPovezavo(indeks, indeks-1);
					dodajPovezavo(indeks, indeks+i);
					dodajPovezavo(indeks, indeks-i);
					dodajPovezavo(indeks, indeks+i+1);
					dodajPovezavo(indeks, indeks-i+1);
				}
				indeks ++;
			}
		}
		
		//zgornji trikotnik romba
		for (int i = 12; i <= 21; i++) {
			int l = 22 - i;
			for (int j = 1; j <= 22 - i; j++ ) {
				Vozlisce a = new Vozlisce(i, j); //pozor: tole niso obiène karteziène koordinate
				this.tabelaVozlisc[indeks] = a; 
				if (i >= 11 && j == 1) {
					a.rob.add(Rob.m2);
					dodajPovezavo(indeks, Math.min(indeks+1,120));
					dodajPovezavo(indeks, Math.min(indeks+l,120));
					dodajPovezavo(indeks, indeks - l - 1);
					dodajPovezavo(indeks, indeks - l);

				} if (i + j == 22) {
					a.rob.add(Rob.r2);
					dodajPovezavo(indeks, indeks - 1);
					dodajPovezavo(indeks, indeks + l -1);
					dodajPovezavo(indeks, indeks -l -1);
					dodajPovezavo(indeks, indeks - l);
				} if(j > 1 && j < l) {		// imamo notranje vozlišèe, dodamo 6 sosedov
					dodajPovezavo(indeks, indeks+1);
					dodajPovezavo(indeks, indeks-1);
					dodajPovezavo(indeks, indeks+l);
					dodajPovezavo(indeks, indeks-(l));
					dodajPovezavo(indeks, indeks+l+1);
					dodajPovezavo(indeks, indeks-(l)+1);
				}
				indeks ++;
			}
		}
		
	
		//TODO: dodaj sosede robnim vozlišèem (tistim, ki imajo 2, 3 ali 4 sosede)
		
	}
	
	//TODO: metoda simetrija, ki vrne simetrièni par za dano vozlišèe (implementacije prek 'podgrup')
	//TODO: rotacija plošèe za 180°. baje pomaga pri complexity (zgleda da bo šlo èez z tabelaVozlisc.reverse())

}
