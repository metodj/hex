package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Poteza;

public class GlavnoOkno extends JFrame implements ActionListener{
	
	private IgralnoPolje polje;
	
	private Igra igra;
	
	public GlavnoOkno() {
		this.setTitle("Hex");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout()); //kaj je tole??
		
		// igralno polje
		polje = new IgralnoPolje(this);
		GridBagConstraints polje_layout = new GridBagConstraints(); //kaj je tole?? (enako za spodnje vrstice)
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		//igra
		this.igra = new Igra(Igralec.MODRI); //tole bo pol metoda novaIgra(), glej kodo od prof. Bauer. 
		igra.odigraj_potezo_advanced(new Poteza(1,2));
		igra.odigraj_potezo_advanced(new Poteza(2,1));
		
		
	}
	
	public Polje[][] getPlosca() {
		return (igra == null ? null : igra.plosca.matrikaPolj);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void odigraj(Poteza poteza) {
		// TODO Auto-generated method stub
		
	}

	public void klikniPolje(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
