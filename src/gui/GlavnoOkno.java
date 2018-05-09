package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Poteza;

public class GlavnoOkno extends JFrame implements ActionListener{
	
	private IgralnoPolje polje;
	
	private Igra igra;
	
	private JLabel status;
	
	private Strateg strateg_moder;
	
	private Strateg strateg_rdec;
	
	private JMenuItem nova_igra;
	
	public GlavnoOkno() {
		this.setTitle("Hex");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout()); //kaj je tole??
		
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Igra");
		menu_bar.add(igra_menu);
		nova_igra = new JMenuItem("Nova igra");
		igra_menu.add(nova_igra);
		nova_igra.addActionListener(this);
		
		// igralno polje
		polje = new IgralnoPolje(this);
		GridBagConstraints polje_layout = new GridBagConstraints(); //kaj je tole?? (enako za spodnje vrstice)
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		// statusna vrstica za sporocila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		//igra
		//this.igra = new Igra(Igralec.MODRI); //tole bo pol metoda novaIgra(), glej kodo od prof. Bauer. 
		//igra.odigraj_potezo_advanced(new Poteza(1,2));
		//igra.odigraj_potezo_advanced(new Poteza(2,1));
		
		nova_igra();
		
		
	}
	
	private void nova_igra() {
		
		if (strateg_moder != null) { strateg_moder.prekini(); }
		if (strateg_rdec != null) { strateg_rdec.prekini(); }
		this.igra = new Igra(Igralec.RDECI);
		strateg_moder = new Clovek(this);
		strateg_rdec = new Clovek(this);
		// Tistemu, ki je na potezi, to povemo
		switch (igra.stanje()) {
		case POTEZA_MODRI: strateg_moder.na_potezi(); break;
		case POTEZA_RDECI: strateg_rdec.na_potezi(); break;
		default: break;
		}
		osveziGUI();
		repaint();
		
	}

	private void osveziGUI() {
		if (igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(igra.stanje()) {
			case POTEZA_MODRI: status.setText("Na potezi je modri"); break;
			case POTEZA_RDECI: status.setText("Na potezi je rdeci"); break;
			case ZMAGA_MODRI: status.setText("Zmagal je modri"); break;
			case ZMAGA_RDECI: status.setText("Zmagal je rdeci"); break;
			}
		}
		polje.repaint();
		
	}

	public Polje[][] getPlosca() {
		return (igra == null ? null : igra.plosca.matrikaPolj);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nova_igra) {
			nova_igra();
		}
		
	}

	public void odigraj(Poteza p) {
		igra.odigraj_potezo_advanced(p);
		osveziGUI();
		switch (igra.stanje()) {
		case POTEZA_MODRI: strateg_moder.na_potezi(); break;
		case POTEZA_RDECI: strateg_rdec.na_potezi(); break;
		case ZMAGA_MODRI: break;
		case ZMAGA_RDECI: break;
		}
		
	}

	public void klikniPolje(int i, int j) {
		if (igra != null) {
			switch (igra.stanje()) {
			case POTEZA_RDECI:
				strateg_rdec.klik(i, j);
				break;
			case POTEZA_MODRI:
				strateg_moder.klik(i, j);
				break;
			default:
				break;
			}
		}	
		
	}
	
	//TODO: ce izberes v meniju nova igra, potem ne dela dobro prva poteza (pie rule). morala bova resetirati stevec potez
	//TODO: kdaj zmaga ne dela pravilno
	//TODO: vcasih je pocasen, spet problem zgornja vrstica

}
