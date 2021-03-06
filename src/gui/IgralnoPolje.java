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
import logika.Tuple;

public class IgralnoPolje extends JPanel implements MouseListener{
	
	private GlavnoOkno master;
	
	/**
	 * Relativna �irina �rte
	 */
	private final static double LINE_WIDTH = 0.1;
	
	//spravimo kordinate pikslov sredisc sestkotnikov
	private Tuple[][] tabela_centrov;
	
	
	public IgralnoPolje(GlavnoOkno master) {
		super();
		setBackground(Color.white);
		this.master = master;
		this.addMouseListener(this);
		this.tabela_centrov = new Tuple[100][100];
	}
	
	//tole je verjetno zacetna velikost okna/polja
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(900, 600);
	}
	
	private double stranicaSestkotnika() {
		double r_w = getWidth()*0.9 / (Math.sqrt(3)*(Plosca.N + Plosca.N / 2));
		double r_h = getHeight()*0.9 / (Plosca.N + Plosca.N % 2 + Plosca.N / 2);
		double r = Math.min(r_w, r_h);
		return r;
	}
	
	
	
	
	/**
	 * Namen lahko zavzema vrednosti -1,0. Glej if stavke za razumevanje.
	 */
	private int[][] ogliscaSestkotnika(double x, double y, int namen) {
		double r = 0.0;
		if (namen == 0) {
			r = stranicaSestkotnika();
		} else if (namen == -1){
			r = stranicaSestkotnika()*0.8; 
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
		g2.fillPolygon(tocke[0],tocke[1], 6);
	}
	
	private void paintRDECA(Graphics2D g2, double x, double y) {
		int[][] tocke = ogliscaSestkotnika(x, y, -1);
		g2.setColor(Color.red);
		g2.fillPolygon(tocke[0],tocke[1], 6);
	}
	
	
	/**
	 * Zamaknemo kartezi�ni koordinatni sistem.
	 */
	private double[] shift(int x, int y) {
		double r = stranicaSestkotnika();
		double shift_x = r * Math.sqrt(3);
		double shift_y = r * 1.5;
		double shift_row = (y-1) * Math.sqrt(3) * r / 2.0;
		
		double[] koordinati = new double[2];
		koordinati[0] = (getWidth()-(Plosca.N + Plosca.N / 2)*r*Math.sqrt(3))/2.0 + r*Math.sqrt(3)*0.5 + (x-1)*shift_x + shift_row;
		koordinati[1] = getHeight()-(getHeight()-(Plosca.N + Plosca.N % 2 + Plosca.N / 2)*r)/2.0 - r - (y-1)*shift_y; 
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
		
		//risemo sestkotnike.
		for (int y = 1; y <= Plosca.N; y++) {
			for(int x = 1; x <= Plosca.N; x++) {
				double[] tocka = shift(x,y);
				int[][] tocke= ogliscaSestkotnika(tocka[0], tocka[1], 0);
				tabela_centrov[(y-1)][x-1] = new Tuple(round(tocka[0]),round(tocka[1]));
				
				g2.drawPolygon(tocke[0], tocke[1], 6); 
			}
			
		}
		
		//barvamo rob
		for(int x = 1; x <= Plosca.N; x++) {
			double[] tocka_spodaj = shift(x,1);
			double[] tocka_zgoraj = shift(x,Plosca.N);
			int[][] ogl2= ogliscaSestkotnika(tocka_spodaj[0], tocka_spodaj[1], 0);
			int[][] ogl1= ogliscaSestkotnika(tocka_zgoraj[0], tocka_zgoraj[1], 0);
			g2.setColor(Color.blue);
			g2.drawLine(ogl1[0][2], ogl1[1][2], ogl1[0][3], ogl1[1][3]);
			g2.drawLine(ogl1[0][3], ogl1[1][3], ogl1[0][4], ogl1[1][4]);
			g2.drawLine(ogl2[0][5], ogl2[1][5], ogl2[0][0], ogl2[1][0]);
			g2.drawLine(ogl2[0][0], ogl2[1][0], ogl2[0][1], ogl2[1][1]);
		}
		
		for(int y = 1; y <= Plosca.N; y++) {
			double[] tocka_spodaj = shift(1,y);
			double[] tocka_zgoraj = shift(Plosca.N,y);
			int[][] ogl2= ogliscaSestkotnika(tocka_spodaj[0], tocka_spodaj[1], 0);
			int[][] ogl1= ogliscaSestkotnika(tocka_zgoraj[0], tocka_zgoraj[1], 0);
			g2.setColor(Color.red);
			g2.drawLine(ogl1[0][0], ogl1[1][0], ogl1[0][1], ogl1[1][1]);
			g2.drawLine(ogl1[0][2], ogl1[1][2], ogl1[0][1], ogl1[1][1]);
			g2.drawLine(ogl2[0][3], ogl2[1][3], ogl2[0][4], ogl2[1][4]);
			g2.drawLine(ogl2[0][5], ogl2[1][5], ogl2[0][4], ogl2[1][4]);
		}
		
		
		// barvamo modre in rdece
		Polje[][] plosca = master.getPlosca();
		if (plosca != null) {
			for (int y = 1; y <= Plosca.N; y++) {
				for (int x = 1; x <= Plosca.N; x++) {
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
	
	public int evklidska(Tuple a, Tuple b) {
		return (a.getX()- b.getX())*(a.getX()- b.getX()) + (a.getY()- b.getY())*(a.getY()- b.getY());
	}
		
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int najmanjsa_razdalja = Math.max(getHeight(), getWidth())*Math.max(getHeight(), getWidth());
		int izbran_x = 0;
		int izbran_y = 0;
		for (int i= 1; i <= Plosca.N; i ++) {
			for (int j = 1; j <= Plosca.N; j ++) {
				if (evklidska(tabela_centrov[i-1][j-1], new Tuple(x,y)) < najmanjsa_razdalja ) {
					izbran_x = j;
					izbran_y = i;
					najmanjsa_razdalja = evklidska(tabela_centrov[i-1][j-1], new Tuple(x,y));
				}
			}
		}
		//hint: ocrtana kroznica
		if (Math.sqrt(najmanjsa_razdalja) < stranicaSestkotnika()) {
			master.klikniPolje(izbran_x,izbran_y);
			
		}
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
