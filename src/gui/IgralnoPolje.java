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

public class IgralnoPolje extends JPanel implements MouseListener{
	
	private GlavnoOkno master;
	
	/**
	 * Relativna širina èrte
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
		return new Dimension(600, 600);
	}
	
	private double stranicaSestkotnika() {
		return Math.min(getWidth()-100, getHeight()-100) / (Plosca.N + (Plosca.N-1)/2); 
		//zashiftamo 10krat, vedno za 'pol' seskotnika. Od tu pride (Plosca.N-1)/2
		// -100 pa zato, da je na zacetku cela plosca na zaslonu
	}
	
	//ni lepo, ampak dela lol
	private int[][] ogliscaSestkotnika(double x, double y) {
		double r = stranicaSestkotnika();
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
	
	@Override
	protected void paintComponent(Graphics g) {
		//uvodna magija
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		double r = stranicaSestkotnika();
		// èrte
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke((float) (r * LINE_WIDTH)));
		
		//risemo sestkotnike. Kot je naprogramirano sedaj, se bodo robovi verjetno zadeli v 'navbar' in ostale
		//elemente, zato je potrebno dodati lepotni popravek. Glej vlogo LINE_WIDTH v Bauerjevi for zanki pri isti metodi
		
		double shift_x = r * Math.sqrt(3);
		double shift_y = r * 1.5;
		
		for (int y = 1; y <= Plosca.N; y++) {
			double y_2 = 600 - (y-1)*shift_y; //PAZI: tale 600 je od dimension, glej metodo getPrefferedSize
			double shift_row = (y-1) * Math.sqrt(3) * r / 2.0;
			for(int x = 1; x <= Plosca.N; x++) {
				int[][] tocke= ogliscaSestkotnika(50 + (x-1)*shift_x + shift_row, y_2);
				//tole je fuul SHADY!!! ker smo double v funkciji ogliscaSestkotnika spremenili v int.
				//funkcija drawPolygon je namrec zahtevala int!
				g2.drawPolygon(tocke[0], tocke[1], 6); 
			}
			
		}
		
		// barvamo modre in rdece
		
		int[][]test = ogliscaSestkotnika(50 , 600);
		g2.fillPolygon(new Polygon(test[0], test[1], 6));
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
