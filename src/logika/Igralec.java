package logika;

public enum Igralec {
	RDECI, MODRI;
	
	
	public Igralec nasprotnik() {
		return (this == RDECI ? MODRI : RDECI);
	}


}

