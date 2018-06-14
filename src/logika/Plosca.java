package logika;

import java.util.LinkedList;
import java.util.List;

public class Plosca {
	public static int N = 11;
	public Polje[][] matrikaPolj;
	
	

	/**
	 * Plosco predstavimo kot matriko vozlisc.
	 */
	public Plosca() {
		matrikaPolj = new Polje[N+2][N+2]; //  N+2, ker bo rob naprogramiran po principu 'stražarja'
	}
	
	
	
	public void inicializacija() {
		for (int y = N + 1; y >= 0; y --) {
			for (int x = 0; x <= N + 1; x ++) {
				this.matrikaPolj[y][x] = Polje.PRAZNO;
				
			}
		}
	}
	
	
	/**
	 * Vrne seznam tuplov ki predstavljajo lokacijo sosedov vozlisca s kordinatama x, y.
	 */
	public static List<Tuple> sosedi(int x, int y) {
		List<Tuple> tmp = new LinkedList<Tuple>();
		if ((x+1) <= N) {
			Tuple sosed = new Tuple(x+1,y);
			tmp.add(sosed);
		} if (1 <= (x-1)) {
			Tuple sosed = new Tuple(x-1,y);
			tmp.add(sosed);
		} if ((y+1) <= N) {
			Tuple sosed = new Tuple(x,y+1);
			tmp.add(sosed);
		} if (1 <= (y-1)) {
			Tuple sosed = new Tuple(x,y-1);
			tmp.add(sosed);
		} if ((x+1) <= N && 1 <= (y-1)) {
			Tuple sosed = new Tuple(x+1,y-1);
			tmp.add(sosed);
		} if (1 <= (x-1) && (y+1) <= N) {
			Tuple sosed = new Tuple(x-1,y+1);
			tmp.add(sosed);
		}
		return tmp;
	}
	
	public static List<Tuple> sosedi_bridge(int x, int y) {
		List<Tuple> tmp = new LinkedList<Tuple>();
		if ((x+1) <= N && (y+1) <= N) {
			Tuple sosed = new Tuple(x+1,y+1);
			tmp.add(sosed);
		} if ((x+2) <= N  && 1 <= (y-1)) {
			Tuple sosed = new Tuple(x+2,y-1);
			tmp.add(sosed);
		} if ((x+1) <= N && 1 <= (y-2)) {
			Tuple sosed = new Tuple(x+1,y-2);
			tmp.add(sosed);
		} if (1 <= (y-1) && 1 <= (x-1)) {
			Tuple sosed = new Tuple(x-1,y-1);
			tmp.add(sosed);
		} if ((y+1) <= N && 1 <= (x-2)) {
			Tuple sosed = new Tuple(x-2,y+1);
			tmp.add(sosed);
		} if (1 <= (x-1) && (y+2) <= N) {
			Tuple sosed = new Tuple(x-1,y+2);
			tmp.add(sosed);
		}
		return tmp;
	}
	

}
