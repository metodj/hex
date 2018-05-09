package gui;

//kopirano od prof. Bauer, po potrebi spremeniti
public abstract class Strateg {
	/**
	 * Glavno okno klièe to metodo, ko je strateg na potezi.
	 */
	public abstract void na_potezi();
	
	
	/**
	 * Strateg naj neha igrati potezo.
	 */
	public abstract void prekini();
	
	
	/**
	 * @param i
	 * @param j
	 * 
	 * Glanvo okno klièe to metodo, ko uporabnik klikne na polje (i,j).
	 */
	public abstract void klik(int i, int j);
		
}
