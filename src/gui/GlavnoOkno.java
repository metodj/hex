package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import logika.Poteza;

public class GlavnoOkno extends JFrame implements ActionListener{
	
	private IgralnoPolje polje;
	
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
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void odigraj(Poteza poteza) {
		// TODO Auto-generated method stub
		
	}

}
