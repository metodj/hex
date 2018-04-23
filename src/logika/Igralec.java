package logika;

public enum Igralec {
	rdeci, modri;
	
	
	public Igralec nasprotnik() {
		return (this == rdeci ? modri : rdeci);
	}


}
