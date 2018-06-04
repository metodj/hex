package logika;

import java.util.LinkedList;
import java.util.List;

public class Plosca {
	public final static int N = 11;
	public Polje[][] matrikaPolj;
	//public int[][] matrikaSosednostiModri;
	//public int [][] matrikaSosednostiRdeci;
	
//Plosco predstavimo kot matriko vozlisc
	public Plosca() {
		matrikaPolj = new Polje[N+2][N+2]; //  N+2, ker bo rob naprogramiran po principu 'stražarja'
		//matrikaSosednostiModri = new int[N*N + 2][N*N + 2];
		//matrikaSosednostiRdeci = new int[N*N + 2][N*N + 2];
	}
	
	/*TODO: tale rob po principu strazarja je povsem nepotreben sedaj, ob priliki ga lahko odstraniva in
	 * potem polje ne bo vec 13*13 ampak le 11*11
	*/
	
	public void inicializacija() {
		for (int y = N + 1; y >= 0; y --) {
			for (int x = 0; x <= N + 1; x ++) {
				this.matrikaPolj[y][x] = Polje.PRAZNO;
				
				/*//zaenkrat grda incializacija matrik sosednosti, ali se da bolje?
				if( x != 0 && y != 0 && x != N + 1 && y != N + 1) {
					int indeks = x + N*(y-1);
					for (Tuple sosed : sosedi(x,y)) {
						this.matrikaSosednostiModri[indeks][sosed.getX() + N*(sosed.getY()-1)] = 1;
						this.matrikaSosednostiRdeci[indeks][sosed.getX() + N*(sosed.getY()-1)] = 1;
					}
				}
				
				//uredimo se povezave sink-ov in source-ov
				if (y == 0) {
					for (int i = 1; i <= N; i ++) {
						this.matrikaSosednostiRdeci[0][i] = 100000;
					}
				}
				if (x == 0) {
					for (int i = 1; i <= N; i ++) {
						this.matrikaSosednostiModri[0][1 + (i-1)*N] = 100000;
					}
				}
				if (y == N) {
					for (int i = N*N - N; i <= N*N; i ++) {
						this.matrikaSosednostiRdeci[i][N*N + 1] = 100000;
					}
				}
				if (x == N) {
					for (int i = 1; i <= N; i ++) {
						this.matrikaSosednostiModri[i*N][N*N + 1] = 100000;
					}
				}*/
				
			}
		}
	}
	
	//Vrne seznam tuplov ki predstavljajo lokacijo sosedov vozlisca s kordinatama x, y
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
