package logika;

import java.util.HashSet;
import java.util.Set;

public class Vozlisce {
	public int x;
	public int y;
	public Set<Rob> rob;//ali je vozlisce na robu, ker imajo vogali 2 roba. Notranji bodo prazne mnozice.
	public Barva barva;
	public Set<Vozlisce> sosedi;
	public Object ime;
	
	
	public Vozlisce(int x, int y, Object ime) {
		this.x = x;
		this.y = y;
		this.ime = ime;
		this.barva = Barva.prazen;
		this.rob = new HashSet<Rob>();
		this.sosedi = new HashSet<Vozlisce>();
	} 
	
	
	
	

}

