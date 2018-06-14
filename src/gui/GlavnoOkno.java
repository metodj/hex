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
import javax.swing.JOptionPane;

import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;
import logika.Poteza;

public class GlavnoOkno extends JFrame implements ActionListener{
	
	private IgralnoPolje polje;
	
	private Igra igra;
	
	private JLabel status;
	
	private Strateg strateg_moder;
	
	private Strateg strateg_rdec;
	
	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	private JMenuItem velikostMenu;
	
	public GlavnoOkno() {
		this.setTitle("Hex");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout()); //kaj je tole??
		
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Igra");
		menu_bar.add(igra_menu);
		JMenu nastavitve = new JMenu("Nastavitve");
		menu_bar.add(nastavitve);
		
		velikostMenu = new JMenuItem("Velikost plošèe");
		nastavitve.add(velikostMenu);
		velikostMenu.addActionListener(this);
		
		igraClovekRacunalnik = new JMenuItem("Èlovek – raèunalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("Raèunalnik – èlovek");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);

		igraRacunalnikRacunalnik = new JMenuItem("Raèunalnik – raèunalnik");
		igra_menu.add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);

		igraClovekClovek = new JMenuItem("Èlovek – èlovek");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
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
		
		
		nova_igra(new Racunalnik(this, Igralec.MODRI), 
						new Clovek(this, Igralec.RDECI));
		
		
	}
	
	private void nova_igra(Strateg noviSrategModer, Strateg noviStrategRdec) {
		
		if (strateg_moder != null) { strateg_moder.prekini(); }
		if (strateg_rdec != null) { strateg_rdec.prekini(); }
		this.igra = new Igra(Igralec.RDECI);
		// Ustvarimo nove stratege
		strateg_moder = noviSrategModer;
		strateg_rdec = noviStrategRdec;
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
			case POTEZA_MODRI: status.setText("Na potezi je modri."); break;
			case POTEZA_RDECI: status.setText("Na potezi je rdeèi."); break;
			case ZMAGA_MODRI: status.setText("Zmagal je modri!"); break;
			case ZMAGA_RDECI: status.setText("Zmagal je rdeèi!"); break;
			}
		}
		polje.repaint();
		
	}

	public Polje[][] getPlosca() {
		return (igra == null ? null : igra.plosca.matrikaPolj);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekRacunalnik) {
			if (Plosca.N <= 11) {
			 nova_igra(new Racunalnik(this, Igralec.MODRI),
					new Clovek(this, Igralec.RDECI));
			}
		}
		else if (e.getSource() == igraRacunalnikClovek) {
			if (Plosca.N <= 11) {
			nova_igra( new Clovek(this, Igralec.MODRI),
					new Racunalnik(this, Igralec.RDECI));
			}
		}
		else if (e.getSource() == igraRacunalnikRacunalnik) {
			if (Plosca.N <= 11) {
			nova_igra(new Racunalnik(this, Igralec.MODRI),
					  new Racunalnik(this, Igralec.RDECI));
			}
		}
		else if (e.getSource() == igraClovekClovek) {
			nova_igra(new Clovek(this, Igralec.MODRI),
			          new Clovek(this, Igralec.RDECI));
		} else if(e.getSource() == velikostMenu) {
			String n = JOptionPane.showInputDialog("Vnesi velikost plošèe (igra raèunalnika bo onemogoèena za velikosti veèje od 11):");
			int stevilo = Integer.parseInt(n);
			Plosca.N = stevilo;
			// igralno polje
			this.polje = new IgralnoPolje(this);
			GridBagConstraints polje_layout = new GridBagConstraints(); 
			polje_layout.gridx = 0;
			polje_layout.gridy = 0;
			polje_layout.fill = GridBagConstraints.BOTH;
			polje_layout.weightx = 1.0;
			polje_layout.weighty = 1.0;
			getContentPane().add(polje, polje_layout);
			
			nova_igra(new Clovek(this, Igralec.MODRI),
			          new Clovek(this, Igralec.RDECI));
		}
		
	}

	public void odigraj(Poteza p) {
		igra.odigraj_potezo_advanced(p);
		System.out.println(igra.obstaja_zmagovalna_bridge_pot());
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

	/**
	 * @return kopija trenutne igre
	 */
	public Igra copyIgra() {
		return new Igra(igra);
	}

}
