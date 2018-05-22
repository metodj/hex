package logika;

import java.util.Collections;

public class MatrikaSosednosti {
	
	public int[][] matrika;

	public MatrikaSosednosti() {
		this.matrika = new int[Plosca.N*Plosca.N+2][Plosca.N*Plosca.N+2];
	}
	
	public void inicializacija(Igralec tistiKiReze) {
		for (int y = Plosca.N ; y >= 1; y --) {
				for (int x = 1; x <= Plosca.N; x ++) {
					int indeks = x + Plosca.N*(y-1);
					for (Tuple sosed : Plosca.sosedi(x,y)) {
						this.matrika[indeks][sosed.getX() + Plosca.N*(sosed.getY()-1)] = 1;
						this.matrika[indeks][sosed.getX() + Plosca.N*(sosed.getY()-1)] = 1;
					}
				}	
			}
		if (tistiKiReze == Igralec.RDECI) {
			for (int i = 1; i <= Plosca.N; i ++) {
				this.matrika[0][i] = 100000;
			}
			for (int i = Plosca.N*Plosca.N - Plosca.N; i <= Plosca.N*Plosca.N; i ++) {
				this.matrika[i][Plosca.N*Plosca.N + 1] = 100000;
			}
			
		} else {
			for (int i = 1; i <= Plosca.N; i ++) {
				this.matrika[0][1 + (i-1)*Plosca.N] = 100000;
			}
			for (int i = 1; i <= Plosca.N; i ++) {
				this.matrika[i*Plosca.N][Plosca.N*Plosca.N + 1] = 100000;
			}
		}
	}
	
	public void popravi_matriko_sosednosti(Polje barvaPloscka, Igralec tistiKiReze, Poteza p) {
		if ((barvaPloscka == Polje.MODRO && tistiKiReze == Igralec.RDECI) | (barvaPloscka == Polje.RDECE && tistiKiReze == Igralec.MODRI)) {
			int indeks = p.getX() + Plosca.N*(p.getY()-1);
			for(Tuple sosed: Plosca.sosedi(p.getX(), p.getY())) {
				if (this.matrika[indeks][sosed.getX() + Plosca.N*(sosed.getY()-1)] == 1) {
					this.matrika[indeks][sosed.getX() + Plosca.N*(sosed.getY()-1)] = 100000;//spremenimo vrstico
					this.matrika[sosed.getX() + Plosca.N*(sosed.getY()-1)][indeks] = 100000;//spremenimo stolpec
				}
			}
		} else if ((barvaPloscka == Polje.RDECE && tistiKiReze == Igralec.RDECI) | (barvaPloscka == Polje.MODRO && tistiKiReze == Igralec.MODRI)) {
			int indeks = p.getX() + Plosca.N*(p.getY()-1);
			for(Tuple sosed: Plosca.sosedi(p.getX(), p.getY())) {
				this.matrika[indeks][sosed.getX() + Plosca.N*(sosed.getY()-1)] = 0;//spremenimo vrstico
				this.matrika[sosed.getX() + Plosca.N*(sosed.getY()-1)][indeks] = 0;//spremenimo stolpec
			}
		} 
	}
	
	

}
