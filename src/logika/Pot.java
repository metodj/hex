package logika;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Pot {
	public boolean koncna; //povezuje roba plosce
	public Set<Vozlisce> pot;
	
	public Pot() {
		this.koncna = false;
		this.pot = new HashSet<Vozlisce>();
	}


	@Override
	public String toString() {
		return "Pot [koncna=" + koncna + ", pot=" + pot + "]";
	}
	
	
	
	

}
