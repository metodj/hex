package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import logika.Igra;
import logika.Plosca;
import logika.Polje;

public class IgralnoPolje extends JPanel implements MouseListener{
	
	private GlavnoOkno master;
	
	/**
	 * Relativna �irina �rte
	 */
	private final static double LINE_WIDTH = 0.1;
	
	
	public IgralnoPolje(GlavnoOkno master) {
		super();
		setBackground(Color.white);
		this.master = master;
		this.addMouseListener(this);
	}
	
	//tole je verjetno zacetna velikost okna/polja
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(900, 600);
	}
	
	private double stranicaSestkotnika() {
		return (Math.min(getHeight(), (11.0/16.0)*getWidth())-100) / (Plosca.N + (Plosca.N-1)/2); 
		//zashiftamo 10krat, vedno za 'pol' seskotnika. Od tu pride (Plosca.N-1)/2
		// -100 pa zato, da je na zacetku cela plosca na zaslonu
		//(Math.min(getHeight(), (11.0/16.0)*getWidth())-100) tole nevem ce je cist dobr. Na zacetku blo samo getHeight()
		//tale stvar se uporablja tud v metodi koordinati()
	}
	
	//ni lepo, ampak dela lol
	//namen lahko zavzema vrednosti -1,0,1. Glej if stavke za razumvanje
	private int[][] ogliscaSestkotnika(double x, double y, int namen) {
		double r = 0.0;
		if (namen == 0) {
			r = stranicaSestkotnika();
		} else if (namen == 1) {
			r = stranicaSestkotnika() + 40*LINE_WIDTH;
		} else if (namen == -1){
			r = stranicaSestkotnika() - getHeight()*0.1*LINE_WIDTH; //40* zato da je mal belega prostora med robom in pobarvanim poljem
		}
		int[][] tmp = new int[2][6]; //prva vrsta so x-i, drugi vrsta so y-i
		tmp[0][0] = round(x);
		tmp[1][0] = round(y + r);
		tmp[0][3] = round(x);
		tmp[1][3] = round(y - r);
		tmp[0][1] = round(x + Math.cos(Math.PI/6)*r);
		tmp[1][1] = round(y + Math.sin(Math.PI/6)*r);
		tmp[0][4] = round(x - Math.cos(Math.PI/6)*r);
		tmp[1][4] = round(y - Math.sin(Math.PI/6)*r);
		tmp[0][2] = round(x + Math.cos(Math.PI/6)*r);
		tmp[1][2] = round(y - Math.sin(Math.PI/6)*r);
		tmp[0][5] = round(x - Math.cos(Math.PI/6)*r);
		tmp[1][5] = round(y + Math.sin(Math.PI/6)*r);
		return tmp;
	}
	
	//da v zgornji metodi lahko double spremenimo v int
	public static int round(double x) {
			return (int)(x + 0.5);
		}
		
	private void paintMODRA(Graphics2D g2, double x, double y) {
		int[][] tocke = ogliscaSestkotnika(x, y, -1);
		g2.setColor(Color.blue);
		//g2.setStroke(new BasicStroke((float) (stranicaSestkotnika() * LINE_WIDTH)));
		g2.fillPolygon(tocke[0],tocke[1], 6);
	}
	
	private void paintRDECA(Graphics2D g2, double x, double y) {
		int[][] tocke = ogliscaSestkotnika(x, y, -1);
		g2.setColor(Color.red);
		//g2.setStroke(new BasicStroke((float) (stranicaSestkotnika() * LINE_WIDTH)));
		g2.fillPolygon(tocke[0],tocke[1], 6);
	}
	
	private double[] shift(int x, int y) {
		double r = stranicaSestkotnika();
		double shift_x = r * Math.sqrt(3);
		double shift_y = r * 1.5;
		double shift_row = (y-1) * Math.sqrt(3) * r / 2.0;
		
		
		double[] koordinati = new double[2];
		koordinati[0] = Math.min(getHeight(), (11.0/16.0)*getWidth())*0.07 + (x-1)*shift_x + shift_row;//*0.07, da se ne zabijemo v levi rob okna
		koordinati[1] = Math.min(getHeight(), (11.0/16.0)*getWidth())*0.915 - (y-1)*shift_y; //*0.915, da se spodnji rob mreze ne zabije v spodnji rob okna
		return koordinati;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//uvodna magija
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// �rte
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke((float) (stranicaSestkotnika() * LINE_WIDTH)));
		
		//risemo sestkotnike. Kot je naprogramirano sedaj, se bodo robovi verjetno zadeli v 'navbar' in ostale
		//elemente, zato je potrebno dodati lepotni popravek. Glej vlogo LINE_WIDTH v Bauerjevi for zanki pri isti metodi
		for (int y = 1; y <= Plosca.N; y++) {
			for(int x = 1; x <= Plosca.N; x++) {
				double[] tocka = shift(x,y);
				int[][] tocke= ogliscaSestkotnika(tocka[0], tocka[1], 0);
				//tole je fuul SHADY!!! ker smo double v funkciji ogliscaSestkotnika spremenili v int.
				//funkcija drawPolygon je namrec zahtevala int!
				g2.drawPolygon(tocke[0], tocke[1], 6); 
			}
			
		}
		
		//barvamo rob
		
		for(int x = 1; x <= Plosca.N; x++) {
			double[] tocka_spodaj = shift(x,1);
			double[] tocka_zgoraj = shift(x,11);
			int[][] ogl2= ogliscaSestkotnika(tocka_spodaj[0], tocka_spodaj[1], 1);
			int[][] ogl1= ogliscaSestkotnika(tocka_zgoraj[0], tocka_zgoraj[1], 1);
			g2.setColor(Color.blue);
			g2.drawLine(ogl1[0][2], ogl1[1][2], ogl1[0][3], ogl1[1][3]);
			g2.drawLine(ogl1[0][3], ogl1[1][3], ogl1[0][4], ogl1[1][4]);
			g2.drawLine(ogl2[0][5], ogl2[1][5], ogl2[0][0], ogl2[1][0]);
			g2.drawLine(ogl2[0][0], ogl2[1][0], ogl2[0][1], ogl2[1][1]);
		}
		
		for(int y = 1; y <= Plosca.N; y++) {
			double[] tocka_spodaj = shift(1,y);
			double[] tocka_zgoraj = shift(11,y);
			int[][] ogl2= ogliscaSestkotnika(tocka_spodaj[0], tocka_spodaj[1], 1);
			int[][] ogl1= ogliscaSestkotnika(tocka_zgoraj[0], tocka_zgoraj[1], 1);
			g2.setColor(Color.red);
			g2.drawLine(ogl1[0][0], ogl1[1][0], ogl1[0][1], ogl1[1][1]);
			g2.drawLine(ogl1[0][2], ogl1[1][2], ogl1[0][1], ogl1[1][1]);
			g2.drawLine(ogl2[0][3], ogl2[1][3], ogl2[0][4], ogl2[1][4]);
			g2.drawLine(ogl2[0][5], ogl2[1][5], ogl2[0][4], ogl2[1][4]);
		}
		
		
		// barvamo modre in rdece
		Polje[][] plosca = master.getPlosca();
		if (plosca != null) {
			for (int y = 1; y < Plosca.N; y++) {
				for (int x = 1; x < Plosca.N; x++) {
					double[] tocka = shift(x,y);
					switch(plosca[y][x]) {
					case MODRO: paintMODRA(g2, tocka[0], tocka[1]); break;
					case RDECE: paintRDECA(g2, tocka[0], tocka[1]); break;
					default: break;
					}
				}
			}
		}
		
	}
		

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
