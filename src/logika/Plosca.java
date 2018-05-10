package logika;

import java.util.LinkedList;
import java.util.List;

public class Plosca {
	public final static int N = 11;
	public Polje[][] matrikaPolj;
	
//Plosco predstavimo kot matriko vozlisc
	public Plosca() {
		matrikaPolj = new Polje[N+2][N+2]; //  N+2, ker bo rob naprogramiran po principu 'stražarja'
	}
	
	/*TODO: tale rob po principu strazarja je povsem nepotreben sedaj, ob priliki ga lahko odstraniva in
	 * potem polje ne bo vec 13*13 ampak le 11*11
	*/
	
	public void inicializacija() {
		for (int y = N + 1; y >= 0; y --) {
			for (int x = 0; x <= N + 1; x ++) {
				if (y == 0 | y == 12) {
					this.matrikaPolj[y][x] = Polje.PRAZNO;
				} else if ( x == 0 | x == 12) {
					this.matrikaPolj[y][x] = Polje.PRAZNO;
				} else {
					this.matrikaPolj[y][x] = Polje.PRAZNO;
				}
			}
		}
	}
	
	//Vrne seznam tuplov ki predstavljajo lokacijo sosedov vozlisca s kordinatama x, y
	public List<Tuple> sosedi(int x, int y) {
		List<Tuple> tmp = new LinkedList<Tuple>();
		if ((x+1) <= (N+1)) {
			Tuple sosed = new Tuple(x+1,y);
			tmp.add(sosed);
		} if (0 <= (x-1)) {
			Tuple sosed = new Tuple(x-1,y);
			tmp.add(sosed);
		} if ((y+1) <= (N+1)) {
			Tuple sosed = new Tuple(x,y+1);
			tmp.add(sosed);
		} if (0 <= (y-1)) {
			Tuple sosed = new Tuple(x,y-1);
			tmp.add(sosed);
		} if ((x+1) <= (N+1) && 0 <= (y-1)) {
			Tuple sosed = new Tuple(x+1,y-1);
			tmp.add(sosed);
		} if (0 <= (x-1) && (y+1) <= (N+1)) {
			Tuple sosed = new Tuple(x-1,y+1);
			tmp.add(sosed);
		}
		return tmp;
	}
	

}
